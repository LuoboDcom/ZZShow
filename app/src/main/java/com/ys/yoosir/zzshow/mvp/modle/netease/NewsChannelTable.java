package com.ys.yoosir.zzshow.mvp.modle.netease;

/**
 * @version 1.0
 * @author Yoosir
 * Created by Administrator on 2016/11/14 0014.
 */
public class NewsChannelTable {

    private String newsChannelName;     //频道名称
    private String newsChannelId;       //频道ID
    private String newsChannelType;     //频道类型
    private boolean newsChannelSelect;  //频道是否选中
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
}
