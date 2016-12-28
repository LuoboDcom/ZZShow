package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.app_about_text)
    TextView mAboutText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        mAboutText.setAutoLinkMask(Linkify.ALL);
        mAboutText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
