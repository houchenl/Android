package com.yulin.wcdb;

import android.content.Context;

import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EncryptDbManager {

    private Context mContext;
    private String mPassword;

    private EncryptDbHelper mHelper;
    private SQLiteDatabase mDb;

    public EncryptDbManager(Context context, String password) {
        this.mContext = context;
        this.mPassword = password;
    }

    /**
     * 内部实现了数据库的迁移
     * */
    public boolean init() {
        try {
            mHelper = new EncryptDbHelper(mContext, mPassword);
            mDb = mHelper.getWritableDatabase();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<PlainPerson> getPersonListData() {
        List<PlainPerson> persons = new ArrayList<>();
        Cursor c = getAllPersonInfo();

        while (c.moveToNext()) {
            PlainPerson person = new PlainPerson();
            person.setName(c.getString(c.getColumnIndex("name")));
            person.setAddress(c.getString(c.getColumnIndex("address")));
            persons.add(person);
        }

        c.close();
        return persons;
    }

    public Cursor getAllPersonInfo() {
        return mDb.rawQuery("SELECT * FROM person;", null);
    }

    public void closeDb() {
        mDb.close();
    }

    public boolean deleteDatabase() {
        return mHelper.onDelete();
    }

}
