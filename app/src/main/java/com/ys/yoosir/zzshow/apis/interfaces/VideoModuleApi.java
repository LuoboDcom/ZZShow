package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.apis.Constants;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *  视频数据接口
 * Created by Yoosir on 2016/10/23.
 */

public interface VideoModuleApi {

    @GET(Constants.VIDEO_PATH)
    Observable<ArticleResult<List<VideoData>>> getVideos(@QueryMap Map<String,String> params);

}
