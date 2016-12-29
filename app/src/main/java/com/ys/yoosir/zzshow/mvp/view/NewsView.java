package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.base.BaseView;

import java.util.List;

/**
 *   @version 1.0
 *   @author  yoosir
 * Created by Administrator on 2016/10/19 0019.
 */
public interface NewsView extends BaseView {

    void initViewPager(List<NewsChannelTable> newsChannels);

}
