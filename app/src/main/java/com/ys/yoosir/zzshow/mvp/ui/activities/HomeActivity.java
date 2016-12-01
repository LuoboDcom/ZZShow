package com.ys.yoosir.zzshow.mvp.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.mvp.ui.fragments.News.NewsFragment;
import com.ys.yoosir.zzshow.mvp.ui.fragments.PhotoFragment;
import com.ys.yoosir.zzshow.mvp.ui.fragments.VideoFragment;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;
import com.ys.yoosir.zzshow.widget.video.VideoPlayView;

import butterknife.BindView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,VideoFragment.OnVideoFIListener,NewsFragment.OnNewsFIListener,PhotoFragment.OnPhotoFIListener {

    private final String CHILD_FRAGMENT_TAG = "child_news" ;

    private NewsFragment mNewsFragment;
    private VideoFragment mVideoFragment;
    private PhotoFragment mPhotoFragment;


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.full_screen)
    FrameLayout mFullScreenLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariables() {
        mNewsFragment = new NewsFragment();
        mVideoFragment = new VideoFragment();
        mPhotoFragment = new PhotoFragment();
    }

    @Override
    public void initViews() {
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
        mNavigationView.setCheckedItem(R.id.nav_news);
        setChildFragment(mNewsFragment);
    }

    public void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
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


    private void setChildFragment(BaseFragment childFragment){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.show_content_layout,childFragment,CHILD_FRAGMENT_TAG);
        mFragmentTransaction.commit();
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
            setChildFragment(mNewsFragment);
        } else if (id == R.id.nav_photo) {
            setChildFragment(mPhotoFragment);
        } else if (id == R.id.nav_video) {
            setChildFragment(mVideoFragment);
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
        } else if (id == R.id.nav_night) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_share_text));
        startActivity(Intent.createChooser(intent, getTitle()));
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
    public void onVideoToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.menu_video);
        setToolbar(toolbar);
    }

    @Override
    public void onNewsToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.menu_news);
        setToolbar(toolbar);
    }

    @Override
    public void onPhotoToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.menu_photo);
        setToolbar(toolbar);
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
}
