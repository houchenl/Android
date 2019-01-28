package com.yulin.wcdb;

import android.content.Context;
import android.util.Log;

import com.tencent.wcdb.DatabaseUtils;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

import java.io.File;

public class EncryptDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "EncryptDbHelper";

    private final static String ENCRYPT_NAME = "encrypt.db";
    private final static String PLAIN_NAME = "plain.db";
    private final static int VERSION = 2;
    private Context mContext;

    /**
     * 创建加密数据库
     * */
    public EncryptDbHelper(Context context, String password) {
        super(context, ENCRYPT_NAME, password.getBytes(), null, VERSION, null);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        File plainFile = mContext.getDatabasePath(PLAIN_NAME);

        // 判断旧的数据库文件是否存在
        if (plainFile.exists()) {
            move(plainFile, db);
        } else {
            final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20), address TEXT)";
            db.execSQL(SQL_CREATE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: oldVersion " + oldVersion + " ------> newVersion " + newVersion);
    }

    private void move(File file, SQLiteDatabase db) {
        db.endTransaction();

        final String sql = String.format("ATTACH DATABASE %s AS old KEY '';"
                , DatabaseUtils.sqlEscapeString(file.getPath()));
        db.execSQL(sql);

        db.beginTransaction();
        DatabaseUtils.stringForQuery(db, "SELECT sqlcipher_export('main', 'old');", null);
        db.setTransactionSuccessful();
        db.endTransaction();

        int oldVersion = (int) DatabaseUtils.longForQuery(db, "PRAGMA old.user_version;", null);
        db.execSQL("DETACH DATABASE old;");

        if (file.delete()) {
            Log.d(TAG, "旧数据库删除成功");
        }

        db.beginTransaction();

        if (oldVersion > VERSION) {
            onDowngrade(db, oldVersion, VERSION);
        } else if (oldVersion < VERSION) {
            onUpgrade(db, oldVersion, VERSION);
        }
    }

    // 删除数据库 db 文件
    public boolean onDelete() {
        File file = mContext.getDatabasePath(ENCRYPT_NAME);
        return SQLiteDatabase.deleteDatabase(file);
    }

}
