package com.ys.yoosir.zzshow.utils;

import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/11 0011.
 */
public class DateUtil {

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     * @param before 时间戳
     * @return  日期
     */
    public static String formatDate(String before){
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm",Locale.getDefault()).format(date);
        } catch (ParseException e) {
            KLog.e("转换新闻日期格式异常：" + e.toString());
            return before;
        }
        return after;
    }

    /**
     *   将 秒 100 转为  01：40 格式
     * @param length
     * @return
     */
    public static String getLengthStr(long length) {
        int hour = (int) (length / (60 * 60));
        int hourLast = (int) (length % (60 * 60));
        int minute = hourLast / 60;
        int second = hourLast % 60;
        StringBuffer sb = new StringBuffer();
        if(hour != 0){
            sb.append(zeroize(hour));
            sb.append(":");
        }

        if(minute != 0){
            sb.append(zeroize(minute));
            sb.append(":");
        }else{
            sb.append("00:");
        }

        if(second != 0){
            sb.append(zeroize(second));
        }else{
            sb.append("00");
        }

        return sb.toString();
    }

    public static String zeroize(int time){
        if(time < 10){
            return "0" + time ;
        }
        return String.valueOf(time);
    }

}
