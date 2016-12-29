package com.ys.yoosir.zzshow.repository.localdb;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.common.ApiConstants;
import com.ys.yoosir.zzshow.db.DBManager;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public class NewsChannelTableManager2 {

    /**
     *  首次打开程序初始化db
     */
    public static void initDB(){
        if(!SharedPreferencesUtil.isInitDB()){
            String[] channelNames = MyApplication.getInstance().getResources().getStringArray(R.array.news_channel_name);
            String[] channelIds = MyApplication.getInstance().getResources().getStringArray(R.array.news_channel_id);
            for (int i = 0; i < channelNames.length; i++) {
                NewsChannelTable entity = new NewsChannelTable(
                    channelNames[i],
                        channelIds[i],
                        ApiConstants.getType(channelIds[i]),i <= 7,i,i == 0);
                DBManager.init(MyApplication.getInstance()).addNewsChannel(entity);
            }
            SharedPreferencesUtil.updateInitDBValue(true);
        }
    }

    /**
     *  查询 index > ?  频道
     * @param index
     */
    public static List<NewsChannelTable> loadNewsChannelsIndexGt(int index){
        return DBManager.init(MyApplication.getInstance()).loadNewsChannelsByWhere(" news_channel_index > ? ",new String[]{index+""});
    }



    /**
     * 修改频道
     * @param channelTable
     */
    public static void update(NewsChannelTable channelTable) {
        DBManager.init(MyApplication.getInstance()).update(channelTable);
    }

    /**
     *  加载 用户已选中的频道
     * @return 已被选择的频道
     */
    public static List<NewsChannelTable> loadNewsChannelsMine(){
        return DBManager.init(MyApplication.getInstance()).loadNewsChannels("1");
    }

    /**
     *  加载 推荐频道
     * @return 推荐频道列表
     */
    public static List<NewsChannelTable> loadNewsChannelsRecommend(){
        return DBManager.init(MyApplication.getInstance()).loadNewsChannels("0");
    }

    public static int getCount(){
        return (int) DBManager.init(MyApplication.getInstance()).getCount();
    }

    /**
     *  index < channelIndex ，且 未被选中的
     * @param channelIndex
     * @return
     */
    public static List<NewsChannelTable> loadNewsChannelsIndexLtAndIsUnselect(int channelIndex) {
        return DBManager.init(MyApplication.getInstance()).loadNewsChannelsByWhere("news_channel_index < ? and news_channel_select = ? ",new String[]{channelIndex+"","0"});
    }

    /**
     *  查询被选中的频道数量
     * @return
     */
    public static int getNewsChannelSelectSize() {
        return (int) DBManager.init(MyApplication.getInstance()).getCountByWhere(" news_channel_select = ? ",new String[]{"1"});
    }

    public static NewsChannelTable loadNewsChannel(int index) {
        return DBManager.init(MyApplication.getInstance()).findNewsChannelByIndex(index);
    }

    public static List<NewsChannelTable> loadNewsChannelsWithin(int from, int to) {
        return DBManager.init(MyApplication.getInstance())
                .loadNewsChannelsByWhere("news_channel_index BETWEEN ? AND ? ",new String[]{from+"",to+""});
    }
}
