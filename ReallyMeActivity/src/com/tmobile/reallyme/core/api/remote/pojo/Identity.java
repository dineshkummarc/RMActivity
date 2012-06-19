package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.utils.Utils;


public class Identity extends BaseData{
    private String fname;
    private String lname;
    private String avatar;
    private String activities;
    private String aboutMe;
    private String interests;
    private String relationshipStatus;
    private String hometownLoc;
    private String birthDay;
    private String buzz;
    private List<String> preferredChannels = new ArrayList();

    private List<String> mutualFriendsUIDS = new ArrayList();
    private List<Channel> channels = new ArrayList();
    private Profile profile;
    private Context context;
    
    public Identity() {}

    public Identity(String id) { this.id = id;}

    public String getFname() {
        if (fname == null) {
            fname = getProfileItem("firstName");
        }
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        if (lname == null) {
            lname = getProfileItem("lastName");
        }
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAvatar() {
        if (avatar == null) {
            avatar = getProfileItem("imageUrl");
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getActivities() {
        if (activities == null) {
            activities = getProfileItem("activities");
        }
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getAboutMe() {
        if (aboutMe == null) {
            aboutMe = getProfileItem("aboutMe");
        }
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getInterests() {
        if (interests == null) {
            interests = getProfileItem("interests");
        }
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getRelationshipStatus() {
        if (relationshipStatus == null) {
            relationshipStatus = getProfileItem("relationshipStatus");
        }
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getHometownLoc() {
        if (hometownLoc == null) {
            hometownLoc = getProfileItem("hometownLoc");
        }
        return hometownLoc;
    }

    public void setHometownLoc(String hometownLoc) {
        this.hometownLoc = hometownLoc;
    }

    public String getBirthDay() {
        if (birthDay == null) {
            birthDay = getProfileItem("birthDay");
        }
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBuzz() {
        return buzz;
    }

    public void setBuzz(String buzz) {
        this.buzz = buzz;
    }

    public List<String> getMutualFriendsUIDS() {
        return mutualFriendsUIDS;
    }

    public void setMutualFriendsUIDS(List<String> mutualFriendsUIDS) {
        this.mutualFriendsUIDS = mutualFriendsUIDS;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPreferredChannels(List<String> preferredChannels) {
        this.preferredChannels = preferredChannels;
    }
    
    public List<String> getPreferredChannels() {
        if (preferredChannels.isEmpty()) {
            if (profile != null && !profile.items.isEmpty()) {
                for(Item item : profile.items) {
                    if (item.name.equals("preferredChannel")) {
                        preferredChannels.add(item.value);
                    }
                }
            } else {
                return preferredChannels;
            }
        }
        return preferredChannels;
    }

    public String getPreferredChannel(String ch) {
        if (getPreferredChannels() != null) {
            for (String i : getPreferredChannels()) {
                if (i.startsWith(ch)) {
                    return i.substring(i.indexOf("/")+1);
                }
            }
        }
        return null;
    }

    public String getPreferredChannel(String[] chs) {
        if (getPreferredChannels() != null) {
            for (String i : getPreferredChannels()) {
                for(String ch : chs) {
                    if (i.startsWith(ch)) {
                        return i.substring(i.indexOf("/")+1);
                    }
                }
            }
        }
        return null;
    }

    /*
    *@return principal MSISDN or empty array
    */
    public Map<String, Boolean> getMSISDN() {
        Map<String, Boolean> map = new HashMap();
        
        for (Channel channel : channels) {
          	if (channel.id.equals(Channel.Mobile_Phone)) {
                 map.put(channel.value, channel.verified);        
            }
        }
        return map;
    }

    /*
    *Find profile item by name
    */
    public String  getProfileItem(String name) {
        if (profile != null && profile.items != null) {
            for(Item item : profile.items) {
                if (item.name.equals(name)) {
                    return item.value;
                }
            }
        }
        return "";
    }

    public com.tmobile.reallyme.application.enums.State getState() {
        return State.values()[context.state.id-1];
    }

    public String toString() {
        StringBuilder h = new StringBuilder();
        if (lastUpdate != null) {
            //TODO: Kolesnik Aleksey
            h.append("<identity id=\"").append(id).append("\" lastUpdate=\"").append(/*IsoDate.dateToString(lastUpdate, 3)*/"").append("\">\n");
        } else {
            h.append("<identity id=\"").append(id).append("\">\n");
        }

        if (channels.size() > 0) {
            h.append("<channels>\n");
            for(Channel channel : channels){h.append(channel);}
            h.append("</channels>\n");
        }
        if (context != null) {
            h.append(context);
        }
        if (profile != null) {
            h.append(profile);
        }
        h.append("</identity>\n");
        return h.toString();
    }
}
