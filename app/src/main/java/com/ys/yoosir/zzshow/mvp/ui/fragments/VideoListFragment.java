package com.ys.yoosir.zzshow.mvp.ui.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.mvp.presenter.VideoListPresenterImpl;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.RecyclerListener;
import com.ys.yoosir.zzshow.mvp.ui.fragments.base.BaseFragment;
import com.ys.yoosir.zzshow.mvp.view.VideoListView;
import com.ys.yoosir.zzshow.widget.video.VideoListLayout;
import com.ys.yoosir.zzshow.widget.video.VideoPlayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,VideoListView,RecyclerListener{

    private static final String TAG = VideoListFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.video_list_layout)
    VideoListLayout mVideoListLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


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
    }

    @Override
    public int getLayoutId() {
        Log.d(TAG,"getLayoutId");
        return R.layout.fragment_video_list;
    }

    @Override
    public void initViews(View view) {
        Log.d(TAG,"initViews");
        initPresenter();
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
        if (context instanceof OnVideoFIListener) {
            mListener = (OnVideoFIListener) context;
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
                break;
            case LoadDataType.TYPE_LOAD_MORE:
                break;
        }
        mVideoList.addAll(videoDataList);
        mVideoListLayout.setVisibility(View.VISIBLE);
        mVideoListLayout.setData(mVideoList);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Completion","onConfigurationChanged ");
        //VideoView 是否初始化成功
        if(mVideoListLayout.hasVideoItem(newConfig)){
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                //切换到竖屏时
                //从横屏移除
                if(mListener != null){
                    Log.d("Completion","onConfigurationChanged - mListener ");
                    mListener.onVideoFI(1,null);
                }
                //添加到竖屏
                Log.d("Completion","onConfigurationChanged updateOrientationPortrait ");
                mVideoListLayout.updateOrientationPortrait();
            }else{
                //切换到横屏时
                if(mVideoListLayout.updateOrientationLandscape()){
                    //并添加到横屏显示
                    if(mListener != null){
                        mListener.onVideoFI(2,mVideoListLayout.getVideoItemView());
                    }
                }
            }
        }else{
            if(mListener != null){
                mListener.onVideoFI(3,null);
            }
        }
    }

    private OnVideoFIListener mListener;

    public interface OnVideoFIListener {
        // TODO: Update argument type and name
        void onVideoFI(int stateCode,VideoPlayView playView);
    }
}
