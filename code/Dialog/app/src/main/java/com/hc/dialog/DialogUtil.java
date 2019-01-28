package com.hc.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by houchen on 2018/2/9.
 */

public class DialogUtil {

    /**
     * 视频过程中 网络差 弹出切换成语音模式 提示框
     *
     * @param context
     * @param content
     * @param submit
     * @param listener
     * @return
     */
    public static AlertDialog showChangeVoice(Context context, String content, String submit, final View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatDialogStyle);
        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();
        Window window = alert.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setContentView(R.layout.mtc_base_dialog_change_voice);
        TextView tvContent = (TextView) alert.findViewById(R.id.tv_content);
        TextView tvClick = (TextView) alert.findViewById(R.id.tv_click);
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
        if (!TextUtils.isEmpty(submit)) {
            tvClick.setText(submit);
        }
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                alert.cancel();
            }
        });

        return alert;
    }

}
