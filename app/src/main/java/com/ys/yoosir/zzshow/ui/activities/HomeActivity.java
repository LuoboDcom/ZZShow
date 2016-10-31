package com.ys.yoosir.zzshow.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.modle.PostChannelTable;
import com.ys.yoosir.zzshow.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.ui.adapters.PostFragmentPagerAdapter;
import com.ys.yoosir.zzshow.ui.fragments.PostListFragment;
import com.ys.yoosir.zzshow.ui.fragments.VideoListFragment;
import com.ys.yoosir.zzshow.utils.TabLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Fragment> mPostFragmentList = new ArrayList<>();
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mNavigationView.setNavigationItemSelectedListener(this);

        initViews();

        initValues();
    }

    private void initValues() {
        List<PostChannelTable> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PostChannelTable postChannelTable = new PostChannelTable(i+1,"Channel-"+(i+1));
            list.add(postChannelTable);
        }
        initViewPager(list);
    }

    private void initViews() {

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViewPager(List<PostChannelTable> postChannels){
        final List<String> channelNames = new ArrayList<>();
        if(postChannels != null){
            setPostList(postChannels,channelNames);
            setViewPager(channelNames);
        }
    }

    private void setPostList(List<PostChannelTable> postChannels, List<String> channelNames) {
        mPostFragmentList.clear();
        for (PostChannelTable postChannel: postChannels) {
//            if("Channel-2".equals(postChannel.getName())) {
                VideoListFragment videoListFragment = VideoListFragment.newInstance("1","2");
                mPostFragmentList.add(videoListFragment);
//            }else{
//                PostListFragment postListFragment = createListFragment(postChannel);
//                mPostFragmentList.add(postListFragment);
//            }
            channelNames.add(postChannel.getName());
        }
    }

    private PostListFragment createListFragment(PostChannelTable postchannel){
        PostListFragment fragment =  PostListFragment.newInstance("1","2");
        return fragment;
    }

    private void setViewPager(List<String> channelNames) {
        PostFragmentPagerAdapter adapter = new PostFragmentPagerAdapter(
                getSupportFragmentManager(),channelNames,mPostFragmentList);
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

}
