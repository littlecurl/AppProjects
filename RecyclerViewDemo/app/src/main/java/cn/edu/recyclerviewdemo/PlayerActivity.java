package cn.edu.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("videoUrl");
        VideoView videoView = findViewById(R.id.videoview);
        Uri uri =  Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
