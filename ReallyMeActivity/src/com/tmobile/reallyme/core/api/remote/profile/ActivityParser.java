package com.tmobile.reallyme.core.api.remote.profile;

import java.util.Date;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:24
 */
class ActivityParser extends Parser<ActivitiesMetaData> {

    public ActivityParser() {
        super(new ActivitiesMetaData());
    }

    public void parse(JSONObject jsonObject) throws JSONException {
        ActivityMetaData activity = new ActivityMetaData();
        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
            String key = iterator.next();
            if (key.equals("status")) {
                activity.status = jsonObject.getString(key);
            } else if (key.equals("datetime")) {
                activity.dateTime = stringToDate(jsonObject.getString(key));
            } else if (key.equals("source")) {
                activity.source = jsonObject.getString(key);
            } else if (key.equals("statetype")) {
                activity.statusId = jsonObject.getInt(key);
            }
        }
        data.list.add(activity);
    }
    //2009-07-30 21:53:25.0
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
    private Date stringToDate(String date) {
        Date _date = null;
        try {
            _date = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _date;
    }

    protected  void clear(){
        data = new ActivitiesMetaData();
    }
}
