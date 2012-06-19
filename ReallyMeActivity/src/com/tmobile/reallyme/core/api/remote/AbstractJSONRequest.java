package com.tmobile.reallyme.core.api.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Kolesnik Aleksey
 * Date: 24.07.2009
 * Time: 17:13:59
 */
public abstract class AbstractJSONRequest extends AbstractRequest {
    protected JSONArray array = null;

    @Override
    protected void parsinResult(InputStream instream) {
        try {
            // A Simple JSONObject Creation

// Test Activity
//            String testResult =
//                    "[" +
//                    "{\"statusId\":\"7\", \"status\":\"Available to talk\", \"datetime\":\"2008-11-05T08:15:30-05:00\", \"source\":\"Twitter\"},"+
//                    "{\"statusId\":\"1\", \"status\":\"Watching TV\", \"datetime\":\"2008-12-05T08:15:30-05:00\", \"source\":\"Facebook\"}" +
//                    "]";

//Test CallerTuneCollection
//            String testResult = "[" +
//                    "{\"title\":\"Title DDx\",\"expirationDate\":\"2001\",\"VCode\":\"WL\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 98\"}," +
//                    "{\"title\":\"Title zfu\",\"expirationDate\":\"2001\",\"VCode\":\"&r\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 36\"}," +
//                    "{\"title\":\"Title rRY\",\"expirationDate\":\"2001\",\"VCode\":\"R3\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 64\"}" +
//                    "]";

//Test CallerTuneSelection
//            String testResult =
//                    "[" +
//                        "{\"id\":\"c572850e-f92a-4b5d-8c97-dd2e2e2cf693\"," +
//                        "\"mySelection\":[" +
//                            "{\"title\":\"Title DDx\",\"expirationDate\":\"2001\",\"VCode\":\":i\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 72\"}," +
//                            "{\"title\":\"Title zfu\",\"expirationDate\":\"2001\",\"VCode\":\"hJ\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 67\"}," +
//                            "{\"title\":\"Title rRY\",\"expirationDate\":\"2001\",\"VCode\":\"t.\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 10\"}," +
//                            "{\"title\":\"Title rRY\",\"expirationDate\":\"2001\",\"VCode\":\"Qb\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 63\"}" +
//                        "]," +
//                        "\"selectionForMe\":[], \"name\":\"U77 U177\",\"imageURL\":\"/images/nobody.png\"}," +
//
//                        "{\"id\":\"1398cce8-6b02-4d5f-b2e5-e069c2d2d0ac\"," +
//                        "\"mySelection\":[" +
//                            "{\"title\":\"Title DDx\",\"expirationDate\":\"2001\",\"VCode\":\"ck\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 15\"}," +
//                            "{\"title\":\"Title zfu\",\"expirationDate\":\"2001\",\"VCode\":\"`e\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 95\"}," +
//                            "{\"title\":\"Title rRY\",\"expirationDate\":\"2001\",\"VCode\":\"z3\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 01\"}," +
//                            "{\"title\":\"Title rRY\",\"expirationDate\":\"2001\",\"VCode\":\"S3\",\"primaryInterface\":\"com.real.rbt.legacy.tmo.service.ctma.util.gupapi.xml.CallerTuneType\",\"artist\":\"Artist 00\"}" +
//                        "]," +
//                        "\"selectionForMe\":[], \"name\":\"U7 U17\",\"imageURL\":\"/images/nobody.png\"}" +
//                    "]";

//            array = new JSONArray(testResult);
            array = new JSONArray(convertStreamToString(instream));
            log.info("<jsonobject>\n"+array.toString()+"\n</jsonobject>");
            // A Simple JSONObject Parsing
            for (int i = 0; i < array.length(); i++) {
                parse(array.getJSONObject(i));
            }
            onEndDocument();
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
    }

    protected abstract void parse(JSONObject jsonObject) throws JSONException;
    
    /*
    * To convert the InputStream to String we use the BufferedReader.readLine()
    * method. We iterate until the BufferedReader return null which means
    * there's no more data to read. Each line will appended to a StringBuilder
    * and returned as String.
    */
    protected String  convertStreamToString(InputStream is) {
       BufferedReader reader = new BufferedReader(new InputStreamReader(is));
       StringBuilder sb = new StringBuilder();

       String line = null;
       try {
           while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               is.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       log.info(sb.toString());
       return sb.toString();
    }

}
