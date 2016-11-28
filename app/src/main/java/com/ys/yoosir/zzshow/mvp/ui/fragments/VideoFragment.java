package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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


    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    private ArrayList<Fragment> mNewsFragmentList = new ArrayList<>();
    private String mCurrentViewPagerName;
    private List<String> mChannelNames;

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
        return R.layout.fragment_news;
    }

    @Override
    public void initViews(View view) {
        initPresenter();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        mNewsFragmentList.clear();
        for (VideoChannel videoChannel: videoChannelList) {
            VideoListFragment videoListFragment = createListFragment(videoChannel);
            mNewsFragmentList.add(newsListFragment);
            channelNames.add(newsChannelTable.getNewsChannelName());
        }
    }

    private VideoListFragment createListFragment(NewsChannelTable newsChannelTable){
        NewsListFragment fragment =  NewsListFragment.newInstance(newsChannelTable.getNewsChannelType(),newsChannelTable.getNewsChannelId(),newsChannelTable.getNewsChannelIndex());
        return fragment;
    }

    private void setViewPager(List<String> channelNames) {
        PostFragmentPagerAdapter adapter = new PostFragmentPagerAdapter(
                getChildFragmentManager(),channelNames,mNewsFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        TabLayoutUtil.dynamicSetTabLayoutMode(mTabLayout);
        setPageChangeListener();

        mChannelNames = channelNames;
        int currentViewPagerPosition = getCurrentViewPagerPosition();
        mViewPager.setCurrentItem(currentViewPagerPosition,false);
    }

    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentViewPagerName = mChannelNames.get(position);
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

}
