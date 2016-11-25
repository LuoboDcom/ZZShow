package com.ys.yoosir.zzshow.events;

/**
 * 频道 滑动位置置换事件
 *
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/22.
 */

public class ChannelItemMoveEvent {

    private int fromPosition;
    private int toPosition;

    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public ChannelItemMoveEvent(int fromPosition, int toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }
}
