package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.modle.PostChannelTable;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import java.util.List;

/**
 *   首页
 * Created by Administrator on 2016/10/19 0019.
 */
public interface HomeView extends BaseView {

    void initViewPager(List<PostChannelTable> postChannels);

}
