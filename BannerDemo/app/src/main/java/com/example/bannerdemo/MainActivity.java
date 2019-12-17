package com.example.bannerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> images = new ArrayList<>();
        images.add("https://img.hacpai.com/file/2019/12/1573992259378-902aa738.png?imageView2/2/w/1280/format/jpg/interlace/1/q/100");
        images.add("https://img.hacpai.com/file/2019/12/1573992043854-28052515.png?imageView2/2/w/1280/format/jpg/interlace/1/q/100");
        List<String> titles = new ArrayList<>();
        titles.add("1");
        titles.add("asdhasiudh");
        banner = findViewById(R.id.banner);
//        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
