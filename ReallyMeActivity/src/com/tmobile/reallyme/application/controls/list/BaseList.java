package com.tmobile.reallyme.application.controls.list;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;

import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.TestIdentityDataList;

/**
 * dku
 */
public class BaseList extends View {
    ArrayList<Identity> dataList = TestIdentityDataList.getDataList();
    public BaseList(Context context) {
        super(context);        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearLayout linearLayout = new LinearLayout(getContext());
        int index = 0;
        int hOffset = 0;
        for(Identity item : dataList){
            int itemHeight = getHeightById(index);
            BaseListItem listItem = new BaseListItem(getContext(),0,0);
            listItem.location = item.getContext().location.name;
            listItem.fname = item.getFname();
            listItem.lname = item.getLname();
            listItem.statusText = item.getContext().state.desc;
            listItem.itemHeight = itemHeight;
            listItem.itemWidth = getWidthById(index);
            
            listItem.layout((canvas.getWidth() - listItem.itemWidth)/2, hOffset, canvas.getWidth(), hOffset + itemHeight);
            linearLayout.addView(listItem);
            hOffset += itemHeight+3;

            index++;
        }
        linearLayout.draw(canvas);
        
    }
    private Integer getWidthById(int idx){
        return getWidth() - idx * 10;
    }
    private Integer getHeightById(int idx){
        return 60 - idx * 2;
    }
}
