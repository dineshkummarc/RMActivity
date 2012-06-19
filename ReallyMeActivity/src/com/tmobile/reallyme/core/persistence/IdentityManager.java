package com.tmobile.reallyme.core.persistence;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.tmobile.reallyme.core.api.remote.pojo.*;
import com.tmobile.reallyme.core.config.TMobileContextProvider;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.Utils;


/**
 * User: Kolesnik Aleksey
 * Date: 08.07.2009
 * Time: 22:56:52
 */
public class IdentityManager {
    public static final String DELIMETER = ";";

    public static Cursor loadAllFriend(String sortOrder) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        return content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.TYPE + "=?", new String[] {IdentityTypeEnum.FRIEND.getId()}, sortOrder);
    }

    public static Identity loadByUID(String uid) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.UID + "=?", new String[]{uid}, null);
        if (cursor.getCount() > 0) {
           Identity identity = resultToIdentities(cursor).iterator().next();
           cursor.close();
           return identity;
        } else {
            cursor.close();
            return null;
        }
    }

     public static Identity loadByID(String id) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition._ID + "=?", new String[]{id}, null);
        if (cursor.getCount() > 0) {
           Identity identity = resultToIdentities(cursor).iterator().next();
           cursor.close();
           return identity;
        } else {
            cursor.close();
            return null;
        }
    }

    public static Set<Identity> loadByType(IdentityTypeEnum type) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        String[] arg = {type.getId()};
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.TYPE + "=?", arg, null);
        if (cursor.getCount() > 0) {
            Set<Identity> identities = resultToIdentities(cursor);
            cursor.close();
            return identities;
        }
        return null;
    }

    public static void insert(List<Identity> identities, IdentityTypeEnum type) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        for (Identity identity : identities){
            insert(content, identityToContentValues(identity, type));
        }
    }

    public static void insert(Identity identity, IdentityTypeEnum type) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.UID + "=?", new String[]{identity.id}, null);
        if (cursor.getCount() > 0) {
            update(identity, type);
            cursor.close();
        } else {
            insert(content, identityToContentValues(identity, type));
            cursor.close();
        }
    }
     private static  ContentValues identityToContentValuesForUpdate(Identity identity, IdentityTypeEnum type) {
        ContentValues values = new ContentValues();
        if (Utils.isNotBlank(identity.getFname())) {
            values.put(IdentityDefinition.FIRST_NAME, identity.getFname());
        }
        if (Utils.isNotBlank(identity.getFname())) {
            values.put(IdentityDefinition.LAST_NAME, identity.getLname());
        }
        if (identity.getContext() != null) {
            if (identity.getContext().location != null) {
                values.put(IdentityDefinition.LOCATION_NAME, identity.getContext().location.name);
                values.put(IdentityDefinition.TIME_ZONE, identity.getContext().location.timeZone);
            }
            if (identity.getContext().state != null) {
                values.put(IdentityDefinition.STATUS_MESSAGE, identity.getContext().state.desc);
                values.put(IdentityDefinition.STATUS_ID, identity.getContext().state.id);
            }
        }
        if (identity.getChannels() != null && identity.getChannels().size() > 0) {
//            channels = n.channels;
        }

        //Update profile
        //TODO: Kolesnik Aleksey P1 
        if (identity.getProfile() != null && identity.getProfile().items.size() > 0) {
//            for (Item item : identity.getProfile().items) {
//                //dunno maybe use another approach
//                String itemName = item.name;
//                Item _item = null;
//                for(Item item2 : identity.getProfile().items) {
//                    //log.info("{i.name} == {j.name}");
//                    if (item2.name == item.name) {
//                            _item = item2;
//                            break;
//                    };
//                 }
//                 if (_item == null) {
//                     //log.info("Do insert {item}");
//                     insert i into profile.items;
//                 } else {
//                     //log.info("Do update {item}");
//                     //update
//                     delete item from profile.items;
//                     insert i into profile.items;
//                 }
//            }
        }

        if (Utils.isNotBlank(identity.getAvatar()) && !identity.getAvatar().equals("http://yourServer:port/images/nobody.png")) {
            values.put(IdentityDefinition.AVATAR_URL, identity.getAvatar());
        }
        if (Utils.isNotBlank(identity.getActivities())) {
            values.put(IdentityDefinition.ACTIVITIES, identity.getActivities());
        }
        if (Utils.isNotBlank(identity.getInterests())) {
            values.put(IdentityDefinition.ACTIVITIES, identity.getActivities());
        }
        if (Utils.isNotBlank(identity.getAboutMe())) {
            values.put(IdentityDefinition.ABOUT_ME, identity.getAboutMe());
        }
        if (Utils.isNotBlank(identity.getHometownLoc())) {
            values.put(IdentityDefinition.HOMETOWN_LOC, identity.getHometownLoc());
        }
        if (Utils.isNotBlank(identity.getBirthDay())) {
            values.put(IdentityDefinition.BIRTHDAY, identity.getBirthDay());
        }
        if (Utils.isNotBlank(identity.getRelationshipStatus())) {
            values.put(IdentityDefinition.RELATIONSHIP_STATUS, identity.getRelationshipStatus());
        }
        if (identity.getPreferredChannels() != null && identity.getPreferredChannels().size() > 0) {
            StringBuilder preferredChannels = new StringBuilder();
            for(String channel : identity.getPreferredChannels()) {
                if(preferredChannels.length() > 0 ) {
                    preferredChannels.append(DELIMETER);
                }
                preferredChannels.append(channel);
            }
            values.put(IdentityDefinition.PREFERRED_CHANNELS, preferredChannels.toString());
        }
        if ( !identity.getChannels().isEmpty() ) {
            StringBuilder channels = new StringBuilder();
            for(Channel channel : identity.getChannels()) {
                if(channels.length() > 0 ) {
                    channels.append(DELIMETER);
                }
                channels.append(channel.id).append("/").append(channel.id).append("/").append(channel.value).append("/").append(channel.verified);
            }
            values.put(IdentityDefinition.CHANNELS, channels.toString());
        }
        return values;
     }
    
    private static  ContentValues identityToContentValues(Identity identity, IdentityTypeEnum type) {
        ContentValues values = new ContentValues();
        values.put(IdentityDefinition.FIRST_NAME, identity.getFname());
        values.put(IdentityDefinition.LAST_NAME, identity.getLname());
        values.put(IdentityDefinition.LOCATION_NAME, identity.getContext().location.name);
        values.put(IdentityDefinition.TIME_ZONE, identity.getContext().location.timeZone);
        values.put(IdentityDefinition.STATUS_MESSAGE, identity.getContext().state.desc);
        values.put(IdentityDefinition.STATUS_ID, identity.getContext().state.id);
        String avatarUrl = "";
        if (Utils.isNotBlank(identity.getAvatar()) && !identity.getAvatar().equals("http://yourServer:port/images/nobody.png")) {
           avatarUrl = identity.getAvatar();
        }
        values.put(IdentityDefinition.AVATAR_URL, avatarUrl);
        values.put(IdentityDefinition.UID, identity.id);
        values.put(IdentityDefinition.TYPE, type.getId());
        values.put(IdentityDefinition.ACTIVITIES, identity.getActivities());
        values.put(IdentityDefinition.INTERESTS, identity.getInterests());
        values.put(IdentityDefinition.ABOUT_ME, identity.getAboutMe());
        values.put(IdentityDefinition.HOMETOWN_LOC, identity.getHometownLoc());
        values.put(IdentityDefinition.BIRTHDAY, identity.getBirthDay());
        values.put(IdentityDefinition.RELATIONSHIP_STATUS, identity.getRelationshipStatus());
        StringBuilder preferredChannels = new StringBuilder();
        for(String channel : identity.getPreferredChannels()) {
            if(preferredChannels.length() > 0 ) {
                preferredChannels.append(DELIMETER);
            }
            preferredChannels.append(channel);    
        }
        values.put(IdentityDefinition.PREFERRED_CHANNELS, preferredChannels.toString());

        StringBuilder channels = new StringBuilder();
        for(Channel channel : identity.getChannels()) {
            if(channels.length() > 0 ) {
                channels.append(DELIMETER);
            }
            channels.append(channel.id).append("/").append(channel.id).append("/").append(channel.value).append("/").append(channel.verified);
        }
        values.put(IdentityDefinition.CHANNELS, channels.toString());

        return values;
    }

    public static Set<Identity> resultToIdentities(Cursor cursor) {
        Set<Identity> result = new LinkedHashSet();
        if (cursor == null) return result;
        while(cursor.moveToNext()) {
            result.add(rowToIdentity(cursor));
        }
        return result;
    }

    public static Identity rowToIdentity(Cursor cursor) {
        String uid = cursor.getString(cursor.getColumnIndex(IdentityDefinition.UID));
        if (!Utils.isNotBlank(uid)) {
            throw new NullPointerException("Identity uid can't be null");
        }
        String fname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.FIRST_NAME));
        String lname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LAST_NAME));
        String locationName = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LOCATION_NAME));
        String timeZone = cursor.getString(cursor.getColumnIndex(IdentityDefinition.TIME_ZONE));
        String stMessage = cursor.getString(cursor.getColumnIndex(IdentityDefinition.STATUS_MESSAGE));
        Integer statusId = cursor.getInt(cursor.getColumnIndex(IdentityDefinition.STATUS_ID));
        String avatar = cursor.getString(cursor.getColumnIndex(IdentityDefinition.AVATAR_URL));
        String activities = cursor.getString(cursor.getColumnIndex(IdentityDefinition.ACTIVITIES));
        String interests = cursor.getString(cursor.getColumnIndex(IdentityDefinition.INTERESTS));
        String aboutMe = cursor.getString(cursor.getColumnIndex(IdentityDefinition.ABOUT_ME));
        String hometownLoc = cursor.getString(cursor.getColumnIndex(IdentityDefinition.HOMETOWN_LOC));
        String birhday = cursor.getString(cursor.getColumnIndex(IdentityDefinition.BIRTHDAY));
        String relationshipStatus = cursor.getString(cursor.getColumnIndex(IdentityDefinition.RELATIONSHIP_STATUS));
        String _preferredChannels = cursor.getString(cursor.getColumnIndex(IdentityDefinition.PREFERRED_CHANNELS));
        List<String> preferredChannels = new ArrayList();
        if (Utils.isNotBlank(_preferredChannels)) {
            for(String channel : _preferredChannels.split(DELIMETER)) {
                preferredChannels.add(channel);
            }
        }

        String _channels = cursor.getString(cursor.getColumnIndex(IdentityDefinition.CHANNELS));
        List<Channel> channels = new ArrayList();
        if (Utils.isNotBlank(_channels)) {
            for(String channel : _channels.split(DELIMETER)) {
                channels.add(
                        new Channel(
                            Integer.valueOf(channel.split("/")[0]),//id
                            String.valueOf(channel.split("/")[1]),//cid
                            String.valueOf(channel.split("/")[2]),//value
                            Boolean.valueOf(channel.split("/")[3])//verified
                        )
                );
            }
        }

        Identity identity = new Identity(uid);
        identity.setFname(fname);
        identity.setLname(lname);
        Context context = new Context();
        context.location = new Location(locationName, timeZone);
        context.state = new State(statusId, stMessage);
        identity.setContext(context);
        identity.setAvatar(avatar);
        identity.setActivities(activities);
        identity.setInterests(interests);
        identity.setAboutMe(aboutMe);
        identity.setHometownLoc(hometownLoc);
        identity.setBirthDay(birhday);
        identity.setRelationshipStatus(relationshipStatus);
        identity.setPreferredChannels(preferredChannels);
        identity.setChannels(channels);
        return identity;
    }

    private static void insert(ContentResolver content, ContentValues values) {
        content.insert(IdentityDefinition.CONTENT_URI, values);    
    }

    public static void update(Identity identity, IdentityTypeEnum type) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        ContentValues values = identityToContentValuesForUpdate(identity, type);
        if(values != null && values.size() > 0) {
            String[] whereArgs = {identity.id};
            content.update(IdentityDefinition.CONTENT_URI, values,
                    IdentityDefinition.UID + "=?", whereArgs);
        }
    }
}
