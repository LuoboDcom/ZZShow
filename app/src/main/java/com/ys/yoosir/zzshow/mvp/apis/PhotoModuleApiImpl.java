package com.ys.yoosir.zzshow.mvp.apis;

import com.ys.yoosir.zzshow.common.HostType;
import com.ys.yoosir.zzshow.mvp.apis.interfaces.PhotoModuleApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.repository.network.PhotoService;
import com.ys.yoosir.zzshow.mvp.entity.photos.GirlData;
import com.ys.yoosir.zzshow.mvp.entity.photos.PhotoGirl;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public class PhotoModuleApiImpl implements PhotoModuleApi<List<PhotoGirl>> {

    @Override
    public Subscription getPhotoList(final RequestCallBack<List<PhotoGirl>> callBack, int size, int startPage) {
        return RetrofitManager.getInstance(HostType.GANK_GIRL_PHOTO)
                .createService(PhotoService.class)
                .getPhotoList(OkHttpUtil.getCacheControl(),size,startPage)
                .flatMap(new Func1<GirlData, Observable<PhotoGirl>>() {
                    @Override
                    public Observable<PhotoGirl> call(GirlData girlData) {
                        return Observable.from(girlData.getResults());
                    }
                })
                .toList()
                .compose(RxJavaCustomTransform.<List<PhotoGirl>>defaultSchedulers())
                .subscribe(new Subscriber<List<PhotoGirl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        callBack.success(photoGirls);
                    }
                });
    }

}
