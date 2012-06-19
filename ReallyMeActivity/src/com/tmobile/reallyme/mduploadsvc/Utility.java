package com.tmobile.reallyme.mduploadsvc;

import java.util.Date;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.util.Log;

public class Utility {
	SimpleDateFormat dateFormat = null;
	static String logmsg = "";
	String TAG = "HN";
	static Utility utility = null;
	public static Context context = null;

	public Utility() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa z");
	}

	public static Utility getInstance() {
		if (utility == null) {
			// Log.v("SmartMeter", "UtilityObj creation");
			utility = new Utility();
		}
		return utility;
	}

	public String getFormat(long date) {
		return dateFormat.format(date);
	}

	public String getDateFormat(Date date) {
		return dateFormat.format(date);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void writetoFile(String tag, String message, String msgtype) {
		try {
			try {

				FileOutputStream fOut = this.getContext().openFileOutput(
						"HereNowLog.txt", Context.MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut);
				osw.write(logmsg);
				osw.flush();
				osw.close();
			} catch (Exception e) {
				// Log.v(TAG, "Exception in FileWrite-------->" +
				// e.getMessage());
			}

		} catch (Exception e) {
			writetoFile(TAG, "Exception in write to file----->"
					+ e.getMessage(), "VERBOSE");
		}

	}
}
