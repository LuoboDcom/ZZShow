package com.ys.yoosir.zzshow.mvp.apis.interfaces;

import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.entity.netease.NewsChannelTable;

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
