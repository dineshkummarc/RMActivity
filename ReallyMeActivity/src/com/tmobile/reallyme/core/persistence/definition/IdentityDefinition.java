package com.tmobile.reallyme.core.persistence.definition;

import android.net.Uri;

import com.tmobile.reallyme.utils.Constants;

/**
 *
 */
public class IdentityDefinition extends BaseDefinition{

    public static final String TABLE_NAME = "identity";
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
    public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd.reallyme.identities";    

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.identity";

    static public final String UID = "uid";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String LOCATION_NAME = "location_name";
    public static final String TIME_ZONE = "time_zone";
    public static final String STATUS_MESSAGE = "status_message";
    public static final String STATUS_ID = "status_id";
    public static final String AVATAR_URL = "avatar_url";
    public static final String ACTIVITIES = "activities";
    public static final String ABOUT_ME = "about_me";
    public static final String INTERESTS = "interests";
    public static final String RELATIONSHIP_STATUS = "relationship_status";
    public static final String HOMETOWN_LOC = "hometown_loc";
    public static final String BIRTHDAY = "birthday";
    public static final String CHANNELS = "channels";
    public static final String PREFERRED_CHANNELS = "preferred_channels";
    static public final String CREATED_DATE = "created_date";
    static public final String UPDATED_DATE = "updated_date";
    /**
     * Identtyti can be for main user, friend or mutual friend ( see {@link com.tmobile.reallyme.core.persistence.IdentityTypeEnum}).
     */
    static public final String TYPE = "type";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER =  CREATED_DATE + " DESC";
}
