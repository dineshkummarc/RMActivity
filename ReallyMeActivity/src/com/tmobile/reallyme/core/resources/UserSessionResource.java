package com.tmobile.reallyme.core.resources;

import java.util.Set;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.config.TMobileContextProvider;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.IdentityTypeEnum;
import com.tmobile.reallyme.core.persistence.definition.UserSessionDefinition;
import com.tmobile.reallyme.utils.Utils;

/**
 * This class is DB utility whith update, save and insert user_session table. 
 * User: Kolesnik Aleksey
 * Date: 05.07.2009
 * Time: 17:57:41
 */
public class UserSessionResource {

    private static final String[] USER_SESSION_PROJECTION = new String[] {
        UserSessionDefinition.SESSION_ID,
        UserSessionDefinition.CLIENT_ID,
        UserSessionDefinition.PASSWORD
    };

    private static final String DELIMETER = ";";

    /**
     * Set UserSession from Db
     * @return UserSession
     */
    public static UserSession load() {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }
        UserSession userSession = UserSession.getInstance();
        Cursor cursor = content.query(UserSessionDefinition.CONTENT_URI, USER_SESSION_PROJECTION, null, null, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String sessionId = cursor.getString(cursor.getColumnIndex(UserSessionDefinition.SESSION_ID));
            if (Utils.isNotBlank(sessionId)) {
               userSession.setSessionid(sessionId);
            }
            String clientId = cursor.getString(cursor.getColumnIndex(UserSessionDefinition.CLIENT_ID));
            if (Utils.isNotBlank(clientId)) {
               userSession.setClientId(clientId);
            }
            String password = cursor.getString(cursor.getColumnIndex(UserSessionDefinition.PASSWORD));
            if (Utils.isNotBlank(password)) {
               userSession.setPassword(password);
            }
            Set<Identity> identities = IdentityManager.loadByType(IdentityTypeEnum.MY);
            if( identities != null && identities.size() > 0) {
                userSession.setIdentity(identities.iterator().next(), false);
            }
        }
        cursor.close();
        return  userSession;
    }

    /**
     * Save or update DB user_session table
     */
    public static void save(Set<String> chnagedColumns) {
        ContentResolver content = TMobileContextProvider.contentResolver;
        if (content == null) {
            throw new NullPointerException("TMobileContextProvider.contentResolver is NULL");
        }

        ContentValues values = new ContentValues();
        for(String column : chnagedColumns) {
            if (UserSessionDefinition.SESSION_ID.equals(column)) {
                values.put(UserSessionDefinition.SESSION_ID, UserSession.getSessionid());
            } else if (UserSessionDefinition.CLIENT_ID.equals(column)) {
                values.put(UserSessionDefinition.CLIENT_ID, UserSession.getClientId());
            } else if (UserSessionDefinition.PASSWORD.equals(column)) {
                values.put(UserSessionDefinition.PASSWORD, UserSession.getPassword());
            } else if(UserSessionDefinition.IDENTYTI.equals(column)) {
                Set<Identity> identities = IdentityManager.loadByType(IdentityTypeEnum.MY);
                if( identities != null && identities.size() > 0) {
                    IdentityManager.update(UserSession.getInstance().getIdentity(), IdentityTypeEnum.MY);
                } else {
                    IdentityManager.insert(UserSession.getInstance().getIdentity(), IdentityTypeEnum.MY);
                }
            }
        }
        if (values.size() > 0) {
            Cursor cursor = content.query(UserSessionDefinition.CONTENT_URI, USER_SESSION_PROJECTION, null, null, null);
            if(cursor.getCount() > 0) {
                content.update(UserSessionDefinition.CONTENT_URI, values, "", null); 
            }   else {
                content.insert(UserSessionDefinition.CONTENT_URI, values);
            }
            cursor.close();
        }
    }



}
