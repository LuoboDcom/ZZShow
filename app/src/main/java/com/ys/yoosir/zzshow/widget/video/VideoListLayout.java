package com.ys.yoosir.zzshow.widget.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.videos.VideoData;
import com.ys.yoosir.zzshow.mvp.ui.adapters.VideoListAdapter;
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

    private final String TAG = "VideoListLayout";

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private VideoListAdapter mAdapter;

    @BindView(R.id.video_list)
    RecyclerView mVideoListView;

    private VideoPlayView mVideoItemView;

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

        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        mVideoListView.setLayoutManager(mLayoutManager);
        mAdapter = new VideoListAdapter(context);
        mVideoListView.setAdapter(mAdapter);

        mVideoItemView = new VideoPlayView(context);
    }

    private void initActions() {

        mVideoItemView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {
                Log.d("Completion","VideoListLayout ");
                //播放完还原播放界面
                FrameLayout frameLayout = (FrameLayout) mVideoItemView.getParent();
                //回收VideoView
                mVideoItemView.release();
                Log.d("Completion","VideoListLayout - frameLayout ");
                if(frameLayout != null){
                    frameLayout.removeAllViews();
                    if(frameLayout.getId() != R.id.full_screen){
                        View itemView = (View) frameLayout.getParent();
                        if(itemView != null){
                            //显示封面
                            itemView.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                            //重置标识
                            lastPosition = -1;
                        }
                    }
                }
            }
        });

        mAdapter.setStartClick(new VideoListAdapter.StartClick() {
            @Override
            public void onClick(int position) {
                if(position != lastPosition){
                    //有视频在播放或暂停中
                    if(mVideoItemView.getVideoStatus() == IjkVideoView.STATE_PLAYING ||
                            mVideoItemView.getVideoStatus() == IjkVideoView.STATE_PAUSED ){
                        //停止
                        mVideoItemView.stop();
                        mVideoItemView.release();
                    }
                    //将 上一个 VideoView 移除掉
                    if(lastPosition != -1){
                        //找到 VideoItemView 的父类，然后 remove
                        ViewGroup lastVideoViewParent = (ViewGroup) mVideoItemView.getParent();
                        if(lastVideoViewParent != null){
                            lastVideoViewParent.removeAllViews();
                            //找到在 RecyclerView 的 itemView
                            View itemView = (View)lastVideoViewParent.getParent();
                            if(itemView != null){
                                itemView.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                //设置当前 item 的 ui
                View itemView = mVideoListView.findViewHolderForAdapterPosition(position).itemView;
                //隐藏封面
                itemView.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                //添加 VideoView
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.item_layout_video);
                frameLayout.removeAllViews();
                frameLayout.addView(mVideoItemView);
                //播放
                List<VideoData> videoList = mAdapter.getData();
                String videoUrl = videoList.get(position).getMp4_url();
                mVideoItemView.start(videoUrl);
                //
                lastPosition = position;

            }
        });

        mVideoListView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                //当前被添加的 childView 的 位置下标
                int index = mVideoListView.getChildAdapterPosition(view);
                //显示视频的封面View
                view.findViewById(R.id.video_cover_layout).setVisibility(View.VISIBLE);
                //判断 此 childView 是否当前正在操作的item
                if(index == lastPosition){
                    //是，设置VideoView的显示区域
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    if(mVideoItemView != null &&
                            (mVideoItemView.isPlay() || mVideoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED)){
                        //VideoView 正在播放 或 暂停 ，都要隐藏 视频封面图
                        view.findViewById(R.id.video_cover_layout).setVisibility(View.GONE);
                    }

                    if(mVideoItemView.getParent() != null) {
                        ((ViewGroup) mVideoItemView.getParent()).removeAllViews();
                    }
                    if(mVideoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED){
                        //TODO  show play btn and controller ui
                        mVideoItemView.setShowController(true);
                    }else if(mVideoItemView.VideoStatus() == IjkVideoView.STATE_PLAYING){
                        //TODO show controller ui
                        mVideoItemView.setShowController(true);
                    }
                    frameLayout.addView(mVideoItemView);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int index = mVideoListView.getChildAdapterPosition(view);
                if(index == lastPosition){
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
                    frameLayout.removeAllViews();
                    lastPosition = -1;
                    mVideoItemView.stop();
                    mVideoItemView.release();
                }
            }
        });
    }

    public void setData(List<VideoData> videosData){
        mAdapter.setData(videosData);
    }

    /**
     *  是否有 VideoPlayView
     * @return true 有 ；false 没有
     */
    public boolean hasVideoItem(Configuration newConfig){
        boolean flag = mVideoItemView != null;
        if(flag){
            mVideoItemView.onChanged(newConfig);
        }else{
            mAdapter.notifyDataSetChanged();
            mVideoListView.setVisibility(View.VISIBLE);
        }
        return flag;
    }

    /**
     *  切换到竖屏时操作
     */
    public boolean updateOrientationPortrait(){
        mVideoListView.setVisibility(View.VISIBLE);
        Log.d("Completion","VideoListLayout updateOrientationPortrait ");
        if (lastPosition <= mLayoutManager.findLastVisibleItemPosition()
                && lastPosition >= mLayoutManager.findFirstVisibleItemPosition()) {
            Log.d("Completion", "VideoListLayout position in ");
            View view = mVideoListView.findViewHolderForAdapterPosition(lastPosition).itemView;
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.item_layout_video);
            frameLayout.removeAllViews();
            if(mVideoItemView.getVideoStatus() == IjkVideoView.STATE_IDLE){
                view.findViewById(R.id.video_cover_layout).setVisibility(VISIBLE);
            }else {
                frameLayout.addView(mVideoItemView);
                mVideoItemView.setShowController(true);
                mVideoItemView.setControllerVisible();
            }
        }
        return true;
    }

    /**
     *  切换到横屏时操作
     * @return
     */
    public boolean updateOrientationLandscape(){
        //将 VideoView 重竖屏移除
        ViewGroup viewGroup = (ViewGroup) mVideoItemView.getParent();
        if(viewGroup == null)
            return false;
        viewGroup.removeAllViews();
        return true;
    }

    public VideoPlayView getVideoItemView(){
        return mVideoItemView;
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

        if(lastPosition != -1){
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
