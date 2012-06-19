package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.utils.Constants;

/**
 *
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 15:30:05
 */
public class RegisterManager extends AbstarctXmlRequest {

    // temporary variables needed during processing
    private BaseMetaData result;

    /* Location of REST url
     */
    public String getUrl() {return Constants.API_URL + "/register";}

    protected void processStart(String namespaceURI, String localName,
                        String qName, Attributes atts) {
        /*
         *<reg status="0" session_id="d7d45daa-5993-491b-8c18-70d22d02b1ed" password="5245" client_id="1c84ec55-c527-4755-a32e-7ca29db8e213" ok="true"/>
         */
        if (localName.equals("reg")) {
            String status = atts.getValue("status");
            String sessionId = atts.getValue("session_id");
            String password = atts.getValue("password");
            String clientId = atts.getValue("client_id");
            Boolean ok = Boolean.valueOf(atts.getValue("ok"));
            UserSession userSession = UserSession.getInstance();
            userSession.setSessionid(sessionId);
            userSession.setClientId(clientId);
            userSession.setPassword(password);
            userSession.save();
            log.info("Registration return status=" + status + ". Received new sessionId = " + sessionId + " . Client " + clientId + ". Password " + password + "");
            result = new BaseMetaData(ok);
        } else {
            result = new BaseMetaData(false);
        }
    }
    protected void processEnd(String namespaceURI, String localName,
            String qName) {}

    /**
     * A function that gets called back when u finish parsing
     * from the PullParser
     */
    public void onEndDocument() {
        //Store application clientid and password for future
        if (result.isOk()) {
            endHook();
        } else {
            errorAction();
        }
    }

    public void endHook() {}
    public void errorAction() {}

//    Request:
//    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
//    <init deviceId="BlackBerry-1234">
//        <identity>
//            <channels>
//                <channel verified="true" value="+16043764280" id="3"/>
//                <channel verified="true" value="774088254" id="2"/>
//                <channel verified="false" value="macloud239" id="4"/>
//            </channels>
//            <profile>
//                <item value="Pavel" name="firstName"/>
//                <item value="Feldman" name="lastName"/>
//                <item value="Vancouver" name="city"/>
//                <item value="yes!!!" name="sex"/>
//            </profile>
//        </identity>
//    </init>
//
//   Response:
//   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
//   <reg status="0" session_id="d7d45daa-5993-491b-8c18-70d22d02b1ed" password="5245" client_id="1c84ec55-c527-4755-a32e-7ca29db8e213" ok="true"/>

    /*
     * Register new user.
     * Channel values and profile items. Which profile items are supported is still to be decided. First name, last name
     * and mobile phone number are mandatory.
    */
    public void register(Identity ident) {
        /*Do validation*/
        String xml = XML_START + "<init deviceId=\"javaFX-"+ System.currentTimeMillis() +"\">" + ident + "</init>";
        post(xml);
    }
}
