package com.tmobile.reallyme.core.api.remote.profile;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:53
 */
abstract class Parser<T extends ProfileMetaData> {
    protected T data = null;

    protected Parser(T data) {
        this.data = data;
    }

    /**
     * Parse and save to meta data
     */
    protected  abstract void parse(JSONObject jsonObject)  throws JSONException;

    /**
     * Clear
     */
    protected  abstract void clear();
    
    public T getData() {
        return data;
    }
}
