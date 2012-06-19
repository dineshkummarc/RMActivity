package com.tmobile.reallyme.application.pages.profile.blocks;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.utils.ImageManager;

/**
 * User: Kolesnik Aleksey
 * Date: 16.07.2009
 * Time: 20:34:24
 */
public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    private List<Identity> identities;
    private ImageManager imageManager = null;

    public ImageAdapter(Activity activity, List<Identity> identities) {
        this.activity = activity;
        this.identities = identities;
        imageManager = new ImageManager(activity);
    }

    public int getCount() {
        return identities.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = (View)View.inflate(activity, R.layout.profile_block_mutual_friends_cell, null);
        } else {
            view = (View) convertView;
        }
        final ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        final TextView text = (TextView) view.findViewById(R.id.text);

        imageManager.fetchDrawableLazy(identities.get(position).getAvatar().replaceAll("null", ""), avatar);
        text.setText(identities.get(position).getFname() + " " + identities.get(position).getLname());
        return view;
    }
    
}

