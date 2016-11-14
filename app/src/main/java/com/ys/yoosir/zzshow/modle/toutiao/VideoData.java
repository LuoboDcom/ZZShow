package com.ys.yoosir.zzshow.modle.toutiao;

/**
 *
 *  视频 实体类
 * Created by Yoosir on 2016/10/23.
 */

public class VideoData {

    private String image_url;
    private String media_avatar_url;
    private String media_url;
    private String title;
    private String video_duration_str;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMedia_avatar_url() {
        return media_avatar_url;
    }

    public void setMedia_avatar_url(String media_avatar_url) {
        this.media_avatar_url = media_avatar_url;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_duration_str() {
        return video_duration_str;
    }

    public void setVideo_duration_str(String video_duration_str) {
        this.video_duration_str = video_duration_str;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "image_url='" + image_url + '\'' +
                ", media_avatar_url='" + media_avatar_url + '\'' +
                ", media_url='" + media_url + '\'' +
                ", title='" + title + '\'' +
                ", video_duration_str='" + video_duration_str + '\'' +
                '}';
    }
}
