package com.ys.yoosir.zzshow.mvp.presenter;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.mvp.apis.NewsDetailModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.apis.interfaces.NewsDetailModuleApi;
import com.ys.yoosir.zzshow.mvp.entity.netease.NewsDetail;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsDetailPresenter;
import com.ys.yoosir.zzshow.mvp.view.NewsDetailView;

import javax.inject.Inject;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView,NewsDetail> implements NewsDetailPresenter{

    private final String TAG = "NewsDetailPresenterImpl";

    private NewsDetailModuleApi<NewsDetail> moduleApi;

    private String mPostId;

    @Inject
    public NewsDetailPresenterImpl(NewsDetailModuleApiImpl newsDetailModuleApi){
        moduleApi = newsDetailModuleApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsDetail();
    }

    @Override
    public void setPostId(String postId) {
        mPostId = postId;
    }

    @Override
    public void success(NewsDetail data) {
        super.success(data);
        KLog.d(TAG,data.toString());
        mView.showNewsContent(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    public void loadNewsDetail(){
        mSubscription = moduleApi.getNewsDetail(this,mPostId);
    }
}
