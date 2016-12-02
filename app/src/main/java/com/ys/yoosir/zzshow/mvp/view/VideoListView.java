package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.modle.videos.VideoData;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import java.util.List;

/**
 *  MVP V
 * Created by Yoosir on 2016/10/24 0024.
 */
public interface VideoListView extends BaseView{

    void setVideoList(List<VideoData> videoDataList, int loadType);

    void updateErrorView(int loadType);
}
