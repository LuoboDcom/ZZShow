package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SplashActivity extends AppCompatActivity {

    private static final int RP_WRITE = 2;

    @BindView(R.id.logo_bg)
    ImageView mLogoBgIv;

    @BindView(R.id.logo_word)
    ImageView mLogoWordIv;

    @BindView(R.id.logo_trumpet)
    ImageView mLogoTrumpetIv;

    boolean isShowingRubberEffect = false;
    @BindView(R.id.app_name_tv)
    TextView mAppNameTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoomin, 0);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if(toCheckPermission()) {
            initAnimation();
        }
    }

    private boolean toCheckPermission(){
        int result = ActivityCompat.checkSelfPermission(SplashActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(PERMISSION_GRANTED != result){
            ActivityCompat.requestPermissions(SplashActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},RP_WRITE);
            return false;
        }
        return true;
    }

    private void showDialog(boolean isReTry){
        AlertDialog.Builder builder = new AlertDialog
                .Builder(this)
                .setTitle("SD卡读写权限缺少")
                .setMessage("应用的基础数据本地初始化时，需要SD卡的读写权限，否则将无法正常使用本应用。\n 可通过'设置' -> '应用程序'->'权限设置'，重新设置应用权限。")
                .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                if(isReTry){
                    builder.setPositiveButton("重新授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toCheckPermission();
                        }
                    });
                }
                builder.create().show();
    }

    private void initAnimation() {
        startLogoInner1();
        startLogoOuterAndAppName();
    }

    private void startLogoInner1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        mLogoWordIv.startAnimation(animation);
    }

    private void startLogoOuterAndAppName() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                KLog.d("fraction: " + fraction);
                if (fraction >= 0.8 && !isShowingRubberEffect) {
                    isShowingRubberEffect = true;
                    startLogoOuter();
                    startShowAppName();
                    finishActivity();
                } else if (fraction >= 0.95) {
                    valueAnimator.cancel();
                    startLogoInner2();
                }

            }
        });
        valueAnimator.start();
    }

    private void startLogoOuter() {
//        YoYo.with(Techniques.RubberBand).duration(1000).playOn(mLogoBgIv);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(ObjectAnimator.ofFloat(mLogoBgIv, "scaleX", new float[]{1.0F, 1.25F, 0.75F, 1.15F, 1.0F}),
                ObjectAnimator.ofFloat(mLogoBgIv, "scaleY", new float[]{1.0F, 0.75F, 1.25F, 0.85F, 1.0F}));
        animatorSet.start();
    }

    private void startShowAppName() {
//        YoYo.with(Techniques.FadeIn).duration(1000).playOn(mAppNameTv);
        ObjectAnimator.ofFloat(mAppNameTv,"alpha",new float[]{0,1}).setDuration(1000).start();
        ObjectAnimator.ofFloat(mLogoTrumpetIv,"alpha",new float[]{0,1}).setDuration(1000).start();
    }

    private void startLogoInner2() {
//        YoYo.with(Techniques.Bounce).duration(1000).playOn(mLogoWordIv);
        ObjectAnimator.ofFloat(mLogoWordIv, "translationY", new float[]{0.0F, 0.0F, -30.0F, 0.0F, -15.0F, 0.0F, 0.0F});
    }

    private void finishActivity() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(RxJavaCustomTransform.<Long>defaultSchedulers())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        overridePendingTransition(0, android.R.anim.fade_out);
                        finish();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(RP_WRITE == requestCode){
            if (grantResults[0] == PERMISSION_GRANTED) {
                //TODO continue
                initAnimation();
            } else {
                //TODO show dialog to user
                //判断用户是否勾选 不再询问的选项，未勾选可以 说明权限作用，重新授权。
                boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(shouldShow){
                    showDialog(true);
                }else{
                    showDialog(false);
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
