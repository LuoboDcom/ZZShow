package com.ys.yoosir.zzshow.apis;

import android.util.Log;

import com.ys.yoosir.zzshow.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.utils.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 视频 Video
 * Created by Yoosir on 2016/10/24 0024.
 */
public class VideoModuleApiImpl {

    private static final String TAG = VideoModuleApiImpl.class.getSimpleName();

    private VideoModuleApi service;
    private Map<String,String> params;

    public static VideoModuleApiImpl getInstance(){
        return new VideoModuleApiImpl();
    }

    public VideoModuleApiImpl(){
        service = RetrofitManager.getRetrofitInstance().create(VideoModuleApi.class);
    }

    public void getVideoList(final RequestCallBack<ArticleResult<List<VideoData>>> callBack,long maxBehotTime){
        service.getVideos(getParams(maxBehotTime))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleResult<List<VideoData>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,e.getMessage());
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArticleResult<List<VideoData>> listArticleResult) {
                        for (VideoData data:listArticleResult.getData()) {
                            Log.d(TAG,data.toString());
                        }
                        callBack.success(listArticleResult);
                    }
                });
    }

    public Map<String,String > getParams(long maxBehotTime){
        if(params == null){
            params = new HashMap<>();
            params.put("category","video");
            params.put("utm_source","toutiao");
            params.put("widen","0");
            params.put("as","A1F548F0AD5AC6A");
            params.put("cp","580D4A9C76EAFE1");
        }
        params.put("max_behot_time","" + maxBehotTime);
        params.put("max_behot_time_tmp","" + maxBehotTime);
        return params;
    }

}
