package com.tmobile.reallyme.core.api.remote.pojo;

/**
 * User: Kolesnik Aleksey
 * Date: 04.07.2009
 * Time: 15:36:32
 */
public class Preference {
    public Integer stateId;
    public Integer channelId;

    public Preference(Integer stateId, Integer channelId) {
        this.stateId = stateId;
        this.channelId = channelId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String toString() {
        return "<pref state_id=\"" + stateId + "\" channel_id=\""+ channelId +"\"/>\n";
    }
}
