package com.tmobile.reallyme.application.pages.profile;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.application.pages.profile.customaccordion.CustomAccordion;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.utils.Utils;
import com.tmobile.reallyme.utils.Constants;

import java.util.LinkedList;

/**
 * User: Kolesnik Aleksey
 * Date: 25.06.2009
 * Time: 18:32:21
 */
public abstract class AbstractProfile extends ExpandableListActivity {
    protected String identityUID = null;
    protected String preferredChannels = null;
    protected ImageManager imageManager = null;

    private Cursor cursor = null;
    protected ContentResolver content = null;

    //init accordion
    protected LinkedList<AccordionEnum> accordionEnums = null;

    private TextView location = null;
    private TextView userName = null;
    private TextView statusMsg = null;
    private ImageView avatar = null;
    private ImageView state = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        location = (TextView)findViewById(R.id.location);
        userName = (TextView)findViewById(R.id.username);
        statusMsg = (TextView)findViewById(R.id.statemsg);
        avatar = (ImageView)findViewById(R.id.avatar);
        state = (ImageView)findViewById(R.id.state);
        content = getContentResolver();
        //create note info about user (image, status description, loactiob description)
        View userNoteView = View.inflate(this, R.layout.base_list_item, null);
        ((FrameLayout)findViewById(R.id.user_note)).addView(userNoteView);
        //
        this.accordionEnums = initAccordion();
        //
        UserSession userSession = UserSession.getInstance();
        imageManager = new ImageManager(this);
        //
        initNoteInfo();
        initCustomAccordion();
    }

    protected abstract LinkedList<AccordionEnum> initAccordion();

    private void initNoteInfo() {
        cursor = getCursor();
        updateUserNote(cursor);
        cursor.registerContentObserver(new ContentObserver(new Handler()){
                @Override
                public boolean deliverSelfNotifications() {
                    return true;
                }

                @Override
                public void onChange(boolean b) {
                    cursor.requery();
                    if (cursor.getCount() > 0) {
                        updateUserNote(cursor);
                    }
                }
            }
            );
    }

    private void updateUserNote(Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            final TextView location = (TextView)findViewById(R.id.location);
            final TextView userName = (TextView)findViewById(R.id.username);
            final TextView statusMsg = (TextView)findViewById(R.id.statemsg);
            final ImageView avatar = (ImageView)findViewById(R.id.avatar);
            final ImageView state = (ImageView)findViewById(R.id.state);
            this.identityUID = cursor.getString(cursor.getColumnIndex(IdentityDefinition.UID));
            String avatarUrl = cursor.getString(cursor.getColumnIndex(IdentityDefinition.AVATAR_URL));
            String fname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.FIRST_NAME));
            String lname = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LAST_NAME));
            String locationDesc = cursor.getString(cursor.getColumnIndex(IdentityDefinition.LOCATION_NAME));
            Integer statusId = cursor.getInt(cursor.getColumnIndex(IdentityDefinition.STATUS_ID));
            String stMessage = cursor.getString(cursor.getColumnIndex(IdentityDefinition.STATUS_MESSAGE));
            this.preferredChannels = cursor.getString(cursor.getColumnIndex(IdentityDefinition.PREFERRED_CHANNELS));

            if (Utils.isNotBlank(avatarUrl)) {
                imageManager.fetchDrawableLazy(avatarUrl, avatar);
            }
            state.setImageResource(State.getById(statusId).getSelectedImageResourceSmall());

            userName.setText(fname + " " + lname);
            location.setText(locationDesc);
            statusMsg.setText(stMessage);
            //Set main text. It is My AbstractProfile or Pawan profile
            String name =  fname + " " + Utils.addApostrophes(lname);
            if (this.identityUID.equals(UserSession.getInstance().getIdentity().id)) {
                name = "My";
            }
            ((TextView)findViewById(R.id.profile_name)).setText(name + " Profile Info");

         }
    }
    private ExpandableListAdapter mAdapter;
    private void initCustomAccordion() {
//        ExpandableListView expandableView = (ExpandableListView) findViewById(android.R.id.list);
        mAdapter = new MyExpandableListAdapter(accordionEnums, IdentityManager.loadByUID(this.identityUID));
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
    }

    private void initAccordionButtons() {

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.accordion_buttons);
//        final StringBuilder ids = new StringBuilder();
//        for(final AccordionEnum accordionEnum : accordionEnums) {
//            if (ids.length() > 0) {ids.append(";");}
//            ids.append(accordionEnum.getId());
//        }
//        for (final AccordionEnum accordionEnum : this.accordionEnums) {
//            final ImageView image = (ImageView)View.inflate(AbstractProfile.this, R.layout.accordion_list_parent, null);
//            image.setImageResource(accordionEnum.getUnselectedImageResource());
//            image.setOnTouchListener(
//                new View.OnTouchListener() {
//                    public boolean onTouch(View v, MotionEvent motionEvent) {
//                        switch (motionEvent.getAction()) {
//                            case MotionEvent.ACTION_DOWN : {
//                                 image.setImageResource(accordionEnum.getOnpressImageResource());
//                                 break;
//                            }
//                            case MotionEvent.ACTION_UP : {
//                                Intent extras = new Intent();
//                                extras.putExtra(CustomAccordion.ACTIVE_ID, accordionEnum.getId());
//                                //
//                                StringBuilder str = new StringBuilder();
//                                for (AccordionEnum accordionEnum : accordionEnums) {
//                                    if (str.length()>0) {
//                                        str.append(Constants.DELIMETER);
//                                    }
//                                    str.append(accordionEnum.getId());
//                                }
//                                extras.putExtra(CustomAccordion.IDS, str.toString());
//                                //
//                                extras.putExtra(IdentityDefinition.UID, identityUID);
////                                PageManager.openPage(v, accordionEnum.getPageEnum(), extras);
//                                PageManager.openPage(v, PageEnum.PROFILE_ACCORDION, extras);
//                                image.setImageResource(accordionEnum.getUnselectedImageResource());
//                                break;
//                            }
//                            default :  break;
//                        }
//                        return true;
//                    }
//                }
//            );
//            linearLayout.addView(image);
//        }
    }

    protected abstract Cursor getCursor();

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private Identity identity = null;
        private LinkedList<AccordionEnum> accordionEnums = new LinkedList();

        public MyExpandableListAdapter(LinkedList<AccordionEnum> accordionEnums, Identity identity) {
            this.accordionEnums = accordionEnums;
            this.identity = identity;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            LinearLayout linearLayout = (LinearLayout)View.inflate(AbstractProfile.this, R.layout.abstract_profile_block_layout, null);
            LinearLayout body  = (LinearLayout)linearLayout.findViewById(R.id.profile_body);
            View view = getGroup(groupPosition).getClazz().getBodyView(AbstractProfile.this, this.identity);
            body.addView(view);
            return linearLayout;
        }

        public AccordionEnum getGroup(int groupPosition) {
            return accordionEnums.get(groupPosition);
        }

        public int getGroupCount() {
            return accordionEnums.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            View view = View.inflate(AbstractProfile.this, R.layout.my_faves_group_layout, null);
            TextView groupName = (TextView)view.findViewById(R.id.name);
            groupName.setText(getGroup(groupPosition).getName());
            return groupName;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}