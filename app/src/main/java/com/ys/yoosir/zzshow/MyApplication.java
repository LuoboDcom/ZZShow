package com.ys.yoosir.zzshow;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.common.AppManager;
import com.ys.yoosir.zzshow.di.module.AppModule;
import com.ys.yoosir.zzshow.greendao.gen.DaoMaster;
import com.ys.yoosir.zzshow.greendao.gen.DaoSession;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

/**
 * @version 1.0
 * Created by Yoosir on 2016/10/21 0021.
 */
public class MyApplication extends Application{
    private final String TAG = this.getClass().getSimpleName();
    private static MyApplication mApplication;

    private AppModule mAppModule;
    @Inject
    protected AppManager mAppManager;

    private static DaoSession mDaoSession;

    public static Context getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mAppModule = new AppModule(this,mAppManager);//提供application
        initDayNightMode();
        initDaoSession();
        KLog.init(true);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if(mAppModule != null){
            this.mAppModule = null;
        }
        if(mAppManager != null){
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if(mApplication != null){
            mApplication = null;
        }
    }

    private void initDayNightMode() {
        if (SharedPreferencesUtil.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initDaoSession(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"zzshow-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSeesion(){
        return mDaoSession;
    }
}
