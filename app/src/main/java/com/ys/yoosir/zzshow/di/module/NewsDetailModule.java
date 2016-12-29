package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.model.apis.NewsModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.view.NewsDetailView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */

@Module
public class NewsDetailModule {

    private NewsDetailView  mView;

    public NewsDetailModule(NewsDetailView view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    NewsDetailView provideNewsDetailView(){
        return mView;
    }

    @ActivityScope
    @Provides
    NewsModuleApi provideNewsModuleApi(){
        return new NewsModuleApiImpl();
    }
}
