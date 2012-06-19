package com.tmobile.reallyme.application.controls.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.persistence.TestIdentityDataList;


/**
 * dku
 */
public class BaseListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Identity> dataList = new ArrayList<Identity>();
    private int hOffset = 0;
    
    public BaseListViewAdapter(Context context) {
        this.context = context;
        dataList = TestIdentityDataList.getDataList();        
    }

    public int getCount() {
        return dataList.size();
    }

    public Identity getItem(int i) {
        return dataList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }
    
    public View getView(int index, View view, ViewGroup viewGroup) {
        BaseListItem listItem = null;
        int itemHeight = getHeightById(index);
        int itemWidth = getWidthById(index);
        if(view == null){
            listItem = new BaseListItem(context, itemWidth, itemHeight);
        }else{
            if(view instanceof RelativeLayout){
                View item = ((RelativeLayout)view).getChildAt(0);
                if(item instanceof BaseListItem)
                    listItem = (BaseListItem) item;
            }
            //listItem = (BaseListItem)view;
        }
        if(listItem != null){
            Identity identity = dataList.get(index);
            listItem.setFname(identity.getFname());
            listItem.setLname(identity.getLname());
            listItem.location = identity.getContext().location.name;
            listItem.statusText = identity.getContext().state.desc;
            //listItem.avatar = identity.avatarUrl;
            listItem.itemHeight = itemHeight;
            listItem.itemWidth = itemWidth;
            //listItem.measure(itemHeight, listItem.itemWidth);
            //listItem.layout((400 - listItem.itemWidth)/2, hOffset, 400, hOffset + itemHeight);
            //listItem.measure(itemWidth, itemHeight);
            hOffset += itemHeight+3;
            //listItem.invalidate();
            if(view == null){
                RelativeLayout layout = new RelativeLayout(context);
                layout.addView(listItem);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);
                return layout;
            }else{
                return view;
            }
        }
        return null;
    }

    private Integer getWidthById(int idx){
        return 320 - idx * 10;
    }
    private Integer getHeightById(int idx){
        return 60 - idx * 2;
    }

}
