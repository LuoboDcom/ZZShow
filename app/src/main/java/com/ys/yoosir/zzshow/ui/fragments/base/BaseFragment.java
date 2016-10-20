package com.ys.yoosir.zzshow.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ys.yoosir.zzshow.presenter.interfaces.BasePresenter;

import butterknife.ButterKnife;

/**
 *
 * Created by Yoosir on 2016/10/19 0019.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment{

    protected T mPresenter;
    protected View mFragmentView;

    public abstract int getLayoutId();
    public abstract void initViews(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        }
    }
}
