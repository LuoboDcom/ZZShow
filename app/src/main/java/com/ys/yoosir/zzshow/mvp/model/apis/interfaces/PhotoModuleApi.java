package com.ys.yoosir.zzshow.mvp.model.apis.interfaces;

import android.net.Uri;

import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.base.BaseApi;
import com.ys.yoosir.zzshow.mvp.model.entity.photos.PhotoGirl;

import java.util.List;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public interface PhotoModuleApi extends BaseApi{

    /**
     * 获取图片列表
     * @param callBack
     * @param size
     * @param startPage
     * @return
     */
    Subscription getPhotoList(RequestCallBack<List<PhotoGirl>> callBack, int size, int startPage);


    /**
     * 获取图片
     * @param callBack
     * @param url
     * @return
     */
    Subscription saveImageAndGetImageUri(RequestCallBack<Uri> callBack, String url);
}
