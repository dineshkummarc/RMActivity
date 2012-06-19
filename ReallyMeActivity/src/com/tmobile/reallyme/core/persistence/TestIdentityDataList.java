package com.tmobile.reallyme.core.persistence;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.tmobile.reallyme.core.api.remote.pojo.Identity;
import com.tmobile.reallyme.core.api.remote.pojo.Location;
import com.tmobile.reallyme.core.api.remote.pojo.State;


public class TestIdentityDataList {
    public static ArrayList<Identity> dataList = new ArrayList<Identity>();

    public void initDb(Context context){
        ContentResolver content = context.getContentResolver();
        ContentValues values = new ContentValues();
        getDataList();
        for (Identity identity : dataList){
            IdentityManager.insert(identity, IdentityTypeEnum.FRIEND);
            IdentityManager.insert(identity, IdentityTypeEnum.MY);
        }

    }

    public static ArrayList<Identity> getDataList(){

        Identity identity = new Identity();

        identity.id = "1";
        identity.setFname("Aleksey");
        identity.setLname("Kolesnik");
        com.tmobile.reallyme.core.api.remote.pojo.Context context = new com.tmobile.reallyme.core.api.remote.pojo.Context();
        context.location = new Location("Kharkov", "");
        context.state = new State(1, "Cool");
        identity.setContext(context);
        identity.setAvatar("http://profile.ak.facebook.com/v225/105/1/q774088254_7075.jpg");

        dataList.add(identity);
        /*
        item = new Identity();
        item.id = "2";
        item.location="Kharkov";
        item.firstName = "Adam";
        item.lastName = "Clarke";
        item.statusMassage = "Silent: Android Demo";
        item.avatarUrl = "http://profile.ak.facebook.com/v222/184/79/q591544571_5580.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "3";
        item.location="Mexico";
        item.firstName = "Brian";
        item.lastName = "Martin";
        item.statusMassage = "Available: Watching Wall - E";
        item.avatarUrl = "http://profile.ak.facebook.com/v225/1333/67/q644492494_5900.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "4";
        item.location="Kiev";
        item.firstName = "Jane";
        item.lastName = "Fynn";
        item.statusMassage = "Available: Reading…";
        item.avatarUrl = "http://profile.ak.facebook.com/v226/1866/90/q737707645_487.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "5";
        item.location="Kiev";
        item.firstName = "Jim";
        item.lastName = "Farr";
        item.statusMassage = "Available: Reading…";
        item.avatarUrl = "http://profile.ak.facebook.com/v222/176/8/q1040184291_8220.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "6";
        item.location="Kiev";
        item.firstName = "Sriram";
        item.lastName = "Iyer";
        item.statusMassage = "Available: Reading…";
        item.avatarUrl = "http://profile.ak.facebook.com/v230/1240/36/q1016038549_3262.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "7";
        item.location="Kiev";
        item.firstName = "Lakisha";
        item.lastName = "Brooks";
        item.statusMassage = "Available: Reading…";
        item.avatarUrl = "http://profile.ak.facebook.com/v230/1546/21/q588158189_2529.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "8";
        item.location="Kiev";
        item.firstName = "Shannon";
        item.lastName = "Jones";
        item.statusMassage = "wow ! I know a lot of MJ fans ! BTW, MJ passing is not in newspaper in India, so if it were not for FB/Twitter, Id have never learned of it.  A wave of compassion for the little boy who never had a childhood.";
        item.avatarUrl = "http://profile.ak.facebook.com/v230/1748/36/q1052505669_9365.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "9";
        item.location="Kiev";
        item.firstName = "Jyotsna";
        item.lastName = "Pattabiraman";
        item.statusMassage = "happy monsoon...";
        item.avatarUrl = "http://profile.ak.facebook.com/v224/1875/105/q730805513_2621.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "10";
        item.location="California";
        item.firstName = "Glenn";
        item.lastName = "Cochran";
        item.statusMassage = "MJ will most definitely be missed. RIP!";
        item.avatarUrl = "http://profile.ak.facebook.com/v225/1542/109/q628857344_1950.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "11";
        item.location="Kiev";
        item.firstName = "Connie";
        item.lastName = "Chiu";
        item.statusMassage = "Thinks New Wave is gonna make a comeback.";
        item.avatarUrl = "http://profile.ak.facebook.com/v230/1687/39/q691652649_7711.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "12";
        item.location="California";
        item.firstName = "Rand";
        item.lastName = "Bradley";
        item.statusMassage = "Useful information on twitter searches http://tr.im/pLaD.";
        item.avatarUrl = "http://profile.ak.facebook.com/v225/448/120/q687756514_2980.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "13";
        item.location="San Francisco";
        item.firstName = "Sangjin";
        item.lastName = "Lee";
        item.statusMassage = "MJ will most definitely be missed. RIP!";
        item.avatarUrl = "http://profile.ak.facebook.com/v222/1088/100/q759503581_2949.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "14";
        item.location="Kiev";
        item.firstName = "Thomas";
        item.lastName = "Wilsher";
        item.statusMassage = "Thinks New Wave is gonna make a comeback.";
        item.avatarUrl = "http://profile.ak.facebook.com/v225/1327/30/q586395784_1867.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);

        item = new Identity();
        item.id = "15";
        item.location="New Delhi";
        item.firstName = "Tarun";
        item.lastName = "Arora";
        item.statusMassage = "just realised that i am already a part of a management workflow - sounds like floating in a workflow - Enjoying the project mgmt bit..";
        item.avatarUrl = "http://profile.ak.facebook.com/v224/871/39/q858510143_3290.jpg";
        item.lastUpdate = new Date();
        dataList.add(item);
                             */
        return dataList;
    }

}
