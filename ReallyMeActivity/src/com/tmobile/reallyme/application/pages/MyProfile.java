package com.tmobile.reallyme.application.pages;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.tmobile.reallyme.application.pages.profile.AbstractProfile;
import com.tmobile.reallyme.application.pages.profile.AccordionEnum;
import com.tmobile.reallyme.core.UserSession;
import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.definition.IdentityDefinition;

import java.util.LinkedList;

/**
 * User: Kolesnik Aleksey
 * Date: 09.07.2009
 * Time: 17:28:14
 */
public class MyProfile extends AbstractProfile {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   }

    public Identity initIdentity() {
        return UserSession.getInstance().getIdentity();
    }

    @Override
    protected Cursor getCursor(){
        final Intent intent = getIntent();
        String uid = intent.getStringExtra(IdentityDefinition.UID);
        return content.query(IdentityDefinition.CONTENT_URI,
                null, IdentityDefinition.UID + "=?", new String[]{UserSession.getInstance().getIdentity().id}, null);
    }
    
    public LinkedList<AccordionEnum> initAccordion() {
        LinkedList<AccordionEnum> accordionEnums = new LinkedList();
        accordionEnums.add(AccordionEnum.INFO);
        accordionEnums.add(AccordionEnum.MY_CONTEXT);
        accordionEnums.add(AccordionEnum.MUTUAL_FRIENDS);
        accordionEnums.add(AccordionEnum.MY_CALLER_TUNES);
        accordionEnums.add(AccordionEnum.ACTIVITY);
        return accordionEnums;
    }
}
