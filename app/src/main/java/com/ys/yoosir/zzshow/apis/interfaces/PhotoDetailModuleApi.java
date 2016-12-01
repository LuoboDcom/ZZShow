package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/30.
 */

public interface PhotoDetailModuleApi<T> {

    Subscription saveImageAndGetImageUri(RequestCallBack<T> callBack,String url);

}
