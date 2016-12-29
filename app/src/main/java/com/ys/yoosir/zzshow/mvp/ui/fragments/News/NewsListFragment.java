package com.ys.yoosir.zzshow.mvp.ui.fragments.News;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.common.LoadDataType;
import com.ys.yoosir.zzshow.di.component.AppComponent;
import com.ys.yoosir.zzshow.di.component.DaggerNewsListComponent;
import com.ys.yoosir.zzshow.di.module.NewsListModule;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsPhotoDetail;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.presenter.NewsListPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.NewsListPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsDetailActivity;
import com.ys.yoosir.zzshow.mvp.ui.activities.NewsPhotoDetailActivity;
import com.ys.yoosir.zzshow.mvp.ui.adapters.NewsListAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.NewsListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends BaseFragment<NewsListPresenterImpl> implements MyRecyclerListener,NewsListView{

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

    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @OnClick(R.id.empty_view)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.empty_view:
                mPresenter.firstLoadData();
                break;
        }
    }

    private NewsListAdapter mAdapter;
//    private boolean isFirst = true;
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
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsListComponent.builder()
                .appComponent(appComponent)
                .newsListModule(new NewsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void initViews(View view) {
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mPresenter.setNewsTypeAndId(mNewsChannelType, mNewsChannelId);
        KLog.d(TAG,"initPresenter - mNewsChannelIndex = "+ mNewsChannelIndex);
        if(getUserVisibleHint()){
            KLog.d(TAG,"initPresenter - mNewsChannelIndex = "+ mNewsChannelIndex + " -- onCreate");
            mPresenter.onCreate();
        }
    }

    private void initRecyclerView() {
        final int leftRightPadding = getResources().getDimensionPixelSize(R.dimen.padding_size_l);
        final int topBottomPadding = getResources().getDimensionPixelOffset(R.dimen.padding_size_s);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(leftRightPadding,topBottomPadding,leftRightPadding,topBottomPadding);
//                super.getItemOffsets(outRect, view, parent, state);
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

                if(!isLoading && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1){
                    //TODO load more % show footer
                    isLoading = true;
                    if(mAdapter != null) {
                        mAdapter.showFooter();
                    }
                    mPresenter.loadMore();
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO refresh data
                mPresenter.refreshData();
            }
        });
    }

    public void scrollToTop(){
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && mPresenter != null){
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
        mAdapter = new NewsListAdapter(null);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        List<NewsSummary> mNewsSummaryList = mAdapter.getList();
        NewsSummary newsSummary = mNewsSummaryList.get(position);
        if(NewsListAdapter.TYPE_PHOTO_SET == mAdapter.getItemViewType(position)){
            NewsPhotoDetail mNewsPhotoDetail = setNewsPhotoDetail(newsSummary);
            startActivity(NewsPhotoDetailActivity.getNewsDetailIntent(getActivity(),mNewsPhotoDetail));
            KLog.d(TAG,"postId = " + newsSummary.getPostid() +"--- postSetId= "+ newsSummary.getPhotosetID());
        }else{
            Intent intent = NewsDetailActivity.getNewsDetailIntent(getActivity(),newsSummary.getPostid(),newsSummary.getImgsrc());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ImageView animationIv = (ImageView) view.findViewById(R.id.news_picture_iv);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(),
                        animationIv,
                        Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
                startActivity(intent,options.toBundle());
            }else{
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                        view,
                        view.getWidth() / 2,
                        view.getHeight() / 2,
                        0,
                        0);
                ActivityCompat.startActivity(getActivity(),intent,optionsCompat.toBundle());
            }
        }
    }

    private NewsPhotoDetail setNewsPhotoDetail(NewsSummary newsSummary){
        List<NewsSummary.AdsBean> adsBeanList =  newsSummary.getAds();
        List<NewsSummary.ImgextraBean> imgextraBeanList = newsSummary.getImgextra();
        List<NewsPhotoDetail.PictureItem> pictureItemList = new ArrayList<>();
        NewsPhotoDetail mNewsPhotoDetail = new NewsPhotoDetail();
        mNewsPhotoDetail.setTitle(newsSummary.getTitle());

        setValuesAndAddToList(pictureItemList,newsSummary.getTitle(),newsSummary.getImgsrc());
        if(adsBeanList != null) {
            for (NewsSummary.AdsBean adsBean : adsBeanList) {
                setValuesAndAddToList(pictureItemList,adsBean.getTitle(),adsBean.getImgsrc());
            }
        }
        if(imgextraBeanList != null) {
            for (NewsSummary.ImgextraBean imgtra : imgextraBeanList) {
                setValuesAndAddToList(pictureItemList,null,imgtra.getImgsrc());
            }
        }
        mNewsPhotoDetail.setPictureItemList(pictureItemList);
        return mNewsPhotoDetail;
    }

    private void setValuesAndAddToList(List<NewsPhotoDetail.PictureItem> pictureItemList, String description, String imgPath) {
        NewsPhotoDetail.PictureItem picture = new NewsPhotoDetail.PictureItem();
        if (description == null) {
            description = "这是一个描述";
        }
        picture.setDescription(description);
        picture.setImgPath(imgPath);

        pictureItemList.add(picture);
    }


    @Override
    public void setNewsList(List<NewsSummary> newsSummaryList, int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mEmptyView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                mAdapter.setList(newsSummaryList);
                mAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setList(newsSummaryList);
                mAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                isLoading = false;
                mAdapter.hideFooter();
                mAdapter.addMore(newsSummaryList);
                break;
        }
    }

    @Override
    public void updateErrorView(int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mSwipeRefreshLayout.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case LoadDataType.TYPE_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                isLoading = false;
                mAdapter.hideFooter();
                mAdapter.notifyDataSetChanged();
                break;
            default:
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
