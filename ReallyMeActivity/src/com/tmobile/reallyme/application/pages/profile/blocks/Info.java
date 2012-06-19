package com.tmobile.reallyme.application.pages.profile.blocks;

import java.util.LinkedHashMap;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.utils.Utils;
import android.app.Activity;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 15:07:01
 */
public class Info extends AbstractBlock{

    public View getBodyView(Activity activity, Identity identity) {
        if (identity == null) return new TableLayout(activity);
        this.identity = identity;
        this.activity = activity;
        return createLayout();
    }

    public View createLayout() {
        LinkedHashMap<String, String> content = new LinkedHashMap();
        if (Utils.isNotBlank(identity.getAboutMe())) {
            content.put("Note:", identity.getAboutMe());
        }
        if (Utils.isNotBlank(identity.getBirthDay())) {
            content.put("Birth Day:", identity.getBirthDay());
        }
        if (Utils.isNotBlank(identity.getRelationshipStatus())) {
            content.put("RelationShips:", identity.getRelationshipStatus());
        }
        if (Utils.isNotBlank(identity.getInterests())) {
            content.put("Interests:", identity.getInterests());
        }
        if (Utils.isNotBlank(identity.getActivities())) {
            content.put("Activities:", identity.getActivities());
        }
        if (content.isEmpty()) {
            return addEmpryMessage("No status updates");
        } 
        return createNotations(content);
    }

    private View createNotations(LinkedHashMap<String, String> content) {
        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for(String key : content.keySet()) {
            linearLayout.addView(createNotation(key, content.get(key)));
            linearLayout.addView(addSeparator());
        }
        scrollView.addView(linearLayout);
        return scrollView;
    }

    protected View createNotation(String note, String text) {
        RelativeLayout relativeLayout = (RelativeLayout)View.inflate(this.activity, R.layout.profile_block_info, null);
        ((TextView)relativeLayout.findViewById(R.id.note)).setText(note);
        ((TextView)relativeLayout.findViewById(R.id.text)).setText(text);
        return relativeLayout;
    }
}
