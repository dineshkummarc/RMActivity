package com.tmobile.reallyme.mduploadsvc;

import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import java.util.Date;
import android.content.ContentValues;

import com.tmobile.reallyme.ReallyMeActivity;

public class CallManager implements iPropertyManager {
	static Context callcontext = null;
	String[] colName;
	StringBuffer sb;
	String STR_NEWLINE = "\r\n";
	String TAG = "HN";
	static CallManager callmgr = null;
	int numberColumn = 0;
	int dateColumn = 0;
	int typeColumn = 0;
	int duration = 0;
	int lastcallid = 0;
	int callId = 0;
	String calls = "";
	int callrows = 0;
	int rows = 0;
	long callDate = 0;

	private CallManager() {

	}

	public void setContext(Context context) {
		this.callcontext = context;
	}

	public static CallManager getInstance() {
		if (callmgr == null) {
			callmgr = new CallManager();
		}
		return callmgr;
	}

	public String processData() {
		String callLog = "";
		try {
			callLog = getCallLog();
		} catch (Exception e) {

		}
		
		return callLog;
	}

	public String getCallLog() {
		String data = "";
		long lastcalldate = 0;
		callDate = 0;
		Cursor cursor = null;
		ContentValues values = new ContentValues(2);
		try {
			Cursor cur = DBHelper.getDatabaseObj().query("Calls", null, null,
					null, null, null, null);
			int idcol = cur.getColumnIndex("lastcalldate");
			rows = cur.getCount();
			if (rows == 0) {

				try {
					cursor = this.callcontext.getContentResolver().query(
							android.provider.CallLog.Calls.CONTENT_URI, null,
							null, null, "date");
				} catch (Exception e) {

				}

			} else {
				cur.moveToNext();
				if (cur.isFirst()) {
					lastcalldate = cur.getLong(idcol);
				}
				try {
					cursor = this.callcontext.getContentResolver().query(
							android.provider.CallLog.Calls.CONTENT_URI, null,
							"date >" + lastcalldate, null, "date");
				} catch (Exception e) {

				}
				cur.close();
			}

			data = formatCallLog(cursor);
			cursor.close();
		
			if(callDate > 0){
				values.put("lastcalldate", callDate);
			}
			

			if (rows == 0) {
				DBHelper.getDatabaseObj().insert("Calls", null, values);
			} else {
				DBHelper.getDatabaseObj().update("Calls", values, null, null);
			}

		} catch (Exception e) {
			 //Log.v(TAG,"Exception in getCallUpdate"+e.getMessage());
		}
		return data;
	}

	public void clearData() {
		calls = "";
	}

	public String formatCallLog(Cursor dbCur) {
		calls = "";
		numberColumn = dbCur
				.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
		dateColumn = dbCur.getColumnIndex(android.provider.CallLog.Calls.DATE);
		typeColumn = dbCur.getColumnIndex(android.provider.CallLog.Calls.TYPE);
		duration = dbCur
				.getColumnIndex(android.provider.CallLog.Calls.DURATION);
		dbCur.moveToNext();
		if (dbCur.moveToFirst()) {
			do {
				String callerPhoneNumber = dbCur.getString(numberColumn);
				long calldate = dbCur.getLong(dateColumn);
				Date dateFormat = new Date(calldate);
				String callsDate = Utility.getInstance().getDateFormat(
						dateFormat);
				int callType = dbCur.getInt(typeColumn);
				int dur = dbCur.getInt(duration);
				
				callDate = calldate;
				calls = calls + "<mdItem><item name=\"mdType\" value=\"TX\"/>"
						+ "<item name=\"commType\" value=\"1\"/>"
						+ "<item name=\"pCid\" value=\""
						+ ReallyMeActivity.pCid + "\"/>" + STR_NEWLINE;
				calls = calls + "<item name=\"ch\" value=\"3\"/>" + STR_NEWLINE;
				calls = calls + "<item name=\"lat\" value=\""
						+ ReallyMeActivity.lat + "\"/>" + STR_NEWLINE;
				calls = calls + "<item name=\"long\" value=\""
						+ ReallyMeActivity.longi + "\"/>" + STR_NEWLINE;

				calls = calls + "<item name=\"oCid\" value=\""
						+ callerPhoneNumber + "\"/>" + STR_NEWLINE;
				calls = calls + "<item name=\"dt\" value=\"" + callsDate
						+ "\"/>" + STR_NEWLINE;
				calls = calls + "<item name=\"duration\" value=\"" + dur
						+ "\"/>" + STR_NEWLINE;
				printLog("Caller No --->"+callerPhoneNumber + "callsDate ----->"+callsDate);
				switch (callType) {
				case android.provider.CallLog.Calls.INCOMING_TYPE:
					calls = calls + "<item name=\"action\" value=\"3\"/>"
							+ STR_NEWLINE;
					break;
				case android.provider.CallLog.Calls.OUTGOING_TYPE:
					calls = calls + "<item name=\"action\" value=\"3\"/>"
							+ STR_NEWLINE;
					break;
				case android.provider.CallLog.Calls.MISSED_TYPE:
					calls = calls + "<item name=\"action\" value=\"5\"/>"
							+ STR_NEWLINE;
					break;
				}
				calls = calls + "</mdItem>";
			} while (dbCur.moveToNext());
		}
		dbCur.close();
		 //Log.d(TAG,"CallXML is ===>"+calls);
		return calls;
	}

	private void printLog(String msg) {
		// Log.d(TAG,"CALL ::"+msg);
	}

}
