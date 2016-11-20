package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.logo_outer_iv)
    ImageView mLogoOuterIv;
    @BindView(R.id.logo_inner_iv)
    ImageView mLogoInnerIv;
    boolean isShowingRubberEffect = false;
    @BindView(R.id.app_name_tv)
    TextView mAppNameTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoomin, 0);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initAnimation();
    }

    private void initAnimation() {
        startLogoInner1();
        startLogoOuterAndAppName();
    }

    private void startLogoInner1() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        mLogoInnerIv.startAnimation(animation);
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
//        YoYo.with(Techniques.RubberBand).duration(1000).playOn(mLogoOuterIv);
    }

    private void startShowAppName() {
//        YoYo.with(Techniques.FadeIn).duration(1000).playOn(mAppNameTv);
    }

    private void startLogoInner2() {
//        YoYo.with(Techniques.Bounce).duration(1000).playOn(mLogoInnerIv);
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
}
