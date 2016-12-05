package com.ys.yoosir.zzshow.events;

/**
 * @version 1.0
 * @version yoosir
 * Created by Administrator on 2016/12/2 0002.
 */
public class ChannelChangeEvent {

    private String channelName = null;
    private boolean isChannelChanged = false;

    public ChannelChangeEvent(String channelName,boolean isChannelChanged) {
        this.channelName = channelName;
        this.isChannelChanged = isChannelChanged;
    }

    public String getChannelName() {
        return channelName;
    }

    public boolean isChannelChanged() {
        return isChannelChanged;
    }
}
