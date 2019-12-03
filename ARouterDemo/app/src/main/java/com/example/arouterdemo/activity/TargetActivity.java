package com.example.arouterdemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.R;
import com.example.arouterdemo.bean.TestObj;
import com.example.arouterdemo.router.MyRouter;

import java.util.List;
import java.util.Map;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = MyRouter.ACTIVITY_URL_TARGET)
public class TargetActivity extends BaseActivity {
    /**
     * 1、需要写上对应的name，否则会出现null
     * 2、这里的参数不需要private之类的修饰符，否则会报错
     * 3、List、Map只能写成List、Map；不能用ArrayList、HashMap之类替代
     */
    @Autowired(name = "key1")
    byte key1;
    @Autowired(name = "key2")
    short key2;
    @Autowired(name = "key3")
    int key3;
    @Autowired(name = "key4")
    long key4;
    @Autowired(name = "key5")
    float key5;
    @Autowired(name = "key6")
    double key6;
    @Autowired(name = "key7")
    char key7;
    @Autowired(name = "key8")
    boolean key8;
    @Autowired(name = "key9")
    String key9;
    @Autowired(name = "key10")
    TestObj key10;
    @Autowired(name = "key11")
    List<TestObj> key11;
    @Autowired(name = "key12")
    Map<String,TestObj> key12;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        TextView textView = findViewById(R.id.textView);
        if (key8) {
            textView.setText(key1 + "\n" + key2 + "\n" + key3 + "\n" + key4
                    + "\n" + key5 + "\n" + key6 + "\n" + key7 + "\n" + key8
                    + "\n" + key9 + "\n" + key10.toString() + "\n" + key11.toString() + "\n" + key12.toString());
        }

        Button bt_target = findViewById(R.id.bt_target);
        bt_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 应用内简单的跳转
                ARouter.getInstance().build(MyRouter.ACTIVITY_URL_MAIN).navigation();
                finish();
            }
        });

        setResult(666);
    }
}
