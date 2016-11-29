package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.mvp.modle.photos.PhotoGirl;
import com.ys.yoosir.zzshow.mvp.presenter.PhotoPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoPresenter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.PhotoGirlAdapter;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.PhotoGirlView;

import java.util.List;

import butterknife.BindView;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public class PhotoFragment extends BaseFragment<PhotoPresenter> implements PhotoGirlView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mPhotoRecyclerView;

    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private PhotoGirlAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void initViews(View view) {
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

    private void initRecyclerView(){
        mPhotoRecyclerView.setHasFixedSize(true);
        mPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mPhotoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPhotoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter = new PhotoGirlAdapter(null);
        mPhotoRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter(){
        mPresenter = new PhotoPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
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

    @Override
    public void updateListView(List<PhotoGirl> photoGirlList, int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mAdapter.setList(photoGirlList);
                mAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_REFRESH:
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                break;
            default:
                break;
        }
    }
}
