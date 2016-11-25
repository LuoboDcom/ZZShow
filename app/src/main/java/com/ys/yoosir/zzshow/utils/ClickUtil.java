package com.ys.yoosir.zzshow.utils;

import android.os.SystemClock;

/**
 * @version 1.0
 * @author yoosir
 * Created by Administrator on 2016/11/22.
 */

public class ClickUtil {

    private static long mLastClickTime = 0;
    private static final int SPACE_TIME = 500;

    /**
     * 屏蔽快速点击
     * @return boolean
     */
    public static boolean isFastDoubleClick(){
        long time = SystemClock.elapsedRealtime();
        if(time - mLastClickTime <= SPACE_TIME){
            return true;
        }else{
            mLastClickTime = time;
            return false;
        }
    }
}
