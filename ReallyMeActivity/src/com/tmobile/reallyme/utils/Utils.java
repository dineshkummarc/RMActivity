package com.tmobile.reallyme.utils;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.core.config.TMobileContextProvider;

import java.util.Map;
import java.util.HashMap;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * Created by IntelliJ IDEA.
 * User: Kolesnik Aleksey
 * Date: 03.07.2009
 * Time: 0:25:53
 */
public class Utils {

    /**
     * Encode parameter to remove special characters like
     *
     * Dollar ("$")                   %24
     * Ampersand ("&")                %26
     * Plus ("+")                     %2B
     * Comma (",")                    %2C
     * Forward slash/Virgule ("/")    %2F
     * Colon (":")                    %3A
     * Semi-colon (";")               %3B
     * Equals ("=")                   %3D
     * Question mark ("?")            %3F
     * 'At' symbol ("@")              %40
     *
     * for a GET request
     */
    public static String encodeParameters(String param) {
        String encodedParam = param.replaceAll(" ", "+");
        if(param.indexOf("$") != -1) { encodedParam = param.replaceAll("$", "%24"); }
        if(param.indexOf("#") != -1) { encodedParam = param.replaceAll("#", "%23"); }
        if(param.indexOf("&") != -1) { encodedParam = param.replaceAll("&", "%26"); }
        if(param.indexOf("+") != -1) { encodedParam = param.replaceAll("\\+", "%2B"); }
        if(param.indexOf(",") != -1) { encodedParam = param.replaceAll(",", "%2C"); }
        if(param.indexOf("/") != -1) { encodedParam = param.replaceAll("/", "%2F"); }
        if(param.indexOf(":") != -1) { encodedParam = param.replaceAll(":", "%3A"); }
        if(param.indexOf(";") != -1) { encodedParam = param.replaceAll(";", "%3B"); }
        if(param.indexOf("=") != -1) { encodedParam = param.replaceAll("=", "%3D"); }
        if(param.indexOf("?") != -1) { encodedParam = param.replaceAll("\\?", "%3F"); }
        if(param.indexOf("@") != -1) { encodedParam = param.replaceAll("@", "%40"); }
        if(param.indexOf(".") != -1) { encodedParam = param.replaceAll(".", "%2C"); }
        return encodedParam;
    }
    public static String encodeXML(String param) {
        //return "<![CDATA[{param}]]>"
        String encodedParam = param.replaceAll("\"", "");
        if(param.indexOf("&") != -1) { encodedParam = encodedParam.replaceAll("&", "&amp;"); }
        if(param.indexOf("\'") != -1) { encodedParam = encodedParam.replaceAll("\'", ""); }
        //TODO: recheck \\?
        if(param.indexOf("?") != -1) { encodedParam = encodedParam.replaceAll("\\?", ""); }
        if(param.indexOf("<") != -1) { encodedParam = encodedParam.replaceAll("<", "&lt;"); }
        return encodedParam;
    }

    public static Boolean isNotBlank(String str) {
        return str != null && !str.trim().equals("");
    }

    public static int getBuzzImageID(Identity i) {
        //if can call - green
        //println("|||||| {i.getFirstName()} {i.getLastName()} - the preferred channels is {i.getPreferredChannels()}");
        if (i.getPreferredChannel(talkChannels) != null) {
            return R.drawable.buzz_free;
        } else {
            return R.drawable.buzz_busy;
        }
    }

    public static int getBuzzImageID(String preferredChannels) {
        //if can call - green
        if (Utils.isNotBlank(preferredChannels)) {
            for(String channel : preferredChannels.split(IdentityManager.DELIMETER)) {
                for(String ch : talkChannels) {
                    if (channel.startsWith(ch)) {
                        return R.drawable.buzz_free;
                    }
                }
            }
        }
        return R.drawable.buzz_busy;
    }

    public static Boolean isTalkChannels(String preferredChannels) {
        return isChannels(preferredChannels, talkChannels);
    }

    public static Boolean isTextChannels(String preferredChannels) {
        return isChannels(preferredChannels, textChannels);
    }

    public static Boolean isPostChannels(String preferredChannels) {
        return isChannels(preferredChannels, postChannels);
    }

    private static Boolean isChannels(String preferredChannels, String[] channels) {
        //if can call - green
        if (Utils.isNotBlank(preferredChannels)) {
            for(String channel : preferredChannels.split(IdentityManager.DELIMETER)) {
                for(String ch : channels) {
                    if (channel.startsWith(ch)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final String[] talkChannels = {"3", "4", "5", "6", "7", "8", "9", "10"};
    public static final String[] textChannels = {"3", "9", "11"};
    public static final String[] postChannels = {"12", "13", "14", "17"};

    public static String addApostrophes(String str) {
        String out = "";
        if (isNotBlank(str)) {
            if (str.lastIndexOf("s") != -1 || str.lastIndexOf("x") != -1) {
                return str += "'";
            } else {
                return str += "'s";
            }
        }
        return str;
    }
    
    public static String getPrincipalMSISDN(){
    	String  MSISDN = "+13106146629";
    	return MSISDN;
    }


}
