package com.tmobile.reallyme.core.persistence.definition;

import android.net.Uri;

import com.tmobile.reallyme.core.api.remote.pojo.Context;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.utils.Constants;

/**
 * User: Kolesnik Aleksey
 * Date: 05.07.2009
 * Time: 0:48:00
 */
public class UserSessionDefinition {
    public static final String TABLE_NAME = "user_session";
    /**
    * The content:// style URL for this table
    */
    public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.DATABASE_ID + "/"+TABLE_NAME);

     /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.reallyme.user_session";

   //User identity
    public Identity identity;
    private Context context;
    //    Context on replace {
    //        state = State.getById(context.state.id);
    //        //println("Change status to {context.state.id}")
    //    };

    //API session id
    public static final String SESSION_ID = "session_id";
    //API Registered device id
    public static final String CLIENT_ID = "client_id";
    //API Device password
    public static final String PASSWORD = "password";
    //User identity. We don't use this value like collumn name.
    public static final String IDENTYTI = "identyti";
}
