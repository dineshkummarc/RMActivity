package com.tmobile.reallyme.application.pages;


import android.app.ExpandableListActivity;
import android.os.Bundle;

import com.tmobile.reallyme.R;

/**
 * User: Kolesnik Aleksey
 * Date: 17.07.2009
 * Time: 1:14:57
 */
public class TempPage extends ExpandableListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_faves_layout);
    }
}
