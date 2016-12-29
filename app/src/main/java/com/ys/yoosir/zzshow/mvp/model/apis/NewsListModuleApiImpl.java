package com.ys.yoosir.zzshow.mvp.model.apis;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.common.ApiConstants;
import com.ys.yoosir.zzshow.common.HostType;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsListModuleApi;
import com.ys.yoosir.zzshow.repository.network.NewsService;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.utils.DateUtil;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class NewsListModuleApiImpl implements NewsListModuleApi<List<NewsSummary>> {

    private final String TAG = "NewsListModuleApiImpl";

    public NewsListModuleApiImpl(){

    }

    @Override
    public Subscription loadNews(final RequestCallBack<List<NewsSummary>> listener, String type, final String id, int startPage) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .createService(NewsService.class)
                .getNewsList(OkHttpUtil.getCacheControl(),type,id,startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        if(ApiConstants.NETEASE_ID_HOUSE.equals(id)){
                            return Observable.from(stringListMap.get("北京"));
                        }else{
                            return Observable.from(stringListMap.get(id));
                        }
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = DateUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(RxJavaCustomTransform.<List<NewsSummary>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {
                        KLog.d(TAG,"-- onCompleted--");
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(TAG,e.toString());
                        if(listener != null) {
                            listener.onError(e.toString());
                        }
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        for (NewsSummary bean:newsSummaries) {
                            KLog.d(TAG,"onNext -- bean = "+bean.toString());
                        }
                        if(listener != null) {
                            listener.success(newsSummaries);
                        }
                    }
                });
    }
}
