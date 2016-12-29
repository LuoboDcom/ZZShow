package com.ys.yoosir.zzshow.mvp.model.entity.netease;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @version 1.0
 * @author Yoosir
 * Created by Administrator on 2016/11/14 0014.
 */
@Entity(nameInDb = "NEWS_CHANNEL_TABLE")
public class NewsChannelTable {

    @Id
    private Long _id;

    @NotNull
    private String newsChannelName;     //频道名称
    @NotNull
    private String newsChannelId;       //频道ID
    @NotNull
    private String newsChannelType;     //频道类型
    private boolean newsChannelSelect;  //频道是否选中
    @NotNull
    private int newsChannelIndex;       //频道排序的位置
    private boolean newsChannelFixed;   //频道是否固定

    public NewsChannelTable(){

    }

    public NewsChannelTable(String newsChannelName, String newsChannelId, String newsChannelType, boolean newsChannelSelect, int newsChannelIndex, boolean newsChannelFixed) {
        this.newsChannelName = newsChannelName;
        this.newsChannelId = newsChannelId;
        this.newsChannelType = newsChannelType;
        this.newsChannelSelect = newsChannelSelect;
        this.newsChannelIndex = newsChannelIndex;
        this.newsChannelFixed = newsChannelFixed;
    }

    @Generated(hash = 142064416)
    public NewsChannelTable(Long _id, @NotNull String newsChannelName, @NotNull String newsChannelId, @NotNull String newsChannelType, boolean newsChannelSelect,
            int newsChannelIndex, boolean newsChannelFixed) {
        this._id = _id;
        this.newsChannelName = newsChannelName;
        this.newsChannelId = newsChannelId;
        this.newsChannelType = newsChannelType;
        this.newsChannelSelect = newsChannelSelect;
        this.newsChannelIndex = newsChannelIndex;
        this.newsChannelFixed = newsChannelFixed;
    }

    public String getNewsChannelName() {
        return newsChannelName;
    }

    public void setNewsChannelName(String newsChannelName) {
        this.newsChannelName = newsChannelName;
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public String getNewsChannelType() {
        return newsChannelType;
    }

    public void setNewsChannelType(String newsChannelType) {
        this.newsChannelType = newsChannelType;
    }

    public boolean isNewsChannelSelect() {
        return newsChannelSelect;
    }

    public void setNewsChannelSelect(boolean newsChannelSelect) {
        this.newsChannelSelect = newsChannelSelect;
    }

    public int getNewsChannelIndex() {
        return newsChannelIndex;
    }

    public void setNewsChannelIndex(int newsChannelIndex) {
        this.newsChannelIndex = newsChannelIndex;
    }

    public boolean isNewsChannelFixed() {
        return newsChannelFixed;
    }

    public void setNewsChannelFixed(boolean newsChannelFixed) {
        this.newsChannelFixed = newsChannelFixed;
    }

    @Override
    public String toString() {
        return "NewsChannelTable{" +
                "newsChannelName='" + newsChannelName + '\'' +
                ", newsChannelId='" + newsChannelId + '\'' +
                ", newsChannelType='" + newsChannelType + '\'' +
                ", newsChannelSelect=" + newsChannelSelect +
                ", newsChannelIndex=" + newsChannelIndex +
                ", newsChannelFixed=" + newsChannelFixed +
                '}';
    }

    public boolean getNewsChannelSelect() {
        return this.newsChannelSelect;
    }

    public boolean getNewsChannelFixed() {
        return this.newsChannelFixed;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
