package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.mvp.modle.videos.VideoData;
import com.ys.yoosir.zzshow.mvp.presenter.VideoListPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoListPresenter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.VideoListAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.VideoListView;
import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;
import com.ys.yoosir.zzshow.widget.video.VideoPlayView;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListFragment extends BaseFragment<VideoListPresenter> implements VideoListView,MyRecyclerListener {

    private static final String TAG = VideoListFragment.class.getSimpleName();

    private static final String VIDEO_CHANNEL_TYPE = "VIDEO_CHANNEL_TYPE";

    // TODO: Rename and change types of parameters
    private String mVideoType;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.video_list)
    RecyclerView mVideoListView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoading = false;
    private boolean hasMore = false;

    public VideoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static VideoListFragment newInstance(String videoChannelType) {
        Log.d(TAG,"newInstance - videoChannelType = "+ videoChannelType);
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_CHANNEL_TYPE, videoChannelType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG,"setUserVisibleHint mVideoType="+mVideoType + "-- isVisibleToUser ="+isVisibleToUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVideoType = getArguments().getString(VIDEO_CHANNEL_TYPE);
        }
        Log.d(TAG,"onCreate mVideoType="+mVideoType);
    }

    @Override
    public int getLayoutId() {
        Log.d(TAG,"getLayoutId mVideoType="+mVideoType);
        return R.layout.fragment_video_list;
    }

    @Override
    public void initViews(View view) {
        Log.d(TAG,"initViews mVideoType="+mVideoType);
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
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

    private VideoPlayView       mVideoPlayView;
    private LinearLayoutManager mLayoutManager;
    private VideoListAdapter    mVideoListAdapter;
    private int mLastPosition = -1;

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mVideoListView.setLayoutManager(mLayoutManager);
        mVideoListAdapter = new VideoListAdapter(null);
        mVideoListView.setAdapter(mVideoListAdapter);
        initActions();
    }

    private void initActions() {
        mVideoListAdapter.setOnItemClickListener(new MyRecyclerListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                if(mVideoPlayView == null) return;

                //有视频在播放 或 暂停中
                if(position != mLastPosition && mLastPosition != -1){
                    if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PLAYING
                            || mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_PAUSED){
                        //停止
                        mVideoPlayView.stop();
                        mVideoPlayView.release();
                    }

                    //并从Item View 中移除 video view
                    ViewGroup lastParent = (ViewGroup) mVideoPlayView.getParent();
                    if(lastParent != null){
                        lastParent.removeAllViews();
                        //找到 RecyclerView Item View
                        ViewGroup lastGrandParent = (ViewGroup) lastParent.getParent();
                        lastGrandParent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                    }
                }

                //设置 当前要播放视频 的View
                view.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                //add VideoPlayView
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                frameLayout.removeAllViews();
                frameLayout.addView(mVideoPlayView);
                //播放视频
                List<VideoData> videoList = mVideoListAdapter.getList();
                String videoUrl = videoList.get(position).getMp4_url();
                mVideoPlayView.start(videoUrl);
                mLastPosition = position;
            }
        });

        mVideoListView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if(mVideoPlayView == null) return;

                //当前被添加的 childView 的位置下标
                int position = mVideoListView.getChildAdapterPosition(view);
                //显示视频的封面View
                view.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                //判断此 childView 是否是当前正在操作的Item
                if(position == mLastPosition){
                    //是，添加 VideoView播放
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    if(mVideoPlayView != null &&
                            (mVideoPlayView.isPlay() || mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PAUSED)){
                        // VideoView 正在播放或暂停， 隐藏视频封面图
                        view.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                    }

                    if(mVideoPlayView.getParent() != null){
                        ((ViewGroup)mVideoPlayView.getParent()).removeAllViews();
                    }

                    if(mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PAUSED){
                        mVideoPlayView.setShowController(true);
                    }else if(mVideoPlayView.VideoStatus() == IjkVideoView.STATE_PLAYING){
                        mVideoPlayView.setShowController(true);
                    }
                    frameLayout.addView(mVideoPlayView);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int position = mVideoListView.getChildAdapterPosition(view);
                if(position == mLastPosition){
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    mLastPosition = -1;
                    mVideoPlayView.stop();
                    mVideoPlayView.release();
                }
            }
        });
    }

    private void initPresenter(){
        Log.d(TAG,"initPresenter mVideoType="+mVideoType);
        mPresenter = new VideoListPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.setVideoType(mVideoType);
        mPresenter.onCreate();
    }

    public void setVideoPlayView(VideoPlayView videoPlayView){
        mVideoPlayView = videoPlayView;
    }

    public void updateOrientation(){
        if (mLastPosition <= mLayoutManager.findLastVisibleItemPosition()
                && mLastPosition >= mLayoutManager.findFirstVisibleItemPosition()) {
            View view = mVideoListView.findViewHolderForAdapterPosition(mLastPosition).itemView;
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
            frameLayout.removeAllViews();
            if(mVideoPlayView.getVideoStatus() == IjkVideoView.STATE_IDLE){
                view.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
            }else {
                frameLayout.addView(mVideoPlayView);
                mVideoPlayView.setShowController(true);
                mVideoPlayView.setControllerVisible();
            }
        }
    }

    public void scrollToTop(){
        mVideoListView.getLayoutManager().scrollToPosition(0);
    }

    /**
     *  改变 List item view
     * @param parent 此 item view
     */
    public void onCompletionListener(ViewGroup parent){
        //显示封面
        parent.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
        //重置标识
        mLastPosition = -1;
    }

    /**
     * 离开此Fragment 或 不可见 时
     */
    public void onDetachFromWindow(){
        if(mVideoPlayView != null){
            ViewGroup parent = (ViewGroup) mVideoPlayView.getParent();
            if(parent != null){
                parent.removeAllViews();
                if (mLastPosition <= mLayoutManager.findLastVisibleItemPosition()
                        && mLastPosition >= mLayoutManager.findFirstVisibleItemPosition()) {
                    View view = mVideoListView.findViewHolderForAdapterPosition(mLastPosition).itemView;
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    view.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                }
            }
            mVideoPlayView.stop();
            mVideoPlayView.release();
        }
        mLastPosition = -1;
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
    public void setVideoList(List<VideoData> videoDataList, int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mVideoListAdapter.setList(videoDataList);
                mVideoListAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_REFRESH:
                mVideoListAdapter.setList(videoDataList);
                mVideoListAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                mVideoListAdapter.addMore(videoDataList);
                break;
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
    public void OnItemClickListener(View view, int position) {

    }

}
