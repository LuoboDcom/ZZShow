package com.ys.yoosir.zzshow.mvp.presenter.interfaces;

import android.support.annotation.NonNull;

import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

/**
 *  Presenter 基类
 *
 * Created by Yoosir on 2016/10/19 0019.
 */
public interface BasePresenter {

    //创建
    void onCreate();

    //绑定
    void attachView(@NonNull BaseView vid);

    void onDestroy();
}
