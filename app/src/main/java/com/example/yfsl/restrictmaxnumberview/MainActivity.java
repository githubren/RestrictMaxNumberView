package com.example.yfsl.restrictmaxnumberview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText content_et;
    private TextView content_number_tip_tv;
    private CharSequence tempChar;
    private int editStart;
    private int editEnd;
    private long toastInterval;
    private final int INTERVAL = 3*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        //实现EditText计数功能的监听
        content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tempChar = charSequence;
            }

            /**
             * 当文本框中输入的内容发生变化时 计数的TextView跟随变化
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content_number_tip_tv.setText(charSequence.length()+"/150");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                editStart = content_et.getSelectionStart();
                editEnd = content_et.getSelectionEnd();
                if (editable.length()>150){
                    long l = System.currentTimeMillis();
                    if (l-toastInterval >= INTERVAL){
                        Toast.makeText(MainActivity.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                        toastInterval = l;
                    }
                    editable.delete(editStart-1,editEnd);
                    content_et.setText(editable);
                    content_et.setSelection(150);
                }
            }
        });
    }

    private void initView() {
        content_et = findViewById(R.id.content_et);
        content_number_tip_tv = findViewById(R.id.content_number_tip_tv);
    }
}
