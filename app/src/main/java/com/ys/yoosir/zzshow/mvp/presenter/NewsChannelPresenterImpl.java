package com.ys.yoosir.zzshow.mvp.presenter;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.apis.NewsChannelApiImpl;
import com.ys.yoosir.zzshow.apis.interfaces.NewsChannelApi;
import com.ys.yoosir.zzshow.events.ChannelChangeEvent;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;
import com.ys.yoosir.zzshow.utils.RxBus;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,
        Map<Integer,List<NewsChannelTable>>> implements NewsChannelPresenter {

    private NewsChannelApi<Map<Integer,List<NewsChannelTable>>> mNewsApi;
    private boolean mIsChannelChanged;
    private String selectChannelName = null;

    public NewsChannelPresenterImpl(){
        mNewsApi =  new NewsChannelApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNewsApi.loadNewsChannels(this);
    }

    @Override
    public void selectIndex(String newsChannelName){
        selectChannelName = newsChannelName;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mIsChannelChanged || selectChannelName != null) {
            //TODO 当我的频道改变时，就通知新闻列表改变
            KLog.d("NewsChannelPresenterImpl","mine channel has changed");
            RxBus.getInstance().post(new ChannelChangeEvent(selectChannelName,mIsChannelChanged));
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void success(Map<Integer, List<NewsChannelTable>> data) {
        super.success(data);
        mView.updateRecyclerView(data.get(Constants.NEWS_CHANNEL_MINE),data.get(Constants.NEWS_CHANNEL_RECOMMEND));
    }

    @Override
    public void onItemSwap(int fromPosition, int toPosition) {
        mNewsApi.swapDB(fromPosition,toPosition);
        mIsChannelChanged = true;
    }

    @Override
    public void onItemAddOrRemove(NewsChannelTable newsChannel, boolean isChannelMine) {
        mNewsApi.updateDB(newsChannel,isChannelMine);
        mIsChannelChanged = true;
    }
}
