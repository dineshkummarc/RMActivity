//package com.tmobile.reallyme.core.persistence;
//
//import com.tmobile.reallyme.core.api.remote.pojo.Identity;
//import com.tmobile.reallyme.core.api.remote.pojo.Context;
//import com.tmobile.reallyme.core.api.remote.pojo.Location;
//import com.tmobile.reallyme.core.api.remote.pojo.State;
//import com.tmobile.reallyme.core.config.TMobileContextProvider;
//import com.tmobile.reallyme.core.persistence.definition.StateDefinition;
//import com.tmobile.reallyme.utils.Utils;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.database.Cursor;
//
//import java.util.*;
//
//
///**
// * User: Kolesnik Aleksey
// * Date: 08.07.2009
// * Time: 22:56:52
// */
//public class StateManager {
//    private static final String DELIMETER = ";";
//
//    public static Identity loadByUID(String uid) {
//        ContentResolver content = TMobileContextProvider.contentResolver;
//        if (content == null) {
//            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
//        }
//        String[] arg = {uid};
//        Cursor cursor = content.query(StateDefinition.CONTENT_URI,
//                null, StateDefinition.UID + "=?", arg, null);
//        if (cursor.getCount() > 0) {
//           Identity identity = resultToIdentities(cursor).iterator().next();
//           cursor.close();
//           return identity;
//        } else {
//            cursor.close();
//            return null;
//        }
//    }
//
//     public static State loadByID(String id) {
//        ContentResolver content = TMobileContextProvider.contentResolver;
//        if (content == null) {
//            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
//        }
//        String[] arg = {id};
//        Cursor cursor = content.query(StateDefinition.CONTENT_URI,
//                null, StateDefinition._ID + "=?", arg, null);
//        if (cursor.getCount() > 0) {
//           Identity identity = resultToIdentities(cursor).iterator().next();
//           cursor.close();
//           return identity;
//        } else {
//            cursor.close();
//            return null;
//        }
//    }
//
//    public static void insert(Identity identity, IdentityTypeEnum type) {
//        ContentResolver content = TMobileContextProvider.contentResolver;
//        if (content == null) {
//            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
//        }
//        insert(content, identityToContentValues(identity);
//    }
//
//    private static  ContentValues identityToContentValues(State state) {
//        ContentValues values = new ContentValues();
//        values.put(StateDefinition.FIRST_NAME, identity.getFname());
//        values.put(IdentityDefinition.LAST_NAME, identity.getLname());
//        values.put(IdentityDefinition.LOCATION, identity.getContext().location.name);
//        values.put(IdentityDefinition.STATUS_MESSAGE, identity.getContext().state.desc);
//        values.put(IdentityDefinition.AVATAR_URL, identity.getAvatar());
//        values.put(IdentityDefinition.UID, identity.id);
//        values.put(IdentityDefinition.TYPE, type.getId());
//        values.put(IdentityDefinition.ACTIVITIES, identity.getActivities());
//        values.put(IdentityDefinition.INTERESTS, identity.getInterests());
//        values.put(IdentityDefinition.ABOUT_ME, identity.getAboutMe());
//        values.put(IdentityDefinition.HOMETOWN_LOC, identity.getHometownLoc());
//        values.put(IdentityDefinition.BIRTHDAY, identity.getBirthDay());
//        values.put(IdentityDefinition.RELATIONSHIP_STATUS, identity.getRelationshipStatus());
//        StringBuilder preferredChannels = new StringBuilder();
//        for(String channel : identity.getPreferredChannels()) {
//            if(preferredChannels.length() > 0 ) {
//                preferredChannels.append(DELIMETER);
//            }
//            preferredChannels.append(channel);
//        }
//        values.put(IdentityDefinition.PREFERRED_CHANNELS, preferredChannels.toString());
//        return values;
//    }
//
//    public static Set<State> resultToState(Cursor cursor) {
//        Set<State> result = new LinkedHashSet();
//        if (cursor == null) return result;
//        while(cursor.moveToNext()) {
//            Integer state_id = cursor.getInt(cursor.getColumnIndex(StateDefinition.STATE_ID));
//            String desc = cursor.getString(cursor.getColumnIndex(StateDefinition.DESCRIPTION));
//
//            State state = new State(state_id, desc);
//            result.add(state);
//        }
//        return result;
//    }
//
//    private static void insert(ContentResolver content, ContentValues values) {
//        content.insert(IdentityDefinition.CONTENT_URI, values);
//    }
//
//    public static void update(Identity identity, IdentityTypeEnum type) {
//        ContentResolver content = TMobileContextProvider.contentResolver;
//        if (content == null) {
//            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
//        }
//        String[] whereArgs = {identity.id};
//        content.update(IdentityDefinition.CONTENT_URI, identityToContentValues(identity, type),
//                IdentityDefinition.UID + "=?", whereArgs);
//    }
//}