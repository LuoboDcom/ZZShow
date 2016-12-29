package com.ys.yoosir.zzshow.mvp.model.apis;

import com.ys.yoosir.zzshow.common.HostType;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.VideoListModuleApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.repository.network.VideoService;
import com.ys.yoosir.zzshow.mvp.model.entity.videos.VideoData;
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
public class VideoListModuleApiImpl implements VideoListModuleApi<List<VideoData>> {

    private static final String TAG = "VideoListModuleApiImpl";


    public VideoListModuleApiImpl(){}


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
                .toList()
                .compose(RxJavaCustomTransform.<List<VideoData>>defaultSchedulers())
                .subscribe(new Subscriber<List<VideoData>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("--------------------- onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------- onError:"+e.getMessage());
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VideoData> videoDatas) {
                        System.out.println("--------------------- onNext:"+videoDatas.size());
                        for (VideoData v:videoDatas) {
                            System.out.println(v.getCover() +" ,"+v.getTitle());
                        }
                        callBack.success(videoDatas);
                    }
                });

    }
}
