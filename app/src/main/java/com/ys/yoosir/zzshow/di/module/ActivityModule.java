package com.ys.yoosir.zzshow.di.module;

import android.app.Activity;

import com.ys.yoosir.zzshow.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/12/27 0027.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity(){
        return mActivity;
    }

}
