package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;

/**
 *  @version 1.0
 * Created by Yoosir on 2016/10/21 0021.
 */
@FragmentScope
public class NewsPresenterImpl extends BasePresenterImpl<NewsView,NewsModuleApi,List<NewsChannelTable>> implements NewsPresenter{

    @Inject
    public NewsPresenterImpl(NewsView rootView,NewsModuleApi api){
        super(rootView,api);
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
        mSubscription = mApi.loadNewsChannel(this);
    }
}
