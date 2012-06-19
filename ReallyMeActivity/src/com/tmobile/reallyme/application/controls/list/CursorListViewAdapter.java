package com.tmobile.reallyme.application.controls.list;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.enums.State;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.core.api.remote.CallManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.utils.Utils;


/**
 * dku
 */
public class CursorListViewAdapter extends CursorAdapter implements AbsListView.OnScrollListener{
    ImageManager imageManager = null;
    private boolean mBusy;
    private Context context;
    public CursorListViewAdapter(Context context, final Cursor cursor, boolean b) {
        super(context, cursor, b);
        this.context = context;
        imageManager = new ImageManager(context);
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

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View inflated = LayoutInflater.from(context).inflate(R.layout.base_list_item, null);
        return inflated;
    }
  
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView location = (TextView)view.findViewById(R.id.location);
        final TextView userName = (TextView)view.findViewById(R.id.username);
        final TextView statusMsg = (TextView)view.findViewById(R.id.statemsg);
        final ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
        final ImageView state = (ImageView)view.findViewById(R.id.state);
        final ImageView buzz = (ImageView)view.findViewById(R.id.buzz);

        if(!mBusy){
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
                                            dialog.hide();
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

            if(avatar.getVisibility() == View.INVISIBLE){
                userName.setVisibility(View.VISIBLE);
                statusMsg.setVisibility(View.VISIBLE);
                avatar.setVisibility(View.VISIBLE);
                state.setVisibility(View.VISIBLE);
                buzz.setVisibility(View.VISIBLE);                
            }
        }else{
            location.setText("... LOADING ...");
            userName.setVisibility(View.INVISIBLE);
            statusMsg.setVisibility(View.INVISIBLE);
            avatar.setVisibility(View.INVISIBLE);
            state.setVisibility(View.INVISIBLE);
            buzz.setVisibility(View.INVISIBLE);
        }
    }

    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
            mBusy = false;
            view.invalidateViews();
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            mBusy = true;
            //mStatus.setText("Touch scroll");
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
            mBusy = true;
            //mStatus.setText("Fling");
            break;
        }
    }

    


    private Integer getWidthById(int idx){
        return 320 - idx * 10;
    }
    private Integer getHeightById(int idx){
        return 60 - idx * 2;
    }

}
