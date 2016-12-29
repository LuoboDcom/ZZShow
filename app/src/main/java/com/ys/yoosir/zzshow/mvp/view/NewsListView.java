package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.base.BaseView;

import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/11 0011.
 */
public interface NewsListView extends BaseView {

    void setNewsList(List<NewsSummary> newsSummaryList,int loadType);

    void updateErrorView(int loadType);
}
