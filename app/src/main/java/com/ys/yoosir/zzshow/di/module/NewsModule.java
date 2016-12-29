package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.NewsModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.view.NewsView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@Module
public class NewsModule {

    private NewsView mView;

    /**
     * 构建 NewsModule时，将View的实现类传进来,这样就可以提供View的实现类给presenter
     * @param view
     */
    public NewsModule(NewsView view){
        mView = view;
    }

    @FragmentScope
    @Provides
    NewsView provideNewsView(){
        return mView;
    }

    @FragmentScope
    @Provides
    NewsModuleApi provideNewsApi(){
        return new NewsModuleApiImpl();
    }
}
