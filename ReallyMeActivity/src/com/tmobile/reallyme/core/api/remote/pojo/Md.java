package com.tmobile.reallyme.core.api.remote.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Kolesnik Aleksey
 * Date: 26.06.2009
 * Time: 15:48:11
 */
public class Md {
    public List<MdItem> mdItems = new ArrayList();

    public Md() {}

    public Md(List<MdItem> mdItems) {
        this.mdItems = mdItems;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        if (mdItems.size() > 0) {
            str.append("<md>\n");
            for(MdItem mdItem : mdItems) {
                str.append(mdItem);
            }
            str.append("</md>\n");
        }
        return str.toString();
    }
}
