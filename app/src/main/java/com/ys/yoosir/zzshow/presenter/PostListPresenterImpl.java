package com.ys.yoosir.zzshow.presenter;

import com.ys.yoosir.zzshow.apis.PostModuleApiImpl;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.presenter.interfaces.PostListPresenter;
import com.ys.yoosir.zzshow.view.PostListView;

import java.util.List;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class PostListPresenterImpl extends BasePresenterImpl<PostListView,List<ArticleData>> implements PostListPresenter {

    private PostModuleApiImpl mPostModlueService;

    public PostListPresenterImpl(){
        mPostModlueService = PostModuleApiImpl.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            loadData();
        }
    }

    @Override
    public void loadData() {
        long maxmaxBehotTime = System.currentTimeMillis()/1000 - 3 * 60 * 60;
        mPostModlueService.getArticles(this,maxmaxBehotTime);
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void success(List<ArticleData> data) {
        super.success(data);
        if(mView != null){
            mView.setPostList(data);
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if(mView != null){
            mView.showMsg(errorMsg);
        }
    }
}
