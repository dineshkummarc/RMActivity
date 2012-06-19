package com.tmobile.reallyme.core.persistence.definition;

import android.net.Uri;

import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class FaveMemberDefinition extends BaseDefinition{
    public static final String TABLE_NAME = "fave_member";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);

    public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd.reallyme.fave_members";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.fave_member";

    public static final String FMID = "uid";
    public static final String PARENT_FAVE_ID = "fave_uid";        
}
