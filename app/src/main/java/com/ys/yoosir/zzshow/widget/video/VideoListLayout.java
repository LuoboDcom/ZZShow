package com.ys.yoosir.zzshow.widget.video;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.ui.adapters.VideoListAdapter;
import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 *  video list
 * Created by Yoosir on 2016/10/26 0026.
 */
public class VideoListLayout extends RelativeLayout{

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private VideoListAdapter mAdapter;

    @BindView(R.id.video_list)
    RecyclerView mVideoListView;

    @BindView(R.id.small_layout)
    FrameLayout mSmallLayout;

    @BindView(R.id.layout_video)
    FrameLayout mVideoLayout;

    @BindView(R.id.close)
    ImageView mCloseIv;

    @BindView(R.id.full_screen)
    FrameLayout mFullScreenLayout;

    private VideoPlayView mVideoItemView;

    private int position = -1;
    private int lastPosition = -1;

    public VideoListLayout(Context context) {
        super(context);
        initView(context);
        initActions();
    }

    public VideoListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initActions();
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
//        mVideoListView = (RecyclerView) findViewById(R.id.video_list);
//        mSmallLayout = (RelativeLayout) findViewById(R.id.small_layout);

        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        mVideoListView.setLayoutManager(mLayoutManager);
        mAdapter = new VideoListAdapter(context);
        mVideoListView.setAdapter(mAdapter);

        mVideoItemView = new VideoPlayView(context);
    }

    private void initActions() {

        mCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoItemView.isPlay()){
                    mVideoItemView.stop();
                    position = -1;
                    lastPosition = -1;
                    mVideoLayout.removeAllViews();
                    mSmallLayout.setVisibility(View.GONE);
                }
            }
        });

        mSmallLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmallLayout.setVisibility(View.GONE);
                ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        mVideoItemView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {

                //播放完还原播放界面
                if(mSmallLayout.getVisibility() == View.VISIBLE){
                    mVideoLayout.removeAllViews();
                    mSmallLayout.setVisibility(View.GONE);
                    mVideoItemView.setShowController(true);
                }

                FrameLayout frameLayout = (FrameLayout) mVideoItemView.getParent();
                mVideoItemView.release();
                if(frameLayout != null && frameLayout.getChildCount() > 0){
                    frameLayout.removeAllViews();
                    View itemView = (View) frameLayout.getParent();
                    //TODO show cover view
                }

                lastPosition = -1;
            }
        });

        mAdapter.setStartClick(new VideoListAdapter.StartClick() {
            @Override
            public void onClick(int position) {
                VideoListLayout.this.position = position;
                if(mVideoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED){
                    if(position != lastPosition){
                        mVideoItemView.stop();
                        mVideoItemView.release();
                    }
                }

                if(mSmallLayout.getVisibility() == View.VISIBLE){
                    mSmallLayout.setVisibility(View.GONE);
                    mVideoLayout.removeAllViews();
                    mVideoItemView.setShowController(true);
                }

                if(lastPosition != -1){
                    //找到 VideoItemView 的父类，然后 remove
                    ViewGroup last = (ViewGroup) mVideoItemView.getParent();
                    if(last != null){
                        last.removeAllViews();
                        View itemView = (View)last.getParent();
//                        if(itemView != null){
//                            itemView.findViewById(R.id.)
//                        }
                    }
                }

                if(mVideoItemView.getParent() != null){
                    ((ViewGroup)mVideoItemView.getParent()).removeAllViews();
                }

                View view = mVideoListView.findViewHolderForAdapterPosition(position).itemView;
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                frameLayout.removeAllViews();
                frameLayout.addView(mVideoItemView);
                String videoUrl = "http://v6.pstatp.com/video/c/a930338a6087407d8ae568afc3a51eb3/?Signature=Udk9e0SkjMBgMVsanZc2BnpMaNI%3D&Expires=1477912023&KSSAccessKeyId=qh0h9TdcEMrm1VlR2ad/";
                mVideoItemView.start(videoUrl);
                lastPosition = position;

            }
        });

        mVideoListView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int index = mVideoListView.getChildAdapterPosition(view);
                //TODO show cover

                if(index == position){
                    //当前正在播放
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    if(mVideoItemView != null &&
                            (mVideoItemView.isPlay() || mVideoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED)){
                        //TODO hide cover

                    }

                    if(mVideoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED){
                        if(mVideoItemView.getParent() != null)
                            ((ViewGroup)mVideoItemView.getParent()).removeAllViews();
                        frameLayout.addView(mVideoItemView);
                        return;
                    }

                    if(mSmallLayout.getVisibility() == View.VISIBLE && mVideoItemView != null && mVideoItemView.isPlay()){
                        mSmallLayout.setVisibility(View.GONE);
                        mVideoLayout.removeAllViews();
                        mVideoItemView.setShowController(true);
                        frameLayout.addView(mVideoItemView);
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int index = mVideoListView.getChildAdapterPosition(view);
                if(index == position){
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    if(mSmallLayout.getVisibility() == View.GONE
                            && mVideoItemView != null && mVideoItemView.isPlay()){
                        mVideoLayout.removeAllViews();
                        mVideoItemView.setShowController(false);
                        mVideoLayout.addView(mVideoItemView);
                        mSmallLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void setData(List<VideoData> videoDatas){
        mAdapter.setData(videoDatas);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(mVideoItemView != null){
            mVideoItemView.onChanged(newConfig);
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                mFullScreenLayout.setVisibility(View.GONE);
                mVideoListView.setVisibility(View.VISIBLE);
                mFullScreenLayout.removeAllViews();
                if(position <= mLayoutManager.findLastVisibleItemPosition()
                        && position >= mLayoutManager.findFirstVisibleItemPosition()){
                    View view = mVideoListView.findViewHolderForAdapterPosition(position).itemView;
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    frameLayout.addView(mVideoItemView);
                    mVideoItemView.setShowController(true);
                }else{
                    mVideoLayout.removeAllViews();
                    mVideoLayout.addView(mVideoItemView);
                    mVideoItemView.setShowController(false);
                    mSmallLayout.setVisibility(View.VISIBLE);
                }
                mVideoItemView.setControllerVisible();
            }else{
                ViewGroup viewGroup = (ViewGroup) mVideoItemView.getParent();
                if(viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                mFullScreenLayout.addView(mVideoItemView);
                mSmallLayout.setVisibility(View.GONE);
                mVideoLayout.setVisibility(View.GONE);
                mFullScreenLayout.setVisibility(View.VISIBLE);
            }
        }else{
            mAdapter.notifyDataSetChanged();
            mVideoListView.setVisibility(View.VISIBLE);
            mFullScreenLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mVideoItemView == null)
            mVideoItemView = new VideoPlayView(mContext);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mVideoLayout == null)
            return;

        if(mSmallLayout.getVisibility() == View.VISIBLE){
            mSmallLayout.setVisibility(View.GONE);
            mVideoLayout.removeAllViews();
        }

        if(position != -1){
            ViewGroup view = (ViewGroup) mVideoItemView.getParent();
            if(view != null){
                view.removeAllViews();
            }
        }

        mVideoItemView.stop();
        mVideoItemView.release();
        mVideoItemView.onDestory();
        mVideoItemView = null;
    }

}
