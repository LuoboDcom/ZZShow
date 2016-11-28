package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.mvp.modle.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoPresenter;
import com.ys.yoosir.zzshow.mvp.view.VideoView;

import java.util.List;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public class VideoPresenterImpl extends BasePresenterImpl<VideoView,List<VideoChannel>> implements VideoPresenter {




    @Override
    public void loadVideoChannel() {

    }
}
