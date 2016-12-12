package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import java.util.List;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/23.
 */

public interface NewsChannelView extends BaseView{

    void updateRecyclerView(List<NewsChannelTable> newsChannelMine,List<NewsChannelTable> newsChannelRecommend);

}
