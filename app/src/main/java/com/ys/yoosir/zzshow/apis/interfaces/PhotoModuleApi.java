package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoModuleApi<T> {

    Subscription getPhotoList(RequestCallBack<T> callBack,int size,int startPage);

}
