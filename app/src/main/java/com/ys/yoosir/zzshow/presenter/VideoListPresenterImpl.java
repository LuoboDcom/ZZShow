package com.ys.yoosir.zzshow.presenter;

import com.ys.yoosir.zzshow.apis.LoadDataType;
import com.ys.yoosir.zzshow.apis.VideoModuleApiImpl;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.presenter.interfaces.VideoListPresenter;
import com.ys.yoosir.zzshow.view.VideoListView;

import java.util.List;

/**
 *  视频 MVP  P 类
 * Created by Yoosir on 2016/10/24 0024.
 */
public class VideoListPresenterImpl extends BasePresenterImpl<VideoListView,ArticleResult<List<VideoData>>> implements VideoListPresenter{

    private int mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;

    private VideoModuleApiImpl mVideoModuleService;

    public VideoListPresenterImpl(){
        mVideoModuleService = VideoModuleApiImpl.getInstance();
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
        mVideoModuleService.getVideoList(this,maxBehotTime);
    }

    @Override
    public void success(ArticleResult<List<VideoData>> data) {
        super.success(data);
        mView.setVideoList(data.getData(),data.isHas_more(),mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.showMsg(errorMsg);
    }
}
