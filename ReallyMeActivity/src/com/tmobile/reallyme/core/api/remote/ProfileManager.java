package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.utils.Constants;

/**
 * User: Kolesnik Aleksey
 * Date: 20.07.2009
 * Time: 0:03:50
 */
public class ProfileManager  extends AbstarctXmlRequest {

    private ProfileMetaData result;

    public void onEndDocument() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUrl() {
        return Constants.API_URL + "/updateIdentity?sid=" + UserSession.getSessionid();
    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("update_identity_response")) {
            Boolean ok = Boolean.valueOf(atts.getValue("ok"));
            Boolean verificationRequired = Boolean.valueOf(atts.getValue("ok"));
            log.info("ProfileManager return " + ok);
            result = new ProfileMetaData(ok, verificationRequired);
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
    Update the identity
    */
    public void update() {
        /*Do validation*/
        String xml = XML_START + UserSession.getInstance().getIdentity();
        post(xml);
    }
}
