package com.tmobile.reallyme.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * User: Kolesnik Aleksey
 * Date: 31.07.2009
 * Time: 18:37:52
 */
public class TimeUtils {


    public static String getUpdatedTimeText(Date date){
       String timestampText = "";
       if(date != null){
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar recieved = Calendar.getInstance();

            int offset = TimeZone.getDefault().getOffset(
                1,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),now.get(Calendar.DAY_OF_WEEK),
                now.get(Calendar.MILLISECOND));
            recieved.setTime(date);

            long c = new Date().getTime();
            long gmtMills = c - offset;

            //nowDate.setTime(gmtMills);
            //now.setTime(new Date(gmtMills));
            long diffMills1 = gmtMills - date.getTime();
            long diffMills = now.getTime().getTime() - recieved.getTime().getTime();
            //log.info("DIFF1={diffMills1}, DIFF2={diffMills}");

            //log.info("Now date = {now.getTime()} | lastUpdate = {recieved.getTime()} | ms diff={diffMills}");
            /*
            AK:Workaround. Do calculation in plain java because FX has issues with division.
            */
            int[] r  = IsoDate.calculateDiff(diffMills);
            int yearDiff = r[0];
            int monthDiff = r[1];
            int daysDiff = r[2];
            int hours = r[3];
            int mins = r[4];

            //log.info("years {yearDiff}, months {monthDiff}, days {daysDiff}, hours {hours}, mins {mins}");
            //log.info("years {now.get(Calendar.YEAR) - recieved.get(Calendar.YEAR)}, months {now.get(Calendar.MONTH) - recieved.get(Calendar.MONTH)}, days {now.get(Calendar.DAY_OF_MONTH) - recieved.get(Calendar.DAY_OF_MONTH)}, hours {now.get(Calendar.HOUR_OF_DAY) - recieved.get(Calendar.HOUR_OF_DAY)}, mins {mins}, lastUpdate {data.lastUpdate}");
            if(now.get(Calendar.YEAR) - recieved.get(Calendar.YEAR) == 1){
               return timestampText = "Last year";
            }
            if(yearDiff == 0){
                if (now.get(Calendar.MONTH) - recieved.get(Calendar.MONTH) == 1) {
                    return timestampText = "Last month";
                }
                if(monthDiff==0){
                    if (now.get(Calendar.DAY_OF_MONTH) - recieved.get(Calendar.DAY_OF_MONTH) == 1) {
                        return timestampText = "Yesterday";
                    }

                    if(daysDiff == 0) {
                        if(hours == 0){//less than hour ago
                            if(mins <= 1) timestampText = "Just now";
                            if(mins > 1) timestampText = mins + " minutes ago";
                        }else{
                            if(hours <= 1) timestampText = "Today, one hour ago";
                            if(hours >= 2) timestampText = "Today, " + hours + " hours ago";
                        }
                    }else{
                        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
                        if(dayOfWeek - daysDiff > 0){
                            int days = hours/24;
                            if(days <= 1){
                                timestampText = "One day ago";
                            }else{
                                timestampText = days + " days ago";
                            }
                        }else {
                            int weeks = daysDiff / 7;
                            if(weeks <= 1){
                                timestampText = "Last week";
                            }else{
                                timestampText = weeks + " weeks ago";
                            }
                        }
                    }
                }else {
                    if(monthDiff==1){
                        timestampText = monthDiff + " month ago";
                    }else{
                        timestampText = monthDiff + " months ago";
                    }
                }
            }else{
                timestampText = yearDiff + " years ago";
            }
        }
        return timestampText;
    }
}
