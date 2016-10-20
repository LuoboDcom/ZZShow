package com.ys.yoosir.zzshow.modle.toutiao;

/**
 *  图片集合
 *    key : image_list
 * Created by Yoosir on 2016/10/20 0020.
 */
public class ArticleImageItem {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ArticleImageItem{" +
                "url='" + url + '\'' +
                '}';
    }
}
