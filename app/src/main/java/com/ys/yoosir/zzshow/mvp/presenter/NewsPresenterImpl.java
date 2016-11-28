package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.NewsModuleApiImpl;
import com.ys.yoosir.zzshow.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsView;

import java.util.List;

/**
 *  @version 1.0
 * Created by Yoosir on 2016/10/21 0021.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView,List<NewsChannelTable>> implements NewsPresenter{

    private NewsModuleApi<List<NewsChannelTable>> moduleApi;

    public NewsPresenterImpl(){
        moduleApi = new NewsModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsChannelFromDB();
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void loadNewsChannels() {
        loadNewsChannelFromDB();
    }

    private void loadNewsChannelFromDB(){
        mSubscription = moduleApi.loadNewsChannel(this);
    }
}
