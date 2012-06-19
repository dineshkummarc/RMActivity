package com.tmobile.reallyme;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Contacts;
import android.util.Log;

import com.tmobile.reallyme.mduploadsvc.CallManager;
import com.tmobile.reallyme.mduploadsvc.DBHelper;
import com.tmobile.reallyme.mduploadsvc.PhoneBookManager;
import com.tmobile.reallyme.mduploadsvc.SmsLogManager;
import com.tmobile.reallyme.mduploadsvc.HttpCommunication;
import com.tmobile.reallyme.mduploadsvc.Utility;

public class BackgroundService extends Service {
	private String TAG = "HN";

	static Context context = null;
	private DBHelper dbHelper = null;
	HttpCommunication httpcomm = null;
	String xmldata = "";
	ContactsObserver contactObserver;
	SmsObserver smsObserver;
	CallObserver callObserver;
	String phoneDetails = "";
	private int noOfContacts = 0;
	private String contactXml = "";

	static BackgroundService obj = null;

	class ContactsObserver extends ContentObserver {
		public ContactsObserver(Handler h) {
			super(h);
		}

		public void onChange(boolean selfChange) {
			contactXml = "";
			//Log.d(TAG, "ContactsObserver :: ONCHANGE notification received");
			contactXml = PhoneBookManager.getInstance()
					.getFormattedContactXML();
			Thread contactPostThread = new Thread() {
				public void run() {
					// No change
					uploadPhoneBook(contactXml);
				}
			};
			contactPostThread.start();
			super.onChange(selfChange);
		}
	}

	class SmsObserver extends ContentObserver {
		public SmsObserver(Handler h) {
			super(h);
		}

		public void onChange(boolean selfChange) {
			//Log.d(TAG, "SmsObserver :: ONCHANGE notification received");
			Thread smsPostThread = new Thread() {
				public void run() {
					String smsDetails = SmsLogManager.getInstance()
							.processData();
					sendMetaData(smsDetails);
				}
			};
			smsPostThread.start();
			super.onChange(selfChange);
		}
	}

	class CallObserver extends ContentObserver {
		public CallObserver(Handler h) {
			super(h);
		}

		public void onChange(boolean selfChange) {
			//Log.d(TAG, "CallObserver :: ONCHANGE notification received");
			Thread callsPostThread = new Thread() {
				public void run() {
					String callDetails = CallManager.getInstance()
							.processData();
					sendMetaData(callDetails);
				}
			};
			callsPostThread.start();
			super.onChange(selfChange);
		}
	}

	private void registerContentObservers() {
		ContentResolver cr = getContentResolver();
		Handler handler = new Handler();
		contactObserver = new ContactsObserver(handler);
		smsObserver = new SmsObserver(handler);
		callObserver = new CallObserver(handler);
		cr.registerContentObserver(Contacts.People.CONTENT_URI, true,
				contactObserver);
		cr.registerContentObserver(android.provider.CallLog.Calls.CONTENT_URI,
				true, callObserver);
		cr.registerContentObserver(Uri.parse("content://sms"), true,
				smsObserver);
	}

	public void onCreate() {
		context = this.getApplicationContext();
		dbHelper = new DBHelper(context);

		httpcomm = new HttpCommunication();
		Utility.getInstance().setContext(context);
		CallManager.getInstance().setContext(context);
		SmsLogManager.getInstance().setContext(context);
		PhoneBookManager.getInstance().setContext(context);
		noOfContacts = PhoneBookManager.getInstance().getNoOfContact();
		registerContentObservers();

		// initial posting
		Thread initialPostThread = new Thread() {
			public void run() {
				String smsDetails = SmsLogManager.getInstance().processData();
				sendMetaData(smsDetails);

				String callDetails = CallManager.getInstance().processData();
				sendMetaData(callDetails);
			}
		};
		initialPostThread.start();
	}

	public static BackgroundService getInstance() {
		if (obj == null) {
			obj = new BackgroundService();
		}
		return obj;
	}

	public void sendMetaData(String data) {

		xmldata = data;

		if (xmldata != "") {
			xmldata = "<md>" + xmldata + "</md>";
		} else {
			return; // nothing to send
		}

		boolean returnFlag = false;
		try {
			//Log.d(TAG, "sendMetaData :: ************************************************************************");
			//Log.d(TAG, "sendMetaData :: XML Data ===> \n " + xmldata);
			//Log.d(TAG, "sendMetaData :: ************************************************************************");
			returnFlag = httpcomm.sendMetaData(xmldata);
			if (returnFlag) {
				//Log.d(TAG, "sendMetaData :: ****************** Posting Success ******************");
			} else {
				//Log.d(TAG, "sendMetaData :: ****************** Posting Failed ******************");
			}
		} catch (Exception e) {
			//Log.d(TAG, "sendMetaData :: Exception while posting data ===> " + e.getMessage());
		}
	}

	public void uploadPhoneBook(String data) {

		xmldata = data;

		if (xmldata != "") {
			xmldata = "pid=" + ReallyMeActivity.pCid + "&Data=" + data;
		} else {
			return;
		}

		boolean returnFlag = false;
		try {
			returnFlag = httpcomm.uploadPhoneBook(xmldata);
			if (returnFlag) {
				//Log.d(TAG, "uploadPhoneBook :: ****************** Posting Success ******************");
			} else {
				//Log.d(TAG, "uploadPhoneBook :: ****************** Posting Failed ******************");
			}
		} catch (Exception e) {
			//Log.d(TAG, "uploadPhoneBook :: Exception while posting data ===> " + e.getMessage());
		}
	}

	public void onDestroy() {
		try {
			BackgroundService.this.stopSelf();
			super.onDestroy();

		} catch (Exception e) {

		}
	}

	public IBinder onBind(Intent intent) {
		return nbinder;

	}

	private final IBinder nbinder = new Binder() {
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};
}
