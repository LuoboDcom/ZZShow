package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.HomeModuleApiImpl;
import com.ys.yoosir.zzshow.apis.interfaces.HomeModuleApi;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.HomePresenter;
import com.ys.yoosir.zzshow.mvp.view.HomeView;

import java.util.List;

/**
 *  @version 1.0
 * Created by Yoosir on 2016/10/21 0021.
 */
public class HomePresenterImpl extends BasePresenterImpl<HomeView,List<NewsChannelTable>> implements HomePresenter{

    private HomeModuleApi<List<NewsChannelTable>> moduleApi;

    public HomePresenterImpl(){
        moduleApi = new HomeModuleApiImpl();
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
