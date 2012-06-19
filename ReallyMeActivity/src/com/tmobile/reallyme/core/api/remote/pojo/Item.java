package com.tmobile.reallyme.core.api.remote.pojo;

import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 26.06.2009
 * Time: 15:46:42
 */
public class Item {
    public String name;
    public String value;
    public Boolean visible;

    public Item(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Item(String name, String value, Boolean visible) {
        this(name, value);
        this.visible = visible;
    }

    public String toString() {
          return "<item value=\"" + Utils.encodeXML(value) + "\" name=\"" + name + "\" visible=\""+ visible+"\"/>\n";
    }
}
