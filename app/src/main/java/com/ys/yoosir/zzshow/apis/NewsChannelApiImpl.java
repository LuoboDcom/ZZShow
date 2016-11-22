package com.ys.yoosir.zzshow.apis;

import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.apis.interfaces.NewsChannelApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.db.NewsChannelTableManager;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public class NewsChannelApiImpl implements NewsChannelApi<Map<Integer,List<NewsChannelTable>>> {

    @Override
    public Subscription loadNewsChannels(final RequestCallBack<Map<Integer, List<NewsChannelTable>>> callBack) {
        return Observable.create(new Observable.OnSubscribe<Map<Integer,List<NewsChannelTable>>>() {
            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannelTable>>> subscriber) {
                Map<Integer, List<NewsChannelTable>> newsChannelListMap = getNewsChannelData();
                subscriber.onNext(newsChannelListMap);
                subscriber.onCompleted();
            }
        })
                .compose(RxJavaCustomTransform.<Map<Integer,List<NewsChannelTable>>>defaultSchedulers())
                .subscribe(new Subscriber<Map<Integer, List<NewsChannelTable>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Map<Integer, List<NewsChannelTable>> integerListMap) {
                        callBack.success(integerListMap);
                    }
                });
    }

    @Override
    public void swapDB(int fromPosition, int toPosition) {

    }

    @Override
    public void updateDB(NewsChannelTable newsChannelTable, boolean isChannelMine) {

    }

    private Map<Integer,List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer,List<NewsChannelTable>> map = new HashMap<>();
        List<NewsChannelTable> channelListMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelListRecommend = NewsChannelTableManager.loadNewsChannelsRecommend();
        map.put(Constants.NEWS_CHANNEL_MINE,channelListMine);
        map.put(Constants.NEWS_CHANNEL_RECOMMEND,channelListRecommend);
        return map;
    }
}
