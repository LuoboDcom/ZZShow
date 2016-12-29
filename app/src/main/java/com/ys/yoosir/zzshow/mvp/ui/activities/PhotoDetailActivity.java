package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.di.component.DaggerPhotoGirlDetailComponent;
import com.ys.yoosir.zzshow.di.module.ActivityModule;
import com.ys.yoosir.zzshow.di.module.PhotoGirlDetailModule;
import com.ys.yoosir.zzshow.mvp.presenter.PhotoDetailPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoDetailPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.view.PhotoDetailView;
import com.ys.yoosir.zzshow.utils.SystemUiVisibilityUtil;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 *  1.共享元素 动画，使用 PhotoView 作为共享元素时 ，动画不流畅
 *  2.当使用Glide加载图片时，会导致 ImageView 与 PhotoView 呈现的大小不一样
 */

public class PhotoDetailActivity extends BaseActivity<PhotoDetailPresenterImpl> implements PhotoDetailView {

    private static final String PHOTO_URL = "PHOTO_URL";

    @BindView(R.id.img_iv)
    ImageView mImgIv;

    @BindView(R.id.photo_iv)
    PhotoView mPhotoIv;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mPhotoUrl;

    private boolean isHidden = false;
    private boolean mIsStatusBarHidden = false;

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

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotoDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(PhotoDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    public void hideToolBarAndTextView(){
        isHidden = !isHidden;
        if(isHidden){
            startAnimation(true,1.0f,0.0f);
        }else{
            startAnimation(false,0.1f,1.0f);
        }
    }

    private void startAnimation(final boolean endState, float startValue, float endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(startValue,endValue).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1;
                if(endState){
                    y1 = (0 - animation.getAnimatedFraction())*mToolbar.getHeight();
                }else{
                    y1 = (animation.getAnimatedFraction() - 1)*mToolbar.getHeight();
                }
                mToolbar.setTranslationY(y1);
            }
        });
        animator.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initVariables() {
        mPhotoUrl = getIntent().getStringExtra(PHOTO_URL);
    }

    @Override
    public void initViews() {

        Picasso.with(this)
                .load(mPhotoUrl)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail)
                .into(mImgIv);

        mPhotoIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideToolBarAndTextView();
                hideOrShowStatusBar();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

//        Glide.with(this).load(mPhotoUrl)
//                .asBitmap()
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .placeholder(R.mipmap.ic_loading)
//                .error(R.mipmap.ic_load_fail)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        Log.d("ThreadName","----------------"+Thread.currentThread().getName());
//                        mImgIv.setImageBitmap(resource);
//                    }
//                });


        initLazyLoadView();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoGirlDetailComponent.builder()
                .appComponent(appComponent)
                .photoGirlDetailModule(new PhotoGirlDetailModule(this))
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
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
//        Glide.with(this).load(mPhotoUrl)
//                .asBitmap()
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .placeholder(R.mipmap.ic_loading)
//                .error(R.mipmap.ic_load_fail)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mPhotoIv);
        Picasso.with(this)
                .load(mPhotoUrl)
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
        Toast.makeText(PhotoDetailActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
