package com.ys.yoosir.zzshow.mvp.ui.adapters.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/22.
 */

public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    private OnItemMoveListener mOnItemMoveListener;
    private boolean mIsLongPressEnabled;

    public ItemDragHelperCallback(OnItemMoveListener mOnItemMoveListener) {
        this.mOnItemMoveListener = mOnItemMoveListener;
    }

    public void setLongPressEnabled(boolean isLongPressEnabled) {
        this.mIsLongPressEnabled = isLongPressEnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mIsLongPressEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = setDragFlags(recyclerView);
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return !isDifferentItemViewType(viewHolder,target) &&
                mOnItemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    private boolean isDifferentItemViewType(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return viewHolder.getItemViewType() != target.getItemViewType();
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public int setDragFlags(RecyclerView recyclerView) {
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager){
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return dragFlags;
    }

    public interface OnItemMoveListener {
        boolean onItemMove(int fromPosition,int toPosition);
    }
}
