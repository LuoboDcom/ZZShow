package com.ys.yoosir.zzshow.view;

import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.view.base.BaseView;

import java.util.List;

/**
 *  MVP V
 * Created by Yoosir on 2016/10/24 0024.
 */
public interface VideoListView extends BaseView{

    void setVideoList(List<VideoData> videoDataList, boolean hasMore, int loadType);


}
