package com.ys.yoosir.zzshow.utils.httputil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/11 0011.
 */
public class RxJavaCustomTransform {

    public static <T> Observable.Transformer<T,T> defaultSchedulers(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
