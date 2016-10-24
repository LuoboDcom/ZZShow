package com.ys.yoosir.zzshow.ui.adapters;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.widget.TextureVideoView;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.modle.toutiao.VideoData;
import com.ys.yoosir.zzshow.ui.adapters.listener.RecyclerListener;
import com.ys.yoosir.zzshow.utils.VideoListGlideModule;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帖子列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerListener mItemListener;
    private List<VideoData>   mPostList;

    public VideoListAdapter(RecyclerListener itemListener, List<VideoData> postList){
        this.mItemListener = itemListener;
        this.mPostList = postList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_list_item, parent, false),mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VideoViewHolder mHolder = (VideoViewHolder) holder;
        VideoData data = mPostList.get(position);
        if("null".equals(data.getMedia_url()) || data.getMedia_url() == null){
            return;
        }
        String imgPath = data.getImage_url();
        //TODO show iv
        Glide.with(MyApplication.getInstance())
                .load(imgPath)
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(mHolder.videoCoverIv);
        mHolder.videoTitleTv.setText(data.getTitle());
        mHolder.videoDurationTv.setText(data.getVideo_duration_str());
        mHolder.videoView.setVideoPath(data.getMedia_url());
        // load video file
        Glide.with(MyApplication.getInstance())
                .using(VideoListGlideModule.getOkHttpUrlLoader(), InputStream.class)
                .load(new GlideUrl(data.getMedia_url()))
                .as(File.class)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);

        mHolder.videoView.setMediaPlayerCallback(new TextureVideoView.MediaPlayerCallback() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

            }

            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }

            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        mHolder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start play video
                mHolder.videoView.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements ListItem{

        @BindView(R.id.video_view)
        TextureVideoView videoView;

        @BindView(R.id.video_cover_iv)
        ImageView videoCoverIv;

        @BindView(R.id.video_play_btn)
        ImageView videoPlayBtn;

        @BindView(R.id.video_title_tv)
        TextView videoTitleTv;

        @BindView(R.id.video_duration_tv)
        TextView videoDurationTv;

        public VideoViewHolder(View itemView, final RecyclerListener itemListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.OnItemClickListener(v,0,getAdapterPosition());
                }
            });
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void setActive(View view, int i) {
            videoView.start();
        }

        @Override
        public void deactivate(View view, int i) {
            videoView.stop();
        }
    }
}
