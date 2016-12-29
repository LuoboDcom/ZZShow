package com.ys.yoosir.zzshow.mvp.model.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public interface NewsDetailModuleApi<T> {

    Subscription getNewsDetail(RequestCallBack<T> callBack,String postId);

}
