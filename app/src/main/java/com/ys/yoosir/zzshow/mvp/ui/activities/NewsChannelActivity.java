package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.di.component.DaggerNewsChannelComponent;
import com.ys.yoosir.zzshow.di.module.NewsChannelModule;
import com.ys.yoosir.zzshow.events.ChannelItemMoveEvent;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.NewsChannelPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsChannelPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.ui.adapters.NewsChannelAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.ItemDragHelperCallback;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
import com.ys.yoosir.zzshow.mvp.view.NewsChannelView;
import com.ys.yoosir.zzshow.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 *  频道管理
 * @version 1.0
 * Created by Yoosir on 2016/11/21.
 */

public class NewsChannelActivity extends BaseActivity<NewsChannelPresenterImpl> implements NewsChannelView {


    @BindView(R.id.tv_edit)
    TextView mEditBtn;

    @BindView(R.id.mine_recycler_view)
    RecyclerView mMineRecyclerView;

    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;

    @OnClick(R.id.tv_edit)
    public void onClick(View v){
        if(v.getId() == R.id.tv_edit){
            if(mMineAdapter.isEdit()){
                mEditBtn.setText(R.string.edit_text);
                mMineAdapter.setEdit(false);
            }else {
                mEditBtn.setText(R.string.finish_text);
                mMineAdapter.setEdit(true);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }
    private NewsChannelAdapter mMineAdapter;

    private NewsChannelAdapter mRecommendAdapter;

    @Override
    public void initVariables() {
        mSubscription = RxBus.getInstance().toObservable(ChannelItemMoveEvent.class)
                .subscribe(new Action1<ChannelItemMoveEvent>() {
                    @Override
                    public void call(ChannelItemMoveEvent channelItemMoveEvent) {
                        if(!mMineAdapter.isEdit()){
                            mEditBtn.setText(R.string.finish_text);
                            mMineAdapter.setEdit(true);
                        }
                        mEditBtn.setText(R.string.finish_text);
                        mPresenter.onItemSwap(channelItemMoveEvent.getFromPosition(),channelItemMoveEvent.getToPosition());
                    }
                });
    }

    @Override
    public void initViews() {
        initRecyclerView(mMineRecyclerView);
        initRecyclerView(mRecommendRecyclerView);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsChannelComponent.builder()
                .appComponent(appComponent)
                .newsChannelModule(new NewsChannelModule(this))
                .build()
                .inject(this);
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
                newsChannel.setNewsChannelSelect(true);
                if(!mMineAdapter.isEdit()) {
                    mEditBtn.setText(R.string.finish_text);
                    mMineAdapter.setEdit(true);
                }
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

        mMineAdapter.setItemDragHelperCallback(itemDragHelperCallback);
    }

    private void setMineChannelOnItemClick() {
        //从 我的频道 中取消选中的频道
        mMineAdapter.setOnItemClickListener(new MyRecyclerListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                NewsChannelTable newsChannel = mMineAdapter.getList().get(position);
                if(!mMineAdapter.isEdit()){
                    mPresenter.selectIndex(newsChannel.getNewsChannelName());
                    finish();
                    return;
                }
                if(!newsChannel.isNewsChannelFixed()){
                    newsChannel.setNewsChannelSelect(false);
                    mRecommendAdapter.add(mRecommendAdapter.getItemCount(),newsChannel);
                    mMineAdapter.delete(position);
                    mPresenter.onItemAddOrRemove(newsChannel,true);
                }
            }
        });
    }
}
