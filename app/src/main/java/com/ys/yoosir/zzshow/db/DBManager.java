package com.ys.yoosir.zzshow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.mvp.modle.netease.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public class DBManager {

    private static DBManager mDBManager;
    private final SQLiteDatabase db;

    public static DBManager init(Context context){
        if(mDBManager == null){
            mDBManager = new DBManager(context);
        }
        return mDBManager;
    }

    private DBManager(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 向数据库添加频道信息
     * @param channelTable 需要添加的频道信息
     * @return  当前的行数id
     */
    public long addNewsChannel(NewsChannelTable channelTable){
        long rowId = -1;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("news_channel_name", channelTable.getNewsChannelName());
            values.put("news_channel_id", channelTable.getNewsChannelId());
            values.put("news_channel_type", channelTable.getNewsChannelType());
            values.put("news_channel_select", channelTable.isNewsChannelSelect() ? 1:0);
            values.put("news_channel_index", channelTable.getNewsChannelIndex());
            values.put("news_channel_fixed", channelTable.isNewsChannelFixed()? 1:0);
            rowId = db.insert(DBHelper.TABLE_NEWS_CHANNEL, null, values);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        return rowId;
    }

    public List<NewsChannelTable> loadNewsChannels(String isSelect){
        List<NewsChannelTable> list = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = null;
        try{
            cursor = db.query(DBHelper.TABLE_NEWS_CHANNEL,null,"news_channel_select = ? ",new String[]{isSelect},null,null,"news_channel_index asc");
            if(cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    String channelName = cursor.getString(cursor.getColumnIndex("news_channel_name"));
                    String channelId   = cursor.getString(cursor.getColumnIndex("news_channel_id"));
                    String channelType = cursor.getString(cursor.getColumnIndex("news_channel_type"));
                    boolean channelSelect = cursor.getInt(cursor.getColumnIndex("news_channel_select")) == 1;
                    int channelIndex  = cursor.getInt(cursor.getColumnIndex("news_channel_index"));
                    boolean channelFixed = cursor.getInt(cursor.getColumnIndex("news_channel_fixed")) == 1;
                    list.add(new NewsChannelTable(channelName,channelId,channelType,channelSelect,channelIndex,channelFixed));
                }
            }
        }catch (Exception e){
            /**
             *  exception log
             */
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return list;
    }

}
