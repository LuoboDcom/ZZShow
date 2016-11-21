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
    public static final String NIGHT_THEME_MODE = "night_theme_mode";

    public static SharedPreferences getSharedPreference(){
        return MyApplication.getInstance().getSharedPreferences(SHARES_ZZSHOW, Context.MODE_PRIVATE);
    }

    public static boolean isInitDB(){
        return getSharedPreference().getBoolean(INIT_DB,false);
    }

    public static void updateInitDBValue(boolean flag){
        getSharedPreference().edit().putBoolean(INIT_DB,flag).apply();
    }

    /**
     *  获取 日间/夜间 模式状态
     * @return boolean
     */
    public static boolean isNightMode(){
        return getSharedPreference().getBoolean(NIGHT_THEME_MODE,false);
    }

    /**
     * 保存 当前 日夜模式状态
     * @param isNight  是否夜间模式
     */
    public static void saveNightMode(boolean isNight){
        getSharedPreference().edit()
                .putBoolean(NIGHT_THEME_MODE,isNight)
                .apply();
    }
}
