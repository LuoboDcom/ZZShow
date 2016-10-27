package com.ys.yoosir.zzshow.widget.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.ui.adapters.VideoListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  video list
 * Created by Yoosir on 2016/10/26 0026.
 */
public class VideoListLayout extends RelativeLayout{

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private VideoListAdapter mAdapter;

    @BindView(R.id.video_list)
    private RecyclerView mVideoListView;

    @BindView(R.id.small_layout)
    private RelativeLayout mSmallLayout;

    @BindView(R.id.layout_video)
    private FrameLayout mVideoLayout;

    @BindView(R.id.close)
    private ImageView mCloseIv;

    @BindView(R.id.full_screen)
    private FrameLayout mFullScreenLayout;

    public VideoListLayout(Context context) {
        this(context,null);
    }

    public VideoListLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initActions();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        initActions();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_list,this,true);
        this.mContext = context;
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        mVideoListView.setLayoutManager(mLayoutManager);
        mAdapter = new VideoListAdapter(context);
        mVideoListView.setAdapter(mAdapter);

        VideoPlayView mVideoItemView = VideoPlayView(context);
    }

    private void initActions() {

    }
}
