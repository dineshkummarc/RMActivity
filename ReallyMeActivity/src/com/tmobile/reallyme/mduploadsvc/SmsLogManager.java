package com.tmobile.reallyme.mduploadsvc;

import android.util.Log;
import android.database.Cursor;
import android.content.ContentValues;
import android.net.Uri;
import java.util.Date;
import android.content.Context;

import com.tmobile.reallyme.ReallyMeActivity;

public class SmsLogManager implements iPropertyManager {
	static Context smscontext = null;
	Cursor smscursor;
	String TAG = "HN";
	String[] columns;
	String STR_NEWLINE = "\r\n";
	String smsinfo = "";
	int sentSmsID = 0;
	int receivedSmsID = 0;
	int smsrows = 0;
	int typeColumn = 0;
	int dateColumn = 0;
	int addressColumn = 0;
	int idcolumn = 0;
	int rows = 0;
	long smsDate = 0;
	static SmsLogManager smsmgr = null;
	ContentValues values = null;

	private SmsLogManager() {

	}

	public void setContext(Context ctx) {
		this.smscontext = ctx;
	}

	public static SmsLogManager getInstance() {
		if (smsmgr == null) {
			smsmgr = new SmsLogManager();
		}
		return smsmgr;
	}

	public String processData() {
		ContentValues values = null;
		int lastreceivedsms_ID = 0;
		int lastsmssent_ID = 0;
		smsinfo = "";
		try {
			Cursor dbcur = DBHelper.getDatabaseObj().query("Sms", null, null,
					null, null, null, null);
			int sent_idcol = dbcur.getColumnIndex("lastsmssent_id");
			int received_idCol = dbcur.getColumnIndex("lastsmsreceived_id");

			int rows = dbcur.getCount();
			String sent_selection = "";
			String received_selection = "";
			if (rows == 0) {
				sent_selection = null;
				received_selection = null;
			} else {
				dbcur.moveToNext();
				if (dbcur.isFirst()) {
					sentSmsID = dbcur.getInt(sent_idcol);
					receivedSmsID = dbcur.getInt(received_idCol);
					// Log.v("HN","last sent id is: " + sentSmsID );
					// Log.v("HN","last received id is: " + receivedSmsID );
					dbcur.close();
				}
				sent_selection = "_id >" + sentSmsID;
				received_selection = "_id >" + receivedSmsID;

			}
			dbcur.close();
			try {
				smscursor = smscontext.getContentResolver().query(
						Uri.parse("content://sms/inbox"), null,
						received_selection, null, "_id");

			} catch (Exception e) {
				// Log.d(TAG, "Exception in Receiving SmsTable"+e.getMessage());
			}

			typeColumn = smscursor.getColumnIndex("type");
			dateColumn = smscursor.getColumnIndex("date");
			addressColumn = smscursor.getColumnIndex("address");
			idcolumn = smscursor.getColumnIndex("_id");
			if (smscursor.moveToFirst()) {
				do {

					int type = smscursor.getInt(typeColumn);
					long date = smscursor.getLong(dateColumn);
					String address = smscursor.getString(addressColumn);
					String phoneNo = formatNo(address);
					int id = smscursor.getInt(idcolumn);
					Date smsdate = new Date(date);
					lastreceivedsms_ID = smscursor.getInt(idcolumn);
					printSmsLog("Sms Sender --->"+phoneNo + "smsDate --->"+smsdate);
					smsinfo = smsinfo
							+ "<mdItem><item name=\"mdType\" value=\"TX\"/>"
							+ "<item name=\"commType\" value=\"1\"/>"
							+ "<item name=\"pCid\" value=\""
							+ ReallyMeActivity.pCid + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"ch\" value=\"8\"/>"
							+ STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"lat\" value=\""
							+ ReallyMeActivity.lat + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"long\" value=\""
							+ ReallyMeActivity.longi + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"action\" value=\"1\"/>"
							+ STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"oCid\" value=\""
							+ phoneNo + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"dt\" value=\""
							+ Utility.getInstance().getDateFormat(smsdate)
							+ "\"/>" + STR_NEWLINE;
					smsinfo += "</mdItem>";

				} while (smscursor.moveToNext());

				smscursor.close();

			}

			try {

				smscursor = smscontext.getContentResolver().query(
						Uri.parse("content://sms/sent"), null, sent_selection,
						null, "_id");

			} catch (Exception e) {

			}
			if (smscursor.moveToFirst()) {
				do {
					int type = smscursor.getInt(typeColumn);
					long date = smscursor.getLong(dateColumn);
					String address = smscursor.getString(addressColumn);
					String phoneNo = formatNo(address);
					Date smsdate = new Date(date);
					lastsmssent_ID = smscursor.getInt(idcolumn);
					// Log.v("HN","id is " + lastsmssent_ID);
					
					smsinfo = smsinfo
							+ "<mdItem><item name=\"mdType\" value=\"tx\"/><item name=\"pCid\" value=\""
							+ ReallyMeActivity.pCid + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"ch\" value=\"8\"/>"
							+ STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"lat\" value=\""
							+ ReallyMeActivity.lat + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"long\" value=\""
							+ ReallyMeActivity.longi + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"action\" value=\"1\"/>"
							+ STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"oCid\" value=\""
							+ phoneNo + "\"/>" + STR_NEWLINE;
					smsinfo = smsinfo + "<item name=\"dt\" value=\""
							+ Utility.getInstance().getDateFormat(smsdate)
							+ "\"/>" + STR_NEWLINE;
					smsinfo += "</mdItem>";

				} while (smscursor.moveToNext());
				smscursor.close();

			}

			// Log.v("HN"," sent id is: " + lastsmssent_ID );
			// Log.v("HN"," received id is: " + lastreceivedsms_ID );
			values = new ContentValues(2);
			if (lastsmssent_ID > 0) {
				values.put("lastsmssent_id", lastsmssent_ID);
			} else {
				values.put("lastsmssent_id", sentSmsID);
			}
			if (lastreceivedsms_ID > 0) {
				values.put("lastsmsreceived_id", lastreceivedsms_ID);
			} else {
				values.put("lastsmsreceived_id", receivedSmsID);
			}
			if (rows == 0) {
				DBHelper.getDatabaseObj().insert("Sms", null, values);
			} else {
				DBHelper.getDatabaseObj().update("Sms", values, null, null);
			}
		} catch (Exception e) {

		}
		 //Log.i(TAG,"Sms XML Format :: "+smsinfo);
		return smsinfo;
	}

	public void clearData() {

	}

	public static String formatNo(String source) {
		/*
		 * String phoneno = PhoneNumberUtils.formatNumber(source); Log.v(TAG,
		 * "phone number: " + source);
		 */
		return source;
	}

	private void printSmsLog(String msg) {
		 //Log.d(TAG,"SMS :: "+msg);
	}

}
