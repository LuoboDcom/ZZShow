package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.modle.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import java.util.List;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public interface VideoView extends BaseView {

    void initViewPager(List<VideoChannel> videoChannelList);

}
