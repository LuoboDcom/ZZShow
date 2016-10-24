package com.ys.yoosir.zzshow.utils;

import com.ys.yoosir.zzshow.apis.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Retrofit Manager
 * Created by Yoosir on 2016/10/24 0024.
 */
public class RetrofitManager {

    private static Retrofit mRetrofit;

    public static Retrofit getRetrofitInstance(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.URL_HOST)
                    .build();
        }
        return mRetrofit;
    }
}
