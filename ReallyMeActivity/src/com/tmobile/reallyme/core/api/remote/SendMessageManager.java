package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.utils.Constants;

/**
 * User: Kolesnik Aleksey
 * Date: 14.07.2009
 * Time: 11:06:09
 */
public class SendMessageManager extends AbstarctXmlRequest {
    private BaseMetaData result;

    public void onEndDocument() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUrl() {
        return Constants.API_URL + "/sendMessage?sid=" + UserSession.getSessionid();
    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("response")) {
            Boolean ok = Boolean.valueOf(atts.getValue("ok"));
            log.info("SendMessage returns " + ok);
            result = new BaseMetaData(ok);
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *  Send email to the User
     **/
    public void sendEmail(String recepient, String message) {
        send(MessageTypeEnum.POST, recepient, message);
    }

    /**
     *  Send sms tot the user
     **/
    public void sendSMS(String recepient, String message) {
        send(MessageTypeEnum.TEXT, recepient, message);
    }

    /*
        Send message to user
    */
    public void send(MessageTypeEnum type, String recipient, String message) {
        /*Do validation*/
        String xml = XML_START + "<messageRequest type=\"" + type.getId() + "\" recepient_id=\"" + recipient + "\">"
                + "<content><![CDATA[" + message + "]]></content></messageRequest>";
        post(xml);
    }
}
