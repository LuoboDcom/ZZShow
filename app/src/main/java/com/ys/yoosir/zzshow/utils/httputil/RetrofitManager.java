package com.ys.yoosir.zzshow.utils.httputil;

import android.util.SparseArray;

import com.ys.yoosir.zzshow.common.ApiConstants;
import com.ys.yoosir.zzshow.common.HostType;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Retrofit Manager
 * Created by Yoosir on 2016/10/24 0024.
 */
public class RetrofitManager {

    private Retrofit mRetrofit;

    /**
     *  类似 Synchronized
     *  http://www.ibm.com/developerworks/cn/java/j-jtp06197.html
     */
    private static volatile OkHttpClient mOkHttpClient;

    /**
     *  SparseArray VS  HashMap
     */
    private static SparseArray<RetrofitManager> retrofitManagers =  new SparseArray<>();

    public static RetrofitManager getInstance(int hostType){
        RetrofitManager retrofitManager = retrofitManagers.get(hostType);
        if(retrofitManager == null){
            retrofitManager = new RetrofitManager(hostType);
            retrofitManagers.put(hostType,retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    private RetrofitManager(@HostType.HostTypeChecker int hostType){
        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(ApiConstants.getHost(hostType))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient(){
        if(mOkHttpClient == null){
            synchronized (RetrofitManager.class){
                if(mOkHttpClient == null){
                    mOkHttpClient = OkHttpUtil.getInstance().getOkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     * @param service 服务接口
     * @return T
     */
    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T createService(final Class<T> service){
        return mRetrofit.create(service);
    }
}
