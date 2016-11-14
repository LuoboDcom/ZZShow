package com.ys.yoosir.zzshow.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ys.yoosir.zzshow.MyApplication;

/**
 * @version 1.0
 * @author Yoosir
 * Created by Administrator on 2016/11/14 0014.
 */
public class SharedPreferencesUtil {

    private static final String SHARES_ZZSHOW = "shares_zzshow";
    public static final String INIT_DB = "init_db";

    public static SharedPreferences getSharedPreference(){
        return MyApplication.getInstance().getSharedPreferences(SHARES_ZZSHOW, Context.MODE_PRIVATE);
    }

    public static boolean isInitDB(){
        return getSharedPreference().getBoolean(INIT_DB,false);
    }

    public static void updateInitDBValue(boolean flag){
        getSharedPreference().edit().putBoolean(INIT_DB,flag).apply();
    }

}
