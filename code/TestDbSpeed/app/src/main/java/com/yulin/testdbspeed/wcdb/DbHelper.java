package com.yulin.testdbspeed.wcdb;

import android.content.Context;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

import java.io.File;

public class DbHelper extends SQLiteOpenHelper {

    // 数据库 db 文件名称
    private static final String DEFAULT_NAME = "plain.db";

    // 默认版本号
    private static final int DEFAULT_VERSION = 1;

    private Context mContext;

    /**
     * 通过父类构造方法创建 plain 数据库
     * */
    public DbHelper(Context context) {
        super(context, DEFAULT_NAME, null, DEFAULT_VERSION, null);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR(20) , address TEXT, age INTEGER, number INTEGER)";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 删除数据库 db 文件
     */
    public boolean onDelete() {
        File file = mContext.getDatabasePath(DEFAULT_NAME);
        return SQLiteDatabase.deleteDatabase(file);
    }

}
