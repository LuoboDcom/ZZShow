package com.ys.yoosir.zzshow.mvp.presenter;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.apis.NewsListModuleApiImpl;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.apis.interfaces.NewsListModuleApi;
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

    private NewsListModuleApi<List<NewsSummary>> moduleApi;
    private int mLoadDataType;
    private String mNewsType,mNewsId;
    private int mStartPage;

    public NewsListPresenterImpl(){
        moduleApi = new NewsListModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d("NewsListFragment","NewsListPresenterImpl");
        if(mView != null){
            beforeRequest();
            mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
            loadNewsData();
        }
    }

    @Override
    public void success(List<NewsSummary> data) {
        super.success(data);
        mView.setNewsList(data,mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
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
        mLoadDataType = LoadDataType.TYPE_REFRESH;
        loadNewsData();
    }

    @Override
    public void loadMore() {
        mStartPage += 20;
        mLoadDataType = LoadDataType.TYPE_LOAD_MORE;
        loadNewsData();
    }

    public void loadNewsData(){
        mSubscription = moduleApi.loadNews(this,mNewsType,mNewsId,mStartPage);
    }
}
