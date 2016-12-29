package com.ys.yoosir.zzshow.di.component;

import com.ys.yoosir.zzshow.di.module.VideoModule;
import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.ui.fragments.VideoFragment;

import dagger.Component;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@FragmentScope
@Component(modules = VideoModule.class,dependencies = AppComponent.class)
public interface VideoComponent {

    void inject(VideoFragment fragment);

}
