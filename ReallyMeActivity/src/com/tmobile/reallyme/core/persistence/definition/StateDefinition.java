//package com.tmobile.reallyme.core.persistence.definition;
//
//import android.net.Uri;
//import com.tmobile.reallyme.utils.Constants;
//
///**
// *
// */
//public class StateDefinition extends BaseDefinition{
//
//    public static final String TABLE_NAME = "state";
//    /**
//     * The content:// style URL for this table
//     */
//    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);
//
//    /**
//     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
//     */
//    public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd.reallyme.states";
//
//    /**
//     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
//     */
//    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.state";
//
//    public static final String SID = "uid";
//    public static final String STATE_ID = "state_id";
//    public static final String DESCRIPTION = "description";
//}