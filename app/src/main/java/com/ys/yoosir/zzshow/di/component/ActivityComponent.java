package com.ys.yoosir.zzshow.di.component;

import android.app.Activity;

import com.ys.yoosir.zzshow.di.module.ActivityModule;
import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.ui.activities.HomeActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsChannelActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsDetailActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsPhotoDetailActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.PhotoDetailActivity;

import dagger.Component;

/**
 * @version 1.0
 * @author   yoosir
 * Created by Administrator on 2016/12/27 0027.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
}
