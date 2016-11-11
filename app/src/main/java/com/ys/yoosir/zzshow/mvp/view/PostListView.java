package com.ys.yoosir.zzshow.mvp.view;

import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.mvp.view.base.BaseView;

import java.util.List;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public interface PostListView extends BaseView{

    void setPostList(List<ArticleData> articleDataList,boolean hasMore,int loadType);

}
