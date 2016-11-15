package com.ys.yoosir.zzshow.apis;

import com.ys.yoosir.zzshow.apis.common.HostType;
import com.ys.yoosir.zzshow.apis.interfaces.NewsDetailModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.apis.services.NewsService;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsDetail;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public class NewsDetailModuleApiImpl implements NewsDetailModuleApi<NewsDetail>{

    @Override
    public Subscription getNewsDetail(final RequestCallBack<NewsDetail> callBack, final String postId) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).createService(NewsService.class)
                .getNewsDetail(OkHttpUtil.getCacheControl(),postId)
                .map(new Func1<Map<String,NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> stringNewsDetailMap) {
                        return stringNewsDetailMap.get(postId);
                    }
                })
                .compose(RxJavaCustomTransform.<NewsDetail>defaultSchedulers())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        callBack.success(newsDetail);
                    }
                });
    }
}
