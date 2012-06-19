package com.tmobile.reallyme.core.api.remote.pojo;

/**
 * User: Kolesnik Aleksey
 * Date: 29.06.2009
 * Time: 10:48:11
 */
public class Context {
    public State state;
    public Location location;

    public String toString() {
        String h = "<context>\n";
        if (location != null) {
            h = h + location;
        }
        if (state != null) {
            h = h + state;
        }
        h = h + "</context>\n";
        return h;
    }
}
