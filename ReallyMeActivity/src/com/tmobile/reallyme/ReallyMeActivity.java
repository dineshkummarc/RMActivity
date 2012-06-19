package com.tmobile.reallyme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.AuthenticationManager;
import com.tmobile.reallyme.core.api.remote.RegisterManager;
import com.tmobile.reallyme.core.api.remote.pojo.Channel;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.api.remote.pojo.Item;
import com.tmobile.reallyme.core.api.remote.pojo.Profile;
import com.tmobile.reallyme.core.config.TMobileContextProvider;
import com.tmobile.reallyme.core.job.JobsManager;
import com.tmobile.reallyme.core.resources.UserSessionResource;
import com.tmobile.reallyme.mduploadsvc.DBHelper;
import com.tmobile.reallyme.utils.Utils;

public class ReallyMeActivity extends Activity {
	private String TAG = "RMA";
	private EditText phone = null;
	private EditText email = null;
	private Button ok = null;
	private static final int DIALOG_KEY = 0;
	private Context context = null;

	// Location related variables
	private LocationManager locationMgr = null;
	private LocationFinder finder = null;
	public static double lat = 45.8768494;
	public static double longi = 98.487458589;
	public static String pCid = "";
	public static Boolean verified;
	public static String metaDataApiURL = "http://demo.tmobile.amsoftsystems.com/api/v1/uploadMetadata?cid=%2b";
	public static String phoneBookApiURL = "http://66.240.231.95/HereNowServices/v1/uploadAddressBook";

	private static String SERVICE_CLASS_NAME = "com.tmobile.reallyme.BackgroundService";
	private boolean isServiceRunning = false;

	private Handler uiHandler = new Handler();

	public Runnable updateUIThread = new Runnable() {
		public void run() {
			showStatusMessage();
		}
	};

	public void showStatusMessage() {
		//Toast.makeText(context, "Here & Now service started! = " + pCid,Toast.LENGTH_LONG).show();
	}

	/**
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		TMobileContextProvider.contentResolver = getContentResolver();
		UserSession userSession = UserSessionResource.load();
		getMSISDNValues();
	    if (Utils.isNotBlank(userSession.getClientId())) {
			JobsManager.getInstance().start();
			startActivity(new Intent(this, PageEnum.MAIN.getClazz()));
		}
		// Inflate our UI from its XML layout description.
		setContentView(R.layout.start_screen);
		phone = ((EditText) findViewById(R.id.phone));
		email = ((EditText) findViewById(R.id.email));
		// Hook up button presses to the appropriate event handler.
		ok = ((Button) findViewById(R.id.button_ok));
		ok.setOnClickListener(bOkListener);
		((Button) findViewById(R.id.button_cancel))
				.setOnClickListener(mBackListener);

		finder = new LocationFinder();
		locationMgr = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		startBackgroundProcessing();
	}

	/**
	 * Called when the activity is about to start interacting with the user.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				finder);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//locationMgr.removeUpdates(finder);
	}

	/**
	 * A call-back for when the user presses the back button.
	 */
	OnClickListener mBackListener = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	OnClickListener bOkListener = new OnClickListener() {
		public void onClick(final View v) {
			showDialog(DIALOG_KEY);
			if (validate()) {
				if (!isInstalled()) {
					RegisterManager registerManager = new RegisterManager() {
						@Override
						public void endHook() {
							if (Utils.isNotBlank(UserSession.getSessionid())) {
								new AuthenticationManager().onEndDocument();
							} else {
								log.info("Do plain Authentication.");
								new AuthenticationManager().login(UserSession
										.getClientId(), UserSession
										.getPassword());
							}
							removeDialog(DIALOG_KEY);
							PageManager.openPage(v, PageEnum.MAIN);
						}

						@Override
						public void errorAction() {
							removeDialog(DIALOG_KEY);
							Toast.makeText(ReallyMeActivity.this,
									R.string.error_login1_does_not_exist,
									Toast.LENGTH_LONG).show();
						}
					};
					Identity identity = new Identity();
					List<Channel> channels = new ArrayList();
					channels.add(new Channel(Channel.Mobile_Phone, null, phone
							.getText().toString(), true));
					// channels.add(new Channel(Channel.Mobile_Phone, null,
					// phoneNumber.getText().toString(), true));
					identity.setChannels(channels);
					List<Item> items = new ArrayList();
					items.add(new Item("firstName", ""));
					items.add(new Item("lastName", "", true));
					identity.setProfile(new Profile(items));
					registerManager.register(identity);
				} else {
					PageManager.openPage(v, PageEnum.MAIN);
				}
			}
		}
	};

	private Boolean isInstalled() {
		return Utils.isNotBlank(UserSession.getClientId());
	}

	private Boolean emailValidation() {
		// Set the email pattern string
		Pattern p = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$");
		// Match the given string with the pattern
		Matcher m = p.matcher(email.getText().toString());
		// check whether match is found
		boolean matchFound = m.matches();

		if (matchFound) {
			return true;
		} else {
			removeDialog(DIALOG_KEY);
			Toast.makeText(this, R.string.error_login1_credentials,
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	private Boolean phoneValidation() {
		// Set the email pattern string
		// Pattern p = Pattern.compile("^(?:(?:[\\+]?([\\d]{1,3}(?:[
		// ]+|[\\-.])))?[(]?([\\d]{3})[\\-/)]?(?:[ ]+)?)?([a-zA-Z2-9][a-zA-Z0-9
		// \\-.]{6,})(?:(?:[ ]+|[xX]|(i:ext[\\.]?)){1,2}([\\d]{1,5}))?$");
		Pattern p = Pattern
				.compile("(\\({0,1}\\+{0,1}\\d{0,1}\\){0,1} {0,1})?(\\d{3} {0,1})?\\d{3} {0,1}\\d{1,10}");
		// Match the given string with the pattern
		Matcher m = p.matcher(phone.getText().toString());
		// check whether match is found
		boolean matchFound = m.matches();

		if (matchFound) {
			return true;
		} else {
			removeDialog(DIALOG_KEY);
			Toast
					.makeText(this, R.string.error_login1_phone,
							Toast.LENGTH_LONG).show();
			return false;
		}
	}

	private boolean validate() {
		if (phoneValidation()) {
			if (!Utils.isNotBlank(email.getText().toString())
					|| emailValidation()) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("Please wait while loading...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}

	private void startBackgroundProcessing() {
		isServiceRunning = getServiceStatus();
		if(!isServiceRunning) {
			Thread processThread = new Thread() {
				public void run() {
					while (true) {
						getMSISDNValues();
						
						Log.v("HN", "startBackgroundProcessing : run :: pCid     		===> " + pCid);
						Log.v("HN", "startBackgroundProcessing : run :: verified 		===> " + verified);
						//Log.d(TAG, "startBackgroundProcessin9g : run :: isServiceRunning ===> " + isServiceRunning);

						boolean isSuccess = false;
						try {
							if (pCid != null && verified != null && pCid.length() > 0 && verified) {
								startService(new Intent(ReallyMeActivity.this, BackgroundService.class));
								isSuccess = true;
								Log.v("HN", "startBackgroundProcessing : run :: Background service started");
								uiHandler.post(updateUIThread);
							}
						}
						catch (Exception e) {
							//Log.e(TAG, "startBackgroundProcessing : run :: Background service could not be started");
						}
						finally {
							if(isSuccess)
								break;
							else {
								try {
									//Poll again after 2 minutes if pCid is not available
									//Log.e(TAG, "startBackgroundProcessing : run :: finally ==> waiting for pCid to get updated");
									Thread.sleep(30000);
								} catch (Exception e) {
									//Log.e(TAG, "startBackgroundProcessing : run :: Exception while sleeping thread ===> " + e.getMessage());
								}
							}
						}
					}
				}
			};

			processThread.start();
		}
	}

	private boolean getServiceStatus() {
		ActivityManager mgr = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningSer = mgr
				.getRunningServices(20);

		if (runningSer.size() > 0) {
			for (ActivityManager.RunningServiceInfo info : runningSer) {
				String pkg = info.service.getPackageName();
				String serviceName = info.service.getClassName();
				boolean isRunning = info.started;
				//Log.d("TEST", "getServiceStatus :: Service pkg ===> " + pkg + " *** Running State ===> " + isRunning);
				//Log.d(TAG, "getServiceStatus :: Class Name ===> " + serviceName + " *** RunningState ===> " + isRunning);

				if ((serviceName.equals(SERVICE_CLASS_NAME)) && (isRunning)) {
					return true;
				}
			}
		}

		return false;
	}
	
	private void getMSISDNValues(){
		Map<String,Boolean> sessionValues = UserSession.getMSISDN();
		Set set = sessionValues.keySet();
		Iterator<String> iterator = set.iterator();
		
		while (iterator.hasNext()) {
			pCid = iterator.next();
			verified = sessionValues.get(pCid);
		}
	}

	public void updateLocationChange(Location location) {
		try {
			if (location == null) {
				return;
			}

			lat = location.getLatitude();
			longi = location.getLongitude();

			Log.v("HN", "Latitude ===> " + lat);
			Log.v("HN", "Longitude ===> " + longi);
		} catch (Exception e) {
			// Log.v("HN", "=== updateLocationChange ===");
		}
	}

	private class LocationFinder implements LocationListener {

		public void onLocationChanged(Location location) {
			updateLocationChange(location);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

}
