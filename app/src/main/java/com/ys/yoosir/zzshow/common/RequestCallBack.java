package com.ys.yoosir.zzshow.common;

/**
 *  请求回调监听接口
 * Created by Yoosir on 2016/10/20 0020.
 */
public interface RequestCallBack<T> {

    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);

}
