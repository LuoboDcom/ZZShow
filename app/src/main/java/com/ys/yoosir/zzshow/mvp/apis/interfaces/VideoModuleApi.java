package com.ys.yoosir.zzshow.mvp.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public interface VideoModuleApi<T> {

    Subscription getVideoChannelList(RequestCallBack<T> callBack);
}
