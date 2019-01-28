package com.hc.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houchen on 2016/6/11.
 * this service will be started in another app.
 */
public class PayService extends Service {

    private static final String TAG = "aidl_service";

    private ArrayList<Person> persons;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        persons = new ArrayList<>();
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private IBinder myBinder = new IMyAidlInterface.Stub() {
        @Override
        public void pay(int amount) throws RemoteException {
            Log.d(TAG, "pay: " + amount);
        }

        @Override
        public List<Person> add(Person person) throws RemoteException {
            Log.d(TAG, "add: " + person.toString());
            persons.add(person);
            return persons;
        }
    };

}
