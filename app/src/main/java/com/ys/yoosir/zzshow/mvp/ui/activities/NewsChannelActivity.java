package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;

/**
 *  频道管理
 * @version 1.0
 * Created by Yoosir on 2016/11/21.
 */

public class NewsChannelActivity extends BaseActivity {

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

    }

    @Override
    public void initViews() {

    }

    private void initRecyclerView(){
        mMineRecyclerView.setLayoutManager(new GridLayoutManager(this,4, LinearLayoutManager.VERTICAL,false));
        mMineRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }
}
