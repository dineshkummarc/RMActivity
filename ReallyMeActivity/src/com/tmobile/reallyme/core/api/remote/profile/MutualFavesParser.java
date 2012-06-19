package com.tmobile.reallyme.core.api.remote.profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:24
 */
class MutualFavesParser extends Parser<MutualFavesMetaData> {

    public MutualFavesParser() {
        super(new MutualFavesMetaData());
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
        data = new MutualFavesMetaData();
    }
}