package com.tmobile.reallyme.application.controls.list;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;

/**
 * dku
 */
public class BaseActivityList extends ListActivity{
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        TestIdentityDataList testIdentityDataList = new TestIdentityDataList();
//        testIdentityDataList.initDb(this);
        ContentResolver content = getContentResolver();
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                PEOPLE_PROJECTION, null, null, IdentityDefinition.DEFAULT_SORT_ORDER);
        CursorListViewAdapter cursorListViewAdapter = new CursorListViewAdapter(this, cursor, false);
        setListAdapter(cursorListViewAdapter);
        //setListAdapter(new BaseListViewAdapter(this));
        getListView().setDivider(null);
        getListView().setDividerHeight(5);
    }
     private static final String[] PEOPLE_PROJECTION = new String[] {
        IdentityDefinition._ID,
        IdentityDefinition.UID,
        IdentityDefinition.FIRST_NAME,
        IdentityDefinition.LAST_NAME,
        IdentityDefinition.LOCATION_NAME,
        IdentityDefinition.STATUS_MESSAGE,
        IdentityDefinition.AVATAR_URL,
    };
}
