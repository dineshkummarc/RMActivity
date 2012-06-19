package com.tmobile.reallyme.application.pages.profile.blocks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.R;
import com.tmobile.reallyme.utils.ImageManager;
import android.widget.*;

import java.util.Timer;
import java.util.TimerTask;


/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 15:07:23
 */
public abstract class AbstractBlock {
    public Identity identity;
    public Activity activity;
    public ImageManager imageManager;
    private ProgressDialog dialog;

    public View getBodyView(Activity activity, Identity identity) {
        if (identity == null) return new TableLayout(activity);
        this.identity = identity;
        this.activity = activity;
        this.imageManager = new ImageManager(this.activity);
        this.dialog = new ProgressDialog(activity);
        return createLayout();
    }

    public abstract View createLayout();

    protected View addSeparator() {
        ImageView separator = new ImageView(this.activity);
        separator.setImageResource(R.drawable.separator);
        return separator;
    }

    protected View addEmpryMessage(String message) {
        LinearLayout view = (LinearLayout)View.inflate(this.activity, R.layout.profile_block_empty, null);
        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(message);
        return view;
    }

    protected void showDialog() {
        dialog.setMessage("Context loading...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    protected void hideDialog() {dialog.dismiss();}

}




