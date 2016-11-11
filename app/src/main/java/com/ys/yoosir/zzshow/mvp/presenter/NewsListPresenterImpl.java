package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.NewsModuleApiImpl;
import com.ys.yoosir.zzshow.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsListPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsListView;

import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView,List<NewsSummary>>
        implements NewsListPresenter,RequestCallBack<List<NewsSummary>>{

    private NewsModuleApi<List<NewsSummary>> moduleApi;
    private String mNewsType,mNewsId;
    private int mStartPage;

    public NewsListPresenterImpl(){
        moduleApi = new NewsModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsData();
    }

    @Override
    public void setNewsTypeAndId(String newsType, String newsId) {
        mStartPage = 0;
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Override
    public void refreshData() {
        mStartPage = 0;
    }

    @Override
    public void loadMore() {

    }

    public void loadNewsData(){
        mSubscription = moduleApi.loadNews(this,mNewsType,mNewsId,mStartPage);
    }
}
