package com.tmobile.reallyme.application.pages.profile.blocks;

import com.tmobile.reallyme.utils.Utils;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.api.remote.profile.ProfileManager;
import com.tmobile.reallyme.core.api.remote.profile.CallerTuneSelectionMetaData;
import com.tmobile.reallyme.core.api.remote.profile.CallerTuneSelectionElementMetaData;
import com.tmobile.reallyme.core.api.remote.profile.JsonProfileEnum;
import com.tmobile.reallyme.R;
import android.view.View;
import android.widget.*;

/**
 * User: Kolesnik Aleksey
 * Date: 19.07.2009
 * Time: 18:21:55
 */
public class MyCallerTunes extends AbstractCallerTunes {

    protected void initLayout(final LinearLayout linearLayout) {
        final String forTemplet = "My Selection for ";
        final android.content.Context fContext = this.activity;
        showDialog();
        ProfileManager profileManager = new ProfileManager<CallerTuneSelectionMetaData>() {
            @Override
            public void callBack(CallerTuneSelectionMetaData data){
                if (data.list.isEmpty()) {
                    linearLayout.addView(addEmpryMessage("No My Caller Tunes"));
                } else {
                    for (CallerTuneSelectionElementMetaData element : data.list) {
                        String fname = element.name.split(" ")[0];
                        String lname = element.name.split(" ")[1];

                        View view = (View)View.inflate(fContext, R.layout.profile_block_my_caller_tunes_cell, null);
                        ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
                        //temporary
                        if (Utils.isNotBlank(identity.getAvatar())) {
                            imageManager.fetchDrawableLazy(element.imageURL, avatar);
                        }
                        //FOR BLOCK
                        LinearLayout linearLayoutFor = (LinearLayout)view.findViewById(R.id.ll_for);
                        //I hope name is mandatory value
                        if (Utils.isNotBlank(element.name)) {
                            ((EditText)view.findViewById(R.id.editTextFor)).setText(forTemplet + Utils.addApostrophes(fname));
                            ((EditText)view.findViewById(R.id.editTextFor)).setText(forTemplet + Utils.addApostrophes(fname));
                        }
                        for (int i = 0; i < element.mySelection.size(); i++) {
                            TextView text = new TextView(fContext);
                            text.setText(i + ". " + element.mySelection.get(i).title);
                            linearLayoutFor.addView(text);
                        }
                        //TO BLOCK
                        LinearLayout linearLayoutTo = (LinearLayout)view.findViewById(R.id.ll_to);
                        EditText editTextTo = (EditText)view.findViewById(R.id.editTextTo);
                        //I hope name is mandatory value
                        if (Utils.isNotBlank(element.name)) {
                            editTextTo.setText(Utils.addApostrophes(fname) + " Selection for Me ");
                        }
                        if (element.selectionForMe.size() > 0) {
                            for (int i = 0; i < element.selectionForMe.size(); i++) {
                                linearLayoutTo.addView(createText((i + 1) + ". " + element.selectionForMe.get(i).title));
                            }
                        } else {
                            linearLayoutTo.addView(createText(noSelection));
                        }
                        linearLayout.addView(view);
                        linearLayout.addView(addSeparator());
                    }
                }
                hideDialog();
            };
        };
        profileManager.getProfileElement(JsonProfileEnum.CALLER_TUNE_SELECTION, null);
    }
}