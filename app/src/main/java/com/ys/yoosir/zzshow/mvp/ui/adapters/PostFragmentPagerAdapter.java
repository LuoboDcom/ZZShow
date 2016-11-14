package com.ys.yoosir.zzshow.mvp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *  Fragment 适配器
 * Created by Yoosir on 2016/10/21 0021.
 */
public class PostFragmentPagerAdapter extends FragmentPagerAdapter{

    private final List<String> mTitles;
    private List<Fragment> mPostFragmentList;


    public PostFragmentPagerAdapter(FragmentManager fm,List<String> titles,List<Fragment> postFragmentList) {
        super(fm);
        mTitles = titles;
        mPostFragmentList = postFragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mPostFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mPostFragmentList == null ? 0 : mPostFragmentList.size();
    }
}
