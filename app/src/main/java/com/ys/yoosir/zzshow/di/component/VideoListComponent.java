package com.ys.yoosir.zzshow.di.component;

import com.ys.yoosir.zzshow.di.module.VideoListModule;
import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.ui.fragments.VideoListFragment;

import dagger.Component;

/**
 * @version     1.1.0
 * @author      yoosir
 * Created by Administrator on 2016/12/29 0029.
 */

@FragmentScope
@Component(modules = VideoListModule.class,dependencies = AppComponent.class)
public interface VideoListComponent {

    void inject(VideoListFragment fragment);

}
