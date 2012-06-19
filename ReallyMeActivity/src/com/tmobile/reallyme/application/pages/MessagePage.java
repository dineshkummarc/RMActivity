package com.tmobile.reallyme.application.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.enums.MessageTypeEnum;
import com.tmobile.reallyme.core.api.remote.SendMessageManager;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.IdentityManager;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;
import com.tmobile.reallyme.utils.Utils;

/**
 * User: Kolesnik Aleksey
 * Date: 14.07.2009
 * Time: 11:22:19
 */
public class MessagePage extends Activity {
    private Button ok = null;
    private Identity identity = null;
    private MessageTypeEnum type = null;

    private EditText to = null;
    private EditText subject = null;
    private EditText message = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);

        Intent intent = getIntent();
        Integer typeId = intent.getIntExtra("TYPE_ID", 2);
        for(MessageTypeEnum _type : MessageTypeEnum.values()) {
            if (_type.getId().equals(typeId)) {
                type = _type;
                break;
            }
        }
        String uid = intent.getStringExtra(IdentityDefinition.UID);
        Identity identity = IdentityManager.loadByUID(uid);
        this.identity = identity;
        initLayout();
    }

    private void initLayout() {
         //hide subject
        if (type.equals(MessageTypeEnum.TEXT)) {
            ((LinearLayout) findViewById(R.id.parent_subject)).removeAllViews();
        }
        //
        ((TextView)findViewById(R.id.message_type)).setText(type.getName());
                ((TextView)findViewById(R.id.to)).setText(identity.getFname() + " " + identity.getLname());
        //
        to = ((EditText) findViewById(R.id.to));
        subject = ((EditText) findViewById(R.id.subject));
        message = ((EditText) findViewById(R.id.message));
        //Hook up button presses to the appropriate event handler.
        ok = ((Button) findViewById(R.id.button_ok));
        ok.setOnClickListener(bOkListener);
        ((Button) findViewById(R.id.button_cancel)).setOnClickListener(mBackListener);
    }

    View.OnClickListener bOkListener = new View.OnClickListener() {
        public void onClick(final View v) {
            if (type.equals(MessageTypeEnum.POST)) {
                if (Utils.isNotBlank(getMessage())) {
                    String message = "Subject:" + getSubject() + "\n\n" + getMessage();
                    new SendMessageManager().sendEmail(identity.id, message);
                    finish();
                }
            } else if (type.equals(MessageTypeEnum.TEXT)) {
                if (Utils.isNotBlank(getMessage())) {
                    new SendMessageManager().sendSMS(identity.id, getMessage());
                    finish();
                }
            }
        }
    };

    /**
     * A call-back for when the user presses the back button.
     */
    View.OnClickListener mBackListener = new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

    private String getSubject() {return subject.getText().toString();}
    private String getMessage() {return message.getText().toString();}
}
