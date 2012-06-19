package com.tmobile.reallyme.core.persistence.definition;

import android.net.Uri;

import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class MessageDefinition extends BaseDefinition{

    public static final String TABLE_NAME = "message";
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
    public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd.reallyme.messages";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.message";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "time ASC";

    static public final String MID = "mid";
    static public final String RECIPIENT_ID = "recipient_id";
    static public final String SENDER_ID = "sender_id";
    static public final String TYPE = "type";
    static public final String CONTENT = "content";
    static public final String TIME = "time";
    static public final String CREATED_DATE = "created_date";
}
