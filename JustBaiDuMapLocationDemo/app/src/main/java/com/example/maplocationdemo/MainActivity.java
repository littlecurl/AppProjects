package com.example.maplocationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.maplocationdemo.baidu.location.BaiDuLocationActivity;
import com.example.maplocationdemo.baidu.map.BaiDuMapActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btBaiduLocation = findViewById(R.id.bt_baidu_location);
        btBaiduLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BaiDuLocationActivity.class);
                startActivity(intent);
            }
        });

        Button btBaiduMap = findViewById(R.id.bt_baidu_map);
        btBaiduMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BaiDuMapActivity.class);
                startActivity(intent);
            }
        });
    }
}