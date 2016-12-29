package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.VideoModuleApiIml;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.mvp.view.VideoView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */
@Module
public class VideoModule {

    private VideoView mView;

    public VideoModule(VideoView view) {
        this.mView = view;
    }

    @FragmentScope
    @Provides
    VideoView  provideVideoView(){
        return mView;
    }

    @FragmentScope
    @Provides
    VideoModuleApi  provideVideoModuleApi(){
        return new VideoModuleApiIml();
    }
}
