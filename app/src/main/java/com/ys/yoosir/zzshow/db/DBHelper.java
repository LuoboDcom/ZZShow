package com.ys.yoosir.zzshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/14 0014.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "zzshow.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NEWS_CHANNEL = "news_channel_table";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableNewsChannel(db);
    }

    private void createTableNewsChannel(SQLiteDatabase db){
        String sql = "create table if not exists " + TABLE_NEWS_CHANNEL
                + " ( "
                + "_id Integer primary key autoincrement,"
                + "news_channel_name String,"
                + "news_channel_id String,"
                + "news_channel_type String,"
                + "news_channel_select Integer,"
                + "news_channel_index Integer,"
                + "news_channel_fixed Integer"
                + " );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
