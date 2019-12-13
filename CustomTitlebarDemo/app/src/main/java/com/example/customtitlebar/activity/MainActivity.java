package com.example.customtitlebar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;

import com.example.customtitlebar.R;
import com.example.customtitlebar.activity.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitleBarView();
    }

    private void initTitleBarView() {
        LinearLayout left_ll_qrcode = findViewById(R.id.left_ll_qrcode);
        TextView centerTvTheme = findViewById(R.id.center_tv_theme);
        LinearLayout ll_search = findViewById(R.id.right_ll_search);
        RelativeLayout rightRlMsgParent = findViewById(R.id.right_rl_msg_parent);

        left_ll_qrcode.setOnClickListener(this);
        centerTvTheme.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        rightRlMsgParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_ll_qrcode:
                showToastOnUIThread(this, "点击响应事件");
                break;
            case R.id.center_tv_theme:
                showToastOnUIThread(this, "点击响应事件");
                break;
            case R.id.right_ll_search:
                // 仿微信，跳转页面,Fragment无法方便的全屏，故跳转到Activity
                ActivityOptionsCompat compat = getActivityOptionsCompat(this);
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i,compat.toBundle());
                break;
            case R.id.right_rl_msg_parent:
                showToastOnUIThread(this, "点击响应事件");
                break;
        }
    }

    private void showToastOnUIThread(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
