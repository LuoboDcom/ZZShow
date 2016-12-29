package com.ys.yoosir.zzshow.mvp.model.apis;

import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsChannelApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.repository.localdb.NewsChannelTableManager;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public class NewsChannelApiImpl implements NewsChannelApi {

    @Inject
    public NewsChannelApiImpl(){}

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
    public void swapDB(final int fromPosition, final int toPosition) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewsChannelTable fromNewsChannel = NewsChannelTableManager.loadNewsChannel(fromPosition);
                NewsChannelTable toNewsChannel   = NewsChannelTableManager.loadNewsChannel(toPosition);

                if(isAdjacent(fromPosition,toPosition)){
                    swapAdjacentIndexAndUpdate(fromNewsChannel,toNewsChannel);
                } else if (fromPosition - toPosition > 0) {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager
                            .loadNewsChannelsWithin(toPosition,fromPosition - 1);

                    increaseOrReduceIndexAndUpdate(newsChannels,true);
                    changeFromChannelIndexAndUpdate(fromNewsChannel,toPosition);
                } else if (fromPosition - toPosition < 0) {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager
                            .loadNewsChannelsWithin(fromPosition + 1,toPosition);

                    increaseOrReduceIndexAndUpdate(newsChannels,false);
                    changeFromChannelIndexAndUpdate(fromNewsChannel,toPosition);
                }
            }

            private boolean isAdjacent(int fromPosition, int toPosition) {
                return Math.abs(fromPosition - toPosition) == 1;
            }

            private void swapAdjacentIndexAndUpdate(NewsChannelTable fromNewsChannel, NewsChannelTable toNewsChannel) {
                fromNewsChannel.setNewsChannelIndex(toPosition);
                toNewsChannel.setNewsChannelIndex(fromPosition);

                NewsChannelTableManager.update(fromNewsChannel);
                NewsChannelTableManager.update(toNewsChannel);
            }

        });
    }

    @Override
    public void updateDB(final NewsChannelTable newsChannelTable, final boolean isChannelMine) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int channelIndex = newsChannelTable.getNewsChannelIndex();
                if(isChannelMine){
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager.loadNewsChannelsIndexGt(channelIndex);
                    increaseOrReduceIndexAndUpdate(newsChannels,false);

                    int targetIndex = NewsChannelTableManager.getCount();
                    ChangeIsSelectAndIndex(targetIndex,false);
                } else {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager.loadNewsChannelsIndexLtAndIsUnselect(channelIndex);
                    increaseOrReduceIndexAndUpdate(newsChannels,true);

                    int targetIndex = NewsChannelTableManager.getNewsChannelSelectSize();
                    ChangeIsSelectAndIndex(targetIndex,true);
                }
            }

            private void ChangeIsSelectAndIndex(int targetIndex, boolean isSelect) {
                newsChannelTable.setNewsChannelSelect(isSelect);
                changeFromChannelIndexAndUpdate(newsChannelTable,targetIndex);
            }
        });
    }

    private void changeFromChannelIndexAndUpdate(NewsChannelTable fromNewsChannel, int toPosition) {
        fromNewsChannel.setNewsChannelIndex(toPosition);
        NewsChannelTableManager.update(fromNewsChannel);
    }

    private void increaseOrReduceIndexAndUpdate(List<NewsChannelTable> newsChannels, boolean isIncrease) {
       for(NewsChannelTable newsChannel: newsChannels){
           increaseOrReduceIndex(isIncrease,newsChannel);
           NewsChannelTableManager.update(newsChannel);
       }
    }

    private void increaseOrReduceIndex(boolean isIncrease, NewsChannelTable newsChannel) {
        int targetIndex;
        if(isIncrease){
            targetIndex = newsChannel.getNewsChannelIndex() + 1;
        } else {
            targetIndex = newsChannel.getNewsChannelIndex() - 1;
        }
        newsChannel.setNewsChannelIndex(targetIndex);
    }

    private ExecutorService mSingleThreadPool;
    private void createThreadPool() {
        if(mSingleThreadPool == null){
            mSingleThreadPool = Executors.newSingleThreadExecutor();
        }
    }

    private Map<Integer,List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer,List<NewsChannelTable>> map = new HashMap<>();
        List<NewsChannelTable> channelListMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelListRecommend = NewsChannelTableManager.loadNewsChannelsRecommend();
        map.put(Constants.NEWS_CHANNEL_MINE,channelListMine);
        map.put(Constants.NEWS_CHANNEL_RECOMMEND,channelListRecommend);
        return map;
    }

    @Override
    public void onDestroy() {

    }
}
