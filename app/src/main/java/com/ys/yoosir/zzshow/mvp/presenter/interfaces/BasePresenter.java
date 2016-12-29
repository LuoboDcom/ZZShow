package com.ys.yoosir.zzshow.mvp.presenter.interfaces;

import android.support.annotation.NonNull;

import com.ys.yoosir.zzshow.mvp.base.BaseView;

import rx.Subscription;

/**
 *  Presenter 基类
 *
 * Created by Yoosir on 2016/10/19 0019.
 */
public interface BasePresenter {

    //创建
    void onCreate();

    void onDestroy();

    void unSubscribe(Subscription subscription);
}
