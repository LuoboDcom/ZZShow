package com.ys.yoosir.zzshow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public class DBManager {

    private static DBManager mDBManager;
    private static volatile SQLiteDatabase db;

    public static DBManager init(Context context){
        if(mDBManager == null){
            mDBManager = new DBManager(context);
        }
        return mDBManager;
    }

    public static void closeDB(){
        if(db != null){
            db.close();
        }
    }

    private DBManager(Context context){
        if(db == null){
            synchronized (DBManager.class){
                if(db == null){
                    db = new DBHelper(context).getWritableDatabase();
                }
            }
        }
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
                while (!cursor.isAfterLast()) {
                    String channelName = cursor.getString(cursor.getColumnIndex("news_channel_name"));
                    String channelId   = cursor.getString(cursor.getColumnIndex("news_channel_id"));
                    String channelType = cursor.getString(cursor.getColumnIndex("news_channel_type"));
                    boolean channelSelect = cursor.getInt(cursor.getColumnIndex("news_channel_select")) == 1;
                    int channelIndex  = cursor.getInt(cursor.getColumnIndex("news_channel_index"));
                    boolean channelFixed = cursor.getInt(cursor.getColumnIndex("news_channel_fixed")) == 1;
                    NewsChannelTable channelTable = new NewsChannelTable(channelName,channelId,channelType,channelSelect,channelIndex,channelFixed);
                    KLog.d("FIND--","loadNewsChannels--"+channelTable.toString());
                    list.add(channelTable);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            /**
             *  exception log
             */
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }
        return list;
    }

    /**
     * 修改
     * @param channelTable 对象
     */
    public int update (NewsChannelTable channelTable){
        db.beginTransaction();
        int index = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("news_channel_name", channelTable.getNewsChannelName());
            values.put("news_channel_id", channelTable.getNewsChannelId());
            values.put("news_channel_type", channelTable.getNewsChannelType());
            values.put("news_channel_select", channelTable.isNewsChannelSelect() ? 1:0);
            values.put("news_channel_index", channelTable.getNewsChannelIndex());
            values.put("news_channel_fixed", channelTable.isNewsChannelFixed()? 1:0);
            index = db.update(DBHelper.TABLE_NEWS_CHANNEL,values,"news_channel_id = ?",new String[]{channelTable.getNewsChannelId()});
            db.setTransactionSuccessful();
        }catch (Exception e){
            KLog.e(e.getMessage());
        }finally {
            db.endTransaction();
        }
        return index;
    }

    /**
     *  根据 条件查询  频道列表
     * @param selection     where 语句
     * @param selectionArgs  值
     * @return  返回数据
     */
    public List<NewsChannelTable> loadNewsChannelsByWhere(String selection, String[] selectionArgs) {
        List<NewsChannelTable> list = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = null;
        try{
            cursor = db.query(DBHelper.TABLE_NEWS_CHANNEL,null, selection ,selectionArgs ,null,null,"news_channel_index asc");
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String channelName = cursor.getString(cursor.getColumnIndex("news_channel_name"));
                    String channelId   = cursor.getString(cursor.getColumnIndex("news_channel_id"));
                    String channelType = cursor.getString(cursor.getColumnIndex("news_channel_type"));
                    boolean channelSelect = cursor.getInt(cursor.getColumnIndex("news_channel_select")) == 1;
                    int channelIndex  = cursor.getInt(cursor.getColumnIndex("news_channel_index"));
                    boolean channelFixed = cursor.getInt(cursor.getColumnIndex("news_channel_fixed")) == 1;
                    NewsChannelTable channelTable = new NewsChannelTable(channelName,channelId,channelType,channelSelect,channelIndex,channelFixed);
                    KLog.d("FIND--","loadNewsChannelsByWhere--"+channelTable.toString());
                    list.add(channelTable);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }
        return list;
    }

    /**
     *  获取 记录条数
     * @return 记录条数
     */
    public long getCount(){
        db.beginTransaction();
        long count = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select count(news_channel_id) from " + DBHelper.TABLE_NEWS_CHANNEL,null);
            cursor.moveToFirst();
            count = cursor.getLong(0);
            db.setTransactionSuccessful();
        }catch (Exception e){
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null) cursor.close();
            db.endTransaction();
        }
        return count;
    }


    /**
     * 根据条件查询记录条数
     *
     * @param selection    条件
     * @param selectionArgs  值
     * @return
     */
    public long getCountByWhere(String selection,String[] selectionArgs){
        db.beginTransaction();
        long count = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select count(news_channel_id) from " + DBHelper.TABLE_NEWS_CHANNEL + " where " + selection,selectionArgs);
            cursor.moveToFirst();
            count = cursor.getLong(0);
            db.setTransactionSuccessful();
        }catch (Exception e){
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null) cursor.close();
            db.endTransaction();
        }
        return count;
    }

    /**
     *  根据 index 查询频道
     * @param index  频道值
     * @return
     */
    public NewsChannelTable findNewsChannelByIndex(int index) {
        NewsChannelTable newsChannel = null;
        db.beginTransaction();
        Cursor cursor = null;
        try{
            cursor = db.query(DBHelper.TABLE_NEWS_CHANNEL,null, "news_channel_index = ? " ,new String[]{index+""} ,null,null,null);
            if(cursor.moveToFirst()) {
                String channelName = cursor.getString(cursor.getColumnIndex("news_channel_name"));
                String channelId = cursor.getString(cursor.getColumnIndex("news_channel_id"));
                String channelType = cursor.getString(cursor.getColumnIndex("news_channel_type"));
                boolean channelSelect = cursor.getInt(cursor.getColumnIndex("news_channel_select")) == 1;
                int channelIndex = cursor.getInt(cursor.getColumnIndex("news_channel_index"));
                boolean channelFixed = cursor.getInt(cursor.getColumnIndex("news_channel_fixed")) == 1;
                newsChannel = new NewsChannelTable(channelName, channelId, channelType, channelSelect, channelIndex, channelFixed);
                KLog.d("FIND--", "findNewsChannelByIndex--" + newsChannel.toString());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            KLog.e(e.getMessage());
        }finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }
        return newsChannel;
    }
}
