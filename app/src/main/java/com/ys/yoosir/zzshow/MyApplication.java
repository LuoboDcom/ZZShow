package com.ys.yoosir.zzshow;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;

/**
 * @version 1.0
 * Created by Yoosir on 2016/10/21 0021.
 */
public class MyApplication extends Application{

    private static Context mInstance;

    public static Context getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initDayNightMode();
        KLog.init(true);
    }

    private void initDayNightMode() {
        if (SharedPreferencesUtil.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
