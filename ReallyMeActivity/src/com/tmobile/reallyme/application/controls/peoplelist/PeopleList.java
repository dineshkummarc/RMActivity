package com.tmobile.reallyme.application.controls.peoplelist;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.controls.list.CursorListViewAdapter;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;


/**
 * dku
 */
public class PeopleList extends ListActivity {
    private Menu peopleMenu = null;
    private Cursor cursor = null;
    private CursorListViewAdapter cursorListViewAdapter = null;
    private ContentResolver content = null;
    private String[] typeSorts = {"asc", "asc", "asc"};
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        content = getContentResolver();
        cursor = getCursor(null);
        cursorListViewAdapter = new CursorListViewAdapter(this, cursor, false);
        setListAdapter(cursorListViewAdapter);
        getListView().setOnScrollListener(cursorListViewAdapter);
        getListView().setDrawingCacheEnabled(false);
 //        RegisterTMobileManager registerTMobileManager = new RegisterTMobileManager();
//        registerTMobileManager.login();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        peopleMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.people_list_filter_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        String typeSort = "ASC";
        if(cursor != null){
            switch (menuItem.getItemId()) {
                case R.id.pfmi_alphabetical:
//                    typeSort = typeSorts[0].equals("asc") ? "desc" : "asc";
//                    typeSorts[0] = typeSort;
                    cursorListViewAdapter.changeCursor(getCursor(IdentityDefinition.FIRST_NAME +" " + typeSort + ", "
                            + IdentityDefinition.LAST_NAME + " " + typeSort + " "));
                    return true;
                case R.id.pfmi_recency:
//                    typeSort = typeSorts[1].equals("asc") ? "desc" : "asc";
//                    typeSorts[1] = typeSort;
                    cursorListViewAdapter.changeCursor(getCursor(IdentityDefinition.CREATED_DATE + " " + typeSort + " "));
                    return true;
                case R.id.pfmi_frequency:
                    typeSort = typeSorts[2].equals("asc") ? "desc" : "asc";
//                    typeSorts[2] = typeSort;
//                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Log.i(this.getClass().getName(), "onListItemClick");
        super.onListItemClick(listView, view, position, id);
        //get user uid
        Cursor cursor = content.query(IdentityDefinition.CONTENT_URI,
                new String[] {IdentityDefinition.UID},
                IdentityDefinition._ID + "=?", new String[]{String.valueOf(id)}, null);
        cursor.moveToNext();
        String uid = cursor.getString(cursor.getColumnIndex(IdentityDefinition.UID));
        cursor.close();
        Intent extras = new Intent();
        extras.putExtra(IdentityDefinition.UID, uid);
        PageManager.openPage(view, PageEnum.FRIEND_PROFILE, extras);
    }

    private Cursor getCursor(String sortOrder){
        if(sortOrder == null || sortOrder.length() == 0){
            sortOrder = IdentityDefinition.FIRST_NAME + " asc, " + IdentityDefinition.LAST_NAME + " asc ";
        }
        return IdentityManager.loadAllFriend(sortOrder);
    }
}