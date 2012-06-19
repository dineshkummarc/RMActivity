package com.tmobile.reallyme.core;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.definition.UserSessionDefinition;
import com.tmobile.reallyme.core.resources.UserSessionResource;
import com.tmobile.reallyme.utils.Log;

/**
 * User: Kolesnik Aleksey
 * Date: 03.07.2009
 * Time: 0:00:49
 */
public class UserSession {
    private Log log =  new Log(this.getClass().getName());
    private Set<String> chnagedColumns = new HashSet();

    private static final UserSession _instance = new UserSession();

    public static UserSession getInstance() {
        return _instance;
    }

    //API session id
    private String sessionid = "";
    //API Registered device id
    private String clientId = "";
    //API Device password
    private String password = "";

    //User identity
    private Identity identity = new Identity();

    public static String getSessionid() {
        return _instance.sessionid;
    }

    public void setSessionid(String sessionid) {
        if(!_instance.sessionid.equals(sessionid)) {
            _instance.sessionid = sessionid;
            _instance.chnagedColumns.add(UserSessionDefinition.SESSION_ID);
        }
    }

    public static String getClientId() {
        return _instance.clientId;
    }

    public void setClientId(String clientId) {
        if(!_instance.clientId.equals(clientId)) {
            _instance.clientId = clientId;
            _instance.chnagedColumns.add(UserSessionDefinition.CLIENT_ID);
        }
    }

    public static String getPassword() {
        return _instance.password;
    }

    public void setPassword(String password) {
        if(!_instance.password.equals(password)) {
            _instance.password = password;
            _instance.chnagedColumns.add(UserSessionDefinition.PASSWORD);
        }
    }

    public String getFirstName() {
        if(_instance.identity != null && _instance.identity.getFname() != null) {
            return _instance.identity.getFname();
        }
        return "None";
    }

    public void setFirstName(String firstName) {
        _instance.identity.setFname(firstName);
        _instance.chnagedColumns.add(UserSessionDefinition.IDENTYTI);
    }

    public String getLastName() {
        if(_instance.identity != null && _instance.identity.getLname() != null) {
            return _instance.identity.getLname();
        }
        return "None";
    }

    public void setLastName(String lastName) {
        _instance.identity.setLname(lastName);
        _instance.chnagedColumns.add(UserSessionDefinition.IDENTYTI);
    }

    public Identity getIdentity() {
        return _instance.identity;
    }

    public void setIdentity(Identity identity, boolean needSave) {
        if (identity != null) {
            _instance.identity = identity;
            if (needSave) {
                _instance.chnagedColumns.add(UserSessionDefinition.IDENTYTI);
            }
        }
    }
    public void setIdentity(Identity identity) {
        setIdentity(identity, true);
    }

    public static void save() {
        if (_instance.chnagedColumns.size() > 0) {
            UserSessionResource.save(_instance.chnagedColumns); 
        }
        refresh();
    }
    //This method clear chnaged column value
    public static void refresh() {
        _instance.chnagedColumns = new HashSet();
    }

    public static Map<String, Boolean> getMSISDN() {
        return  _instance.identity.getMSISDN();
    }
}
