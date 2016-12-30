package com.ys.yoosir.zzshow.mvp.presenter;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.di.scope.ActivityScope;
import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsChannelApi;
import com.ys.yoosir.zzshow.events.ChannelChangeEvent;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;
import com.ys.yoosir.zzshow.utils.RxBus;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */
@ActivityScope
public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,NewsChannelApi,
        Map<Integer,List<NewsChannelTable>>> implements NewsChannelPresenter {

    private boolean mIsChannelChanged;
    private String selectChannelName = null;

    @Inject
    public NewsChannelPresenterImpl(NewsChannelView rootView,NewsChannelApi newsChannelApi){
        super(rootView,newsChannelApi);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApi.loadNewsChannels(this);
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
        mApi.swapDB(fromPosition,toPosition);
        mIsChannelChanged = true;
    }

    @Override
    public void onItemAddOrRemove(NewsChannelTable newsChannel, boolean isChannelMine) {
        mApi.updateDB(newsChannel,isChannelMine);
        mIsChannelChanged = true;
    }
}
