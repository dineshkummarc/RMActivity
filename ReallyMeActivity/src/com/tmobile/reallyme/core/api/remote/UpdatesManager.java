package com.tmobile.reallyme.core.api.remote;

import java.util.Date;
import java.util.LinkedList;

import org.xml.sax.Attributes;

import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Channel;
import com.tmobile.reallyme.core.api.remote.pojo.Context;
import com.tmobile.reallyme.core.api.remote.pojo.Contract;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.api.remote.pojo.Item;
import com.tmobile.reallyme.core.api.remote.pojo.Location;
import com.tmobile.reallyme.core.api.remote.pojo.Md;
import com.tmobile.reallyme.core.api.remote.pojo.MdItem;
import com.tmobile.reallyme.core.api.remote.pojo.Message;
import com.tmobile.reallyme.core.api.remote.pojo.Preference;
import com.tmobile.reallyme.core.api.remote.pojo.Profile;
import com.tmobile.reallyme.core.api.remote.pojo.State;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.IdentityTypeEnum;
import com.tmobile.reallyme.utils.Constants;

/**
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 13:04:25
 */

/**
Request GET
Response:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<updates id="a756865a-b3b8-4765-b5dd-8739901a2cf0#0" ok="true">
    <friends>
        <identity id="0cbb81c8-97d4-48fb-b8c2-8b821a931323">
            <channels>
                <channel verified="true" value="521532583" id="2"/>
            </channels>
            <profile>
                <item value="Amit" name="firstName"/>
                <item value="Singhai" name="lastName"/>
                <md>
                  <mdItem>
                    <item name="mdType" value="recentUpdates"/>
                    <item name="text" value="Updated pictures on FB 3 hours ago."/>
                    <item name="link" value="http://www.facebook.com/group.php?sid=c0a049701d94a7b4d362da82833222f1&gid=2386855151&ref=search"/>
                    <item name="source" value="Facebook"/>
                    <item name="images" value="[http://profile.ak.facebook.com/v224/167/77/q610882313_7658.jpg][http://profile.ak.facebook.com/profile6/1149/116/q555504334_5317.jpg]"/>
                  </mdItem>
                  <mdItem>
                    <item name="mdType" value="foaf"/>
                    <item name="list" value="
                        <friends>
                            <identity id="0cbb81c8-97d4-48fb-b8c2-8b821a931323">
                                <profile>
                                    <item value="Amit" name="firstName"/>
                                    <item value="Singhai" name="lastName"/>
                                    <item value="http://dev-ind1.equals.com:8080/file.download?id=726bd21d-2b8c-4945-9bf5-8a1f9aaa8264" name="imageUrl"/>
                                </profile>
                            </identity>
                        </friends>"/>
                   </mdItem>
                  </md>
            </profile>
        </identity>
    </friends>
    <contracts>
        <contract with_id="0cbb81c8-97d4-48fb-b8c2-8b821a931323" type="I" groupId="3"/>
        <contract with_id="fee857d1-c13d-466c-a231-7d79f65d4a1a" type="I" groupId="3"/>
        <contract with_id="cb87bdf7-e166-4a93-a346-1b0f2da1fc2c" type="I" groupId="3"/>
        <contract with_id="107a706f-4791-4809-8f06-f08d99abdb83" type="I" groupId="3"/>
        <contract with_id="025982eb-17ce-433d-aae4-057dcdf9946f" type="I" groupId="3"/>
        <contract with_id="b251095a-aae9-4080-94d0-551498fb6054" type="I" groupId="3"/>
        <contract with_id="8" type="G">
            <access>
                <item visible="true" name="locationName"/>
                <item visible="true" name="locationDesc"/>
                <item visible="true" name="stateName"/>
                <item visible="true" name="stateDesc"/>
            </access>
            <preferences>
                <pref state_id="1" channel_id="3"/>
                <pref state_id="2" channel_id="3"/>
                <pref state_id="3" channel_id="3"/>
                <pref state_id="4" channel_id="3"/>
                <pref state_id="5" channel_id="3"/>
                <pref state_id="6" channel_id="3"/>
                <pref state_id="7" channel_id="3"/>
                <pref state_id="8" channel_id="3"/>
            </preferences>
        </contract>
        <contract with_id="1" type="G">
            <access>
                <item visible="true" name="locationName"/>
                <item visible="true" name="locationDesc"/>
                <item visible="true" name="stateName"/>
                <item visible="true" name="stateDesc"/>
            </access>
            <preferences>
                <pref state_id="1" channel_id="3"/>
                <pref state_id="2" channel_id="3"/>
                <pref state_id="3" channel_id="3"/>
                <pref state_id="4" channel_id="3"/>
                <pref state_id="5" channel_id="3"/>
                <pref state_id="6" channel_id="3"/>
                <pref state_id="7" channel_id="3"/>
                <pref state_id="8" channel_id="3"/>
            </preferences>
        </contract>
        <contract with_id="7" type="G">
            <access>
                <item visible="true" name="locationName"/>
                <item visible="true" name="locationDesc"/>
                <item visible="true" name="stateName"/>
                <item visible="true" name="stateDesc"/>
            </access>
            <preferences>
                <pref state_id="1" channel_id="3"/>
                <pref state_id="2" channel_id="3"/>
                <pref state_id="3" channel_id="3"/>
                <pref state_id="4" channel_id="3"/>
                <pref state_id="5" channel_id="3"/>
                <pref state_id="6" channel_id="3"/>
                <pref state_id="7" channel_id="3"/>
                <pref state_id="8" channel_id="3"/>
            </preferences>
        </contract>
        <contract with_id="4" type="G">
            <access>
                <item visible="true" name="locationName"/>
                <item visible="true" name="locationDesc"/>
                <item visible="true" name="stateName"/>
                <item visible="true" name="stateDesc"/>
            </access>
            <preferences>
                <pref state_id="1" channel_id="3"/>
                <pref state_id="2" channel_id="3"/>
                <pref state_id="3" channel_id="3"/>
                <pref state_id="4" channel_id="3"/>
                <pref state_id="5" channel_id="3"/>
                <pref state_id="6" channel_id="3"/>
                <pref state_id="7" channel_id="3"/>
                <pref state_id="8" channel_id="3"/>
            </preferences>
        </contract>
    </contracts>
    <identity id="db1a147f-b0cf-4379-a2f3-ac96401c582e">
        <channels>
            <channel verified="true" value="774088254" id="2"/>
            <channel verified="true" value="+16043764280" id="3"/>
        </channels>
        <profile>
            <item value="Rajpal" name="firstName"/>
            <item value="Chauhan" name="lastName"/>
            <item value="http://imageAddress.com/myImage" name="imageUrl"/>
            <item value="My tshirt says Simple living high Thinking" name="aboutMe"/>
            <item value="Scuba Diving, bungy Jumping" name="activities"/>
            <item value="Partying with Girls" name="interests"/>
            <item value="Committed" name="relationshipStatus"/>
            <item value="Mohali, Punjab" name="hometownLoc"/>
            <item value="6th Oct 1947" name="birthDay"/>
         </profile>
    </identity>
     <messages>
        <message type="3" time="06 May 2009 16:39:34.0 GMT+00:00" recepient_id="bf4f593f-d1af-4b01-ae8d-8c3a443f11e0" is_read="false" id="ca80a7c5-91ff-47c3-b673-b7c5c91ff216" direction="sent"/>
        <message type="3" time="06 May 2009 18:56:09.0 GMT+00:00" recepient_id="6ecdcab6-3feb-45c6-aa16-5b33a7ec8131" is_read="false" id="cfd74912-85c5-42a4-9341-815027314b65" direction="sent"/>
        <message type="3" time="06 May 2009 16:41:13.0 GMT+00:00" sender_id="bf4f593f-d1af-4b01-ae8d-8c3a443f11e0" is_read="false" id="63e228a1-7507-4628-af13-885389278615" direction="sent"/>
    </messages>
</updates>
*/

public class UpdatesManager extends AbstarctXmlRequest {
    private static UpdatesManager _instance = new UpdatesManager();

    public static UpdatesManager getInstance() {
        return _instance;
    }

    // temporary variables needed during processing
    private UpdatesMetaData result;

    private Boolean initialUpdate = true;

//
//    /* Location of REST url
//     */
//    override var url = bind "{Constants.API_URL}/updates?sid={UserSession.sessionid}" ;
//    var confirmurl = bind "{Constants.API_URL}/confirmUpdatesRetrieved?sid={UserSession.sessionid}" ;
//    var contactDataSource : ContactDataSource = Persistence.getContactDataSource();
//    var hereAndNowDataSource : HereAndNowDataSource = Persistence.getHereAndNowDataSource();
//    var hereAndNowHackedDataSource : HereAndNowDataSource = Persistence.getHereAndNowDataSource();
//    var conversationDataSource : ConversationDataSource = Persistence.getConversationDataSource();
    public void onEndDocument() {
        if (result != null) {
            log.info("Updates id = "+result.getId()+", friend count = " + result.friendCount + ", messages = "+result.getMessages().size());
            log.info("GOT THE UPDATE :\n" + result);
            //Confirm reseived update
            new ConfirmManager(result.getId()).confirm();
        }
    }

    public String getUrl() {
        return Constants.API_URL + "/updates?sid=" + UserSession.getSessionid();
    }

    /*Temporary variables*/
    private Boolean isFriend = false;
    private Boolean isContract = false;
    private Boolean isMd = false;
    private Boolean isProfile = false;
    private Channel channel;
    private Item item;
    private Identity identity;
    private Contract contract;
    private Profile profile;
    private Context context;
    private Md md;
    private MdItem mdItem;
    private Message m;

    protected void processStart(String namespaceURI, String localName, String qName, Attributes atts) {
        if (localName.equals("updates")) {
            Boolean ok = Boolean.valueOf(atts.getValue("ok"));
            String id = atts.getValue("id");

            result = new UpdatesMetaData(id, ok);
        } else if (localName.equals("friends")) {
           isFriend = true;
        } else if (localName.equals("contracts")) {
             isContract = true;
        } else if (localName.equals("md")) {
             isMd = true;
             md = new Md();
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
        } else if (localName.equals("contract")) {
            String type = atts.getValue("type");
            String withId = atts.getValue("with_id");
            contract = new Contract(type, withId);

            String groupId = atts.getValue("groupId");
            if (groupId != null && !groupId.equals("")) {
                contract.groupid = groupId;
            }
        } else if (localName.equals("pref")) {
            Integer stateId = Integer.valueOf(atts.getValue("state_id"));
            Integer channelId = Integer.valueOf(atts.getValue("channel_id"));
            contract.preferences.add(new Preference(stateId, channelId));
        } else if (localName.equals("mdItem")) {
            mdItem = new MdItem();
        } else  if (localName.equals("message")) {
            String direction = atts.getValue("direction");
            String id = atts.getValue("id");
            Boolean isRead = Boolean.valueOf(atts.getValue("is_read"));
            String recepientIid = atts.getValue("recepient_id");
            String senderId = atts.getValue("sender_id");

            String time = atts.getValue("time");
            Integer typeId = Integer.valueOf(atts.getValue("type"));
            MessageTypeEnum messageType = MessageTypeEnum.UNDEFINED;
            for(MessageTypeEnum value : MessageTypeEnum.values()) {
                if (value.getId().equals(typeId)) {
                    messageType = value;
                    break;
                }
            }
            m =  new Message(recepientIid, senderId, isRead, direction, messageType, time);

            if (time != null && !time.equals("")){
                //TODO:Kolesnik Aleksey
//                var d:Date =  IsoDate.stringToDate(t, 3);
//                m.lastUpdate = d;
                 m.lastUpdate = new Date();
            }

        } else if (localName.equals("content")) {
            //TODO: Kolesnik Aleksey recheck
            m.content = namespaceURI;//event.text;
        }
    }

    protected void processEnd(String namespaceURI, String localName,
            String qName) {
        if (localName.equals("friends")) {
           isFriend = false;
        } else if (localName.equals("contracts")) {
             isContract = false;
        } else if (localName.equals("md")) {
             isMd = false;
             profile.md = md;
        } else if (localName.equals("identity")) {
            //identity.lastUpdate = new Date();
            if (isFriend) {
                result.friendCount++;
                IdentityManager.insert(identity, IdentityTypeEnum.FRIEND);
                if (initialUpdate) {
//                    contactDataSource.addWithoutcomet(identity);
                }
            } else if (!isFriend && !isContract) {
                UserSession.getInstance().setIdentity(identity);
                UserSession.save();
            }
        } else if (localName.equals("context")) {
            identity.setContext(context);
        } else if (localName.equals("item")) {
            if (isContract) {
                contract.access.add(item);
            } else if (isProfile) {
                //if Md section insert to the Md and else it's frient section. Damn, does easier way to parse this xml exist ?
                if (isMd) {
                    mdItem.items.add(item);
                } else {
                    profile.items.add(item);
                }
            }
        } else if (localName.equals("profile")) {
            isProfile = false;
            identity.setProfile(profile);
        } else if (localName.equals("contract")) {
            result.getContracts().add(contract);
        } else if (localName.equals("mdItem")) {
            md.mdItems.add(mdItem);
        } else  if (localName.equals("message")) {
            result.getMessages().add(m);
        }
    }

   /*
    Get the new updates
    */
    public void getUpdates() {
        if (!active) {
            get();
       } else  {
           log.info("Waiting for previous Update...");
           //wait this update first
       }
    }
}

class UpdatesMetaData {
    private String id;
    private Boolean ok;
    public Integer friendCount = 0;
    private LinkedList<Contract> contracts = new LinkedList();
    private LinkedList<Message> messages = new LinkedList();

    UpdatesMetaData(String id, Boolean ok) {
        this.id = id;
        this.ok = ok;
    }

    public Boolean isOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(LinkedList<Contract> contracts) {
        this.contracts = contracts;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }
}