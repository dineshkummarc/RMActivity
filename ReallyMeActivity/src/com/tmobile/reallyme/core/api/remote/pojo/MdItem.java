package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.List;

/**
 * User: Kolesnik Aleksey
 * Date: 26.06.2009
 * Time: 15:48:35
 */
public class MdItem {
    public List<Item> items;

    public String toString() {
        StringBuilder str = new StringBuilder();
        if (items.size() > 0) {
            str.append("<mdItem>\n");
            for(Item item : items) {
                str.append(item);
            }
            str.append("</mdItem>\n");
        }
        return str.toString();
   }
}
