package com.tmobile.reallyme.core.api.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Channel;
import com.tmobile.reallyme.core.api.remote.pojo.Context;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.api.remote.pojo.Item;
import com.tmobile.reallyme.core.api.remote.pojo.Location;
import com.tmobile.reallyme.core.api.remote.pojo.MdItem;
import com.tmobile.reallyme.core.api.remote.pojo.Profile;
import com.tmobile.reallyme.core.api.remote.pojo.State;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.utils.Utils;

/**
 *      Request  POST    http:/server:port/api/v1/mutualFriends
 *         2.20.2	Example Request
 *         <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *         <mutualFriends>
 *             <withId>37dcef63-34da-48a9-943b-1a17739309c9</withId>
 *         </mutualFriends>
 *
 *      Response:
 *          <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *          <result ok="true">
 *              <mutualFriends>
 *                  <identity id="37dcef63-34da-48a9-943b-1a17739309c9">
 *                      <channels>
 *                          <channel verified="true" value="+22562349219" id="3" cid="6aa8bb82-9af4-4810-ba5f-313d1f72c595">
 *                              <items/>
 *                          </channel>
 *                          <channel verified="true" value="8021334" id="2" cid="bac841e7-3462-491d-ad57-b538c607423f">
 *                              <items/>
 *                          </channel>
 *                      </channels>
 *                      <context>
 *                          <location time_zone="GMT" updateTime="2009-05-20T01:16:30.702+05:30"/>
 *                          <state updateTime="2009-05-20T01:16:24.233+05:30" id="7" description="to talk on a Party Line"/>
 *                      </context>
 *                      <profile>
 *                          <item value="F1" name="firstName"/>
 *                          <item value="" name="lastName"/>
 *                          <item value="null/images/nobody.png" name="imageUrl"/>
 *                          <item name="labId"/>
 *                      </profile>
 *                  </identity>
 *              </mutualFriends>
 *          </result>
 * User: Kolesnik Aleksey
 * Date: 16.07.2009
 * Time: 20:43:55
 */
public class MutualFriendsManager extends AbstarctXmlRequest{
    private List<Identity> identities = null;

    public void onEndDocument() {
        callBack(identities);
    }

    public void callBack(List<Identity> identities) {}

    public String getUrl() {
        return Constants.API_URL + "/mutualFriends?sid=" + UserSession.getSessionid();
    }

    /*Temporary variables*/
    private Boolean isProfile = false;
    private Item item;
    private Identity identity;
    private Profile profile;
    private Context context;
    private MdItem mdItem;

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("mutualFriends")) {
            // от нефиг делать :-)
            identities = new ArrayList();
        } else if (localName.equals("identity")) {
            String id = atts.getValue("id");
            identity = new Identity(id);
        } else if (localName.equals("context")) {
            context = new Context();
        } else if (localName.equals("location")) {
            String name = atts.getValue("name");
            String timeZone = atts.getValue("time_zone");
            context.location = new Location(name, timeZone);

            String updateTime = atts.getValue("updateTime");
            if (updateTime != null && !updateTime.equals("")) {
                //TODO: Kolesnik Aleksey
//                Date d:Date =  IsoDate.stringToDate(t, 3);
//
//                if (identity.lastUpdate == null or (d!=null and d.getTime()> identity.lastUpdate.getTime())) {
//                    //log.info("Got location update time = {t} and this UTC = {d}");
//                    identity.lastUpdate = d;
//                }
                identity.lastUpdate = new Date();
            }
        } else  if (localName.equals("state")) {
            Integer id = Integer.valueOf(atts.getValue("id"));
            String desc = atts.getValue("description");
            context.state = new State(id, desc);

            String updateTime = atts.getValue("updateTime");
            if (updateTime != null && !updateTime.equals("")) {
                //TODO: Kolesnik Aleksey
//                var d:Date =  IsoDate.stringToDate(t, 3);
//                if (identity.lastUpdate == null or (d!=null and d.getTime()> identity.lastUpdate.getTime())) {
//                    //log.info("Got update time = {t} and this UTC = {d}");
//                    identity.lastUpdate = d;
//                }
                identity.lastUpdate = new Date();
            }
        } else if (localName.equals("item")) {
            String name = atts.getValue("name");
            String value = atts.getValue("value");
            Boolean visible = Boolean.valueOf(atts.getValue("visible"));
            if (!Utils.isNotBlank(value)) {
                //some time this value equals null
                value = "";
            }
            item = new Item(name, value, visible);
        }  else if (localName.equals("profile")) {
                isProfile = true;
                profile = new Profile();
        } else if (localName.equals("channel")) {
            Integer id = Integer.valueOf(atts.getValue("id"));
            String cid = atts.getValue("cid");
            String value = atts.getValue("value");
            Boolean verified = Boolean.valueOf(atts.getValue("verified"));
            identity.getChannels().add(new Channel(id, cid, value, verified));
        } else if (localName.equals("mdItem")) {
            mdItem = new MdItem();
        } 
    }

    protected void processEnd(String namespaceURI, String localName, String qName) {
        if (localName.equals("identity")) {
            //identity.lastUpdate = new Date();
            this.identities.add(identity);
        } else if (localName.equals("context")) {
            identity.setContext(context);
        } else if (localName.equals("item")) {
            if (isProfile) {
                //if Md section insert to the Md and else it's frient section. Damn, does easier way to parse this xml exist ?
                profile.items.add(item);
            }
        } else if (localName.equals("profile")) {
            isProfile = false;
            identity.setProfile(profile);
        }
    }

    /*
    Get mutual griends
    */
    public void getFriends(String withId) {
       if (withId == null || withId =="") {
           //log.info("EXCEPTION: withId can't be null. Skip api invoke..");
           return;
       }
       String h = XML_START + "<mutualFriends><withId>" + withId + "</withId>\n</mutualFriends>";
       post(h);
    }
 }
