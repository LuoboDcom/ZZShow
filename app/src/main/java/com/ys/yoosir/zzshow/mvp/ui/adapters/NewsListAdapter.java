package com.ys.yoosir.zzshow.mvp.ui.adapters;

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
 * 帖子列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerListener mItemListener;
    private List<NewsSummary>   mData;

    public NewsListAdapter(RecyclerListener itemListener, List<NewsSummary> data){
        this.mItemListener = itemListener;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_list_item, parent, false),mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        PostViewHolder mHolder = (PostViewHolder) holder;
//        NewsSummary data = mData.get(position);
//        String imgPath = data.getImage_list().get(1).getUrl();
//        //TODO show iv
//        Glide.with(MyApplication.getInstance())
//                .load(imgPath)
//                .placeholder(R.color.image_place_holder)
//                .error(R.mipmap.ic_load_fail)
//                .into(mHolder.postImgIv);
//
//        mHolder.postImgCountTv.setText(mHolder.itemView.getContext().getString(R.string.post_img_count_value,data.getGallary_image_count()));
//        mHolder.postTitleTv.setText(data.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.post_img_iv)
//        ImageView postImgIv;
//
//        @BindView(R.id.post_img_count_tv)
//        TextView postImgCountTv;
//
//        @BindView(R.id.post_title_tv)
//        TextView postTitleTv;

        public PostViewHolder(View itemView, final RecyclerListener itemListener) {
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
