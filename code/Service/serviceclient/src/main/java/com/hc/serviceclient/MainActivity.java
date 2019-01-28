package com.hc.serviceclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hc.service.aidl.IMyAidlInterface;
import com.hc.service.aidl.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "aidl_service";

    private IMyAidlInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.hc.service", "com.hc.service.aidl.PayService"));
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        try {
            unbindService(conn);
        } catch (Exception e) {
            Log.d(TAG, "onDestroy: " + e.getMessage());
        }
    }

    public void pay(View view) throws RemoteException {
        if (service != null) {
            service.pay(100);
        }
    }

    public void add(View view) {
        if (service != null) {
            try {
                List<Person> persons = service.add(new Person("jack", 21));
                Log.d(TAG, "add: " + persons.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected: ");
            service = IMyAidlInterface.Stub.asInterface(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            service = null;
        }
    };

}
