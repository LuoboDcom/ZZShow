package com.ys.yoosir.zzshow.mvp.presenter;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.common.LoadDataType;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsListPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsListView;

import java.util.List;

import javax.inject.Inject;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
@FragmentScope
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView,NewsModuleApi,List<NewsSummary>>
        implements NewsListPresenter,RequestCallBack<List<NewsSummary>>{

    private int mLoadDataType;
    private String mNewsType,mNewsId;
    private int mStartPage;

    @Inject
    public NewsListPresenterImpl(NewsListView rootView,NewsModuleApi newsModuleApi){
        super(rootView,newsModuleApi);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d("NewsListFragment","NewsListPresenterImpl");
        if(mView != null){
            firstLoadData();
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
        mView.updateErrorView(mLoadDataType);
    }

    @Override
    public void setNewsTypeAndId(String newsType, String newsId) {
        mStartPage = 0;
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Override
    public void firstLoadData() {
        beforeRequest();
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        loadNewsData();
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
        mSubscription = mApi.loadNews(this,mNewsType,mNewsId,mStartPage);
    }
}
