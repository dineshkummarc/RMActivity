package com.tmobile.reallyme.core.api.remote;

/**
 * User: Kolesnik Aleksey
 * Date: 14.07.2009
 * Time: 10:54:13
 */
public class BaseMetaData {
    private Boolean ok;

    BaseMetaData(Boolean ok) {
        this.ok = ok;
    }

    public Boolean isOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String toString() {
        return "Status: \"" + ok + "\"";
    }
}
