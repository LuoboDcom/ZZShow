package com.ys.yoosir.zzshow;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

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
        KLog.init(true);
    }
}
