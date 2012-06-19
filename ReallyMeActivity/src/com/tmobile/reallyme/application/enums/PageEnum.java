package com.tmobile.reallyme.application.enums;

import com.tmobile.reallyme.application.controls.peoplelist.PeopleList;
import com.tmobile.reallyme.application.pages.FriendProfile;
import com.tmobile.reallyme.application.pages.MainPage;
import com.tmobile.reallyme.application.pages.MessagePage;
import com.tmobile.reallyme.application.pages.MyProfile;
import com.tmobile.reallyme.application.pages.profile.blocks.*;
import com.tmobile.reallyme.application.pages.profile.customaccordion.CustomAccordion;

/**
 * User: Kolesnik Aleksey
 * Date: 30.06.2009
 * Time: 12:41:42
 */
public enum PageEnum {
       PEOPLE(0, PeopleList.class),
       HERE_AND_NOW(1, null),
       FRIEND_PROFILE(2, FriendProfile.class),
       MY_PROFILE(3, MyProfile.class),
       MAIN(4, MainPage.class),
       MESSAGE(5, MessagePage.class),


       PROFILE_ACCORDION(1010, CustomAccordion.class),
       PROFILE_INFO(100, Info.class),
       PROFILE_MUTUAL_FRIEND(101, MutualFriends.class),
       PROFILE_FRIEND_CONTEXT(102, Context.class),
       PROFILE_MY_CONTEXT(103, MyContext.class),

       PROFILE_CALLER_TUNES(130, CallerTunes.class),
       PROFILE_MY_CALLER_TUNES(131, MyCallerTunes.class),

       PROFILE_ACTIVITY(140, Activity.class);

       private Integer id;
       private Class clazz;

       PageEnum(Integer id, Class clazz) {
           this.id = id;
           this.clazz = clazz;
       }

       public Integer getId() {
           return id;
       }

       public Class getClazz() {
           return this.clazz;
       }

       public Boolean equals(PageEnum page) {
           return this.id.equals(page.getId());
       }
   }