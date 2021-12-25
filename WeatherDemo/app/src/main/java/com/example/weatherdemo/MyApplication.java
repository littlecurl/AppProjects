package com.example.weatherdemo;

import android.app.Application;

import com.xuexiang.xhttp2.XHttpSDK;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        XHttpSDK.init(this);   //初始化网络请求框架，必须首先执行
        XHttpSDK.debug("XHttp");  //需要调试的时候执行
        XHttpSDK.setBaseUrl("http://www.baidu.com");  //设置网络请求的基础地址
    }
}
