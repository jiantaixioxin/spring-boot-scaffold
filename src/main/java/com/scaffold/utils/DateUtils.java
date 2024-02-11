package com.scaffold.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String YYYY_MM_DD_HH_MM_SS ="yyyy-MM-dd HH:mm:ss";

    /*
    * 获取系统当前时间
    * */
    public static String getDateTime(){
        SimpleDateFormat sdf= new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(System.currentTimeMillis());
    }
    /*
     * 获取系统当前时间
     * */
    public static Date getNowDate(){
        return new Date();
    }

    /*
    * 获取时间戳
    * */
    public static String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis());
    }


}
