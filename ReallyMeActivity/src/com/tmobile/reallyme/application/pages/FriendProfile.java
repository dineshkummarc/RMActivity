package com.tmobile.reallyme.application.pages;

import java.util.Timer;
import java.util.TimerTask;
import java.util.LinkedList;
import java.util.Collection;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.application.enums.PageEnum;
import com.tmobile.reallyme.application.managers.PageManager;
import com.tmobile.reallyme.application.pages.profile.AbstractProfile;
import com.tmobile.reallyme.application.pages.profile.AccordionEnum;
import com.tmobile.reallyme.core.api.remote.CallManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 09.07.2009
 * Time: 17:28:25
 */
public class FriendProfile extends AbstractProfile {
    private ImageView talkBt = null;
    private ImageView textBt = null;
    private ImageView postBt = null;
    private View channelsView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelsView = View.inflate(this, R.layout.channels, null);
        FrameLayout frameLayout = ((FrameLayout) findViewById(R.id.channels));
        frameLayout.addView(channelsView);
        initChannels();
    }
    @Override
    protected Cursor getCursor(){
        final Intent intent = getIntent();
        String uid = intent.getStringExtra(IdentityDefinition.UID);
        return content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.UID + "=?", new String[]{uid}, null);
    }

    public LinkedList<AccordionEnum> initAccordion() {
        LinkedList<AccordionEnum> accordionEnums = new LinkedList();
        accordionEnums.add(AccordionEnum.INFO);
        accordionEnums.add(AccordionEnum.CONTEXT);
        accordionEnums.add(AccordionEnum.MUTUAL_FRIENDS);
        accordionEnums.add(AccordionEnum.CALLER_TUNES);
        accordionEnums.add(AccordionEnum.ACTIVITY);
        return accordionEnums;
    }

    private void initChannels() {
        addButtonListeners();
        setChannelButtons();
    }

    private void addButtonListeners() {
        talkBt = ((ImageView) channelsView.findViewById(R.id.p_talk));
        textBt = ((ImageView) channelsView.findViewById(R.id.p_text));
        postBt = ((ImageView) channelsView.findViewById(R.id.p_post));
    }
    /*Generate buttons for current user*/
     private void setChannelButtons() {
        if (Utils.isTalkChannels(preferredChannels)) {
            talkBt.setImageResource(MessageTypeEnum.TALK.getImageResource());
            talkBt.setOnClickListener(talkListener);
        }
        if (Utils.isTextChannels(preferredChannels)) {
            textBt.setImageResource(MessageTypeEnum.TEXT.getImageResource());
            textBt.setOnClickListener(textListener);
        }
        if (Utils.isPostChannels(preferredChannels)) {
            postBt.setImageResource(MessageTypeEnum.POST.getImageResource());
            postBt.setOnClickListener(postListener);
        }
    }

    View.OnClickListener talkListener = new View.OnClickListener() {
        public void onClick(View v) {
            talkBt.setImageResource(MessageTypeEnum.TALK.getOnpressImageResource());
            showDialog(DIALOG_KEY);
            talkBt.setImageResource(MessageTypeEnum.TALK.getImageResource());
            new CallManager().call(identityUID);
            new Timer().schedule(new TimerTask() {
                  public void run() {
                      removeDialog(DIALOG_KEY);
                  }
            }, 5*1000);
        }
    };

    View.OnClickListener textListener = new View.OnClickListener() {
        public void onClick(View v) {
            textBt.setImageResource(MessageTypeEnum.TEXT.getOnpressImageResource());
            Intent extras = new Intent();
            extras.putExtra("TYPE_ID", MessageTypeEnum.TEXT.getId());
            extras.putExtra(IdentityDefinition.UID, identityUID);
            PageManager.openPage(v, PageEnum.MESSAGE, extras);
            textBt.setImageResource(MessageTypeEnum.TEXT.getImageResource());
        }
    };

    View.OnClickListener postListener = new View.OnClickListener() {
        public void onClick(View v) {
            postBt.setImageResource(MessageTypeEnum.POST.getOnpressImageResource());
            Intent extras = new Intent();
            extras.putExtra("TYPE_ID", MessageTypeEnum.POST.getId());
            extras.putExtra(IdentityDefinition.UID, identityUID);
            PageManager.openPage(v, PageEnum.MESSAGE, extras);
            postBt.setImageResource(MessageTypeEnum.POST.getImageResource());
        }
    };
    private static final int DIALOG_KEY = 0;
    @Override
    protected Dialog onCreateDialog(int id) {
         switch (id) {
            case DIALOG_KEY: {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Calling...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }
}
