package com.ys.yoosir.zzshow.apis;

import android.util.Log;

import com.ys.yoosir.zzshow.apis.interfaces.PostModuleApi;
import com.ys.yoosir.zzshow.apis.interfaces.VideoModuleApi;
import com.ys.yoosir.zzshow.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  视频请求管理类
 * Created by Yoosir on 2016/10/23.
 */

public class VideoModuleApiImpl {

    private static final String TAG = VideoModuleApiImpl.class.getName();

    private VideoModuleApi service;
    private HashMap<String,String> params;

    public static VideoModuleApiImpl getInstance(){
        return new VideoModuleApiImpl();
    }

    public VideoModuleApiImpl(){
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.URL_HOST)
                    .build();

            service = retrofit.create(VideoModuleApi.class);
    }

    public void getVideoList(long maxBehotTime){
        service.getVideos(getParams(maxBehotTime))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleResult<List<VideoData>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }

                    @Override
                    public void onNext(ArticleResult<List<VideoData>> listArticleResult) {
                        for (VideoData data:listArticleResult.getData()) {
                            Log.d(TAG,"video = "+ data.toString());
                        }
                    }
                });
    }

    private Map<String,String> getParams(long maxBehotTime){
        if(params == null) {
            params = new HashMap<>();
            params.put("category", "video");
            params.put("utm_source", "toutiao");
            params.put("widen", "0");
            params.put("as", "A19598408C0614F");
            params.put("cp", "580C36D1944F8E1");
        }
        params.put("max_behot_time", "" + maxBehotTime);
        params.put("max_behot_time_tmp","" + maxBehotTime);
        return  params;
    }
}
