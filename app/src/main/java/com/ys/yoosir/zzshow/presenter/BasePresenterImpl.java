package com.ys.yoosir.zzshow.presenter;

import android.support.annotation.NonNull;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.presenter.interfaces.BasePresenter;
import com.ys.yoosir.zzshow.view.base.BaseView;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class BasePresenterImpl<T extends BaseView,E> implements BasePresenter ,RequestCallBack<E>{

    protected T mView;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(@NonNull BaseView vid) {
        mView = (T) vid;
    }

    @Override
    public void onDestroy() {
        //TODO unSubscribe
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void success(E data) {
        mView.hideProgress();
    }

    @Override
    public void onError(String errorMsg) {
        mView.hideProgress();
        mView.showMsg(errorMsg);
    }
}
