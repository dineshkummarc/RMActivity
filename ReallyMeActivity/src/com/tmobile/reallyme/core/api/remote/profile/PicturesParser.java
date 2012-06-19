package com.tmobile.reallyme.core.api.remote.profile;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:24
 */
class PicturesParser extends Parser<PicturesMetaData> {

    public PicturesParser() {
        super(new PicturesMetaData());
    }

    public void parse(JSONObject jsonObject) throws JSONException {
        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
            String key = iterator.next();
//            if (key.equals("status")) {
//                data.status = jsonObject.getString(key);
//            }
        }
     }

    protected  void clear(){
        data = new PicturesMetaData();    
    }
}