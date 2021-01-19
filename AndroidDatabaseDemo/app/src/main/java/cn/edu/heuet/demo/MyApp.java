package cn.edu.heuet.demo;

import android.app.Application;

import androidx.room.Room;

import com.xuexiang.xhttp2.XHttpSDK;

import cn.edu.heuet.demo.mysql.network.NetworkConstant;
import cn.edu.heuet.demo.room.database.DemoDatabase;

public class MyApp extends Application {
    static Application application;
    public static DemoDatabase roomDb;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        roomDb = Room.databaseBuilder(this, DemoDatabase.class, "demo_room").build();

        initHttp();
    }


    private static void initHttp() {
        //初始化网络请求框架，必须首先执行
        XHttpSDK.init(application);
        //需要调试的时候执行
        XHttpSDK.debug();
        //设置网络请求的基础地址
        XHttpSDK.setBaseUrl(NetworkConstant.BASE_URL);
    }
}
