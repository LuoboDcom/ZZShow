package com.ys.yoosir.zzshow.di.module;

import android.app.Application;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.common.AppManager;
import com.ys.yoosir.zzshow.di.scope.AppScope;
import com.ys.yoosir.zzshow.di.scope.ContextLifeScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *  @version 1.0
 *  @author  yoosir
 * Created by Administrator on 2016/12/23 0023.
 */
@Module
public class AppModule {

    private MyApplication myApplication;
    private AppManager    mAppManager;

    public AppModule(MyApplication myApplication,AppManager appManager) {
        this.myApplication = myApplication;
        this.mAppManager = appManager;
    }

    @Singleton
    @Provides
    Application provideApplication(){
        return myApplication;
    }

    @Singleton
    @Provides
    AppManager provideAppManager(){
        return mAppManager;
    }

}
