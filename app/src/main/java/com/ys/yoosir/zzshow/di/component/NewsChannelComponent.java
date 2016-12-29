package com.ys.yoosir.zzshow.di.component;

import com.ys.yoosir.zzshow.di.module.NewsChannelModule;
import com.ys.yoosir.zzshow.di.module.NewsModule;
import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsChannelActivity;
import com.ys.yoosir.zzshow.mvp.ui.fragments.News.NewsFragment;

import dagger.Component;

/** @version    1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */

@ActivityScope
@Component(modules = NewsChannelModule.class,dependencies = AppComponent.class)
public interface NewsChannelComponent {
    void inject(NewsChannelActivity activity);
}
