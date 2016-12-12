package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.mvp.apis.VideoModuleApiIml;
import com.ys.yoosir.zzshow.mvp.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.mvp.entity.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoPresenter;
import com.ys.yoosir.zzshow.mvp.view.VideoView;

import java.util.List;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public class VideoPresenterImpl extends BasePresenterImpl<VideoView,List<VideoChannel>> implements VideoPresenter {

    private VideoModuleApi<List<VideoChannel>> videoModuleApi;

    public VideoPresenterImpl(){
        videoModuleApi = new VideoModuleApiIml();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadVideoChannel();
    }

    @Override
    public void loadVideoChannel() {
        mSubscription = videoModuleApi.getVideoChannelList(this);
    }

    @Override
    public void success(List<VideoChannel> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
