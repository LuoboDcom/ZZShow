package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.apis.PostModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PostListPresenter;
import com.ys.yoosir.zzshow.mvp.view.PostListView;

import java.util.List;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class PostListPresenterImpl extends BasePresenterImpl<PostListView,ArticleResult<List<ArticleData>>> implements PostListPresenter {

    private int mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
    private PostModuleApiImpl mPostModuleService;
    private long nexMaxBehotTime = 0l;

    public PostListPresenterImpl(){
        mPostModuleService = PostModuleApiImpl.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            beforeRequest();
            loadData();
        }
    }

    @Override
    public void loadData() {
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        long maxBehotTime = System.currentTimeMillis()/1000 - 3 * 60 * 60;
        loadPastData(maxBehotTime);
    }

    @Override
    public void loadMoreData() {
        mLoadDataType = LoadDataType.TYPE_LOAD_MORE;
        loadPastData(nexMaxBehotTime);
    }

    @Override
    public void refreshData() {
        mLoadDataType = LoadDataType.TYPE_REFRESH;
        long maxBehotTime = System.currentTimeMillis()/1000 - 3 * 60 * 60;
        loadPastData(maxBehotTime);
    }

    private void loadPastData(long maxBehotTime){
        mPostModuleService.getArticles(this,maxBehotTime);
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void success(ArticleResult<List<ArticleData>> data) {
        if(mView != null){
            super.success(data);
            nexMaxBehotTime = data.getNext().getMax_behot_time();
            mView.setPostList(data.getData(),data.isHas_more(),mLoadDataType);
        }
    }

    @Override
    public void onError(String errorMsg) {
        if(mView != null){
            super.onError(errorMsg);
            mView.showMsg(errorMsg);
        }
    }
}
