package com.ys.yoosir.zzshow.utils.mediavideo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ys.yoosir.zzshow.R;

/**
 *  设置
 * Created by Administrator on 2016/10/26 0026.
 */
public class MediaSettings {

    private Context mAppContext;
    private SharedPreferences mSharedPreferences;

    public static final int PV_PLAYER_Auto               = 0;
    public static final int PV_PLAYER_AndroidMediaPlayer = 1;
    public static final int PV_PLAYER_IjkMediaPlayer     = 2;
    public static final int PV_PLAYER_IjkExoMediaPlayer  = 3;

    public MediaSettings(Context context){
        mAppContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public boolean getEnableBackgroundPlay(){
        String key = mAppContext.getString(R.string.pref_key_enable_background_play);
        return mSharedPreferences.getBoolean(key,false);
    }

    public int getPlayer(){
        String key = mAppContext.getString(R.string.pref_key_player);
        String value = mSharedPreferences.getString(key,"");
        try {
            return Integer.valueOf(value).intValue();
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public boolean getUsingMediaCodes(){
        String key = mAppContext.getString(R.string.pref_key_using_media_codec);
        return mSharedPreferences.getBoolean(key,false);
    }

    public boolean getUsingMediaCodecAutoRotate(){
        String key = mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate);
        return mSharedPreferences.getBoolean(key,false);
    }

    public boolean getUsingOpenSLES(){
        String key = mAppContext.getString(R.string.pref_key_using_opensl_es);
        return mSharedPreferences.getBoolean(key,false);
    }

    public String getPixelFormat() {
        String key = mAppContext.getString(R.string.pref_key_pixel_format);
        return mSharedPreferences.getString(key, "");
    }

    public boolean getEnableNoView() {
        String key = mAppContext.getString(R.string.pref_key_enable_no_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getEnableSurfaceView() {
        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getEnableTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getEnableDetachedSurfaceTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_detached_surface_texture);
        return mSharedPreferences.getBoolean(key, false);
    }

    public String getLastDirectory() {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        return mSharedPreferences.getString(key, "/");
    }

    public void setLastDirectory(String path) {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        mSharedPreferences.edit().putString(key, path).apply();
    }

    public void setEnableTextureView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putBoolean(key,enable);
        editor.commit();
    }

    public void setEnableSurfaceView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putBoolean(key,enable);
        editor.commit();
    }
}
