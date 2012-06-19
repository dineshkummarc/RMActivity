package com.tmobile.reallyme.core.api.remote;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.utils.Utils;

/**
 * Created by IntelliJ IDEA.
 * User: Kolesnik Aleksey
 * Date: 04.07.2009
 * Time: 18:51:14
 */
public class ConfirmManager extends AbstarctXmlRequest{
    private String id;
    ConfirmManager(String id) {this.id = id;}


    public void onEndDocument() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUrl() {
        return Constants.API_URL + "/confirmUpdatesRetrieved?sid=" + UserSession.getSessionid() + "&id=" + Utils.encodeParameters(id);
    }

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void confirm() {
        log.info("Confirm received update! id is " + id + " " + getUrl());
        get();
    }
}
