package com.example.app.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangshuwen on 2017/6/21.
 */
public class TimeUtils {

    /**
     * 把 秒 换算成 时、分、秒 int
     */
    public static Triple<Integer, Integer, Integer> processSeconds(int seconds) {
        return new Triple<>(
                seconds / (60 * 60),
                seconds / 60,
                seconds % 60
        );
    }


    public static String formatTimeSeconds(int seconds) {
        String timeString = (seconds / 60 < 10 ? ("0" + seconds / 60) : String.valueOf(seconds / 60))
                + ":"
                + (seconds % 60 < 10 ? ("0" + seconds % 60) : String.valueOf(seconds % 60));
        return timeString;
    }


    public static String formatTimestamp(String timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date myDate = null;
        try {
            myDate = format.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null == myDate) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(myDate);
    }

    public static String formatTimestampCurrent() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static String formatTimestampS(long timestamp) {
        if (timestamp < Math.pow(10, 11)) {
            timestamp = timestamp * 1000;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(new Date(timestamp));
    }


    public static String formatTimestamp(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static String formatTimestampM(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        return simpleDateFormat.format(new Date(timestamp));
    }


    public static String formatTimestampMD(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static String formatTimestampCustom(long timestamp, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp));
    }


    public static String getDynamicTime(long timestamp) {
        if (timestamp < Math.pow(10, 11)) {
            timestamp = timestamp * 1000;
        }
        long currentTimeMillis = System.currentTimeMillis();
        int reduceTime = (int) ((currentTimeMillis - timestamp) / 1000);
        if (reduceTime < 300) {
            return "刚刚";
        }
        int minutes = reduceTime / 60;
        if (minutes < 60) {
            return minutes + "分钟前";
        }
        int hours = reduceTime / 3600;
        if (hours < 24) {
            return hours + "小时前";
        }
        int days = reduceTime / 3600 / 24;
        if (days < 10) {
            return days + "天前";
        }
        return formatTimestampM(timestamp);
    }


    public static String getRaffleTime(long timestamp) {
        if (timestamp < Math.pow(10, 11)) {
            timestamp = timestamp * 1000;
        }
        long currentTimeMillis = System.currentTimeMillis();
        int reduceTime = (int) ((timestamp - currentTimeMillis) / 1000);
        if (reduceTime < 60) {
            return reduceTime + "秒";
        }
        int minutes = reduceTime / 60;
        if (minutes < 60) {
            return minutes + "分钟";
        }
        int hours = reduceTime / 3600;
        if (hours < 24) {
            return hours + "小时";
        }
        int days = reduceTime / 3600 / 24;
        if (days < 365) {
            return days + "天";
        }
        int year = days / 365;
        return year + "年";
    }

    public static long getCurrentTime() {
        return new Date().getTime();
    }
}
