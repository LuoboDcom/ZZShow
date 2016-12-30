package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.di.component.DaggerNewsDetailComponent;
import com.ys.yoosir.zzshow.di.module.NewsDetailModule;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsDetail;
import com.ys.yoosir.zzshow.mvp.presenter.NewsDetailPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsDetailPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.view.NewsDetailView;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;
import com.ys.yoosir.zzshow.widget.phototext.PhotoTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenterImpl> implements NewsDetailView {

    private static final String NEWS_POST_ID = "NEWS_POST_ID";
    private static final String NEWS_IMG_RES = "NEWS_IMG_RES";

    private String mPostId;
    private String mPostImgPath;
    private String mShareLink;
    private String mNewsTitle;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.news_content_text)
    PhotoTextView newsContentTextTv;

    @BindView(R.id.news_content_from)
    TextView newsContentFromTv;

    @BindView(R.id.news_detail_picture_iv)
    ImageView newsDetailPictureIv;

    @OnClick(R.id.fab)
    public void onClick(View v){
        if(v.getId() == R.id.fab){
            share();
        }
    }

    public static Intent getNewsDetailIntent(Context context, String postId, String postImgPath){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(NEWS_POST_ID,postId);
        intent.putExtra(NEWS_IMG_RES,postImgPath);
        return intent;
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getShareContents());
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    @NonNull
    private String getShareContents() {
        if (mShareLink == null) {
            mShareLink = "";
        }
        return getString(R.string.share_contents, mNewsTitle, mShareLink);
    }

    @Override
    protected void onDestroy() {
        if(newsContentTextTv != null){
            newsContentTextTv.cancelImageGetterSubscription();
        }
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initVariables() {
        getIntentParams();
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

        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.title_color));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.title_color));
        Glide.with(this).load(mPostImgPath).asBitmap()
                .placeholder(R.mipmap.ic_loading)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(newsDetailPictureIv);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsDetailComponent.builder()
                .appComponent(appComponent)
                .newsDetailModule(new NewsDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        if(mPresenter != null){
            mPresenter.setPostId(mPostId);
            mPresenter.onCreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        toolbarLayout.setTitle(mNewsTitle);
        newsContentFromTv.setText(getString(R.string.news_content_from_value,newsDetail.getSource(),newsDetail.getPtime()));
        newsContentTextTv.setContainText(newsDetail.getBody(),false);
//        setBodyView(newsDetail.getBody());
    }

    private void setBodyView(final String bodyText){
        if(bodyText != null)
            Observable.timer(500, TimeUnit.MILLISECONDS)
            .compose(RxJavaCustomTransform.<Long>defaultSchedulers())
            .subscribe(new Subscriber<Long>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Long aLong) {
                    newsContentTextTv.setContainText(bodyText,false);
                }
            });

    }

}
