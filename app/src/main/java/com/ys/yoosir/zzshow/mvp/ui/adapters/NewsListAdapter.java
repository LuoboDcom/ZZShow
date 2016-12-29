package com.ys.yoosir.zzshow.mvp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.ys.yoosir.zzshow.utils.DimenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsSummary>{

    public static final int TYPE_PHOTO_SET = 2;

    private float photoThreeHeight;
    private float photoTwoHeight;
    private float photoOneHeight;

    public NewsListAdapter(List<NewsSummary> list){
        super(list);
        photoThreeHeight = DimenUtil.dp2px(90);
        photoTwoHeight =  DimenUtil.dp2px(120);
        photoOneHeight =  DimenUtil.dp2px(150);
    }

    @Override
    public int getItemViewType(int position) {
        if(mIsShowFooter && isFooterPosition(position)){
            return TYPE_FOOTER;
        }else if("photoset".equals(mList.get(position).getSkipType())){
            return TYPE_PHOTO_SET;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_FOOTER:
                return new FooterViewHolder(getView(parent,R.layout.adapter_footer_item));
            case TYPE_PHOTO_SET:
                PhotoSetViewHolder photoSetViewHolder = new PhotoSetViewHolder(getView(parent,R.layout.adapter_news_3_img_item));
                setItemOnClickEvent(photoSetViewHolder);
                return photoSetViewHolder;
            case TYPE_ITEM:
            default:
                NormalViewHolder normalViewHolder = new NormalViewHolder(getView(parent,R.layout.adapter_news_1_img_item));
                setItemOnClickEvent(normalViewHolder);
                return normalViewHolder;
        }
    }

    private void setItemOnClickEvent(final RecyclerView.ViewHolder holder){
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.OnItemClickListener(v,holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        int viewType = getItemViewType(position);
        if(viewType == TYPE_PHOTO_SET){
            updatePhotoSetViews((PhotoSetViewHolder) holder,position);
        }else if(viewType == TYPE_ITEM){
            updateNormalViews((NormalViewHolder)holder,position);
        }
    }

    private void updateNormalViews(NormalViewHolder holder,int position){
        NewsSummary data = mList.get(position);
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

        float photoHeight;
        NewsSummary data = mList.get(position);
        holder.newsTitleTv.setText(data.getTitle());
        holder.newsPtimeTv.setText(data.getPtime());
        holder.newsSourceTv.setText(data.getSource());

        LinearLayout.LayoutParams groupLayoutParams = (LinearLayout.LayoutParams) holder.mNewsPictureGroup.getLayoutParams();

        String imgPath1 = data.getImgsrc();
        String imgPath2 = null;
        String imgPath3 = null;
        photoHeight = photoOneHeight;
        if(data.getImgextra() != null){
            int size = data.getImgextra().size();
            if(size >= 2) {
                imgPath2 = data.getImgextra().get(0).getImgsrc();
                imgPath3 = data.getImgextra().get(1).getImgsrc();
                photoHeight = photoThreeHeight;
            }else{
                imgPath2 = data.getImgextra().get(0).getImgsrc();
                photoHeight = photoTwoHeight;
            }
        }
        groupLayoutParams.height = (int) photoHeight;
        holder.mNewsPictureGroup.setLayoutParams(groupLayoutParams);
        showAndSetPhoto(holder,imgPath1,imgPath2,imgPath3);
    }

    private void showAndSetPhoto(PhotoSetViewHolder holder,String imgPath1,String imgPath2,String imgPath3){
        if(imgPath1 != null){
            loadImage(holder.newsImgIv1,imgPath1);
        }else{
            holder.newsImgIv1.setVisibility(View.GONE);
        }

        if(imgPath2 != null){
            loadImage(holder.newsImgIv2,imgPath2);
        }else{
            holder.newsImgIv2.setVisibility(View.GONE);
        }

        if(imgPath3 != null){
            loadImage(holder.newsImgIv3,imgPath3);
        }else{
            holder.newsImgIv3.setVisibility(View.GONE);
        }
    }

    private void loadImage(ImageView view,String path){
        view.setVisibility(View.VISIBLE);
        Glide.with(MyApplication.getInstance())
                .load(path)
                .placeholder(R.color.image_place_holder)
                .error(R.mipmap.ic_load_fail)
                .into(view);
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

        NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class PhotoSetViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.news_picture_group)
        LinearLayout mNewsPictureGroup;

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

        PhotoSetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
