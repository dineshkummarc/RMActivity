package com.tmobile.reallyme.application.pages.profile.customaccordion;


import com.tmobile.reallyme.R;
import com.tmobile.reallyme.utils.Constants;
import com.tmobile.reallyme.application.pages.profile.AccordionEnum;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;

import java.util.LinkedList;


/**
 * User: Kolesnik Aleksey
 * Date: 16.07.2009
 * Time: 16:04:33
 */
public class CustomAccordion extends ExpandableListActivity {
    public static final String IDS = "ids";
    public static final String ACTIVE_ID = "active_id";

    private ExpandableListAdapter mAdapter;

    protected Identity identity = null;
    protected AccordionEnum accordionEnum = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accordion_layout);
        // Set up our adapter
        Intent intent = getIntent();
        String activeId = intent.getStringExtra(ACTIVE_ID);
        LinkedList<AccordionEnum> accordionEnums = new LinkedList();
        String[] ids = intent.getStringExtra(IDS).split(Constants.DELIMETER);
        for (String id : ids) {
            for (AccordionEnum accordionEnum : AccordionEnum.values()){
                if (id.equals(accordionEnum.getId())) {
                    accordionEnums.add(accordionEnum);
                    break;
                }
            }

        }
        String identityId = intent.getStringExtra(IdentityDefinition.UID);

        mAdapter = new MyExpandableListAdapter(accordionEnums, IdentityManager.loadByUID(identityId));
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
        setSelectedGroup(1);
    }

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
            LinearLayout linearLayout = (LinearLayout)View.inflate(CustomAccordion.this, R.layout.abstract_profile_block_layout, null);
            LinearLayout body  = (LinearLayout)linearLayout.findViewById(R.id.profile_body);
            View view = getGroup(groupPosition).getClazz().getBodyView(CustomAccordion.this, this.identity);
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
            View view = View.inflate(CustomAccordion.this, R.layout.my_faves_group_layout, null);
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