package com.ys.yoosir.zzshow.mvp.modle.toutiao;

import java.util.ArrayList;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class ArticleData {

    private String      keyword;             //描述
    private boolean     has_video;           //视频
    private String      title;               //标题
    private String        datetime;            //时间
    private int         gallary_image_count; //图片数量
    private ArrayList<ArticleImageItem> image_list; //图片集合

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getGallary_image_count() {
        return gallary_image_count;
    }

    public void setGallary_image_count(int gallary_image_count) {
        this.gallary_image_count = gallary_image_count;
    }

    public ArrayList<ArticleImageItem> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<ArticleImageItem> image_list) {
        this.image_list = image_list;
    }

    @Override
    public String toString() {
        return "ArticleData{" +
                "keyword='" + keyword + '\'' +
                ", has_video=" + has_video +
                ", title='" + title + '\'' +
                ", datetime=" + datetime +
                ", gallary_image_count=" + gallary_image_count +
                '}';
    }
}
