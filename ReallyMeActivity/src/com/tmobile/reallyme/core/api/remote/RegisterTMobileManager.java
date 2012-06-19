package com.tmobile.reallyme.core.api.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.api.remote.pojo.tmo.TMOAuthenticationData;
import com.tmobile.reallyme.core.api.remote.pojo.tmo.TMOAuthenticationError;
import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class RegisterTMobileManager extends AbstarctXmlRequest{
    private Boolean isError = false;
    private TMOAuthenticationData tmoAuthenticationData = new TMOAuthenticationData();
    private TMOAuthenticationError tmoAuthenticationError = new TMOAuthenticationError();

    public String getUrl() {
        return Constants.TMO_LOGIN_API_URL;
    }

    public void onEndDocument() {
        log.info("TMO AUTHENTICATION : isAuthenticated = "+ tmoAuthenticationData.isAuthenticated()+",  imsi = "+ tmoAuthenticationData.getImsi());
    }


    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("authenticationData")) {
            tmoAuthenticationData.setAccountStatus(atts.getValue("accountStatus"));
            tmoAuthenticationData.setAccountSubType(atts.getValue("accountSubType"));
            tmoAuthenticationData.setAccountType(atts.getValue("accountType"));
            tmoAuthenticationData.setBan(atts.getValue("ban"));
            tmoAuthenticationData.setRegistrationStatus(atts.getValue("registrationStatus"));
            tmoAuthenticationData.setServiceLevel(atts.getValue("serviceLevel"));

            tmoAuthenticationData.setHasMasterPrivileges(Boolean.valueOf(atts.getValue("hasMasterPrivileges")));
            tmoAuthenticationData.setAuthenticated(Boolean.valueOf(atts.getValue("isAuthenticated")));
            tmoAuthenticationData.setPrepaid(Boolean.valueOf(atts.getValue("isPrepaid")));
            tmoAuthenticationData.setTakeControlSubscriber(Boolean.valueOf(atts.getValue("isTakeControlSubscriber")));
            tmoAuthenticationData.setMustChangePassword(Boolean.valueOf(atts.getValue("mustChangePassword")));

            tmoAuthenticationData.setMsisdn(atts.getValue("msisdn"));
            tmoAuthenticationData.setImsi(atts.getValue("imsi"));
            tmoAuthenticationData.setFirstName(atts.getValue("firstName"));
            tmoAuthenticationData.setLastName(atts.getValue("lastName"));
            tmoAuthenticationData.setUsername(atts.getValue("username"));
        }else if(localName.equals("error")){
            isError = true;
            tmoAuthenticationError.setId(atts.getValue("id"));
            tmoAuthenticationError.setDetail(atts.getValue("detail"));
            tmoAuthenticationError.setMessage(atts.getValue("message"));
        }
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
    }

    public void login(){
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("msisdn", "username"));
        nvps.add(new BasicNameValuePair("password", "password"));
        nvps.add(new BasicNameValuePair("src", ""));

        post(nvps);        
    }

   
}
