package com.ys.yoosir.zzshow.di.module;

import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.mvp.model.apis.PhotoModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.PhotoModuleApi;
import com.ys.yoosir.zzshow.mvp.view.PhotoDetailView;

import dagger.Module;
import dagger.Provides;

/** @version    1.1.0
 *  @author     yoosir
 * Created by Administrator on 2016/12/29 0029.
 */

@Module
public class PhotoGirlDetailModule {

    private PhotoDetailView mView;

    public PhotoGirlDetailModule(PhotoDetailView view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    PhotoDetailView providePhotoGirlDetailView(){
        return mView;
    }

    @ActivityScope
    @Provides
    PhotoModuleApi providePhotoModuleApi(){
        return new PhotoModuleApiImpl();
    }
}
