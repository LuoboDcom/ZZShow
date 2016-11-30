package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.presenter.PhotoDetailPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoDetailPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.view.PhotoDetailView;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class PhotoDetailActivity extends BaseActivity<PhotoDetailPresenter> implements PhotoDetailView {

    private static final String PHOTO_URL = "PHOTO_URL";

    @BindView(R.id.img_iv)
    ImageView mImgIv;

    @BindView(R.id.photo_iv)
    PhotoView mPhotoIv;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mPhotoUrl;

    public static Intent getPhotoDetailIntent(Context context, String photoUrl){
        Intent intent = new Intent(context,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_URL,photoUrl);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                mPresenter.savePhoto(mPhotoUrl);
                return true;
            case R.id.action_share:
                mPresenter.sharePhoto(mPhotoUrl);
                return true;
            case R.id.action_set_wallpaper:
                mPresenter.setWallpaper(mPhotoUrl);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initVariables() {
        mPhotoUrl = getIntent().getStringExtra(PHOTO_URL);
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new PhotoDetailPresenterImpl(this);
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(this).load(mPhotoUrl)
                .asBitmap()
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImgIv);

        initLazyLoadView();
    }

    private void initLazyLoadView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    loadPhotoView();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }else{
            loadPhotoView();
        }
    }

    private void loadPhotoView(){
        Glide.with(this).load(mPhotoUrl)
                .asBitmap()
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPhotoIv);
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
}
