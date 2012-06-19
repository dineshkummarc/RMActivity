package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.job.JobsManager;
import com.tmobile.reallyme.utils.Constants;

/**
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 15:48:26
 */
public class AuthenticationManager extends AbstarctXmlRequest{
    /* Location of REST url
     */
    public String getUrl() {return Constants.API_URL + "/login";}

//    Response:
//        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
//        <logined session_id="0d73ec5d-0f05-4285-ae55-229a6cf7dbcf" ok="true"/>
    protected void processStart(String namespaceURI, String localName,
                        String qName, Attributes atts) {
        if (localName.equals("logined")) {
            String ssid = atts.getValue("session_id");
            Boolean isOk = Boolean.valueOf(atts.getValue("ok"));
            log.info("AuthenticationManager return ssid=" + ssid);
            UserSession.getInstance().setSessionid(ssid);
            UserSession.save();
        }
    }
    protected void processEnd(String namespaceURI, String localName,
            String qName) {}

    /**
     * A function that gets called back when u finish parsing
     * from the PullParser
     */
    public void onEndDocument() {
        JobsManager.getInstance().start();
    }

    //Do login
    public void login(String clientId, String password) {
        String xml = XML_START + "<auth password=\"" + password + "\" client_id=\"" + clientId + "\"/>";
        post(xml);
    }

}
