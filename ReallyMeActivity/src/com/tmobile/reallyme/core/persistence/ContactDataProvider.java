package com.tmobile.reallyme.core.persistence;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.tmobile.reallyme.core.persistence.definition.FaveDefinition;
import com.tmobile.reallyme.core.persistence.definition.FaveMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.HereAndNowMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.core.persistence.definition.MessageDefinition;
import com.tmobile.reallyme.core.persistence.definition.UserSessionDefinition;
import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class ContactDataProvider extends ContentProvider {
    private DatabaseHelper dbHelper;
    //private static final String TABLE_NAME = "identity";
    private static final UriMatcher sUriMatcher;
    private static final int IDENTITY_LIST = 0;
    private static final int IDENTITY_ITEM = 1;
    public static final int USER_SESSION_ITEM = 2;
    public static final int MESSAGE_LIST = 3;
    public static final int MESSAGE_ITEM = 4;
    public static final int HERE_AND_NOW_MEMBER_ITEM = 5;
    public static final int HERE_AND_NOW_MEMBER_LIST = 6;
    public static final int FAVE_LIST = 7;
    public static final int FAVE_ITEM = 8;
    public static final int FAVE_MEMBER_LIST = 9;
    public static final int FAVE_MEMBER_ITEM = 10;
    
    
    private static HashMap<String, String> identityProjectionMap;
    private static HashMap<String, String> messageProjectionMap;
    private static HashMap<String, String> userSessionProjectionMap;
    private static HashMap<String, String> faveProjectionMap;
    private static HashMap<String, String> faveMemberProjectionMap;
    private static HashMap<String, String> hereAndNowMemberProjectionMap;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.DATABASE_ID, "identity", IDENTITY_LIST);
        sUriMatcher.addURI(Constants.DATABASE_ID, "identity/#", IDENTITY_ITEM);
        sUriMatcher.addURI(Constants.DATABASE_ID, "message", MESSAGE_LIST);
        sUriMatcher.addURI(Constants.DATABASE_ID, "message/#", MESSAGE_ITEM);
        sUriMatcher.addURI(Constants.DATABASE_ID, "user_session", USER_SESSION_ITEM);
        sUriMatcher.addURI(Constants.DATABASE_ID, "here_and_now_member/#", HERE_AND_NOW_MEMBER_ITEM);
        sUriMatcher.addURI(Constants.DATABASE_ID, "here_and_now_member", HERE_AND_NOW_MEMBER_LIST);        
        sUriMatcher.addURI(Constants.DATABASE_ID, "fave", FAVE_LIST);
        sUriMatcher.addURI(Constants.DATABASE_ID, "fave/#", FAVE_ITEM);
        sUriMatcher.addURI(Constants.DATABASE_ID, "fave_member", FAVE_MEMBER_LIST); // for insert/update
        sUriMatcher.addURI(Constants.DATABASE_ID, "fave_member/#", FAVE_MEMBER_ITEM);

        identityProjectionMap = new HashMap<String, String>();
        identityProjectionMap.put(IdentityDefinition._ID, IdentityDefinition._ID);
        identityProjectionMap.put(IdentityDefinition.UID, IdentityDefinition.UID);
        identityProjectionMap.put(IdentityDefinition.FIRST_NAME, IdentityDefinition.FIRST_NAME);
        identityProjectionMap.put(IdentityDefinition.LAST_NAME, IdentityDefinition.LAST_NAME);
        identityProjectionMap.put(IdentityDefinition.LOCATION_NAME, IdentityDefinition.LOCATION_NAME);
        identityProjectionMap.put(IdentityDefinition.TIME_ZONE, IdentityDefinition.TIME_ZONE);
        identityProjectionMap.put(IdentityDefinition.STATUS_MESSAGE, IdentityDefinition.STATUS_MESSAGE);
        identityProjectionMap.put(IdentityDefinition.STATUS_ID, IdentityDefinition.STATUS_ID);
        identityProjectionMap.put(IdentityDefinition.AVATAR_URL, IdentityDefinition.AVATAR_URL);
        identityProjectionMap.put(IdentityDefinition.ACTIVITIES, IdentityDefinition.ACTIVITIES);
        identityProjectionMap.put(IdentityDefinition.INTERESTS, IdentityDefinition.INTERESTS);
        identityProjectionMap.put(IdentityDefinition.ABOUT_ME, IdentityDefinition.ABOUT_ME);
        identityProjectionMap.put(IdentityDefinition.HOMETOWN_LOC, IdentityDefinition.HOMETOWN_LOC);
        identityProjectionMap.put(IdentityDefinition.BIRTHDAY, IdentityDefinition.BIRTHDAY);
        identityProjectionMap.put(IdentityDefinition.RELATIONSHIP_STATUS, IdentityDefinition.RELATIONSHIP_STATUS);
        identityProjectionMap.put(IdentityDefinition.PREFERRED_CHANNELS, IdentityDefinition.PREFERRED_CHANNELS);
        identityProjectionMap.put(IdentityDefinition.CHANNELS, IdentityDefinition.CHANNELS);

        identityProjectionMap.put(IdentityDefinition.CREATED_DATE, IdentityDefinition.CREATED_DATE);

        messageProjectionMap = new HashMap<String, String>();
        messageProjectionMap.put(MessageDefinition._ID, MessageDefinition._ID);
        messageProjectionMap.put(MessageDefinition.MID, MessageDefinition.MID);
        messageProjectionMap.put(MessageDefinition.RECIPIENT_ID, MessageDefinition.RECIPIENT_ID);
        messageProjectionMap.put(MessageDefinition.SENDER_ID, MessageDefinition.SENDER_ID);
        messageProjectionMap.put(MessageDefinition.TYPE, MessageDefinition.TYPE);
        messageProjectionMap.put(MessageDefinition.TIME, MessageDefinition.TIME);
        messageProjectionMap.put(MessageDefinition.CONTENT, MessageDefinition.CONTENT);

        messageProjectionMap.put(IdentityDefinition.UID, IdentityDefinition.UID);
        messageProjectionMap.put(IdentityDefinition.FIRST_NAME, IdentityDefinition.FIRST_NAME);
        messageProjectionMap.put(IdentityDefinition.LAST_NAME, IdentityDefinition.LAST_NAME);
        messageProjectionMap.put(IdentityDefinition.STATUS_MESSAGE, IdentityDefinition.STATUS_MESSAGE);
        messageProjectionMap.put(IdentityDefinition.AVATAR_URL, IdentityDefinition.AVATAR_URL);

        userSessionProjectionMap = new HashMap<String, String>();
        userSessionProjectionMap.put(UserSessionDefinition.SESSION_ID, UserSessionDefinition.SESSION_ID);
        userSessionProjectionMap.put(UserSessionDefinition.CLIENT_ID, UserSessionDefinition.CLIENT_ID);
        userSessionProjectionMap.put(UserSessionDefinition.PASSWORD, UserSessionDefinition.PASSWORD);

        hereAndNowMemberProjectionMap = new HashMap<String, String>();
        hereAndNowMemberProjectionMap.put(HereAndNowMemberDefinition._ID, HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition._ID);
        hereAndNowMemberProjectionMap.put(HereAndNowMemberDefinition.IDENTITY_UID, HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.IDENTITY_UID + " AS " + IdentityDefinition.UID);
        hereAndNowMemberProjectionMap.put(HereAndNowMemberDefinition.DISTANCE, HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.DISTANCE);
        hereAndNowMemberProjectionMap.put(HereAndNowMemberDefinition.SCORE, HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.SCORE);
        hereAndNowMemberProjectionMap.put(HereAndNowMemberDefinition.TYPE, HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.TYPE);
        
        hereAndNowMemberProjectionMap.put(IdentityDefinition.FIRST_NAME, IdentityDefinition.FIRST_NAME);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.LAST_NAME, IdentityDefinition.LAST_NAME);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.LOCATION_NAME, IdentityDefinition.LOCATION_NAME);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.STATUS_MESSAGE, IdentityDefinition.STATUS_MESSAGE);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.STATUS_ID, IdentityDefinition.STATUS_ID);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.AVATAR_URL, IdentityDefinition.AVATAR_URL);
        hereAndNowMemberProjectionMap.put(IdentityDefinition.PREFERRED_CHANNELS, IdentityDefinition.PREFERRED_CHANNELS);

        faveProjectionMap = new HashMap<String, String>();
        faveProjectionMap.put(FaveDefinition._ID, FaveDefinition._ID);
        faveProjectionMap.put(FaveDefinition.FID, FaveDefinition.FID);
        faveProjectionMap.put(FaveDefinition.NAME, FaveDefinition.NAME);

        faveMemberProjectionMap = new HashMap<String, String>();
        faveMemberProjectionMap.put(FaveMemberDefinition._ID, FaveMemberDefinition.TABLE_NAME + "." + FaveMemberDefinition._ID);
        faveMemberProjectionMap.put(FaveMemberDefinition.FMID, FaveMemberDefinition.TABLE_NAME + "." + FaveMemberDefinition.FMID + " AS " + IdentityDefinition.UID);
        faveMemberProjectionMap.put(FaveMemberDefinition.PARENT_FAVE_ID, FaveMemberDefinition.PARENT_FAVE_ID);

        faveMemberProjectionMap.put(IdentityDefinition.FIRST_NAME, IdentityDefinition.FIRST_NAME);
        faveMemberProjectionMap.put(IdentityDefinition.LAST_NAME, IdentityDefinition.LAST_NAME);
        faveMemberProjectionMap.put(IdentityDefinition.LOCATION_NAME, IdentityDefinition.LOCATION_NAME);
        faveMemberProjectionMap.put(IdentityDefinition.STATUS_MESSAGE, IdentityDefinition.STATUS_MESSAGE);
        faveMemberProjectionMap.put(IdentityDefinition.STATUS_ID, IdentityDefinition.STATUS_ID);
        faveMemberProjectionMap.put(IdentityDefinition.AVATAR_URL, IdentityDefinition.AVATAR_URL);
        faveMemberProjectionMap.put(IdentityDefinition.PREFERRED_CHANNELS, IdentityDefinition.PREFERRED_CHANNELS);

    }

    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String orderBy;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case IDENTITY_LIST:
                qb.setTables(IdentityDefinition.TABLE_NAME);
                qb.setProjectionMap(identityProjectionMap);

                break;

            case IDENTITY_ITEM:
                qb.setTables(IdentityDefinition.TABLE_NAME);
                qb.setProjectionMap(identityProjectionMap);
                qb.appendWhere(IdentityDefinition._ID + "=" + uri.getPathSegments().get(1));

                break;

            case HERE_AND_NOW_MEMBER_LIST:
                qb.setTables(HereAndNowMemberDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                        " ON " + HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.IDENTITY_UID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);

                qb.setProjectionMap(hereAndNowMemberProjectionMap);

                break;

            case HERE_AND_NOW_MEMBER_ITEM:
                qb.setTables(HereAndNowMemberDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                        " ON " + HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition.IDENTITY_UID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);

                qb.setProjectionMap(hereAndNowMemberProjectionMap);
                qb.appendWhere(IdentityDefinition._ID + "=" + uri.getPathSegments().get(1));

                break;

            case MESSAGE_LIST:
                qb.setTables(MessageDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                        " ON " + MessageDefinition.TABLE_NAME + "." + MessageDefinition.SENDER_ID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID +
                        " OR " + MessageDefinition.TABLE_NAME + "." + MessageDefinition.RECIPIENT_ID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);
                qb.setProjectionMap(messageProjectionMap);

                break;

            case MESSAGE_ITEM:
                qb.setTables(MessageDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                        " ON " + MessageDefinition.TABLE_NAME + "." + MessageDefinition.SENDER_ID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID +
                        " OR " + MessageDefinition.TABLE_NAME + "." + MessageDefinition.RECIPIENT_ID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);
                qb.setProjectionMap(identityProjectionMap);
                qb.appendWhere(MessageDefinition._ID + "=" + uri.getPathSegments().get(1));
                break;

            case USER_SESSION_ITEM:
                qb.setTables(UserSessionDefinition.TABLE_NAME);
                qb.setProjectionMap(userSessionProjectionMap);
                orderBy = null;
                break;

            case FAVE_ITEM:
                qb.setTables(FaveDefinition.TABLE_NAME);
                qb.setProjectionMap(faveProjectionMap);
                orderBy = null;
                break;

            case FAVE_LIST:
                qb.setTables(FaveDefinition.TABLE_NAME);
                qb.setProjectionMap(faveProjectionMap);
                orderBy = null;
                break;

            case FAVE_MEMBER_LIST:
                qb.setTables(FaveMemberDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                    " ON " + FaveMemberDefinition.TABLE_NAME + "." + FaveMemberDefinition.FMID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);

                qb.setProjectionMap(faveMemberProjectionMap);
                orderBy = null;
                break;

            case FAVE_MEMBER_ITEM:
                qb.setTables(FaveMemberDefinition.TABLE_NAME + " INNER JOIN " + IdentityDefinition.TABLE_NAME +
                    " ON " + FaveMemberDefinition.TABLE_NAME + "." + FaveMemberDefinition.FMID + " = " + IdentityDefinition.TABLE_NAME + "." + IdentityDefinition.UID);
                                
                qb.setProjectionMap(faveMemberProjectionMap);
                orderBy = null;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default



        // Get the database and run the query
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case IDENTITY_LIST:
                return IdentityDefinition.CONTENT_LIST_TYPE;
            case IDENTITY_ITEM:
                return IdentityDefinition.CONTENT_ITEM_TYPE;
            case HERE_AND_NOW_MEMBER_ITEM:
                return HereAndNowMemberDefinition.CONTENT_ITEM_TYPE;
            case HERE_AND_NOW_MEMBER_LIST:
                return HereAndNowMemberDefinition.CONTENT_LIST_TYPE;
            case FAVE_ITEM:
                return FaveDefinition.CONTENT_ITEM_TYPE;
            case FAVE_LIST:
                return FaveDefinition.CONTENT_LIST_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // Validate the requested uri
        /*if (sUriMatcher.match(uri) != IDENTITY_LIST && sUriMatcher.match(uri) != USER_SESSION_ITEM
                && sUriMatcher.match(uri) != HERE_AND_NOW_MEMBER_LIST && sUriMatcher.match(uri) != HERE_AND_NOW_MEMBER_LIST) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        } */

        ContentValues values;
        if (contentValues != null) {
            values = new ContentValues(contentValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case IDENTITY_LIST: {
                if (values.containsKey(IdentityDefinition.UID) == false) {
                    throw new IllegalArgumentException("UID does not specified for insert operation uri:" + uri);
                }
                Long now = Long.valueOf(System.currentTimeMillis());

                // Make sure that the fields are all set
                if (values.containsKey(IdentityDefinition.CREATED_DATE) == false) {
                    values.put(IdentityDefinition.CREATED_DATE, now);
                }

                if (values.containsKey(IdentityDefinition.UPDATED_DATE) == false) {
                    values.put(IdentityDefinition.UPDATED_DATE, now);
                }
                long rowId = db.insert(IdentityDefinition.TABLE_NAME, "", values);
                if (rowId > 0) {
                    Uri identityUri = ContentUris.withAppendedId(IdentityDefinition.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(identityUri, null);
                    return identityUri;
                }
                break;
            }

            case HERE_AND_NOW_MEMBER_LIST: {
                long rowId2 = db.insert(HereAndNowMemberDefinition.TABLE_NAME, "", values);
                if (rowId2 > 0) {
                    Uri userSessionUri = ContentUris.withAppendedId(HereAndNowMemberDefinition.CONTENT_URI, rowId2);
                    getContext().getContentResolver().notifyChange(userSessionUri, null);
                    return userSessionUri;
                }
                break;
            }

            case USER_SESSION_ITEM: {
                long rowId2 = db.insert(UserSessionDefinition.TABLE_NAME, "", values);
                if (rowId2 > 0) {
                    Uri userSessionUri = ContentUris.withAppendedId(UserSessionDefinition.CONTENT_URI, rowId2);
                    getContext().getContentResolver().notifyChange(userSessionUri, null);
                    return userSessionUri;
                }
                break;
            }
            case FAVE_LIST:
            case FAVE_ITEM: {
                long rowId2 = db.insert(FaveDefinition.TABLE_NAME, "", values);
                if (rowId2 > 0) {
                    Uri userSessionUri = ContentUris.withAppendedId(UserSessionDefinition.CONTENT_URI, rowId2);
                    getContext().getContentResolver().notifyChange(userSessionUri, null);
                    return userSessionUri;
                }
                break;
            }
            case FAVE_MEMBER_LIST:
            case FAVE_MEMBER_ITEM: {
                long rowId2 = db.insert(FaveMemberDefinition.TABLE_NAME, "", values);
                if (rowId2 > 0) {
                    Uri userSessionUri = ContentUris.withAppendedId(UserSessionDefinition.CONTENT_URI, rowId2);
                    getContext().getContentResolver().notifyChange(userSessionUri, null);
                    return userSessionUri;
                }
                break;
            }

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case IDENTITY_LIST:
                count = db.delete(IdentityDefinition.TABLE_NAME, where, whereArgs);
                break;

            case IDENTITY_ITEM:
                String identityId = uri.getPathSegments().get(1);
                count = db.delete(IdentityDefinition.TABLE_NAME, IdentityDefinition._ID + "=" + identityId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;

             case HERE_AND_NOW_MEMBER_LIST:
                count = db.delete(HereAndNowMemberDefinition.TABLE_NAME, where, whereArgs);
                break;

            case FAVE_ITEM:
            case FAVE_LIST:
                count = db.delete(FaveDefinition.TABLE_NAME, where, whereArgs);
                break;

            case FAVE_MEMBER_ITEM:
            case FAVE_MEMBER_LIST:
                count = db.delete(FaveMemberDefinition.TABLE_NAME, where, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case IDENTITY_LIST:
                count = db.update(IdentityDefinition.TABLE_NAME, values, where, whereArgs);
                break;

           case IDENTITY_ITEM:
               //String identityId = uri.getPathSegments().get(1);
               count = db.update(IdentityDefinition.TABLE_NAME, values, //IdentityDefinition._ID + "=" + identityId +
                      where, whereArgs);
               break;
           case USER_SESSION_ITEM:
                count = db.update(UserSessionDefinition.TABLE_NAME, values, where, whereArgs);
                break;

           case FAVE_ITEM:
           case FAVE_LIST:
                count = db.update(FaveDefinition.TABLE_NAME, values, where, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
