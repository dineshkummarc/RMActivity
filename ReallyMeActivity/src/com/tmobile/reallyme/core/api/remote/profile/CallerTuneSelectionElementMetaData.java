package com.tmobile.reallyme.core.api.remote.profile;

import java.util.LinkedList;

/**
 * User: Kolesnik Aleksey
 * Date: 28.07.2009
 * Time: 13:58:40
 */
public class CallerTuneSelectionElementMetaData {
    public String id;
    public LinkedList<CallerTuneMetaData> mySelection = new LinkedList();
    public LinkedList<CallerTuneMetaData> selectionForMe = new LinkedList();
    public String name;
    public String imageURL;
}
