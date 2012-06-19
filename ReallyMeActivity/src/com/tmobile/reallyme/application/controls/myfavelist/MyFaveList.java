package com.tmobile.reallyme.application.controls.myfavelist;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.core.api.remote.CallManager;
import com.tmobile.reallyme.core.api.remote.MyFavesManager;
import com.tmobile.reallyme.core.persistence.definition.FaveDefinition;
import com.tmobile.reallyme.core.persistence.definition.FaveMemberDefinition;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: dku
 * User: Kolesnik Aleksey
 * Date: 20.07.2009
 * Time: 1:14:57
 */
public class MyFaveList extends ExpandableListActivity {
    private ImageManager imageManager = null;
    private ExpandableListAdapter mAdapter;
     private ContentResolver content = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.my_faves_layout);
        imageManager = new ImageManager(MyFaveList.this);
        // Query for people
        content = getContentResolver();
        Cursor groupCursor = content.query(FaveDefinition.CONTENT_URI, null, "", null, null);
        // Set up our adapter
        mAdapter = new MyExpandableListAdapter(groupCursor,
                this,
                R.layout.my_faves_group_layout,
                R.layout.base_list_item,
                new String[]{}, new int[]{}, new String[]{}, new int[]{});
        setListAdapter(mAdapter);
        MyFavesManager myFavesManager = MyFavesManager.getInstance();
        myFavesManager.fetchMyFaves();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
        //get user uid
        Cursor cursor = content.query(FaveMemberDefinition.CONTENT_URI,
                new String[] {FaveMemberDefinition.FMID},
                FaveMemberDefinition.TABLE_NAME + "." + FaveMemberDefinition._ID + "=?", new String[]{String.valueOf(id)}, null);
        cursor.moveToNext();
        String uid = cursor.getString(cursor.getColumnIndex(FaveMemberDefinition.FMID));
        cursor.close();
        Intent extras = new Intent();
        extras.putExtra(IdentityDefinition.UID, uid);
        PageManager.openPage(view, PageEnum.FRIEND_PROFILE, extras);
        return true;
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

        public MyExpandableListAdapter(final Cursor cursor, Context context, int groupLayout,
                int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childrenFrom,
                    childrenTo);
            //
            cursor.registerContentObserver(new ContentObserver(new Handler()){
                @Override
                public boolean deliverSelfNotifications() {
                    return true;
                }

                @Override
                public void onChange(boolean b) {
                    cursor.requery();
                }
                }
            );
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // Given the group, we return a cursor for all the children within that group
            final String fid = groupCursor.getString(groupCursor.getColumnIndex(FaveDefinition.FID));
            return content.query(FaveMemberDefinition.CONTENT_URI, null,
                     FaveMemberDefinition.PARENT_FAVE_ID + "=?", new String[] {fid}, null);
        }

        @Override
        protected void bindChildView(final View view, Context context, Cursor cursor, boolean b) {
            final TextView location = (TextView)view.findViewById(R.id.location);
            final TextView userName = (TextView)view.findViewById(R.id.username);
            final TextView statusMsg = (TextView)view.findViewById(R.id.statemsg);
            final ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
            final ImageView state = (ImageView)view.findViewById(R.id.state);
            final ImageView buzz = (ImageView)view.findViewById(R.id.buzz);

            final String uid = cursor.getString(cursor.getColumnIndex(IdentityDefinition.UID));
            String fname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.FIRST_NAME));
            String lname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LAST_NAME));
            String locationDesc = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LOCATION_NAME));
            String avatarUrl = cursor.getString(cursor.getColumnIndex(IdentityDefinition.AVATAR_URL));
            String stMessage = cursor.getString(cursor.getColumnIndex(IdentityDefinition.STATUS_MESSAGE));
            Integer statusId = cursor.getInt(cursor.getColumnIndex(IdentityDefinition.STATUS_ID));
            final String preferredChannels = cursor.getString(cursor.getColumnIndex(IdentityDefinition.PREFERRED_CHANNELS));

            imageManager.fetchDrawableLazy(avatarUrl, avatar);
            state.setImageResource(State.getById(statusId).getSelectedImageResourceSmall());
            buzz.setImageResource(Utils.getBuzzImageID(preferredChannels));
            buzz.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            if (Utils.isTalkChannels(preferredChannels)) {
                                new CallManager().call(uid);
                                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                                dialog.setMessage("Calling...");
                                dialog.setIndeterminate(true);
                                dialog.setCancelable(true);
                                dialog.show();
                                new Timer().schedule(
                                        new TimerTask() {
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 5*1000);
                            } else if (Utils.isPostChannels(preferredChannels)) {
                                Intent extras = new Intent();
                                extras.putExtra("TYPE_ID", MessageTypeEnum.POST.getId());
                                extras.putExtra(IdentityDefinition.UID, uid);
                                PageManager.openPage(v, PageEnum.MESSAGE, extras);
                            } else if (Utils.isTextChannels(preferredChannels)) {
                                Intent extras = new Intent();
                                extras.putExtra("TYPE_ID", MessageTypeEnum.TEXT.getId());
                                extras.putExtra(IdentityDefinition.UID, uid);
                                PageManager.openPage(v, PageEnum.MESSAGE, extras);
                            }
                        }
                    }
                );
                userName.setText(fname + " " + lname);
                location.setText("@" + locationDesc);
                statusMsg.setText(stMessage);
        }

        @Override
        public View newGroupView(Context context, Cursor cursor, boolean b, ViewGroup viewGroup) {
            return View.inflate(context, R.layout.my_faves_group_layout, null);
        }

        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor, boolean b) {
            TextView groupName = (TextView)view.findViewById(R.id.name);
            String name = cursor.getString(cursor.getColumnIndex(FaveDefinition.NAME));
            groupName.setText(name);
        }
    }
}