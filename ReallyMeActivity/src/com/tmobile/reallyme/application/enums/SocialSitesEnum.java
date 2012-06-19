package com.tmobile.reallyme.application.enums;

import com.tmobile.reallyme.R;

/**
 * User: Kolesnik Aleksey
 * Date: 29.07.2009
 * Time: 17:50:48
 */
public enum SocialSitesEnum {
    TWITTER("ss_0", "Twitter", R.drawable.social_site_twitter_logo),
    FACEBOOK("ss_1", "Facebook", R.drawable.social_site_facebook_logo),
    GOOGLE("ss_2", "Google", R.drawable.social_site_google_logo),
    MOBILE("ss_3", "Mobile", R.drawable.social_site_mobile_logo),
    EQUALS("ss_4", "Equals", R.drawable.social_site_equals_logo),
    UNDEFINED("ss_300", "Undefined", R.drawable.spacer);

    private String uid;
    private String name;
    private Integer imageResource;

    SocialSitesEnum(String uid, String name, Integer imageResource) {
        this.uid = uid;
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public static SocialSitesEnum getByName(String name) {
        for(SocialSitesEnum _enum : SocialSitesEnum.values()) {
            if (_enum.getName().equalsIgnoreCase(name)) {
                return _enum;
            }
        }
        return UNDEFINED;
    }
}
