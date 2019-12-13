package com.example.slidedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.slidedemo.base.BaseActivity;

public class HorizontalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        setFullScreen(true,true);
        setContentView(R.layout.activity_horizontal);
        // 转场
        setupWindowAnimations(this,R.transition.slide_from_right);
        // 退场
        Button bt_horizontal = findViewById(R.id.bt_horizontal);
        bt_horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 不能使用finish();否则退场动画无效
                finishAfterTransition();
            }
        });
    }

}
