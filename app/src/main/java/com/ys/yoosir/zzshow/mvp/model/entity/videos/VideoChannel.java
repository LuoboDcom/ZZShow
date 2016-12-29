package com.ys.yoosir.zzshow.mvp.model.entity.videos;

/**
 *  视频频道
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/26.
 */

public class VideoChannel {

    private String videoChannelName;
    private String videoChannelID;

    public String getVideoChannelName() {
        return videoChannelName;
    }

    public void setVideoChannelName(String videoChannelName) {
        this.videoChannelName = videoChannelName;
    }

    public String getVideoChannelID() {
        return videoChannelID;
    }

    public void setVideoChannelID(String videoChannelID) {
        this.videoChannelID = videoChannelID;
    }

    public VideoChannel(String videoChannelName, String videoChannelID) {
        this.videoChannelName = videoChannelName;
        this.videoChannelID = videoChannelID;
    }
}
