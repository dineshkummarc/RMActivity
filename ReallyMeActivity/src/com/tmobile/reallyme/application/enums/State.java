package com.tmobile.reallyme.application.enums;

import com.tmobile.reallyme.R;

/**
 * User: Kolesnik Aleksey
 * Date: 29.06.2009
 * Time: 10:31:15
 */
public enum State {
    HOME(1, "At Home",
            R.drawable.state_at_home,
            R.drawable.state_icons_large_athome_selected,
            R.drawable.state_icons_large_athome_unselected,
            R.drawable.state_icons_large_athome_onpress, "At Home"),

    WORK(2, "Working", R.drawable.state_working,
            R.drawable.state_icons_large_working_selected,
            R.drawable.state_icons_large_working_unselected,
            R.drawable.state_icons_large_working_onpress, "Working"),

    BUSY(3, "Busy", R.drawable.state_unavailable,
            R.drawable.state_icons_large_unavailable_selected,
            R.drawable.state_icons_large_unavailable_unselected,
            R.drawable.state_icons_large_unavailable_onpress, "Busy"),

    SILENT(4, "Silent", R.drawable.state_silent,
            R.drawable.state_icons_large_silent_selected,
            R.drawable.state_icons_large_silent_unselected,
            R.drawable.state_icons_large_silent_onpress, "Silent"),
    
    SHOPPING(5, "Shopping",  R.drawable.state_shopping,
            R.drawable.state_icons_large_shopping_selected,
            R.drawable.state_icons_large_shopping_unselected,
            R.drawable.state_icons_large_shopping_onpress, "Shopping"),

    TRAVEL(6, "Travel", R.drawable.state_travelling,
            R.drawable.state_icons_large_travelling_selected,
            R.drawable.state_icons_large_travelling_unselected,
            R.drawable.state_icons_large_travelling_onpress, "Travelling"),

    AVAILABLE(7, "Available",  R.drawable.state_available,
            R.drawable.state_icons_large_available_selected,
            R.drawable.state_icons_large_available_unselected,
            R.drawable.state_icons_large_available_onpress, "Available"),

    OFFLINE(8, "Offline", R.drawable.state_offline,
            R.drawable.state_icons_large_offline_selected,
            R.drawable.state_icons_large_offline_unselected,
            R.drawable.state_icons_large_offline_onpress, "Offline");


    private Integer id;
    private String name;
    private String text;

    private Integer selectedImageResourceSmall;

    private Integer selectedImageResourceLarge;
    private Integer unselectedImageResourceLarge;
    private Integer onpressImageResourceLarge;

    State(Integer id, String name, Integer selectedImageResourceSmall,
          Integer selectedImageResourceLarge, Integer unselectedImageResourceLarge,
          Integer onpressImageResourceLarge, String text) {
        this.id = id;
        this.name = name;
        this.selectedImageResourceSmall = selectedImageResourceSmall;
        this.selectedImageResourceLarge = selectedImageResourceLarge;
        this.unselectedImageResourceLarge = unselectedImageResourceLarge;
        this.onpressImageResourceLarge = onpressImageResourceLarge;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSelectedImageResourceSmall() {
        return selectedImageResourceSmall;
    }

    public Integer getOnpressImageResourceLarge() {
        return onpressImageResourceLarge;
    }

    public Integer getUnselectedImageResourceLarge() {
        return unselectedImageResourceLarge;
    }

    public Integer getSelectedImageResourceLarge() {
        return selectedImageResourceLarge;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static State getById(Integer id) {
        return State.values()[id-1];
    }
}
