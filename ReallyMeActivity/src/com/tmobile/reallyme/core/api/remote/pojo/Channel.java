package com.tmobile.reallyme.core.api.remote.pojo;

/**
 * User: Kolesnik Aleksey
 * Date: 03.07.2009
 * Time: 0:06:36
 */

public class Channel {
    //public static Integer Twitter = 1;
//public static Integer FaceBook = 2;
public static final Integer Mobile_Phone = 3;
//public static Integer Voice_Mail  = 17;
//public static Integer Orkut  = 18;
//public static Integer Friendster = 20;
//public static Integer Skype_Call_VOIP = 4;
//public static Integer Home_Phone  = 5;
//public static Integer Skype_Call  = 6;
//public static Integer Work_Phone =7;
//public static Integer Hotel_Phone =8;
//public static Integer Pager =9;
//public static Integer Skype_IM =10;
//public static Integer SMS=11;
//public static Integer Work_Email =12;
//public static Integer Primary_Email =13;
//public static Integer Secondary_Email= 14;
//public static Integer Gmail =21;
//public static Integer Yahoo_Email= 22;
//public static Integer Hotmail =23;
//public static Integer Equals= 15;
//public static Integer Google_Talk = 16;
//public static Integer Virgin= 19;
//public static Integer Custom= 20;
    public Integer id;
    public String cid;
    public String value;
    public Boolean verified;

    public Channel(Integer id, String cid, String value, Boolean verified) {
        this.id = id;
        this.cid = cid;
        this.value = value;
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "<channel verified=\"" + verified + "\" value=\"" + value + "\" id=\"" + id + "\"/>\n";
    }
}

