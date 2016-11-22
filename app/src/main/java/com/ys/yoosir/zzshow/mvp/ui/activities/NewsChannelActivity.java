package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;

public class NewsChannelActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_channel);
    }
}
