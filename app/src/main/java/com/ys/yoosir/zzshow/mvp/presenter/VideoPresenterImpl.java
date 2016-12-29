package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.di.scope.FragmentScope;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.mvp.model.entity.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoPresenter;
import com.ys.yoosir.zzshow.mvp.view.VideoView;

import java.util.List;

import javax.inject.Inject;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */
@FragmentScope
public class VideoPresenterImpl extends BasePresenterImpl<VideoView,VideoModuleApi,List<VideoChannel>> implements VideoPresenter {

    @Inject
    public VideoPresenterImpl(VideoView rootView,VideoModuleApi moduleApi){
        super(rootView,moduleApi);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadVideoChannel();
    }

    @Override
    public void loadVideoChannel() {
        mSubscription = mApi.getVideoChannelList(this);
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
