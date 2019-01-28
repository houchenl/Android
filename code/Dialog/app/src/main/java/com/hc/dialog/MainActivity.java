package com.hc.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        final Dialog dialog = new Dialog(this, R.style.Dialog_Translucent_Background);
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_request_uvc_permission_fail, null);
//        dialog.setContentView(view);
//
//        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();

//        final Dialog dialog = new Dialog(this, R.style.Dialog_Translucent_Background);
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_upload_image_way, null);
//        dialog.setContentView(view);
//
//        dialog.show();
    }

    public void showDialog(View view) {
        final AlertDialog alertDialog = DialogUtil.showChangeVoice(this, "切换到语音问诊", "我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.cancel();
                }
            }
        }, 5000);
    }

}
