package com.ys.yoosir.zzshow.di.component;

import com.ys.yoosir.zzshow.di.module.NewsDetailModule;
import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsDetailActivity;

import dagger.Component;

/**
 *  @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@ActivityScope
@Component(modules = NewsDetailModule.class,dependencies = AppComponent.class)
public interface NewsDetailComponent {

    void inject(NewsDetailActivity activity);

}
