package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Principal;
import com.tmobile.reallyme.core.persistence.HereAndNowMemberManager;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 15.07.2009
 * Time: 18:25:50
 */
public class HereAndNowManager extends AbstarctXmlRequest{

    private Integer count = 0;

    public void onEndDocument() {
        log.info("Found here and now " + count);
    }

    public String getUrl() {
        return Constants.API_URL + "/hereandnow?sid=" + UserSession.getSessionid();
    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("hereandnow")) {
        } else if (localName.equals("principal")) {
            Principal principal = new Principal();
            principal.id = atts.getValue("id");
            principal.type = atts.getValue("type");
            if (Utils.isNotBlank(atts.getValue("distance"))) {
                principal.distance = Double.valueOf(atts.getValue("distance"));
            }
            if (Utils.isNotBlank(atts.getValue("score"))) {
                principal.score = Integer.valueOf(atts.getValue("score"));
            }
            HereAndNowMemberManager.insert(principal);
            count++;
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

     /*
    Get the here and now
    */
    public void getHereAndNow() {
        if (!active) {
            get();
       }
    }
}
