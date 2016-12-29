package com.ys.yoosir.zzshow.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 *
 * Created by Yoosir on 2016/10/19 0019.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment{

    private final static String TAG = "BaseFragment";

    protected MyApplication mApplication;

    @Inject
    protected T mPresenter;

    protected View mFragmentView;
    protected Subscription mSubscription;

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);
    protected abstract int getLayoutId();
    protected abstract void initViews(View view);
    protected abstract void initData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
        mApplication = (MyApplication) getActivity().getApplication();
        setupFragmentComponent(mApplication.getAppComponent());
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        if(mFragmentView == null){
            mFragmentView = inflater.inflate(getLayoutId(),container,false);
            ButterKnife.bind(this,mFragmentView);
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
        this.mFragmentView = null;
    }



}
