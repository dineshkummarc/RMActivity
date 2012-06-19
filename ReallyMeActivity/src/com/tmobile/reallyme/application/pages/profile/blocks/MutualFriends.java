package com.tmobile.reallyme.application.pages.profile.blocks;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.core.api.remote.MutualFriendsManager;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import android.app.Activity;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 16:01:17
 */
public class MutualFriends extends AbstractBlock{

    public View createLayout() {
        final Activity fActivity = this.activity;
        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        final LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        showDialog();
        MutualFriendsManager manager = new MutualFriendsManager() {
            public void callBack(List<Identity> identities) {
                if (identities != null && identities.size() > 0) {
                    View view = (View)View.inflate(fActivity, R.layout.profile_block_mutual_friends, null);
                    ((GridView)view.findViewById(R.id.gridview)).setAdapter(new ImageAdapter(fActivity, identities));
                    linearLayout.addView(view);
                } else {
                    linearLayout.addView(addEmpryMessage("No Mutual Friends"));
                }
                hideDialog();
            }
        };
        manager.getFriends(identity.id);
        scrollView.addView(linearLayout);
        return scrollView;
    }
}
