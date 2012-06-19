package com.tmobile.reallyme.application.pages.profile.blocks;

import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tmobile.reallyme.R;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import android.app.Activity;

/**
 * User: Kolesnik Aleksey
 * Date: 29.07.2009
 * Time: 14:07:32
 */
public abstract class AbstractCallerTunes extends AbstractBlock {
    protected String noSelection = null;

    protected View setBodyView(Activity activity, Identity identity) {
        if (identity == null) return new TableLayout(activity);
        this.identity = identity;
        this.activity = activity;
        this.imageManager = new ImageManager(this.activity);
        noSelection =  this.activity.getResources().getString(R.string.no_selection );
        return createLayout();
    }

    public View createLayout() {
        if (identity == null) return new TableLayout(this.activity);

        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        initLayout(linearLayout);
        scrollView.addView(linearLayout);
        return scrollView;
    }

    protected abstract void initLayout(LinearLayout linearLayout);

    protected TextView createText(String text) {
        TextView textView = (TextView)View.inflate(this.activity, R.layout.profile_block_caller_tunes_tune, null);
        textView.setText(text);
        return textView;
    }
}