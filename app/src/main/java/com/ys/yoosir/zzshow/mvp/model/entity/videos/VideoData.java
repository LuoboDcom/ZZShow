package com.ys.yoosir.zzshow.mvp.model.entity.videos;

/**
 * 视频数据结构体
 *
 * @author yoosir
 *         Created by Administrator on 2016/11/24.
 * @version 1.0
 */

public class VideoData {

    /**
     * "topicImg":"http://vimg3.ws.126.net/image/snapshot/2016/11/A/D/VC5K80AAD.jpg",
     * "videosource":"新媒体",
     * "mp4Hd_url":"http://flv2.bn.netease.com/videolib3/1611/24/BCcfR1400/HD/BCcfR1400-mobile.mp4",
     * "topicDesc":"个人化妆品见解，，打击三无，国家化妆品高级配方师。告诉你各种美容护肤小窍门、皮肤保养秘诀",
     * "topicSid":"VC5K80AA2",
     * "cover":"http://vimg2.ws.126.net/image/snapshot/2016/11/7/A/VC5O5977A.jpg",
     * "title":"万能卷发棒轻松打造慵懒卷发",
     * "playCount":0,
     * "replyBoard":"video_bbs",
     * "videoTopic":
     * {
     * "alias":"个人化妆品见解，，打击三无",
     * "tname":"美分钟",
     * "ename":"T1479792933608",
     * "tid":"T1479792933608"
     * }
     * ,
     * "sectiontitle":"",
     * "replyid":"C5O0IE1A008535RB",
     * "description":"",
     * "mp4_url":"http://flv2.bn.netease.com/videolib3/1611/24/BCcfR1400/SD/BCcfR1400-mobile.mp4",
     * "length":572,
     * "playersize":1,
     * "m3u8Hd_url":"http://flv2.bn.netease.com/videolib3/1611/24/BCcfR1400/HD/movie_index.m3u8",
     * "vid":"VC5O0IE1A",
     * "m3u8_url":"http://flv2.bn.netease.com/videolib3/1611/24/BCcfR1400/SD/movie_index.m3u8",
     * "ptime":"2016-11-24 22:09:13",
     * "topicName":"美分钟"
     **/
    private String topicImg;
    private String videosource;
    private String mp4Hd_url;
    private String topicDesc;
    private String topicSid;
    private String cover;
    private String title;
    private int playCount;
    private String replyBoard;
    private String sectiontitle;
    private String replyid;
    private String description;
    private String mp4_url;
    private long length;
    private int playersize;
    private String m3u8Hd_url;
    private String vid;
    private String m3u8_url;
    private String ptime;
    private String topicName;
    private VideoTopicBean videoTopic;

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getPlayersize() {
        return playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public String getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public VideoTopicBean getVideoTopic() {
        return videoTopic;
    }

    public void setVideoTopic(VideoTopicBean videoTopic) {
        this.videoTopic = videoTopic;
    }

    public class VideoTopicBean {
        private String alias;
        private String tname;
        private String ename;
        private String tid;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
