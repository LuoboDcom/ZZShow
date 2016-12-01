package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.modle.videos.VideoChannel;
import com.ys.yoosir.zzshow.mvp.presenter.VideoPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.VideoPresenter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.PostFragmentPagerAdapter;
import com.ys.yoosir.zzshow.mvp.ui.fragments.News.NewsListFragment;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.NewsView;
import com.ys.yoosir.zzshow.mvp.view.VideoView;
import com.ys.yoosir.zzshow.utils.TabLayoutUtil;
import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;
import com.ys.yoosir.zzshow.widget.video.VideoPlayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 *  @version 1.0
 *  @author  yoosir
 */
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.fab)
    FloatingActionButton mFABtn;

    private ArrayList<Fragment> mVideoFragmentList = new ArrayList<>();
    private String mCurrentViewPagerName;
    private List<String> mChannelNames;
    private int mCurrentItem = 0;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initViews(View view) {
        mListener.onVideoToolbar(mToolbar);
        mFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VideoListFragment)mVideoFragmentList.get(mCurrentItem)).scrollToTop();
            }
        });
        initPresenter();
        initVideoPlayView();
    }

    private VideoPlayView mVideoPlayerView;
    private void initVideoPlayView() {
        mVideoPlayerView = new VideoPlayView(getContext());
        mVideoPlayerView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {
                //播放完还原播放画面
                ViewGroup parent = (ViewGroup) mVideoPlayerView.getParent();
                mVideoPlayerView.release();
                if(parent != null){
                    parent.removeAllViews();
                    if(parent.getId() != R.id.full_screen){
                        ViewGroup grandParent = (ViewGroup) parent.getParent();
                        if(grandParent != null){
                            //TODO 改变 ItemView
                            ((VideoListFragment)mVideoFragmentList.get(mViewPager.getCurrentItem())).onCompletionListener(grandParent);
                        }
                    }
                }
            }
        });
    }

    private void initPresenter() {
        mPresenter = new VideoPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVideoFIListener) {
            mListener = (OnVideoFIListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mVideoPlayerView != null) {
            ViewGroup view = (ViewGroup) mVideoPlayerView.getParent();
            if (view != null) {
                view.removeAllViews();
            }
            mVideoPlayerView.stop();
            mVideoPlayerView.release();
            mVideoPlayerView.onDestory();
            mVideoPlayerView = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(hasVideoItem(newConfig)){
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                //从横屏切换到竖屏
                updateOrientationPortrait();
            }else{
                //从竖屏切换到横屏
                updateOrientationLandscape();
            }
        }else{
            mListener.onVideoFI(3,null);
        }
    }

    /**
     *  是否有 VideoPlayView
     * @return true 有 ；false 没有
     */
    public boolean hasVideoItem(Configuration newConfig){
        boolean flag = mVideoPlayerView != null;
        if(mVideoPlayerView != null){
            mVideoPlayerView.onChanged(newConfig);
        }
        return flag;
    }

    /**
     *  切换到竖屏时操作
     */
    public void updateOrientationPortrait(){
        mListener.onVideoFI(1,null);
        ((VideoListFragment)mVideoFragmentList.get(mViewPager.getCurrentItem())).updateOrientation();
    }

    /**
     *  切换到横屏时操作
     * @return
     */
    public void updateOrientationLandscape(){
        //将 VideoView 重竖屏移除
        ViewGroup viewGroup = (ViewGroup) mVideoPlayerView.getParent();
        if(viewGroup != null) {
            viewGroup.removeAllViews();
            mListener.onVideoFI(2,mVideoPlayerView);
        }
    }


    @Override
    public void initViewPager(List<VideoChannel> videoChannelList) {
        final List<String> channelNames = new ArrayList<>();
        if(videoChannelList != null){
            setNewsList(videoChannelList,channelNames);
            setViewPager(channelNames);
        }
    }


    private void setNewsList(List<VideoChannel> videoChannelList, List<String> channelNames) {
        mVideoFragmentList.clear();
        for (VideoChannel videoChannel: videoChannelList) {
            VideoListFragment videoListFragment = createListFragment(videoChannel);
            mVideoFragmentList.add(videoListFragment);
            channelNames.add(videoChannel.getVideoChannelName());
        }
    }

    private VideoListFragment createListFragment(VideoChannel videoChannel){
        return VideoListFragment.newInstance(videoChannel.getVideoChannelID());
    }

    private void setViewPager(List<String> channelNames) {
        PostFragmentPagerAdapter adapter = new PostFragmentPagerAdapter(
                getChildFragmentManager(),channelNames,mVideoFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        TabLayoutUtil.dynamicSetTabLayoutMode(mTabLayout);
        setPageChangeListener();

        mChannelNames = channelNames;
        int currentViewPagerPosition = getCurrentViewPagerPosition();
        mCurrentItem = currentViewPagerPosition;
        mViewPager.setCurrentItem(mCurrentItem,false);
        ((VideoListFragment)mVideoFragmentList.get(mCurrentItem)).setVideoPlayView(mVideoPlayerView);
    }

    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("VideoFragment","onPageSelected mCurrentItem="+mCurrentItem+"--position="+position);
                mCurrentViewPagerName = mChannelNames.get(position);
                if(mCurrentItem != position){
                    ((VideoListFragment)mVideoFragmentList.get(mCurrentItem)).onDetachFromWindow();
                    ((VideoListFragment)mVideoFragmentList.get(position)).setVideoPlayView(mVideoPlayerView);
                    mCurrentItem = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int getCurrentViewPagerPosition(){
        int position = 0;
        if(mCurrentViewPagerName != null){
            for (int i = 0; i < mChannelNames.size(); i++) {
                if (mCurrentViewPagerName.equals(mChannelNames.get(i))){
                    position = i;
                }
            }
        }
        return position;
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

    private OnVideoFIListener mListener;

    public interface OnVideoFIListener {
        // TODO: Update argument type and name
        void onVideoFI(int stateCode,VideoPlayView playView);
        void onVideoToolbar(Toolbar toolbar);
    }
}
