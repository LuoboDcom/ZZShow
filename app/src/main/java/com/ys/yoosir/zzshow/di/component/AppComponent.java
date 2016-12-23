package com.ys.yoosir.zzshow.di.component;

import android.app.Application;

import com.ys.yoosir.zzshow.common.AppManager;
import com.ys.yoosir.zzshow.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *  全局依赖
 *
 *  @version 1.0
 *  @author  yoosir
 * Created by Administrator on 2016/12/23 0023.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Application Application();

    //用于管理所有Activity
    AppManager appManager();
}
