package com.ys.yoosir.zzshow.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ys.yoosir.zzshow.mvp.presenter.interfaces.BasePresenter;

import butterknife.ButterKnife;

/**
 * 绑定 presenter
 * Created by Yoosir on 2016/10/19 0019.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    protected  T mPresenter;

    public abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);
        if(mPresenter != null){
            mPresenter.onCreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }
}
