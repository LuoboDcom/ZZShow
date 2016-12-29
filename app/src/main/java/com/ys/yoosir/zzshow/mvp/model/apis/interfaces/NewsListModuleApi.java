package com.ys.yoosir.zzshow.mvp.model.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;

import rx.Subscription;

/**
 *  新闻模块 API接口
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public interface NewsListModuleApi<T> {

    Subscription loadNews(RequestCallBack<T> listener,String type,String id,int startPage);

}
