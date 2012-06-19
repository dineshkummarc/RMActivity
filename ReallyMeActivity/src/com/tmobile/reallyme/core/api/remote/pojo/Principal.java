package com.tmobile.reallyme.core.api.remote.pojo;

import com.tmobile.reallyme.utils.Utils;

import java.util.Map;

/**
 * User: Kolesnik Aleksey
 * Date: 15.07.2009
 * Time: 18:27:45
 */
public class Principal extends BaseData {
    public Double distance;
    public Integer score;
    public String type;

    public String toString() {
        return  "<principal id=\"" + id + "\" distance=\"" + distance + "\" score=\""
                + score + "\" type=\"" + type + "\"/>\n";
    }
}
