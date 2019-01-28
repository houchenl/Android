package com.yulin.testdbspeed;

import android.app.Application;

import com.yulin.testdbspeed.greendao.DaoMaster;
import com.yulin.testdbspeed.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "green.db");
        Database db = helper.getWritableDb();

        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

}
