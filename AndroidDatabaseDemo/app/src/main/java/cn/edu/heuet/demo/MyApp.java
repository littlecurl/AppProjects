package cn.edu.heuet.demo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.xuexiang.xhttp2.XHttpSDK;

import org.litepal.LitePal;

import cn.edu.heuet.demo.mysql.network.NetworkConstant;
import cn.edu.heuet.demo.room.database.DemoDatabase;
import cn.edu.heuet.demo.sqlite.DatabaseHelper;

public class MyApp extends Application {
    public static Application application;
    public static DemoDatabase roomDb;
    public static SQLiteDatabase sqliteDb;
    public static DatabaseHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        // SQLite
        // 初始化数据库，建库
        dbHelper = new DatabaseHelper(this);
        sqliteDb = dbHelper.getWritableDatabase();

        // ROOM
        roomDb = Room.databaseBuilder(this, DemoDatabase.class, "demo_room").build();

        // LitePal
        LitePal.initialize(this);


        // MYSQL
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
