package com.tmobile.reallyme.core.persistence;

import java.util.LinkedList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.tmobile.reallyme.core.api.remote.pojo.Principal;
import com.tmobile.reallyme.core.config.TMobileContextProvider;
import com.tmobile.reallyme.core.persistence.definition.HereAndNowMemberDefinition;


/**
 * User: Kolesnik Aleksey
 * Date: 08.07.2009
 * Time: 22:56:52
 */
public class HereAndNowMemberManager {


    public static Cursor loadAll(String sortOrder) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        return content.query(HereAndNowMemberDefinition.CONTENT_URI,
                null, "", null, sortOrder);
    }

    public static void insert(LinkedList<Principal> principals) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        for (Principal principal : principals){
            insert(content, principalToContentValues(principal));
        }
    }

    public static void insert(Principal principal) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        insert(content, principalToContentValues(principal));
    }

    private static void insert(ContentResolver content, ContentValues values) {
        content.insert(HereAndNowMemberDefinition.CONTENT_URI, values);
    }

    private static  ContentValues principalToContentValues(Principal principal) {
        ContentValues values = new ContentValues();
        values.put(HereAndNowMemberDefinition.IDENTITY_UID, principal.id);
        values.put(HereAndNowMemberDefinition.DISTANCE, principal.distance);
        values.put(HereAndNowMemberDefinition.SCORE, principal.score);
        values.put(HereAndNowMemberDefinition.TYPE, principal.type);
        return values;
    }
    
    public static void deleteAll() {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        content.delete(HereAndNowMemberDefinition.CONTENT_URI, "", null);
    }
}