package com.ys.yoosir.zzshow.mvp.ui.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.ApiConstants;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.presenter.NewsListPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsListPresenter;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PostListPresenter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.NewsListAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.RecyclerListener;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.NewsListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends BaseFragment<NewsListPresenter> implements RecyclerListener,NewsListView{

    private static final String TAG = "NewsListFragment";

    private static final String NEWS_CHANNEL_ID = "NEWS_CHANNEL_ID";
    private static final String NEWS_CHANNEL_TYPE = "NEWS_CHANNEL_TYPE";
    private static final String NEWS_CHANNEL_INDEX = "NEWS_CHANNEL_INDEX";

    private String mNewsChannelType;
    private String mNewsChannelId;
    private int mNewsChannelIndex = -1;

    @BindView(R.id.news_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.news_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private List<NewsSummary> mNewsSummaryList;
    private NewsListAdapter mAdapter;
    private boolean isFirst = true;
    private boolean hasMore = false;
    private boolean isLoading = false;

    public NewsListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String channelType, String channelId,int channelIndex) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_CHANNEL_TYPE, channelType);
        args.putString(NEWS_CHANNEL_ID, channelId);
        args.putInt(NEWS_CHANNEL_INDEX, channelIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void initViews(View view) {
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager)layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if(!isLoading && hasMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1){
                    //TODO load more % show footer
                    isLoading = true;
//                    ((PostListPresenter)mPresenter).loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO refresh data
            }
        });
    }

    private void initPresenter() {
        mPresenter = new NewsListPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.setNewsTypeAndId(mNewsChannelType, mNewsChannelId);
        KLog.d(TAG,"initPresenter - mNewsChannelIndex = "+ mNewsChannelIndex);
        if(getUserVisibleHint() && isFirst){
            mPresenter.onCreate();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isFirst && mPresenter != null){
            isFirst = false;
            mPresenter.onCreate();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsChannelType = getArguments().getString(NEWS_CHANNEL_TYPE);
            mNewsChannelId = getArguments().getString(NEWS_CHANNEL_ID);
            mNewsChannelIndex = getArguments().getInt(NEWS_CHANNEL_INDEX);
        }
        mNewsSummaryList = new ArrayList<>();
        mAdapter = new NewsListAdapter(getActivity(),this,mNewsSummaryList);
    }

    @Override
    public void OnItemClickListener(View view, int type, int position) {

    }

    @Override
    public void setNewsList(List<NewsSummary> newsSummaryList, int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mNewsSummaryList.clear();
                mNewsSummaryList.addAll(newsSummaryList);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void showProgress() {
        KLog.d(TAG,"showProgress");
        if(mProgressBar != null && mProgressBar.getVisibility() != View.VISIBLE)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        KLog.d(TAG,"hideProgress");
        if(mProgressBar != null && mProgressBar.getVisibility() != View.GONE)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
