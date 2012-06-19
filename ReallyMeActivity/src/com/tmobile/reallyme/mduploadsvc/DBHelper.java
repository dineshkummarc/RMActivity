package com.tmobile.reallyme.mduploadsvc;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {

	private static final String DATABASE_NAME = "HNDB";
	private static SQLiteDatabase db = null;
	Context dbcontext = null;

	String TAG = "HN";

	private static final String Calls_TABLE_CREATE = "create table Calls(call_id  integer,"
			+ "lastcalldate long" + ");";
	private static final String Sms_TABLE_CREATE = "create table Sms(lastsmssent_id integer,"
			+ "lastsmsreceived_id integer" + ");";
	private static final String Contacts_TABLE_CREATE = "create table Contacts(contact_id integer"
			+ ");";
	private static final String Display_TABLE_CREATE = "create table Display(post_flag text"
			+ ");";

	public DBHelper(Context ctx) {
		createDB(ctx);
	}

	public void createDB(Context ctx) {
		try {
			dbcontext = ctx;
			ctx.deleteDatabase(DATABASE_NAME);
			boolean dbExists = false;
			String[] lst = ctx.databaseList();

			for (int i = 0; i < lst.length; i++) {
				if (lst[i].equalsIgnoreCase(DATABASE_NAME)) {
					dbExists = true;
				}
			}
			db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
			if (!dbExists) {
				createAppUsageTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createAppUsageTable() {

		try {
			if (db.isOpen()) {

				db.execSQL(Calls_TABLE_CREATE);
				db.execSQL(Sms_TABLE_CREATE);
				db.execSQL(Contacts_TABLE_CREATE);
				db.execSQL(Display_TABLE_CREATE);
			}
		} catch (SQLException sqle) {
			;
		}
	}

	public void deleteDB() {
		dbcontext.deleteDatabase(DATABASE_NAME);
	}

	public static SQLiteDatabase getDatabaseObj() {
		return db;
	}

}
