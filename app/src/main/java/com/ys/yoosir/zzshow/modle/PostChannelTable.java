package com.ys.yoosir.zzshow.modle;

/**
 *  帖子频道
 * Created by Yoosir on 2016/10/19 0019.
 */
public class PostChannelTable {

    private int post_channel_id;
    private String name;

    public PostChannelTable(int post_channel_id, String name) {
        this.post_channel_id = post_channel_id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PostChannelTable{" +
                "post_channel_id=" + post_channel_id +
                ", name='" + name + '\'' +
                '}';
    }
}
