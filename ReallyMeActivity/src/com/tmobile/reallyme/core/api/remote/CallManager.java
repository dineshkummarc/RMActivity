package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.utils.Constants;

/**
 *
 *    Request:
 *    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *        <call>
 *            <id>firstparticipantId</id>
 *            <id>secondParticipantId</id>
 *        </call>
 *
 *   Response:
 *   <? xml version="1.0" encoding="UTF-8" standalone="yes"?><response ok="true"/>
 *
 *
 * User: Kolesnik Aleksey
 * Date: 14.07.2009
 * Time: 10:50:41
 */
public class CallManager extends AbstarctXmlRequest {
    private BaseMetaData result;

    public void onEndDocument() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUrl() {
        return Constants.API_URL + "/call?sid=" + UserSession.getSessionid();
    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("response")) {
            Boolean ok = Boolean.valueOf(atts.getValue("ok"));
            log.info("Call return " + ok);
            result = new BaseMetaData(ok);
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
      Call the pasrticipants
    */
    public void call(String participants) {
        String[] str = {participants};
        call(str);
    }

    /*
      Call the pasrticipants
    */
    public void call(String[] participants) {
        /*Do validation*/
        String xml = XML_START + "<call>" + generatePartisipants(participants) + "</call>";
        post(xml);
    }

    private String generatePartisipants(String[] participants) {
        StringBuilder h = new StringBuilder();
        for (String p : participants) {
            h.append("<id>").append(p).append("</id>");
        }
        return h.toString();
    }
}
