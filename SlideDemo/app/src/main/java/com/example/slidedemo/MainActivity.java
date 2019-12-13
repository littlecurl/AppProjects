package com.example.slidedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slidedemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 跳转
        Button bt_vertical = findViewById(R.id.bt_vertical);
        bt_vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VerticalActivity.class);
                Bundle bundle = getTransitionActivityOptionsBundle(MainActivity.this);
                startActivity(i,bundle);
            }
        });

        Button bt_horizontal = findViewById(R.id.bt_horizontal);
        bt_horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HorizontalActivity.class);
                Bundle bundle = getTransitionActivityOptionsBundle(MainActivity.this);
                startActivity(i,bundle);
            }
        });
    }


}
