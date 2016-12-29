package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.base.BaseApi;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.BasePresenter;
import com.ys.yoosir.zzshow.mvp.base.BaseView;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Yoosir on 2016/10/20 0020.
 */
public class BasePresenterImpl<V extends BaseView,M extends BaseApi,T> implements BasePresenter ,RequestCallBack<T>{

    protected V mView;
    protected M mApi;
    protected Subscription mSubscription;

    public BasePresenterImpl(V rootView,M model){
        mView = rootView;
        mApi  = model;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        unSubscribe(mSubscription);
        if(mApi != null){
            mApi.onDestroy();
            this.mApi = null;
        }
        this.mView = null;
        this.mSubscription = null;
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        //TODO unSubscribe
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void beforeRequest() {
        if(mView != null) {
            mView.showProgress();
        }
    }

    @Override
    public void success(T data) {
        if(mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        if(mView != null) {
            mView.hideProgress();
            mView.showMsg(errorMsg);
        }
    }
}
