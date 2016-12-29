package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsDetail;
import com.ys.yoosir.zzshow.mvp.base.BaseView;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public interface NewsDetailView extends BaseView{

    void showNewsContent(NewsDetail newsDetail);

}
