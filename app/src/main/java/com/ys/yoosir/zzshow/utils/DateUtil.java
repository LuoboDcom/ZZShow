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

}
