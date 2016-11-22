package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,
        Map<Integer,List<NewsChannelTable>>> implements NewsChannelPresenter {

    @Override
    public void onCreate() {
        super.onCreate();

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

    }
}
