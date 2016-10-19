package com.ys.yoosir.zzshow.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ys.yoosir.zzshow.modle.PostBean;
import com.ys.yoosir.zzshow.ui.adapters.listener.RecyclerListener;

import java.util.List;

/**
 * 帖子列表适配器
 * Created by Yoosir on 2016/10/19 0019.
 */
public class PostListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerListener mItemListener;

    public PostListAdapter(RecyclerListener itemListener, List<PostBean> ){
        this.mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
