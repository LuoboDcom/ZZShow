package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class PhotoDetailActivity extends BaseActivity {

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
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initVariables() {
        mPhotoUrl = getIntent().getStringExtra(PHOTO_URL);
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
                .centerCrop()
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
                .centerCrop()
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPhotoIv);
    }
}
