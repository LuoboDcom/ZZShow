package com.ys.yoosir.zzshow.di.component;

import com.ys.yoosir.zzshow.di.module.ActivityModule;
import com.ys.yoosir.zzshow.di.module.PhotoGirlDetailModule;
import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.ui.activities.PhotoDetailActivity;

import dagger.Component;

/**
 * @version 1.1.0
 * @author  yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@ActivityScope
@Component(modules = {PhotoGirlDetailModule.class, ActivityModule.class},dependencies = AppComponent.class)
public interface PhotoGirlDetailComponent {

    void inject(PhotoDetailActivity activity);

}
