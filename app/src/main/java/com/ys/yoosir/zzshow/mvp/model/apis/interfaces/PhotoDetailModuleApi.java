package com.ys.yoosir.zzshow.mvp.model.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/30.
 */

public interface PhotoDetailModuleApi<T> {

    Subscription saveImageAndGetImageUri(RequestCallBack<T> callBack,String url);

}
