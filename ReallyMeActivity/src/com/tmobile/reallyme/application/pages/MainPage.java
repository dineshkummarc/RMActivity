package com.tmobile.reallyme.application.pages;

import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.controls.hereandnowlist.HereAndNowList;
import com.tmobile.reallyme.application.controls.myfavelist.MyFaveList;
import com.tmobile.reallyme.application.controls.peoplelist.PeopleList;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.core.persistence.IdentityTypeEnum;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.utils.Utils;

/**
 * This is main page for People, My Conversation and Here & Now pages. This page consist from 3 tabs.
 * User: Kolesnik Aleksey
 * Date: 01.07.2009
 * Time: 23:01:24
 */
public class MainPage extends TabActivity {

    private TabHost tabHost;
    ImageManager imageManager = null;
    private Cursor cursor = null;
    private ContentResolver content = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        imageManager = new ImageManager(this);
        content = getContentResolver();
        //
        initNote();
        //
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("People")
                .setContent(new Intent(this, PeopleList.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Here & Now")
                .setContent(new Intent(this, HereAndNowList.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("My Faves")
                .setContent(new Intent(this, MyFaveList.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

    }

    private void initNote() {
        final View userNoteView = View.inflate(this, R.layout.base_list_item, null);
        FrameLayout frameLayout = ((FrameLayout)findViewById(R.id.user_note));
        frameLayout.addView(userNoteView);
        frameLayout.setOnClickListener(myNoteListener);
        cursor = getCursor();
        updateUserNote(userNoteView, cursor);
        cursor.registerContentObserver(new ContentObserver(new Handler()){

            @Override
            public boolean deliverSelfNotifications() {
                return true;
            }

            @Override
            public void onChange(boolean b) {
                cursor.requery();
                if (cursor.getCount() > 0) {
                    updateUserNote(userNoteView, cursor);
                }
            }
        }
        );
    }

    private void updateUserNote(View view, Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            TextView location = (TextView)view.findViewById(R.id.location);
            TextView userName = (TextView)view.findViewById(R.id.username);
            TextView statusMsg = (TextView)view.findViewById(R.id.statemsg);
            ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
            ImageView state = (ImageView)view.findViewById(R.id.state);

            String avatarUrl = cursor.getString(cursor.getColumnIndex(IdentityDefinition.AVATAR_URL));
            String fname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.FIRST_NAME));
            String lname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LAST_NAME));
            String locationDesc = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LOCATION_NAME));
            Integer statusId = cursor.getInt(cursor.getColumnIndex(IdentityDefinition.STATUS_ID));
            String stMessage = cursor.getString(cursor.getColumnIndex(IdentityDefinition.STATUS_MESSAGE));

            //temporary
            if (Utils.isNotBlank(avatarUrl)) {
                imageManager.fetchDrawableLazy(avatarUrl, avatar);
            }
            state.setImageResource(State.getById(statusId).getSelectedImageResourceSmall());

            userName.setText(fname + " " + lname);
            location.setText(locationDesc);
            statusMsg.setText(stMessage);
        }
    }

    private Cursor getCursor(){
        String[] arg = {IdentityTypeEnum.MY.getId()};
        return content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.TYPE + "=?", arg, null);
    }

    /**
     * A call-back for when the user presses the back button.
     */
    View.OnClickListener myNoteListener = new View.OnClickListener() {
        public void onClick(View v) {
//            v.getContext().startActivity(new Intent(MainPage.this, TempPage.class));
              PageManager.openPage(v, PageEnum.MY_PROFILE);
        }
    };
}
