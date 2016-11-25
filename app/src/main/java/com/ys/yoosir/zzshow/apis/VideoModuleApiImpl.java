package com.ys.yoosir.zzshow.apis;

import android.util.Log;

import com.ys.yoosir.zzshow.apis.common.HostType;
import com.ys.yoosir.zzshow.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.apis.services.VideoService;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.mvp.modle.videos.VideoData;
import com.ys.yoosir.zzshow.utils.DateUtil;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * 视频 Video
 * @version 1.0
 * @author  yoosir
 * Created by Yoosir on 2016/10/24 0024.
 */
public class VideoModuleApiImpl implements VideoModuleApi<List<VideoData>> {

    private static final String TAG = "VideoModuleApiImpl";


    public VideoModuleApiImpl(){}


    @Override
    public Subscription getVideoList(final RequestCallBack<List<VideoData>> callBack, final String videoType, int startPage) {
        return RetrofitManager.getInstance(HostType.VIDEO_HOST)
                .createService(VideoService.class)
                .getVideoList(OkHttpUtil.getCacheControl(),videoType,startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> stringListMap) {
                        return Observable.from(stringListMap.get(videoType));
                    }
                })
                .map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String videoLength = DateUtil.getLengthStr(videoData.getLength());
                        videoData.setSectiontitle(videoLength);
                        return videoData;
                    }
                })
                .distinct()
                .toSortedList()
                .compose(RxJavaCustomTransform.<List<VideoData>>defaultSchedulers())
                .subscribe(new Subscriber<List<VideoData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VideoData> videoDatas) {
                        callBack.success(videoDatas);
                    }
                });

    }
}
