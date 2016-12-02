package com.ys.yoosir.zzshow.apis;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.db.NewsChannelTableManager;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * @version 1.0
 *          Created by Yoosir on 2016/11/14 0014.
 */
public class NewsModuleApiImpl implements NewsModuleApi<List<NewsChannelTable>> {


    @Override
    public Subscription loadNewsChannel(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelTableManager.initDB();
                List<NewsChannelTable> list = NewsChannelTableManager.loadNewsChannelsMine();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        })
                .compose(RxJavaCustomTransform.<List<NewsChannelTable>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsChannelTable>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(MyApplication.getInstance().getString(R.string.db_error));
                    }

                    @Override
                    public void onNext(List<NewsChannelTable> newsChannelTables) {
                        callBack.success(newsChannelTables);
                    }
                });
    }
}
