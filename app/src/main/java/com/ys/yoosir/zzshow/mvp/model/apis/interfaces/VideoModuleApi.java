package com.ys.yoosir.zzshow.mvp.model.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.base.BaseApi;
import com.ys.yoosir.zzshow.mvp.model.entity.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.model.entity.videos.VideoData;

import java.util.List;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public interface VideoModuleApi extends BaseApi{

    Subscription getVideoChannelList(RequestCallBack<List<VideoChannel>> callBack);

    Subscription getVideoList(RequestCallBack<List<VideoData>> callBack, String videoType, int startPage);
}
