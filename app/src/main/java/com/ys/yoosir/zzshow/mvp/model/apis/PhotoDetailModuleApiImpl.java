package com.ys.yoosir.zzshow.mvp.model.apis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.PhotoDetailModuleApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/30.
 */

public class PhotoDetailModuleApiImpl implements PhotoDetailModuleApi<Uri> {

    @Override
    public Subscription saveImageAndGetImageUri(final RequestCallBack<Uri> callBack, final String url) {

        return Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(MyApplication.getInstance())
                            .load(url)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                if(bitmap == null){
                    subscriber.onError(new Exception("下载图片失败"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        })
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        return getUriObservable(bitmap,url);
                    }
                })
                .compose(RxJavaCustomTransform.<Uri>defaultSchedulers())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Uri uri) {
                        callBack.success(uri);
                    }
                });
    }

    private Observable<Uri> getUriObservable(Bitmap bitmap,String url){
        File file = getImageFile(bitmap,url);
        if(file == null){
            return Observable.error(new NullPointerException("Save image file failed!"));
        }
        Uri uri = Uri.fromFile(file);
        //通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        MyApplication.getInstance().sendBroadcast(scannerIntent);
        return Observable.just(uri);
    }

    private File getImageFile(Bitmap bitmap, String url){
        String fileName = "/zzshow/photo/" + url.hashCode() + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
