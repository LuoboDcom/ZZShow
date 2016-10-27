package com.ys.yoosir.zzshow.ui.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帖子列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<VideoData>   mVideoDatas;

    public VideoListAdapter(Context context){
        this.mVideoDatas = new ArrayList<>();
    }

    public void setData(List<VideoData> videoDatas){
        mVideoDatas.clear();
        mVideoDatas.addAll(videoDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final VideoViewHolder mHolder = (VideoViewHolder) holder;
        VideoData data = mVideoDatas.get(position);
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

        // load video file
        String videoUrl = "http://v6.pstatp.com/video/c/18aec3b0546642bd8fa5daaed8c41668/?Signature=HsOtVnRnUkmzKex30YQVSDNviPc%3D&Expires=1477313015&KSSAccessKeyId=qh0h9TdcEMrm1VlR2ad/";

        mHolder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.videoCoverIv.setVisibility(View.GONE);
                mHolder.videoPlayBtn.setVisibility(View.GONE);
                //TODO start play video
                if(mStartClick != null){
                    mStartClick.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoDatas == null ? 0 : mVideoDatas.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_layout_video)
        FrameLayout videoLayoutView;

        @BindView(R.id.video_cover_iv)
        ImageView videoCoverIv;

        @BindView(R.id.video_play_btn)
        ImageView videoPlayBtn;

        @BindView(R.id.video_title_tv)
        TextView videoTitleTv;

        @BindView(R.id.video_duration_tv)
        TextView videoDurationTv;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private StartClick mStartClick;

    public void setStartClick(StartClick startClick){
        this.mStartClick = startClick;
    }

    public interface StartClick{
        void onClick(int position);
    }
}
