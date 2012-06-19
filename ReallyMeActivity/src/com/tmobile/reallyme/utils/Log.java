package com.tmobile.reallyme.utils;

/**
 * User: Kolesnik Aleksey
 * Date: 02.07.2009
 * Time: 15:53:24
 */
public class Log {
    private Class name;
    private String stringname;
    private Boolean isDebug = Boolean.TRUE;

    public Log(Class name) {
        this.name = name;
    }

    public Log(String stringname) {
        this.stringname = stringname;
    }

    public Log(Class name, Boolean debug) {
        this(name);
        this.isDebug = debug;
    }

    public Log(String stringname, Boolean debug) {
        this(stringname);
        this.isDebug = debug;
    }

    /*
        Debug method.
        Debug the string.
    */
    public void info(String text) {
        if (this.isDebug) {
            String note = "";
            if (name != null) {
                note = this.name.getName();
            } else if (stringname != null) {
                note = this.stringname;
            }
            android.util.Log.i(note, text);
        }
    }
    /*
        Debug method.
        Debug the string.
    */
    public void error(String text) {
        if (this.isDebug) {
            String note = "";
            if (name != null) {
                note = this.name.getName();
            } else if (stringname != null) {
                note = this.stringname;
            }
            android.util.Log.e(note, text);
        }
    }
}
