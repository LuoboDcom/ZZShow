package com.ys.yoosir.zzshow.mvp.modle.toutiao;

/**
 *  视频 实体类
 * Created by Yoosir on 2016/10/24 0024.
 */
public class VideoData {

    private String image_url;
    private String media_avatar_url;
    private String media_url;
    private String title;
    private int comments_count;
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

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
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
                ", comments_count=" + comments_count +
                ", video_duration_str='" + video_duration_str + '\'' +
                '}';
    }
}
