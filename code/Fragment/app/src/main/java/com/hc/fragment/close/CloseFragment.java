package com.hc.fragment.close;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hc.fragment.R;

/**
 * Created by liu_lei on 2017/11/29.
 *
 */

public class CloseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "houchen-CloseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_close, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        view.findViewById(R.id.btn_close).setOnClickListener(this);
        view.findViewById(R.id.btn_close_delay).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private Handler mHandler = new Handler();

    @Override
    public void onClick(View v) {
//        getActivity().getSupportFragmentManager().popBackStack();
        if (v.getId() == R.id.btn_close) {
            finish();
        } else if (v.getId() == R.id.btn_close_delay) {
            new MyTask(new OnClickInterface() {
                @Override
                public void onSuccess() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "关闭", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });


                }
            }).start();
        }
    }

    private void finish() {
        getFragmentManager().beginTransaction().remove(CloseFragment.this).commitAllowingStateLoss();
    }

    /**
     * 唯一的栈标记
     */
    public String getStackKey() {
        return String.valueOf(hashCode());
    }

    private interface OnClickInterface {
        void onSuccess();
    }

    private class MyTask extends Thread {
        private OnClickInterface mClickInterface;

        public MyTask(OnClickInterface clickInterface) {
            mClickInterface = clickInterface;
        }

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(1500);
                mClickInterface.onSuccess();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
