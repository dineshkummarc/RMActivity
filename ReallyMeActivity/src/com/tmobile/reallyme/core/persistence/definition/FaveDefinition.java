package com.tmobile.reallyme.core.persistence.definition;

import android.net.Uri;

import com.tmobile.reallyme.utils.Constants;

/**
 * dku
 */
public class FaveDefinition extends BaseDefinition{
    public static final String TABLE_NAME = "fave";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);

    public static final String CONTENT_LIST_TYPE = "vnd.android.cursor.dir/vnd.reallyme.faves";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.fave";

    public static final String FID = "uid";
    public static final String NAME = "name";    
}
