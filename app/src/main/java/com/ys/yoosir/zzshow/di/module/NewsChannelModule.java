package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.model.apis.NewsChannelApiImpl;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsChannelApi;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@Module
public class NewsChannelModule {

    private NewsChannelView mView;

    public NewsChannelModule(NewsChannelView view){
        mView = view;
    }

    @ActivityScope
    @Provides
    NewsChannelView provideNewsChannelView(){
        return mView;
    }

    @ActivityScope
    @Provides
    NewsChannelApi  provideNewsChannelApi(){
        return new NewsChannelApiImpl();
    }

}
