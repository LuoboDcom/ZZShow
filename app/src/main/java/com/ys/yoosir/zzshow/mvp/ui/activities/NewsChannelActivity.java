package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.events.ChannelItemMoveEvent;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.NewsChannelPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.ui.adapters.NewsChannelAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.ItemDragHelperCallback;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
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

    private NewsChannelAdapter mMineAdapter;
    private NewsChannelAdapter mRecommendAdapter;

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

        mMineAdapter = new NewsChannelAdapter(newsChannelMine);
        mMineRecyclerView.setAdapter(mMineAdapter);
        setMineChannelOnItemClick();
        initItemDragHelper();

        mRecommendAdapter = new NewsChannelAdapter(newsChannelRecommend);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        setRecommendChannelOnItemClick();

    }

    private void setRecommendChannelOnItemClick() {
        // 推荐频道 item 点击事件
        mRecommendAdapter.setOnItemClickListener(new MyRecyclerListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                NewsChannelTable newsChannel = mRecommendAdapter.getList().get(position);
                mMineAdapter.add(mMineAdapter.getItemCount(), newsChannel);
                mRecommendAdapter.delete(position);
                mPresenter.onItemAddOrRemove(newsChannel, false);
            }
        });
    }

    private void initItemDragHelper() {
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mMineAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(mMineRecyclerView);

        mMineAdapter.setmItemDragHelperCallback(itemDragHelperCallback);
    }

    private void setMineChannelOnItemClick() {
        //从 我的频道 中取消选中的频道
        mMineAdapter.setOnItemClickListener(new MyRecyclerListener() {
            @Override
            public void OnItemClickListener(View view, int position) {

                NewsChannelTable newsChannel = mMineAdapter.getList().get(position);
                if(!newsChannel.isNewsChannelFixed()){
                    mRecommendAdapter.add(mRecommendAdapter.getItemCount(),newsChannel);
                    mMineAdapter.delete(position);
                    mPresenter.onItemAddOrRemove(newsChannel,true);
                }
            }
        });
    }
}
