package com.ys.yoosir.zzshow.presenter;

import com.ys.yoosir.zzshow.apis.Constants;
import com.ys.yoosir.zzshow.apis.LoadDataType;
import com.ys.yoosir.zzshow.apis.PostModuleApiImpl;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.presenter.interfaces.PostListPresenter;
import com.ys.yoosir.zzshow.view.PostListView;

import java.util.List;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class PostListPresenterImpl extends BasePresenterImpl<PostListView,ArticleResult<List<ArticleData>>> implements PostListPresenter {

    private int mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
    private PostModuleApiImpl mPostModlueService;
    private long nexMaxBehotTime = 0l;

    public PostListPresenterImpl(){
        mPostModlueService = PostModuleApiImpl.getInstance();
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
        mPostModlueService.getArticles(this,maxBehotTime);
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void success(ArticleResult<List<ArticleData>> data) {
        super.success(data);
        if(mView != null){
            nexMaxBehotTime = data.getNext().getMax_behot_time();
            mView.setPostList(data.getData(),data.isHas_more(),mLoadDataType);
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
