package com.ys.yoosir.zzshow.utils;

import com.ys.yoosir.zzshow.MyApplication;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public class DimenUtil {

    public static float dp2px(float dp) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(float sp) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int getScreenSize() {
        return MyApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

}
