package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsDetail;
import com.ys.yoosir.zzshow.mvp.presenter.NewsDetailPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsDetailPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.view.NewsDetailView;

import butterknife.BindView;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailView {

    private static final String NEWS_POST_ID = "NEWS_POST_ID";
    private static final String NEWS_IMG_RES = "NEWS_IMG_RES";

    private String mPostId;
    private String mPostImgPath;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.news_content_text)
    TextView newsContentTextTv;

    @BindView(R.id.news_content_from)
    TextView newsContentFromTv;


    public static Intent getNewsDetailIntent(Context context, String postId, String postImgPath){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(NEWS_POST_ID,postId);
        intent.putExtra(NEWS_IMG_RES,postImgPath);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initVariables() {
        getIntentParams();
        mPresenter = new NewsDetailPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.setPostId(mPostId);
    }

    /**
     * 获取跳转传递的参数
     */
    private void getIntentParams(){
        mPostId = getIntent().getStringExtra(NEWS_POST_ID);
        mPostImgPath = getIntent().getStringExtra(NEWS_IMG_RES);
    }

    @Override
    public void initViews() {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void showNewsContent(NewsDetail newsDetail) {
        newsContentFromTv.setText(getString(R.string.news_content_from_value,newsDetail.getSource(),newsDetail.getPtime()));
        newsContentTextTv.setText(Html.fromHtml(newsDetail.getBody()));
    }
}
