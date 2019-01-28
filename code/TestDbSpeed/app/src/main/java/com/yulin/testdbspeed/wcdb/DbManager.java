package com.yulin.testdbspeed.wcdb;

import android.content.Context;
import android.util.Log;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.yulin.testdbspeed.Constant;

public class DbManager {

    private static final String TAG = "DbManager";

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public DbManager(Context context) {
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * 开启插入事务 _id/name/address/age/number
     * */
    public void addPersonData(PlainPerson person) {
        try {
            // 开启事务
            mDb.beginTransaction();

            // 执行插入事务
            final String sql = "INSERT INTO person VALUES(NULL,?,?,?,?)";
            Object[] objects = new Object[]{person.getName(), person.getAddress(), person.getAge(), person.getNumber()};
            mDb.execSQL(sql, objects);
//            Log.d(TAG, "addPersonData: " + person.getNumber());

            // 标志事务执行成功
            mDb.setTransactionSuccessful();
        } finally {
            // 结束事务
            mDb.endTransaction();
        }
    }

    /**
     * 连续插入
     * */
    public long addPersonDatas() {
        long start = System.currentTimeMillis();

        try {
            // 开启事务
            mDb.beginTransaction();

            // 执行插入事务
            final String sql = "INSERT INTO person VALUES(NULL,?,?,?,?)";

            for (int i = 1; i <= Constant.TIMES; i++) {
                long currentTime = System.currentTimeMillis();
                String a = String.valueOf(currentTime % 10);
                int b = (int) (currentTime % 100);
                String c = String.valueOf(currentTime % 10000);

                PlainPerson person = new PlainPerson();
                person.setName(a);
                person.setAddress(c);
                person.setAge(b);
                person.setNumber(i);

                Object[] objects = new Object[]{person.getName(), person.getAddress(), person.getAge(), person.getNumber()};
                mDb.execSQL(sql, objects);
//                Log.d(TAG, "addPersonData: " + person.getNumber());
            }

            // 标志事务执行成功
            mDb.setTransactionSuccessful();
        } finally {
            // 结束事务
            mDb.endTransaction();
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        Log.d(TAG, "addPersonDatas: spend " + spend + " ms.");
        return spend;
    }

    /**
     * 删除一条记录
     * */
    public void deletePersonData(int number) {
        try {
            mDb.beginTransaction();

            final String sql = "DELETE FROM person WHERE number = ?";
            Object[] objects = new Object[]{number};
            mDb.execSQL(sql, objects);
//            Log.d(TAG, "deletePersonData: " + number);

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
    }

    /**
     * 连续删除1万条记录
     * */
    public long deletePersonDatas() {
        long start = System.currentTimeMillis();

        try {
            mDb.beginTransaction();

            final String sql = "DELETE FROM person WHERE number = ?";
            for (int i = 1; i <= Constant.TIMES; i++) {
                Object[] objects = new Object[]{i};
                mDb.execSQL(sql, objects);
//                Log.d(TAG, "deletePersonData: " + i);
            }

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }

        long end = System.currentTimeMillis();
        long spend = end - start;
        Log.d(TAG, "deletePersonDatas: spend " + spend + " ms.");
        return spend;
    }

}
