package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kolesnik Aleksey
 * Date: 04.07.2009
 * Time: 15:39:59
 */
public class Contract {
    public String withid;
    public String type;
    public String groupid;
    public List<Item> access = new ArrayList();
    public List<Preference> preferences = new ArrayList();

    public Contract(String type, String withid) {
        this.withid = withid;
        this.type = type;
    }

    public Contract(String withid, String type, String groupid, List<Item> access, List<Preference> preferences) {
        this.withid = withid;
        this.type = type;
        this.groupid = groupid;
        this.access = access;
        this.preferences = preferences;
    }

    public String toString(){
        StringBuilder h = new StringBuilder();
        h.append("<contract with_id=\"").append(withid).append("\"  type=\"").append(type).append("\" ");
        if (groupid == null) {
            h.append(">\n");
        } else {
            h.append(" groupId=\"").append(groupid).append("\"/>\n");
            return h.toString();
        }

        if (access.size() > 0) {
            h.append("<access>\n");
            for(Item item : access) {
                h.append(item);    
            }
            h.append("</access>\n");
        }
        if (preferences.size() > 0) {
            h.append("<preferences>\n");
            for(Preference preference : preferences){
                 h.append(preference);
            }
            h.append("</preferences>\n");
        }
        h.append("</contract>\n");
        return h.toString();
    }
}
