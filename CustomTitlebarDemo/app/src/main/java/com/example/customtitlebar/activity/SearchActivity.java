package com.example.customtitlebar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.customtitlebar.R;
import com.example.customtitlebar.activity.base.BaseActivity;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        setFullScreen(true, true);
        setContentView(R.layout.activity_search);
        // 转场动画
        setupWindowAnimations(this,R.transition.slide_from_top);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            // 退场
            finishAfterTransition();
        }
    }
}
