package com.ys.yoosir.zzshow.apis.interfaces;

import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;

import rx.Subscription;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public interface NewsChannelApi<T> {

    Subscription loadNewsChannels(RequestCallBack<T> callBack);

    void swapDB(int fromPosition,int toPosition);

    void updateDB(NewsChannelTable newsChannelTable,boolean isChannelMine);

}
