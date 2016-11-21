package com.ys.yoosir.zzshow.widget.phototext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * @version 1.0
 * @author yoosir
 * Created by Administrator on 2016/11/19.
 */

public class UrlImageGetter implements Html.ImageGetter{

    Context mContext;
    TextView mTextView;

    int mScreenWidth;

    public UrlImageGetter(TextView t, Context context,int screenWidth){
        this.mContext = context;
        this.mTextView = t;
        this.mScreenWidth = screenWidth;
        Log.d("YHtml","-------------UrlImageGetter");
    }


    @Override
    public Drawable getDrawable(String source) {
        Log.d("YHtml","-------------getDrawable");
//        String imgSrc = getImgSrc(source);

        final  UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(mContext)
                .load(source)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        float scaleWidth = width / resource.getWidth();
                        Log.d("YHtml","-------------onResourceReady -- mScreenWidth ="+mScreenWidth);

                        Observable.create(new Observable.OnSubscribe<String>(){
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                float scaleWidth = ((float) mScreenWidth) / resource.getWidth();
                                Matrix matrix = new Matrix();
                                matrix.postScale(scaleWidth, scaleWidth);
                                Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0,
                                        resource.getWidth(), resource.getHeight(),
                                        matrix, true);
                                resource.recycle();
                                urlDrawable.mBitmap = bitmap;
                                urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                subscriber.onNext("");
                                subscriber.onCompleted();
                            }
                        })
                                .compose(RxJavaCustomTransform.<String>defaultSchedulers())
                                .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                mTextView.setText(mTextView.getText());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {

                            }
                        });
                    }
                });

        return urlDrawable;
    }


    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap mBitmap;

        @Override
        public void draw(Canvas canvas) {
            if(mBitmap != null){
                canvas.drawBitmap(mBitmap,0,0,getPaint());
            }
        }
    }
}
