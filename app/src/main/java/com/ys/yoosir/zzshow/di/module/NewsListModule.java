package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.NewsModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.view.NewsListView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@Module
public class NewsListModule {

    private NewsListView    mView;

    public NewsListModule(NewsListView view){
        mView = view;
    }

    @FragmentScope
    @Provides
    NewsListView provideNewsListView(){
        return mView;
    }

    @FragmentScope
    @Provides
    NewsModuleApi  provideNewsModuleApi(){
        return new NewsModuleApiImpl();
    }

}
