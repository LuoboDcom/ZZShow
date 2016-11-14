package com.ys.yoosir.zzshow.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.RecyclerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_NORMAL = 1;
    private final int TYPE_PHOTO_SET = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private RecyclerListener mItemListener;
    private List<NewsSummary>   mData;

    public NewsListAdapter(Context context,RecyclerListener itemListener, List<NewsSummary> data){
        this.mContext = context;
        this.mItemListener = itemListener;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_PHOTO_SET){
            return new PhotoSetViewHolder(mInflater.inflate(R.layout.adapter_news_3_img_item, parent, false),mItemListener);
        }else{
            return new NormalViewHolder(mInflater.inflate(R.layout.adapter_news_1_img_item, parent, false),mItemListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        if(viewType == TYPE_PHOTO_SET){
            updatePhotoSetViews((PhotoSetViewHolder) holder,position);
        }else{
            updateNormalViews((NormalViewHolder)holder,position);
        }
    }

    private void updateNormalViews(NormalViewHolder holder,int position){
        NewsSummary data = mData.get(position);
        String imgPath = data.getImgsrc();
        Glide.with(MyApplication.getInstance())
                .load(imgPath)
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(holder.newsImgIv);

        holder.newsTitleTv.setText(data.getTitle());
        holder.newsPtimeTv.setText(data.getPtime());
        holder.newsSourceTv.setText(data.getSource());
    }

    private void updatePhotoSetViews(PhotoSetViewHolder holder,int position){
        NewsSummary data = mData.get(position);
        String imgPath = data.getImgsrc();
        Glide.with(MyApplication.getInstance())
                .load(imgPath)
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(holder.newsImgIv1);

        Glide.with(MyApplication.getInstance())
                .load(data.getImgextra().get(0).getImgsrc())
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(holder.newsImgIv2);

        Glide.with(MyApplication.getInstance())
                .load(data.getImgextra().get(1).getImgsrc())
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(holder.newsImgIv3);


        holder.newsTitleTv.setText(data.getTitle());
        holder.newsPtimeTv.setText(data.getPtime());
        holder.newsSourceTv.setText(data.getSource());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(mData != null && position < mData.size() && position >= 0){
            return "photoset".equals(mData.get(position).getSkipType()) ? TYPE_PHOTO_SET : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.news_picture_iv)
        ImageView newsImgIv;

        @BindView(R.id.news_title_tv)
        TextView newsTitleTv;

        @BindView(R.id.news_ptime_tv)
        TextView newsPtimeTv;

        @BindView(R.id.news_source_tv)
        TextView newsSourceTv;

        NormalViewHolder(View itemView, final RecyclerListener itemListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.OnItemClickListener(v,0,getAdapterPosition());
                }
            });
            ButterKnife.bind(this,itemView);
        }
    }

    static class PhotoSetViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.news_picture_1_iv)
        ImageView newsImgIv1;

        @BindView(R.id.news_picture_2_iv)
        ImageView newsImgIv2;

        @BindView(R.id.news_picture_3_iv)
        ImageView newsImgIv3;

        @BindView(R.id.news_title_tv)
        TextView newsTitleTv;

        @BindView(R.id.news_ptime_tv)
        TextView newsPtimeTv;

        @BindView(R.id.news_source_tv)
        TextView newsSourceTv;

        PhotoSetViewHolder(View itemView, final RecyclerListener itemListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.OnItemClickListener(v,0,getAdapterPosition());
                }
            });
            ButterKnife.bind(this,itemView);
        }
    }
}
