package com.tmobile.reallyme.application.pages.profile;

import com.tmobile.reallyme.R;
import com.tmobile.reallyme.application.pages.profile.blocks.*;

/**
 * User: Kolesnik Aleksey
 * Date: 16.07.2009
 * Time: 17:04:49
 */
public enum AccordionEnum{
        CONTEXT("00", "Context",
                R.drawable.profile_tabs_context_selected,
                R.drawable.profile_tabs_context_unselected,
                R.drawable.profile_tabs_context_onpress, new Context()),

        MY_CONTEXT("01", "My Context",
                R.drawable.profile_tabs_context_selected,
                R.drawable.profile_tabs_context_unselected,
                R.drawable.profile_tabs_context_onpress, new MyContext()),

        INFO("1", "Info",
                R.drawable.profile_tabs_info_selected,
                R.drawable.profile_tabs_info_unselected,
                R.drawable.profile_tabs_info_onpress, new Info()),

        MUTUAL_FRIENDS("20", "Mutual Friends",
                R.drawable.profile_tabs_mutual_friends_selected,
                R.drawable.profile_tabs_mutual_friends_unselected,
                R.drawable.profile_tabs_mutual_friends_onpress, new MutualFriends()),

        CALLER_TUNES("30", "Caller Tunes",
                R.drawable.profile_tabs_caller_tunes_selected,
                R.drawable.profile_tabs_caller_tunes_unselected,
                R.drawable.profile_tabs_caller_tunes_onpress, new CallerTunes()),

        MY_CALLER_TUNES("31", "My Caller Tunes",
                R.drawable.profile_tabs_caller_tunes_selected,
                R.drawable.profile_tabs_caller_tunes_unselected,
                R.drawable.profile_tabs_caller_tunes_onpress, new MyCallerTunes()),

        ACTIVITY("40", "Last Updates",
                R.drawable.profile_tabs_caller_tunes_selected,
                R.drawable.profile_tabs_caller_tunes_unselected,
                R.drawable.profile_tabs_caller_tunes_onpress, new Activity());

        private String uid = null;
        private String name = null;
        private Integer selectedImageResource;
        private Integer unselectedImageResource;
        private Integer onpressImageResource;
        private AbstractBlock clazz = null;

        AccordionEnum(String uid, String name, Integer selectedImageResource, Integer unselectedImageResource,
                      Integer onpressImageResource, AbstractBlock clazz) {
            this.uid = uid;
            this.name = name;
            this.selectedImageResource = selectedImageResource;
            this.unselectedImageResource = unselectedImageResource;
            this.onpressImageResource = onpressImageResource;
            this.clazz = clazz;
        }

        public String getId() {
            return uid;
        }

        public String getName() {
            return this.name;
        }

        public Integer getOnpressImageResource() {
            return onpressImageResource;
        }

        public Integer getUnselectedImageResource() {
            return unselectedImageResource;
        }

        public Integer getSelectedImageResource() {
            return selectedImageResource;
        }

        public AbstractBlock getClazz() {
            return clazz;
        }
    
        public Boolean equals(AccordionEnum ae) {
            return this.uid.equals(ae.getId());
        }
    }
