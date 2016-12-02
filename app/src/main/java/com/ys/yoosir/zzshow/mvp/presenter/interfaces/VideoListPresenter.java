package com.ys.yoosir.zzshow.mvp.presenter.interfaces;

/**
 *  @version 1.0
 *  @author  yoosir
 * Created by Yoosir on 2016/10/24 0024.
 */
public interface VideoListPresenter extends BasePresenter {

    //设置视频的分类
    void setVideoType(String videoType);

    //刷新数据
    void refreshData();

    //加载数据
    void loadData();

    //加载更多
    void loadMoreData();

}
