package com.hc.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAB_USB = "VideoOnlineFragmentUsb";

    private UsbManager mUsbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

//        printUsbInfo();
        registerUsbReceiver();

        final int cameraNumber = getCameraNumber();
        Log.d(TAB_USB, "onCreate: cameraNumber is " + cameraNumber);
        Toast.makeText(this, "onCreate " + cameraNumber, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterUsbReceiver();
    }

    private void printUsbInfo() {
        final HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

        if (deviceList != null) {
            final Set<String> keys = deviceList.keySet();
            if (keys.size() > 0) {
                for (final String key : keys) {
                    final UsbDevice device = deviceList.get(key);

                    Log.i(TAG, "key=" + key + ":" + device + ":" + device.toString());
                }
            } else {
                Log.i(TAG, "no usb device");
            }
        } else {
            Log.i(TAG, "no usb device");
        }
    }

    private void registerUsbReceiver() {
        Log.d(TAB_USB, "registerUsbReceiver: ");
        final IntentFilter filter = new IntentFilter();
        // ACTION_USB_DEVICE_ATTACHED never comes on some devices so it should not be added here
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);
    }

    private void unregisterUsbReceiver() {
        Log.d(TAB_USB, "unregisterUsbReceiver: ");
        unregisterReceiver(mUsbReceiver);
    }

    /**
     * 监听USB插入拔出事件
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAB_USB, "mUsbReceiver action: " + action);
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                final UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                processAttach(device);
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                final UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    processDetach(device);
                }
            }
        }
    };

    private void processAttach(final UsbDevice device) {
        Log.d(TAB_USB, "processAttach: hashCode " + device.hashCode());

        final int cameraNumber = getCameraNumber();
        Log.d(TAB_USB, "processAttach: cameraNumber is " + cameraNumber);
        Toast.makeText(this, "attach " + cameraNumber, Toast.LENGTH_SHORT).show();

//        printUsbDeviceInfo(device);
    }

    private void processDetach(final UsbDevice device) {
        Log.d(TAB_USB, "processDetach: hashCode " + device.hashCode());

        final int cameraNumber = getCameraNumber();
        Log.d(TAB_USB, "processDetach: cameraNumber is " + cameraNumber);
        Toast.makeText(this, "detach " + cameraNumber, Toast.LENGTH_SHORT).show();

//        printUsbDeviceInfo(device);
    }

    private int getCameraNumber() {
        return Camera.getNumberOfCameras();
    }

    private void printUsbDeviceInfo(final UsbDevice device) {
        Log.d(TAB_USB, "printUsbDeviceInfo: " + device);
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("deviceName: " + device.getDeviceName()).append(", ")
//                .append("ProductName: " + device.getProductName()).append(", ")
//                .append("serialNumber: " + device.getSerialNumber()).append(", ")
//                .append("deviceId: " + device.getDeviceId()).append(", ")
//                .append("vendorId: " + device.getVendorId()).append(", ")
//                .append("productId: " + device.getProductId()).append(", ")
//                .append("deviceClass: " + device.getDeviceClass()).append(", ")
//                .append("deviceSubClass: " + device.getDeviceSubclass()).append(", ")
//                .append("deviceProtocol: " + device.getDeviceProtocol()).append(", ")
//                .append("ManufacturerName: " + device.getManufacturerName());
//        String deviceInfo = buffer.toString();
//        Log.d(TAB_USB, "print Usb device Info: " + deviceInfo);
    }

}
