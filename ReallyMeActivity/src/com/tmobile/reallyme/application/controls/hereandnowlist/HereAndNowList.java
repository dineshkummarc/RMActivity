package com.tmobile.reallyme.application.controls.hereandnowlist;

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
import com.tmobile.reallyme.core.api.remote.HereAndNowManager;
import com.tmobile.reallyme.core.persistence.HereAndNowMemberManager;
import com.tmobile.reallyme.core.persistence.definition.HereAndNowMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;

/**
 * User: Kolesnik Aleksey
 * Date: 15.07.2009
 * Time: 18:55:24
 */
public class HereAndNowList extends ListActivity {
    private Menu peopleMenu = null;
    private Cursor cursor = null;
    private CursorListViewAdapter cursorListViewAdapter = null;
    private ContentResolver content = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        content = getContentResolver();
        HereAndNowMemberManager.deleteAll();
        cursor = getCursor(null);
        cursorListViewAdapter = new CursorListViewAdapter(this, cursor, false);
        setListAdapter(cursorListViewAdapter);
        getListView().setOnScrollListener(cursorListViewAdapter);
        getListView().setDrawingCacheEnabled(false);
        new HereAndNowManager().getHereAndNow(); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        peopleMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.here_and_now_list_filter_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        String typeSort = "ASC";
        if(cursor != null){
            switch (menuItem.getItemId()) {
                case R.id.fm_proximility:
                    cursorListViewAdapter.changeCursor(getCursor(HereAndNowMemberDefinition.DISTANCE));
                    return true;
                case R.id.fm_calendar:
                    return true;
                case R.id.fm_times_of_day:
                    cursorListViewAdapter.changeCursor(getCursor(HereAndNowMemberDefinition.SCORE));
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Log.i(this.getClass().getName(), "onListItemClick");
        super.onListItemClick(listView, view, position, id);
         //get user uid
        Cursor cursor = content.query(HereAndNowMemberDefinition.CONTENT_URI,
                new String[] {HereAndNowMemberDefinition.IDENTITY_UID},
                HereAndNowMemberDefinition.TABLE_NAME + "." + HereAndNowMemberDefinition._ID + "=?", new String[]{String.valueOf(id)}, null);
        cursor.moveToNext();
        String uid = cursor.getString(cursor.getColumnIndex(HereAndNowMemberDefinition.IDENTITY_UID));
        cursor.close();
        Intent extras = new Intent();
        extras.putExtra(IdentityDefinition.UID, uid);
        PageManager.openPage(view, PageEnum.FRIEND_PROFILE, extras);
    }

    private Cursor getCursor(String sortOrder){
        if(sortOrder == null || sortOrder.length() == 0){
//            sortOrder = IdentityDefinition.FIRST_NAME + " asc, " + IdentityDefinition.LAST_NAME + " asc ";
        }
        return HereAndNowMemberManager.loadAll(sortOrder);
    }
}
