package com.tmobile.reallyme.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *  IsoDate parser for ISO 8601 format
 *
 * @see http://www.w3.org/TR/xmlschema-2/#date <br/> IsoDate format is
 *      (-)PnYnMnDTnHnMn
 * User: Alex Khimich
 * Date: 31.07.2009
 * Time: 18:43:31
 */
public class IsoDate {
    public static final int DATE = 1;
    public static final int TIME = 2;
    public static final int DATE_TIME = 3;

    static void dd(StringBuffer buf, int i) {
        buf.append((char) (((int) '0') + (i / 10)));
        buf.append((char) (((int) '0') + (i % 10)));
    }

    public static String longToString(long timeinmillis, int type) {
        return dateToString(new Date(timeinmillis), type);
    }

    public static String dateToString(Date date, int type) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        StringBuffer buf = new StringBuffer();

        if ((type & DATE) != 0) {
            int year = c.get(Calendar.YEAR);
            dd(buf, year / 100);
            dd(buf, year % 100);
            buf.append('-');
            dd(buf, c.get(Calendar.MONTH) - Calendar.JANUARY + 1);
            buf.append('-');
            dd(buf, c.get(Calendar.DAY_OF_MONTH));

            if (type == DATE_TIME) {
                buf.append("T");
            }
        }

        if ((type & TIME) != 0) {
            dd(buf, c.get(Calendar.HOUR_OF_DAY));
            buf.append(':');
            dd(buf, c.get(Calendar.MINUTE));
            buf.append(':');
            dd(buf, c.get(Calendar.SECOND));
            buf.append('.');

            int ms = c.get(Calendar.MILLISECOND);
            buf.append((char) (((int) '0') + (ms / 100)));
            dd(buf, ms % 100);
            buf.append('Z');
        }

        return buf.toString();
    }

    /**
     * Parses a date in iso Format as a Java Date in UTC.
     * @param text
     * @param type
     * @return
     */
    public static Date stringToDate(String text, int type) {
        //System.out.print(text);
        try {
            int day = -1;
            Calendar c = Calendar.getInstance();

            if ((type & DATE) != 0) {
                c.set(Calendar.YEAR, Integer.parseInt(text.substring(0, 4)));
                c.set(Calendar.MONTH, Integer.parseInt(text.substring(5, 7)) - 1 + Calendar.JANUARY);
                day = Integer.parseInt(text.substring(8, 10));
                c.set(Calendar.DAY_OF_MONTH, day);

                if ((type != DATE_TIME) || (text.length() < 11)) {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);

                    return c.getTime();
                }
                text = text.substring(11);
            } else {
                c.setTime(new Date(0));
            }
            //AK:1969 year fix!
            if (c.get(Calendar.YEAR) == 1969) {
               // System.out.println("APPLY 1969 YEAR fix.");
                return new Date(0);
            }

            int hour = Integer.parseInt(text.substring(0, 2));

            int plus = text.lastIndexOf('+');

            if (plus != -1) {
                hour = hour - Integer.parseInt(text.substring(plus + 2, text.length() - 3));

                if ((hour < 0) && (day != -1)) {
                    day--;
                    c.set(Calendar.DAY_OF_MONTH, day);
                    hour = 24 + hour;
                }

            }
            int minus = text.lastIndexOf('-');

            if (minus != -1) {
                hour = hour + Integer.parseInt(text.substring(minus + 2, text.length() - 3));

                if ((hour >= 24) && (day != -1)) {
                    day++;
                    c.set(Calendar.DAY_OF_MONTH, day);
                    hour = hour - 24;
                }

            }

            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, Integer.parseInt(text.substring(3, 5)));
            c.set(Calendar.SECOND, Integer.parseInt(text.substring(6, 8)));
            int pos = 8;

            if ((pos < text.length()) && (text.charAt(pos) == '.')) {

                int ms = 0;
                int f = 100;

                while (true) {
                    char d = text.charAt(++pos);

                    if ((d < '0') || (d > '9')) {
                        break;
                    }

                    ms += ((d - '0') * f);
                    f /= 10;
                }

                c.set(Calendar.MILLISECOND, ms);
            } else {
                c.set(Calendar.MILLISECOND, 0);
            }
            return c.getTime();
        }
        catch (Exception e) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            long offset = TimeZone.getDefault().getOffset(
                1,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),now.get(Calendar.DAY_OF_WEEK),
                now.get(Calendar.MILLISECOND));
            Date d = new Date(new Date().getTime() - offset);
            System.out.println(" Exception: RETURN CURRENT GMT DATE: "+d);
            return d;
        }
    }
    public static int[] calculateDiff(long time) {
         long diffMills = time;
         int[] result = new int[5];

         int yearDiff = (int)(diffMills/millsInYear);
         diffMills = diffMills % millsInYear;
         result[0] = yearDiff;

         int monthDiff = (int)(diffMills /millsInMonth);
         diffMills = diffMills % millsInMonth;
         result[1] = monthDiff;

         int daysDiff = (int)(diffMills/millsInDay);
         diffMills = diffMills % millsInDay;
         result[2] = daysDiff;

         int hours = (int)(diffMills / millsInHour) ;
         diffMills = diffMills % millsInHour;
         result[3] = hours;

         int mins = (int)(diffMills / millsInMin) ;
         diffMills = diffMills % millsInMin;
         result[4] = mins;
        return result;
    }
    public static long millsInSec = 1000;
    public static long millsInMin = 1000*60;
    public static long millsInHour = 1000*3600;
    public static long millsInDay = millsInHour * 24;
    public static long millsInWeek = millsInDay * 7;
    public static long millsInMonth = millsInDay * 30;
    public static long millsInYear = millsInDay * 365;
}
