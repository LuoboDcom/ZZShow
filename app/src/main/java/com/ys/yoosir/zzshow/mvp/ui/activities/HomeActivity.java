package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.presenter.HomePresenterImpl;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.ui.adapters.PostFragmentPagerAdapter;
import com.ys.yoosir.zzshow.mvp.ui.fragments.NewsListFragment;
import com.ys.yoosir.zzshow.mvp.ui.fragments.VideoListFragment;
import com.ys.yoosir.zzshow.mvp.view.HomeView;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;
import com.ys.yoosir.zzshow.utils.TabLayoutUtil;
import com.ys.yoosir.zzshow.widget.video.VideoPlayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity
        implements HomeView,NavigationView.OnNavigationItemSelectedListener,VideoListFragment.OnVideoFIListener {

    private ArrayList<Fragment> mNewsFragmentList = new ArrayList<>();
    private String mCurrentViewPagerName;
    private List<String> mChannelNames;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.add_channel_iv)
    ImageView addChannelIv;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton mFloatActionBtn;

    @BindView(R.id.full_screen)
    FrameLayout mFullScreenLayout;

    @OnClick(R.id.add_channel_iv)
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.add_channel_iv:
                startActivity(new Intent(this, NewsChannelActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariables() {
        mPresenter = new HomePresenterImpl();
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        setSupportActionBar(mToolbar);
        mFloatActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(ismIsAddedView()){
                    setmIsAddedView(false);
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }
            }
        });

        mNavigationView.setNavigationItemSelectedListener(this);
        initNightModeSwitch();
    }

    /**
     *  日/夜 模式切换
     */
    private void initNightModeSwitch() {
        MenuItem menuNightMode = mNavigationView.getMenu().findItem(R.id.nav_night);
        SwitchCompat dayNightSwitch = (SwitchCompat) MenuItemCompat.getActionView(menuNightMode);
        setCheckedState(dayNightSwitch);
        setCheckedEvent(dayNightSwitch);
    }

    /**
     *  切换状态
     * @param dayNightSwitch
     */
    private void setCheckedState(SwitchCompat dayNightSwitch){
        if(SharedPreferencesUtil.isNightMode()){
            dayNightSwitch.setChecked(true);
        }else{
            dayNightSwitch.setChecked(false);
        }
    }

    /**
     *  Switch 按钮 切换事件
     * @param dayNightSwitch  按钮
     */
    private void setCheckedEvent(SwitchCompat dayNightSwitch){
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeToNight();
                    SharedPreferencesUtil.saveNightMode(true);
                }else{
                    changeToDay();
                    SharedPreferencesUtil.saveNightMode(false);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            // Handle the camera action
        } else if (id == R.id.nav_photo) {

        } else if (id == R.id.nav_video) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_night) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViewPager(List<String> channelNames) {
        PostFragmentPagerAdapter adapter = new PostFragmentPagerAdapter(
                getSupportFragmentManager(),channelNames,mNewsFragmentList);
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

    /**
     *  VideoListFragment  交互接口
     */
    @Override
    public void onVideoFI(int stateCode,VideoPlayView playView) {
        if(stateCode == 1){
            mFullScreenLayout.setVisibility(View.GONE);
            mFullScreenLayout.removeAllViews();
        }else if(stateCode == 2) {
            mFullScreenLayout.addView(playView);
            mFullScreenLayout.setVisibility(View.VISIBLE);
        }else if(stateCode == 3){
            mFullScreenLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mFullScreenLayout != null && mFullScreenLayout.getVisibility() == View.VISIBLE  && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initViewPager(List<NewsChannelTable> newsChannels) {
        final List<String> channelNames = new ArrayList<>();
        if(newsChannels != null){
            setNewsList(newsChannels,channelNames);
            setViewPager(channelNames);
        }
    }

    private void setNewsList(List<NewsChannelTable> newsChannels, List<String> channelNames) {
        mNewsFragmentList.clear();
        for (NewsChannelTable newsChannelTable: newsChannels) {
            NewsListFragment newsListFragment = createListFragment(newsChannelTable);
            mNewsFragmentList.add(newsListFragment);
            channelNames.add(newsChannelTable.getNewsChannelName());
        }
    }

    private NewsListFragment createListFragment(NewsChannelTable newsChannelTable){
        NewsListFragment fragment =  NewsListFragment.newInstance(newsChannelTable.getNewsChannelType(),newsChannelTable.getNewsChannelId(),newsChannelTable.getNewsChannelIndex());
        return fragment;
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
