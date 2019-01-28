package com.yulin.wcdb;

import android.content.Context;

import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据操作管理类
 * 进行增删改查操作
 * */
public class PlainDbManager {

    private PlainDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public PlainDbManager(Context context) {
        mDbHelper = new PlainDbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * 开启插入事务
     * */
    public void addPersonData(PlainPerson person) {
        try {
            // 开启事务
            mDb.beginTransaction();

            // 执行插入事务
            final String sql = "INSERT INTO person VALUES(NULL,?,?)";
            Object[] objects = new Object[]{person.getName(), person.getAddress()};
            mDb.execSQL(sql, objects);

            // 标志事务执行成功
            mDb.setTransactionSuccessful();
        } finally {
            // 结束事务
            mDb.endTransaction();
        }
    }

    public boolean addPersonList(List<PlainPerson> persons) {
        boolean result = true;

        try {
            // 开启事务
            mDb.beginTransaction();

            // 执行插入语句
            final String sql = "INSERT INTO person VALUES(NULL,?,?)";
            for (PlainPerson person : persons) {
                Object[] objects = new Object[]{person.getName(), person.getAddress()};
                mDb.execSQL(sql, objects);
            }

            // 设置事务完成成功
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            result = false;
        } finally {
            // 关闭事务
            mDb.endTransaction();
        }

        return result;
    }

    /**
     * 获取所有person记录
     * */
    public Cursor getAllPersonRecord() {
        return mDb.rawQuery("SELECT * FROM person", null);
    }

    /**
     * 获取数据中所有person并放在集合中
     * */
    public List<PlainPerson> getPersonListData() {
        List<PlainPerson> persons = new ArrayList<>();
        Cursor cursor = getAllPersonRecord();

        while (cursor.moveToNext()) {
            PlainPerson person = new PlainPerson();
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            persons.add(person);
        }

        cursor.close();

        return persons;
    }

}
