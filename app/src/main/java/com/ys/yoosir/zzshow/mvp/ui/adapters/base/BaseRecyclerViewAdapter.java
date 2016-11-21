package com.ys.yoosir.zzshow.mvp.ui.adapters.base;

import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ys.yoosir.zzshow.mvp.ui.adapters.listener.MyRecyclerListener;

import java.util.List;

/**
 *  适配器基类
 * @version 1.0
 * @author yoosir
 * Created by Administrator on 2016/11/21.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    protected int mLastPosition = -1;
    protected boolean mIsShowFooter;
    protected MyRecyclerListener mOnItemClickListener;
    protected List<T> mList;

    public BaseRecyclerViewAdapter(List<T> list){
        mList = list;
    }

    public void setList(List<T> list){
        mList = list;
    }

    public List<T> getList(){
        return mList;
    }

    public void addMore(List<T> list){
        int startPosition = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(startPosition,mList.size());
    }

    public void add(int position,T item){
        mList.add(position,item);
        notifyItemInserted(position);
    }

    public void delete(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(MyRecyclerListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_FOOTER){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if(layoutParams != null){
                if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        }
    }

    protected View getView(ViewGroup parent, int layoutId){
        return LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
    }

    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;

        int itemSize = mList.size();
        if(mIsShowFooter){
            itemSize += 1;
        }
        return itemSize;
    }

    /**
     * 是否是 footer item
     * @param position
     * @return
     */
    protected boolean isFooterPosition(int position){
        return (getItemCount() - 1) == position;
    }

    /**
     *  item 加载动画
     * @param holder
     * @param position
     * @param type
     */
    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position, @AnimRes int type){
        if(position > mLastPosition){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    /**
     * 显示Footer
     */
    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    /**
     * 隐藏Footer
     */
    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemInserted(getItemCount());
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
