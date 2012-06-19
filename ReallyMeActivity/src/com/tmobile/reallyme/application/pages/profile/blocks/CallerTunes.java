package com.tmobile.reallyme.application.pages.profile.blocks;

import com.tmobile.reallyme.utils.Utils;
import com.tmobile.reallyme.core.api.remote.profile.CallerTuneSelectionMetaData;
import com.tmobile.reallyme.core.api.remote.profile.ProfileManager;
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
public class CallerTunes extends AbstractCallerTunes {

    protected void initLayout(final LinearLayout linearLayout) {
        final String forTemplet = Utils.addApostrophes(this.identity.getFname()) + " Selection for ";
        final android.content.Context fContext = this.activity;
        showDialog();
        ProfileManager profileManager = new ProfileManager<CallerTuneSelectionMetaData>() {
            @Override
            public void callBack(CallerTuneSelectionMetaData data){
                if (data.list.isEmpty()) {
                    linearLayout.addView(addEmpryMessage("No Caller Tune"));
                } else {
                    for (CallerTuneSelectionElementMetaData element : data.list) {
                        View view = (View)View.inflate(fContext, R.layout.profile_block_caller_tunes_cell, null);
                        ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
                        //temporary
                        if (Utils.isNotBlank(identity.getAvatar())) {
                            imageManager.fetchDrawableLazy(element.imageURL, avatar);
                        }
                        //FOR BLOCK
                        LinearLayout linearLayoutFor = (LinearLayout)view.findViewById(R.id.ll_for);
                        //I hope name is mandatory value
                        if (Utils.isNotBlank(element.name)) {
                            String fname = element.name.split(" ")[0];
                            String lname = element.name.split(" ")[1];
                            ((EditText)view.findViewById(R.id.fname)).setText(fname);
                            ((EditText)view.findViewById(R.id.lname)).setText(lname);
                            ((EditText)view.findViewById(R.id.editTextFor)).setText(forTemplet + Utils.addApostrophes(fname));
                            ((EditText)view.findViewById(R.id.editTextFor)).setText(forTemplet + Utils.addApostrophes(fname));
                        }
                        if (element.mySelection.size() > 0) {
                            for (int i = 0; i < element.mySelection.size(); i++) {
                                linearLayoutFor.addView(createText((i + 1) + ". " + element.mySelection.get(i).title));
                            }
                        } else {
                            linearLayoutFor.addView(createText(noSelection));
                        }
                        linearLayout.addView(view);
                        linearLayout.addView(addSeparator());
                    }
                }
                hideDialog();
            };
        };
        profileManager.getProfileElement(JsonProfileEnum.CALLER_TUNE_SELECTION, this.identity.id);
    }
}