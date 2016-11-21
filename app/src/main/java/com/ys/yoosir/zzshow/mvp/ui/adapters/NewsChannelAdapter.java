package com.ys.yoosir.zzshow.mvp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  频道适配器
 *
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/22.
 */

public class NewsChannelAdapter extends BaseRecyclerViewAdapter<NewsChannelTable> {

    private static final int TYPE_CHANNEL_FIXED = 0;
    private static final int TYPE_CHANNEL_NO_FIXED = 1;

    public NewsChannelAdapter(List<NewsChannelTable> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsChannelViewHolder holder = new NewsChannelViewHolder(getView(parent,R.layout.adapter_news_channel_item));
        handleLongPress(holder);
        handleOnClick(holder);
        return holder;
    }

    private void handleLongPress(NewsChannelViewHolder holder) {

    }

    private void handleOnClick(NewsChannelViewHolder holder) {
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if()
                }
            });
        }
    }

    class NewsChannelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_news_channel_name)
        TextView mChannelNameTv;

        @BindView(R.id.iv_delete)
        ImageView mDeleteIv;

        public NewsChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
