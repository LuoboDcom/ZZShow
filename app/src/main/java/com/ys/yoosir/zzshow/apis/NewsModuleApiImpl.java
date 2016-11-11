package com.ys.yoosir.zzshow.apis;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.apis.common.HostType;
import com.ys.yoosir.zzshow.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.apis.interfaces.NewsService;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsSummary;
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
public class NewsModuleApiImpl implements NewsModuleApi<List<NewsSummary>> {

    public NewsModuleApiImpl(){

    }

    @Override
    public Subscription loadNews(final RequestCallBack<List<NewsSummary>> listener, String type, final String id, int startPage) {
        System.out.println("-- loadNews --");
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .createService(NewsService.class)
                .getNewsList(OkHttpUtil.getCacheControl(),type,id,startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        System.out.println("-- flatMap call --");
                        return Observable.from(stringListMap.get(id));
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        System.out.println("-- map call --");
                        String ptime = DateUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        System.out.println("-- toSortedList call --");
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(RxJavaCustomTransform.<List<NewsSummary>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("-- subscribe  onCompleted--");
                        KLog.d("-- onCompleted--");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("-- subscribe  onError error="+e.getMessage());
                        KLog.e(e.toString());
                        if(listener != null) {
                            listener.onError(e.toString());
                        }
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        System.out.println("-- subscribe  onNext--");
                        for (NewsSummary bean:newsSummaries) {
                            System.out.println("onNext -- bean = "+bean.getTitle() + "- "+ bean.getPtime());
                            KLog.d("onNext -- bean = "+bean.getTitle() + "- "+ bean.getPtime());
                        }
                        if(listener != null) {
                            listener.success(newsSummaries);
                        }
                    }
                });
    }
}
