package com.ys.yoosir.zzshow.mvp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.model.entity.videos.VideoData;
import com.ys.yoosir.zzshow.mvp.ui.adapters.base.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帖子列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class VideoListAdapter extends BaseRecyclerViewAdapter<VideoData>{


    public VideoListAdapter(List<VideoData> list) {
        super(list);
    }

    @Override
    public int getItemViewType(int position) {
        if(mIsShowFooter && isFooterPosition(position)){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            return new FooterViewHolder(getView(parent,R.layout.adapter_footer_item));
        }else {
            return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder,position);
        if(getItemViewType(position) == TYPE_ITEM){
            updateVideoView((VideoViewHolder) holder,position);
        }
    }

    private void updateVideoView(final VideoViewHolder mHolder, final int position){
        VideoData data = mList.get(position);
        String imgPath = data.getCover();
        //TODO show iv
        Glide.with(MyApplication.getInstance())
                .load(imgPath)
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(mHolder.videoCoverIv);
        mHolder.videoTitleTv.setText(data.getTitle());
        mHolder.videoDurationTv.setText(data.getSectiontitle());

        mHolder.videoCoverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start play video
                v.setVisibility(View.GONE);
                if(mOnItemClickListener != null){
                    mOnItemClickListener.OnItemClickListener(mHolder.itemView,position);
                }
            }
        });
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_layout_video)
        FrameLayout videoLayoutView;

        @BindView(R.id.video_cover_layout)
        FrameLayout videoCoverLayout;

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
}
