package com.tmobile.reallyme.core.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tmobile.reallyme.core.persistence.definition.FaveDefinition;
import com.tmobile.reallyme.core.persistence.definition.FaveMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.HereAndNowMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.core.persistence.definition.MessageDefinition;
import com.tmobile.reallyme.core.persistence.definition.UserSessionDefinition;

/**
 * dku
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reallyme.db";
    private static final int DATABASE_VERSION = 17;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + IdentityDefinition.TABLE_NAME + " ("
                    + IdentityDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + IdentityDefinition.UID + " VARCHAR(36),"
                    + IdentityDefinition.FIRST_NAME + " TEXT,"
                    + IdentityDefinition.LAST_NAME + " TEXT,"
                    + IdentityDefinition.AVATAR_URL + " TEXT,"
                    + IdentityDefinition.ABOUT_ME + " TEXT,"
                    + IdentityDefinition.ACTIVITIES + " TEXT,"
                    + IdentityDefinition.INTERESTS + " TEXT,"
                    + IdentityDefinition.LOCATION_NAME + " TEXT,"
                    + IdentityDefinition.TIME_ZONE + " TEXT,"
                    + IdentityDefinition.STATUS_MESSAGE + " TEXT,"
                    + IdentityDefinition.STATUS_ID + " INTEGER,"
                    + IdentityDefinition.RELATIONSHIP_STATUS + " TEXT,"
                    + IdentityDefinition.BIRTHDAY + " TEXT,"
                    + IdentityDefinition.HOMETOWN_LOC + " TEXT,"
                    + IdentityDefinition.PREFERRED_CHANNELS + " TEXT,"
                    + IdentityDefinition.CHANNELS + " TEXT,"
                    + IdentityDefinition.CREATED_DATE + " INTEGER,"
                    + IdentityDefinition.UPDATED_DATE + " INTEGER,"
                    + IdentityDefinition.TYPE + " TEXT"
                    + ");");
        
        db.execSQL("CREATE TABLE " + MessageDefinition.TABLE_NAME + " ("
                    + MessageDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + MessageDefinition.MID + " VARCHAR(36),"
                    + MessageDefinition.SENDER_ID + " VARCHAR(36),"
                    + MessageDefinition.RECIPIENT_ID + " VARCHAR(36),"
                    + MessageDefinition.CONTENT + " TEXT,"
                    + MessageDefinition.TYPE + " INTEGER,"
                    + MessageDefinition.TIME + " INTEGER,"
                    + MessageDefinition.CREATED_DATE + " INTEGER"
                    + ");");

        db.execSQL("CREATE TABLE " + UserSessionDefinition.TABLE_NAME + " ("
                    + UserSessionDefinition.SESSION_ID + " TEXT,"
                    + UserSessionDefinition.CLIENT_ID + " TEXT,"
                    + UserSessionDefinition.PASSWORD + " TEXT"
                    + ");");
        //uses fro here and now page
        db.execSQL("CREATE TABLE " + HereAndNowMemberDefinition.TABLE_NAME + " ("
                    + HereAndNowMemberDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + HereAndNowMemberDefinition.IDENTITY_UID + " VARCHAR(36),"
                    + HereAndNowMemberDefinition.DISTANCE + " DOUBLE,"
                    + HereAndNowMemberDefinition.SCORE + " INTEGER,"
                    + HereAndNowMemberDefinition.TYPE + " TEXT"
                    + ");");

        db.execSQL("CREATE TABLE " + FaveDefinition.TABLE_NAME + " ("
                    + FaveDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FaveDefinition.FID + " VARCHAR(36),"
                    + FaveDefinition.NAME + " TEXT"
                    + ");");

        db.execSQL("CREATE TABLE " + FaveMemberDefinition.TABLE_NAME + " ("
                    + FaveMemberDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FaveMemberDefinition.FMID + " VARCHAR(36),"
                    + FaveMemberDefinition.PARENT_FAVE_ID + " VARCHAR(36)"
                    + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        //
        db.execSQL("DROP TABLE IF EXISTS " + IdentityDefinition.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserSessionDefinition.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MessageDefinition.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HereAndNowMemberDefinition.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FaveDefinition.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FaveMemberDefinition.TABLE_NAME);
        
        onCreate(db);
    }

}
