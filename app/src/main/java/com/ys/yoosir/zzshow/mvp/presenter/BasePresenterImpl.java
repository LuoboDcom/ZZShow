package com.ys.yoosir.zzshow.mvp.presenter;

import android.support.annotation.NonNull;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.BasePresenter;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Yoosir on 2016/10/20 0020.
 */
public class BasePresenterImpl<T extends BaseView,E> implements BasePresenter ,RequestCallBack<E>{

    protected T mView;
    protected Subscription mSubscription;

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
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
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
