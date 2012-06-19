package com.tmobile.reallyme.application.pages.profile.blocks;

import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.application.enums.SocialSitesEnum;
import com.tmobile.reallyme.core.api.remote.profile.*;
import com.tmobile.reallyme.utils.TimeUtils;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 15:07:01
 */
public class Activity extends AbstractBlock{

    public View createLayout() {
        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        final LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final android.content.Context fContext = this.activity;
        showDialog();
        ProfileManager profileManager = new ProfileManager<ActivitiesMetaData>() {
            @Override
            public void callBack(ActivitiesMetaData data) {
                if (data.list.isEmpty()) {
                    linearLayout.addView(addEmpryMessage("No status updates"));
                } else {
                    for (ActivityMetaData element : data.list) {
                        View view = (View)View.inflate(fContext, R.layout.profile_block_activity, null);
                        ImageView statusImage = (ImageView)view.findViewById(R.id.statusImage);
                        State status = State.getById(element.statusId);
                        statusImage.setImageResource(status.getUnselectedImageResourceLarge());
                        ((TextView)view.findViewById(R.id.statusPreMsg)).setText(status.getName() + ":");
                        ((TextView)view.findViewById(R.id.statusMsg)).setText(element.status.length() > 30 ? element.status.substring(0, 27) + "..." : element.status);
                        ((TextView)view.findViewById(R.id.timeAgo)).setText(TimeUtils.getUpdatedTimeText(element.dateTime));
                        ImageView socialLogo = (ImageView)view.findViewById(R.id.socialLogo);
                        socialLogo.setImageResource(SocialSitesEnum.getByName(element.source).getImageResource());
                        linearLayout.addView(view);
                    }
                }
                hideDialog();
            };
        };
        profileManager.getProfileElement(JsonProfileEnum.ACTIVITY, this.identity.id);
        scrollView.addView(linearLayout);
        return scrollView;
    }

}