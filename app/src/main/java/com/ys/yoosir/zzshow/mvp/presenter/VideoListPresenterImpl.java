package com.ys.yoosir.zzshow.mvp.presenter;

import android.util.Log;

import com.ys.yoosir.zzshow.apis.VideoListModuleApiImpl;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.apis.interfaces.VideoListModuleApi;
import com.ys.yoosir.zzshow.mvp.modle.videos.VideoData;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoListPresenter;
import com.ys.yoosir.zzshow.mvp.view.VideoListView;

import java.util.List;

/**
 *  视频 MVP  P 类
 * Created by Yoosir on 2016/10/24 0024.
 */
public class VideoListPresenterImpl extends BasePresenterImpl<VideoListView,List<VideoData>> implements VideoListPresenter{

    private int mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;

    private VideoListModuleApi<List<VideoData>> moduleApi;

    private String mVideoType;
    private int mStartPage;

    public VideoListPresenterImpl(){
        moduleApi = new VideoListModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("VideoListPresenterImpl","onCreate mStartPage="+mStartPage+" -- mVideoType="+mVideoType);
        if(mView != null){
            loadData();
        }
    }

    @Override
    public void setVideoType(String videoType) {
        mVideoType = videoType;
    }

    @Override
    public void loadData() {
        beforeRequest();
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        mStartPage = 10;
        loadVideoData(mStartPage);
    }

    @Override
    public void refreshData() {
        mLoadDataType = LoadDataType.TYPE_REFRESH;
        mStartPage = 10;
        loadVideoData(mStartPage);
    }

    @Override
    public void loadMoreData() {
        mLoadDataType = LoadDataType.TYPE_LOAD_MORE;
        mStartPage += 10;
        loadVideoData(mStartPage);
    }

    private void loadVideoData(int startPage){
        mSubscription = moduleApi.getVideoList(this,mVideoType,startPage);
    }

    @Override
    public void success(List<VideoData> videoList) {
        super.success(videoList);
        mView.setVideoList(videoList,mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.updateErrorView(mLoadDataType);
    }
}
