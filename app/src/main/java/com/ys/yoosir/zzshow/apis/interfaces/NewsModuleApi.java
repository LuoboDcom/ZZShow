package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public interface NewsModuleApi<T> {

    Subscription loadNewsChannel(RequestCallBack<T> callBack);

}
