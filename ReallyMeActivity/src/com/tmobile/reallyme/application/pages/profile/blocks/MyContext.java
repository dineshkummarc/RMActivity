package com.tmobile.reallyme.application.pages.profile.blocks;

import android.view.Menu;
import android.app.Activity;
import android.view.View;
import android.widget.*;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.utils.ImageManager;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.ProfileManager;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 19:00:14
 */
public class MyContext extends Context {
    private Menu menu = null;

    private LinearLayout linearLayout = null;
    private LinearLayout editBlock = null;
    private EditText statusMsg = null;

    @Override
    public View getBodyView(Activity activity, Identity identity) {
        if (identity == null) return new LinearLayout(activity);
        this.identity = identity;
        this.activity = activity;
        this.imageManager = new ImageManager(this.activity);
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View view  = createLayout();
        view.setOnClickListener(editListener);
        linearLayout.addView(view);
        this.linearLayout = linearLayout;
        return this.linearLayout;
    }

    private Boolean isEditState = Boolean.FALSE;
    /**
     * Added Edit block to parrent view
     */
    View.OnClickListener editListener = new View.OnClickListener() {
        public void onClick(View v) {
            if ( !isEditState ) {
                isEditState = Boolean.TRUE;
                linearLayout.addView(createEditLayout());
            }
        }
    };

    private MyContextStatusImageAdapter myContextStatusImageAdapter = null;
    private LinearLayout createEditLayout() {
        if (this.editBlock == null) {
            LinearLayout view = (LinearLayout)View.inflate(this.activity, R.layout.profile_block_my_context, null);
            this.statusMsg = (EditText)view.findViewById(R.id.statemsg);
            statusMsg.setText(identity.getContext().state.desc);
            //
            final GridView gridView = (GridView)view.findViewById(R.id.gridview);
            this.myContextStatusImageAdapter = new MyContextStatusImageAdapter(this.activity);
            gridView.setAdapter(this.myContextStatusImageAdapter);
            ((Button)view.findViewById(R.id.save)).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            updateUsersession();
                            new ProfileManager().update();
                            isEditState = Boolean.FALSE;
//                            finish();
                        }
                    }
            );
            //back button
            ((Button)view.findViewById(R.id.cancel)).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            isEditState = Boolean.FALSE;
//                            finish();
                        }
                    }
            );
            this.editBlock = view;
        }
        return this.editBlock;
    }

    private String getStatusMsg() {return statusMsg.getText().toString();}
    private Integer getStatusId() {return myContextStatusImageAdapter.getActiveStatusID();}

    private void updateUsersession() {
        this.identity.getContext().state.desc = getStatusMsg();
        this.identity.getContext().state.id = getStatusId();
        UserSession userSession = UserSession.getInstance();
        userSession.setIdentity(this.identity);
        userSession.save();
    }
}
