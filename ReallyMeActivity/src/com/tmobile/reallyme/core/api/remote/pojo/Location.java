package com.tmobile.reallyme.core.api.remote.pojo;

import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 29.06.2009
 * Time: 10:50:00
 */
public class Location {
    public String timeZone;
    public String name;

    public Location(String name, String timeZone) {
        this.name = name;
        this.timeZone = timeZone;
    }

    public String toString() {
        return  "<location time_zone=\"" + timeZone + "\" name=\"" + name + "\"/>\n";
    }
}
