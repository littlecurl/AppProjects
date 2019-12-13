package com.example.slidedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slidedemo.base.BaseActivity;

public class VerticalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        setFullScreen(true,true);
        setContentView(R.layout.activity_vertical);
        // 转场
        setupWindowAnimations(this, R.transition.slide_from_top);
        // 退场
        Button bt_vertical = findViewById(R.id.bt_vertical);
        bt_vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 不能使用finish();否则退场动画无效
                finishAfterTransition();
            }
        });
    }
}
