package com.tmobile.reallyme.core.api.remote.profile;

import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Kolesnik Aleksey
 * Date: 27.07.2009
 * Time: 13:49:24
 */
class CallerTuneSelectionParser extends Parser<CallerTuneSelectionMetaData> {

    public CallerTuneSelectionParser() {
        super(new CallerTuneSelectionMetaData());
    }

    // [
    // {"id":"c572850e-f92a-4b5d-8c97-dd2e2e2cf693",
    // "mySelection":[
    //      {"title":"Title cRG","expirationDate":"2001","VCode":":i","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 72"},
    //      {"title":"Title Ses","expirationDate":"2001","VCode":"hJ","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 67"},
    //      {"title":"Title huv","expirationDate":"2001","VCode":"t.","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 10"},
    //      {"title":"Title zZk","expirationDate":"2001","VCode":"Qb","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 63"}
    // ],
    // "selectionForMe":[],"name":"U77 U177","imageURL":"/images/nobody.png"},
    //
    // {"id":"1398cce8-6b02-4d5f-b2e5-e069c2d2d0ac",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U7 U17","imageURL":"/images/nobody.png"},
    //
    // {"id":"b15571f2-8bd9-491b-9156-a54b78826f06",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U2 U12","imageURL":"/images/nobody.png"},
    //
    // {"id":"d2336b20-9fa1-45b9-88fb-296048b9026e",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U6 U16","imageURL":"/images/nobody.png"},
    //
    // {"id":"289ba932-847d-4236-a699-89da6559aea7",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U1 U11","imageURL":"/images/nobody.png"},
    //
    // {"id":"17df4ffb-6c6f-4821-bd51-695a2cbf89fd",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U5 U15","imageURL":"/images/nobody.png"},
    //
    // {"id":"2b630f29-4674-48e0-88fb-e8f0fa6b8276",
    // "mySelection":[
    //      {"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U3 U13","imageURL":"/images/nobody.png"},
    //
    // {"id":"b075bfc9-ba18-4e06-8d64-1867a8657a29",
    // "mySelection":[{"title":"Title hWu","expirationDate":"2001","VCode":"ck","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 15"},
    //      {"title":"Title ddz","expirationDate":"2001","VCode":"`e","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 95"},
    //      {"title":"Title UPR","expirationDate":"2001","VCode":"z3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 01"},
    //      {"title":"Title Vdm","expirationDate":"2001","VCode":"S3","primaryInterface":"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType","artist":"Artist 00"}
    // ],
    // "selectionForMe":[],"name":"U4 U14","imageURL":"/images/nobody.png"}
    // ]
    public void parse(JSONObject jsonObject) throws JSONException {
        CallerTuneSelectionElementMetaData _data = new CallerTuneSelectionElementMetaData();
        for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
            String key = iterator.next();

            if (key.equals("id")) {
                _data.id = jsonObject.getString(key);
            } else if (key.equals("mySelection")) {
                if (!jsonObject.isNull(key)) {
                    JSONArray mySelectionArray = jsonObject.getJSONArray(key);
                    for (int i = 0; i < mySelectionArray.length(); i++) {
                        _data.mySelection.add(createCallerTune(mySelectionArray.getJSONObject(i)));
                    }
                }
            } else if (key.equals("selectionForMe")) {
                if (!jsonObject.isNull(key)) {
                    JSONArray selectionForMeArray = jsonObject.getJSONArray(key);
                    for (int i = 0; i < selectionForMeArray.length(); i++) {
                        _data.selectionForMe.add(createCallerTune(selectionForMeArray.getJSONObject(i)));
                    }
                }
            } else if (key.equals("name")) {
                _data.name = jsonObject.getString(key);
            } else if (key.equals("imageURL")) {
                _data.imageURL = jsonObject.getString(key);
            }
        }
        data.list.add(_data);
    }

    private CallerTuneMetaData createCallerTune(JSONObject json) throws JSONException {
        CallerTuneMetaData callerTuneMetaData = new CallerTuneMetaData();
        for (Iterator<String> iterator = json.keys(); iterator.hasNext();) {
            String key = iterator.next();
            if (key.equals("title")) {
                callerTuneMetaData.title = json.getString(key);
            } else if (key.equals("expirationDate")) {
                callerTuneMetaData.expirationDate = new Date();
            } else if (key.equals("VCode")) {
                callerTuneMetaData.VCode = json.getString(key);
            } else if (key.equals("primaryInterface")) {
                callerTuneMetaData.primaryInterface = json.getString(key);
            } else if (key.equals("artist")) {
                callerTuneMetaData.artist = json.getString(key);
            }
        }
        return callerTuneMetaData;
    }
    
    protected  void clear(){
        data = new CallerTuneSelectionMetaData();
    }
}