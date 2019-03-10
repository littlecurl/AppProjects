package com.imooc.main;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

import com.imooc.adapter.MyAdapter;
import com.imooc.entity.ApkEntity;
import com.imooc.view.IReflashListener;
import com.imooc.view.LoadListView;
import com.imooc.view.ReflashListView;

import java.util.ArrayList;

//public class MainActivity extends Activity implements IReflashListener,ILoadListener {
public class MainActivity extends Activity implements IReflashListener {
//    ArrayList<ApkEntity> load_apk_list = new ArrayList<ApkEntity>();
    ArrayList<ApkEntity> reflash_apk_list = new ArrayList<ApkEntity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getData();
//        showLoadListView(load_apk_list);
        setData();
        showReflashListView(reflash_apk_list);
    }
    MyAdapter reflash_adapter;
    com.imooc.view.ReflashListView reflash_listview;
    public void showReflashListView(ArrayList<ApkEntity> apk_list) {
        if (reflash_adapter == null) {
            reflash_listview = (com.imooc.view.ReflashListView) findViewById(R.id.reflash_listview);
            reflash_listview.setInterface(this);
            reflash_adapter = new MyAdapter(this, apk_list);
            reflash_listview.setAdapter(reflash_adapter);
        } else {
            reflash_adapter.onDateChange(apk_list);
        }
    }

    public void setData() {
        for (int i = 0; i < 10; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("下拉刷新");
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            reflash_apk_list.add(entity);
        }
    }

    private void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("更多程序");
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            reflash_apk_list.add(0,entity);
        }
    }

    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取更多数据
                setReflashData();
                // 下拉
                showReflashListView(reflash_apk_list);
                //通知listview加载完毕
                reflash_listview.reflashComplete();
            }
        }, 2000);
    }


//    MyAdapter load_adapter;
//    LoadListView load_listview;
//    private void showLoadListView(ArrayList<ApkEntity> apk_list) {
//        if (load_adapter == null) {
//            load_listview = (LoadListView) findViewById(R.id.load_listview);
//            load_listview.setInterface(this);
//            load_adapter = new MyAdapter(this, apk_list);
//            load_listview.setAdapter(load_adapter);
//        } else {
//            load_adapter.onDateChange(apk_list);
//        }
//    }
//    private void getData() {
//        for (int i = 0; i < 10; i++) {
//            ApkEntity entity = new ApkEntity();
//            entity.setName("上拉加载");
//            entity.setInfo("50w用户");
//            entity.setDes("这是一个神奇的应用！");
//            load_apk_list.add(entity);
//        }
//    }
//    private void getLoadData() {
//        for (int i = 0; i < 2; i++) {
//            ApkEntity entity = new ApkEntity();
//            entity.setName("更多程序");
//            entity.setInfo("50w用户");
//            entity.setDes("这是一个神奇的应用！");
//            load_apk_list.add(entity);
//        }
//    }
//    @Override
//    public void onLoad() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //获取更多数据
//                getLoadData();
//                // 上拉更新listview显示；
//                showLoadListView(load_apk_list);
//                //通知listview加载完毕
//                load_listview.loadComplete();
//            }
//        }, 2000);
//    }

}
