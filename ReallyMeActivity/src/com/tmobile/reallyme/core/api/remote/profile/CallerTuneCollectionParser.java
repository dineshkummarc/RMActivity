package com.tmobile.reallyme.core.api.remote.profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:24
 */
class CallerTuneCollectionParser extends Parser<CallerTuneCollectionMetaData> {

    public CallerTuneCollectionParser() {
        super(new CallerTuneCollectionMetaData());
    }

    // [
    // {"title":"Title DDx","expirationDate":"2001","VCode":"WL","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 98"},
    // {"title":"Title zfu","expirationDate":"2001","VCode":"&r","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 36"},
    // {"title":"Title rRY","expirationDate":"2001","VCode":"R3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 64"}
    // ]
    public void parse(JSONObject jsonObject) throws JSONException {
        CallerTuneMetaData _data = new CallerTuneMetaData();
        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
            String key = iterator.next();
             if (key.equals("title")) {
                _data.title = jsonObject.getString(key);
            } else if (key.equals("expirationDate")) {
                _data.expirationDate = new Date();
            } else if (key.equals("VCode")) {
                _data.VCode = jsonObject.getString(key);
            } else if (key.equals("primaryInterface")) {
                _data.primaryInterface = jsonObject.getString(key);
            } else if (key.equals("artist")) {
                _data.artist = jsonObject.getString(key);
            }
        }
    }

    protected  void clear(){
        data = new CallerTuneCollectionMetaData();
    }
}