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
            beforeRequest();
            mStartPage = 10;
            loadData();
        }
    }

    @Override
    public void setVideoType(String videoType) {
        mVideoType = videoType;
    }

    @Override
    public void loadData() {
        mLoadDataType = LoadDataType.TYPE_FIRST_LOAD;
        Log.d("VideoListPresenterImpl","loadData mStartPage="+mStartPage+" -- mVideoType="+mVideoType);
        moduleApi.getVideoList(this,mVideoType,mStartPage);
    }

    @Override
    public void success(List<VideoData> videoList) {
        super.success(videoList);
        mView.setVideoList(videoList,mLoadDataType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.showMsg(errorMsg);
    }
}
