package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.events.ChannelItemMoveEvent;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.NewsChannelPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;
import com.ys.yoosir.zzshow.utils.RxBus;

import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 *  频道管理
 * @version 1.0
 * Created by Yoosir on 2016/11/21.
 */

public class NewsChannelActivity extends BaseActivity<NewsChannelPresenter> implements NewsChannelView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_edit)
    TextView mEditBtn;

    @BindView(R.id.mine_recycler_view)
    RecyclerView mMineRecyclerView;

    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initVariables() {
        mSubscription = RxBus.getInstance().toObservable(ChannelItemMoveEvent.class)
                .subscribe(new Action1<ChannelItemMoveEvent>() {
                    @Override
                    public void call(ChannelItemMoveEvent channelItemMoveEvent) {
                        mPresenter.onItemSwap(channelItemMoveEvent.getFromPosition(),channelItemMoveEvent.getToPosition());
                    }
                });
        mPresenter = new NewsChannelPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void initViews() {
        initRecyclerView(mMineRecyclerView);
        initRecyclerView(mRecommendRecyclerView);
    }

    private void initRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new GridLayoutManager(this,4, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void updateRecyclerView(List<NewsChannelTable> newsChannelMine, List<NewsChannelTable> newsChannelRecommend) {

        newsChannelMine
    }
}
