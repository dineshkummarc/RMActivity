package com.tmobile.reallyme.application.enums;

import com.tmobile.reallyme.R;

/**
 * Created by IntelliJ IDEA.
 * User: Kolesnik Aleksey
 * Date: 04.07.2009
 * Time: 17:56:32
 */
public enum MessageTypeEnum {
    //like sms
    TEXT(3, "TEXT", R.drawable.btn_text, R.drawable.btn_text_disabled, R.drawable.btn_text_onpress),
    //like call
    TALK(4, "TALK", R.drawable.btn_talk, R.drawable.btn_talk_disabled, R.drawable.btn_talk_onpress),
    //like email
    POST(2, "POST", R.drawable.btn_post, R.drawable.btn_post_onpress, R.drawable.btn_post_disabled),
    UNDEFINED(0, "EQUALS_TEXT_MESSAGE", R.drawable.btn_post, R.drawable.btn_post_onpress, R.drawable.btn_post_disabled);

    private Integer id;
    private String name;
    private Integer imageResource;
    private Integer disabledImageResource;
    private Integer onpressImageResource;

    MessageTypeEnum(Integer id, String name, Integer imageResource, Integer disabledImageResource, Integer onpressImageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.disabledImageResource = disabledImageResource;
        this.onpressImageResource = onpressImageResource;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public Integer getDisabledImageResource() {
        return disabledImageResource;
    }

    public Integer getOnpressImageResource() {
        return onpressImageResource;
    }
}
