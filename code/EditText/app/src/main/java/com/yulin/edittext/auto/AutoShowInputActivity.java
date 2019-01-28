package com.yulin.edittext.auto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yulin.edittext.R;
import com.yulin.edittext.empty.EmptyActivity;

public class AutoShowInputActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_show_input);

        editText = findViewById(R.id.editText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSoftInput(editText, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftInput(editText, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        hideSoftInput(editText, this);
//        hideInputForce(this);
    }

    public void jump(View view) {
        startActivity(new Intent(this, EmptyActivity.class));
    }

//    private void showSoftInput() {
//        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(editText, 0);
//    }
//
//    private void hideSoftInput() {
//        View view = getWindow().peekDecorView();
//        if (view != null) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }

    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param editText
     * @param context
     */
    public void showSoftInput(EditText editText, Context context) {
        if (context == null || editText == null){
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }catch (Exception e){
//            WYLogUtils.WYLogException(TAG, e.getMessage(), e);
        }

    }

    /**
     * 关闭软键盘
     *
     * @param editText
     * @param context
     */
    public void hideSoftInput(EditText editText, Context context) {
        if (context == null || editText == null){
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }catch (Exception e){
//            WYLogUtils.WYLogException(TAG, e.getMessage(), e);
        }

    }

    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        try {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){
//            WYLogUtils.WYLogException(TAG, e.getMessage(), e);
        }
    }

}
