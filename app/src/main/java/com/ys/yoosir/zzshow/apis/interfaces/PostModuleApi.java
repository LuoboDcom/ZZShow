package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.mvp.modle.PostBean;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleResult;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *  帖子模块业务请求
 * Created by Yoosir on 2016/10/20 0020.
 */
public interface PostModuleApi {

    @GET("?c={controller}&a={action}")
    Observable<List<PostBean>> getPostList(@Path("controller") String controller, @Path("action") String action, @QueryMap Map<String ,String> options);

    @GET(Constants.ARTICLE_PATH)
    Observable<ArticleResult<List<ArticleData>>> getArticles(@QueryMap Map<String,String> params);

}
