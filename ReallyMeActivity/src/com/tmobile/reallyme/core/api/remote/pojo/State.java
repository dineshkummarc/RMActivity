package com.tmobile.reallyme.core.api.remote.pojo;

import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 29.06.2009
 * Time: 10:48:37
 */
public class State {
    public Integer id;
    public String desc;

    public State(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String toString() {
        return "<state id=\"" + id + "\" description=\"" + Utils.encodeXML(desc) + "\"/>\n";
    }
}
