package com.ys.yoosir.zzshow.mvp.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public interface NewsModuleApi<T> {

    Subscription loadNewsChannel(RequestCallBack<T> callBack);

}
