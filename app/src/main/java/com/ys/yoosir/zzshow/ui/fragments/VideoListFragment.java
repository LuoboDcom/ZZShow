package com.ys.yoosir.zzshow.ui.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.LoadDataType;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.presenter.VideoListPresenterImpl;
import com.ys.yoosir.zzshow.ui.adapters.VideoListAdapter;
import com.ys.yoosir.zzshow.ui.adapters.listener.RecyclerListener;
import com.ys.yoosir.zzshow.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.view.VideoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,VideoListView,RecyclerListener,ItemsProvider{

    private static final String TAG = VideoListFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.post_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private SingleListViewItemActiveCalculator mCalculator;

    private VideoListAdapter mVideosAdapter;
    private ArrayList<VideoData> mVideoList = new ArrayList<>();
    private boolean isLoading = false;
    private boolean hasMore = false;

    public VideoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoListFragment newInstance(String param1, String param2) {
        Log.d(TAG,"newInstance");
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mVideosAdapter = new VideoListAdapter(this,mVideoList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        Log.d(TAG,"getLayoutId");
        return R.layout.fragment_video_list;
    }

    @Override
    public void initViews(View view) {
        Log.d(TAG,"initViews");
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initSwipeRefreshLayout(){
        Log.d(TAG,"initSwipeRefreshLayout");
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,0,0,getActivity().getResources().getDimensionPixelSize(R.dimen.padding_size_xl));
            }
        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mScrollState;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mScrollState = newState;

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if ( !isLoading && hasMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    //TODO  load more & show footer
                    isLoading = true;
//                    ((VideoListPresenter)mPresenter).loadMoreData();
                    //mRecyclerView.scrollToPosition(mPostsAdapter.getItemCount() - 1);
                }

                if(newState == RecyclerView.SCROLL_STATE_IDLE && mVideosAdapter.getItemCount() > 0){
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCalculator.onScrolled(mScrollState);
            }
        });
        //TODO setAdapter
        mCalculator = new SingleListViewItemActiveCalculator(this,
                new RecyclerViewItemPositionGetter(mLinearLayoutManager, mRecyclerView));
        mRecyclerView.setAdapter(mVideosAdapter);
    }

    private void initPresenter(){
        Log.d(TAG,"initPresenter");
        mPresenter = new VideoListPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setVideoList(List<VideoData> videoDataList, boolean hasMore, int loadType) {
        this.hasMore = hasMore;
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                if (!mVideoList.isEmpty()) {
                    mVideoList.clear();
                }
                break;
            case LoadDataType.TYPE_REFRESH:
                if (!mVideoList.isEmpty()) {
                    mVideoList.clear();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                break;
        }
        mVideoList.addAll(videoDataList);
        if(mVideosAdapter != null) {
            mVideosAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        isLoading = false;
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void OnItemClickListener(View view, int type, int position) {

    }

    @Override
    public ListItem getListItem(int position) {
        if(mRecyclerView != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(position);
            if (holder instanceof ListItem) {
                return (ListItem) holder;
            }
        }
        return null;
    }

    @Override
    public int listItemSize() {
        if(mVideosAdapter != null){
            return mVideosAdapter.getItemCount();
        }
        return 0;
    }
}
