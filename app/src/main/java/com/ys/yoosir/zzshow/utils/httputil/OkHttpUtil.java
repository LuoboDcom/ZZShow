package com.ys.yoosir.zzshow.utils.httputil;

import android.support.annotation.NonNull;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *   OkHttp 的默认配置
 *
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class OkHttpUtil {

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_MAX_AGE = 60*30; // 有网络时 设置缓存超时时间半个小时
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 ;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age="+CACHE_MAX_AGE;

    public static OkHttpUtil getInstance(){
        return new OkHttpUtil();
    }

    public OkHttpClient getOkHttpClient(){
        //缓存路径
        Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir().getAbsolutePath(), "HttpCache"),
                1024 * 1024 * 10);//缓存文件为10MB
        return  new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mLoggingInterceptor)
                .build();
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable()){
                //没网的时候就读缓存
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if(NetworkUtil.isNetworkAvailable()){
                //有网的时候读接口上的 @Headers 里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control",cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control","public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     *  拦截器，配置日志打印
     */
    private final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            KLog.i(String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            KLog.i(String.format(Locale.getDefault(),"Received response for %s in %.1fms%n%s",
                    response.request().url(),(t2 - t1)/1e6d,response.headers()));
            return response;
        }
    };

    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetworkUtil.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }
}
