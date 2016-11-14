package com.ys.yoosir.zzshow.db;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.apis.common.ApiConstants;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public class NewsChannelTableManager {

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
     *  加载 用户已选中的频道
     * @return 已被选择的频道
     */
    public static List<NewsChannelTable> loadNewsChannelsMine(){
        return DBManager.init(MyApplication.getInstance()).loadNewsChannelsMine();
    }

}
