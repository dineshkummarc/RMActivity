package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kolesnik Aleksey
 * Date: 26.06.2009
 * Time: 15:46:08
 */
public class Profile {
    public List<Item> items = new ArrayList();
    public Md md;

    public Profile() {}

    public Profile(List<Item> items) {
        this.items = items;
    }

    public String toString() {
        StringBuilder h = new StringBuilder();
        h.append("<profile>\n");
        if (items.size() > 0) {
            for(Item item : items) {h.append(item);}
        }
        if (md != null) {
            h.append(md);
        }
        h.append("</profile>\n");
        return h.toString();
    }
}
