package cn.edu.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*

1. MainActivity 就是提供一个入口，移植的时候很方便。

2. Android API 28 及以上要注意 两 点：
    ①API 28 及以上，默认情况下，只能访问https，
       解决办法，在AndroidManifest.xml的application标签中加入
        android:usesCleartextTraffic="true"
    ②Android 6.0以后，需要开启网络权限，同样是在AndroidManifest.xml中添加
        <uses-permission android:name="android.permission.INTERNET" />

 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.bt_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideoListActivity.class));
            }
        });
    }
}
