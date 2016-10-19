package com.ys.yoosir.zzshow.presenter;

import com.ys.yoosir.zzshow.view.base.BaseView;

/**
 *  Presenter 基类
 *
 * Created by Yoosir on 2016/10/19 0019.
 */
public interface BasePresenter {

    //创建
    void onCreate();

    //绑定
    void attachView(BaseView vid);

    void onDestroy();
}
