package com.ys.yoosir.zzshow.apis.services;

import com.ys.yoosir.zzshow.mvp.modle.photos.GirlData;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoService {

    @GET("data/福利/{size}/{startPage}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("startPage") int startPage
    );

}
