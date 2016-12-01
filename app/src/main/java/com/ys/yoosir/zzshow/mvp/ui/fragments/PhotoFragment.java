package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.yoosir.zzshow.Constants;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.mvp.modle.photos.PhotoGirl;
import com.ys.yoosir.zzshow.mvp.presenter.PhotoPresenterImpl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoPresenter;
import com.ys.yoosir.zzshow.mvp.ui.activities.PhotoDetailActivity;
import com.ys.yoosir.zzshow.mvp.ui.adapters.PhotoGirlAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.PhotoGirlView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public class PhotoFragment extends BaseFragment<PhotoPresenter> implements PhotoGirlView,MyRecyclerListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mPhotoRecyclerView;

    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @OnClick({R.id.empty_view,R.id.fab})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.empty_view:
                mPresenter.loadPhotoData();
                break;
            case R.id.fab:
                mPhotoRecyclerView.getLayoutManager().scrollToPosition(0);
                break;
        }
    }

    private PhotoGirlAdapter mAdapter;
    private boolean isLoading = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPhotoFIListener) {
            mListener = (OnPhotoFIListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void initViews(View view) {
        mListener.onPhotoToolbar(mToolbar);
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
                mPresenter.refreshData();
            }
        });
    }

    private void initRecyclerView(){
        mPhotoRecyclerView.setHasFixedSize(true);
        mPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mPhotoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPhotoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();


                int[] lastVisibleItemPosition = layoutManager.findLastVisibleItemPositions(null);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!isLoading && visibleItemCount > 0 &&
                        (newState == RecyclerView.SCROLL_STATE_IDLE) &&
                        ((lastVisibleItemPosition[0] >= totalItemCount - 1) ||
                                (lastVisibleItemPosition[1] >= totalItemCount - 1))) {
                    isLoading = true;
                    mPresenter.loadMore();
                    mAdapter.showFooter();
                    mPhotoRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            }
        });
        mAdapter = new PhotoGirlAdapter(null);
        mAdapter.setOnItemClickListener(this);
        mPhotoRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter(){
        mPresenter = new PhotoPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListView(List<PhotoGirl> photoGirlList, int loadType) {
        switch (loadType){
            case LoadDataType.TYPE_FIRST_LOAD:
                mEmptyView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                mAdapter.setList(photoGirlList);
                mAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setList(photoGirlList);
                mAdapter.notifyDataSetChanged();
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                mAdapter.hideFooter();
                mAdapter.addMore(photoGirlList);
                break;
            default:
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
                mAdapter.hideFooter();
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        List<PhotoGirl> list = mAdapter.getList();
        Intent intent = PhotoDetailActivity.getPhotoDetailIntent(getContext(),list.get(position).getUrl());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView animationIv = (ImageView) view.findViewById(R.id.photo_iv);
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

    private OnPhotoFIListener mListener;

    public interface OnPhotoFIListener {
        void onPhotoToolbar(Toolbar toolbar);
    }
}
