package com.ys.yoosir.zzshow.mvp.ui.activities.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.BasePresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.HomeActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsDetailActivity;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * 绑定 presenter
 * Created by Yoosir on 2016/10/19 0019.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    @Inject
    protected  T mPresenter;

    protected MyApplication mApplication;
    private WindowManager mWindowManager = null;
    private View mNightView = null;
    private boolean mIsAddedView;

    protected Subscription mSubscription;

    public abstract int getLayoutId();

    public abstract void initVariables();

    public abstract void initViews();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNightOrDayMode();
//        setStatusBarTranslucent();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        //定义变量或读取传递参数
        initVariables();
        //绑定到butterKnife
        ButterKnife.bind(this);
        //注入依赖
        ComponentInject();
        //设置ToolBar
        initToolBar();
        //初始化View
        initViews();
        initData();
    }

    protected void initData() {
        if(mPresenter != null){
            mPresenter.onCreate();
        }
    }

    protected void ComponentInject(){
        mApplication = (MyApplication) MyApplication.getInstance();
        setupActivityComponent(mApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected void initToolBar(){
        if(!(this instanceof HomeActivity)) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        removeNightModeMask();
        //TODO unSubscribe
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public boolean ismIsAddedView() {
        return mIsAddedView;
    }

    public void setIsAddedView(boolean isAddedView) {
        this.mIsAddedView = isAddedView;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if(!(this instanceof NewsDetailActivity )) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(R.color.colorPrimary);
            }
        }
    }

    public void changeToDay() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mNightView.setBackgroundResource(android.R.color.transparent);
    }

    public void changeToNight() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        initNightView();
        mNightView.setBackgroundResource(R.color.night_mask);
    }

    /**
     *  设置主题样式，必须在setContentView 之前调用
     */
    private void setNightOrDayMode(){
        if(SharedPreferencesUtil.isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            initNightView();
            mNightView.setBackgroundResource(R.color.night_mask);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 初始化 夜间模式 模板
     */
    private void initNightView(){
        //
        if(mIsAddedView){
            return;
        }
        WindowManager.LayoutParams nightViewParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
        );
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mNightView = new View(this);
        mWindowManager.addView(mNightView,nightViewParam);
        mIsAddedView = true;
    }

    private void removeNightModeMask() {
        if (mIsAddedView) {
            // 移除夜间模式蒙板
            mWindowManager.removeViewImmediate(mNightView);
            mWindowManager = null;
            mNightView = null;
        }
    }
}
