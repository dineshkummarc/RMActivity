package com.tmobile.reallyme.application.pages.profile.blocks;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.core.UserSession;

/**
 * User: Kolesnik Aleksey
 * Date: 16.07.2009
 * Time: 20:34:24
 */
public class MyContextStatusImageAdapter extends BaseAdapter {
    private Activity activity;

    private Integer activeUserStatusID = State.getById(UserSession.getInstance().getIdentity().getContext().state.id).getId();

   /**
   * Uses as temp view, if we will change active status we don't need change status image for all
   * view in this GridView. We just need chnage image for this view.
   */
    private ImageView activeStatusImage = null;

    public MyContextStatusImageAdapter(Activity activity) {
        this.activity = activity;
    }

    public int getCount() {
        return State.values().length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final State status = State.values()[position];
        final View view;
        if (convertView == null) {
            view = (View)View.inflate(activity, R.layout.profile_block_my_context_cell, null);
        } else {
            view = (View) convertView;
        }
        final ImageView image = (ImageView) view.findViewById(R.id.avatar);
        final TextView text = (TextView) view.findViewById(R.id.text);
        if (status.getId().equals(this.activeUserStatusID)) {
            image.setImageResource(status.getSelectedImageResourceLarge());
            if (activeStatusImage == null) {
                activeStatusImage = image;
            }
        } else {
            image.setImageResource(status.getUnselectedImageResourceLarge());
        }
        image.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN : {
                                 image.setImageResource(status.getOnpressImageResourceLarge());
                                 break;
                            }
                            case MotionEvent.ACTION_UP : {
                                //change status image to unselected for previous active status
                                if (activeStatusImage != null) {
                                    activeStatusImage.setImageResource(State.getById(activeUserStatusID).getUnselectedImageResourceLarge());
                                }
                                image.setImageResource(status.getSelectedImageResourceLarge());
                                //set new acitve sttaus ID
                                activeUserStatusID = status.getId();
                                //set new active status image
                                activeStatusImage = image;
                                break;
                            }
                            default :  break;
                        }
                        return true;
                    }
                }
            );
        text.setText(State.values()[position].getText());
        return view;
    }


    public Integer getActiveStatusID() {
        return this.activeUserStatusID;
    }
}