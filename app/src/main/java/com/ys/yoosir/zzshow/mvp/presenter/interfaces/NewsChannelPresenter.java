package com.ys.yoosir.zzshow.mvp.presenter.interfaces;

import com.ys.yoosir.zzshow.mvp.entity.netease.NewsChannelTable;

/**
 *
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/22.
 */

public interface NewsChannelPresenter extends BasePresenter{

    void onItemSwap(int fromPosition,int toPosition);

    void onItemAddOrRemove(NewsChannelTable newsChannel,boolean isChannelMine);

    void selectIndex(String newsChannelId);
}
