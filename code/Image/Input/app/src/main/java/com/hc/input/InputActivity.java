package com.hc.input;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et1, et2, et3;
    private Button btn1, btn2, btn3;

    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        /*
        * 1. et.requestFocus()，只调用这个方法时，对应的et会获取到焦点，但是不会自动弹出键盘
        * 2. setFocusable(true)与setFocusableInTouchMode(true)调用后，对1的结果没有影响
        * 3. imm.showSoftInput(et, 0)与1结果，可以获取焦点并自动弹出键盘。
        *    只调用imm.showSoftInput(et, 0)，既不会获取焦点，也不会弹出键盘。
        * */
        switch (v.getId()) {
            case R.id.btn1:
                et1.setFocusable(true);
                et1.setFocusableInTouchMode(true);
                et1.requestFocus();
                break;
            case R.id.btn2:
                et2.requestFocus();
                imm.showSoftInput(et2, 0);
                break;
            case R.id.btn3:
//                et3.requestFocus();
                imm.showSoftInput(et3, 0);
                break;
        }
    }

}
