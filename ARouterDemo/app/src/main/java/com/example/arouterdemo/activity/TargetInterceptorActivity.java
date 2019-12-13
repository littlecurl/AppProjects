package com.example.arouterdemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
@Route(path = MyRouter.ACTIVITY_URL_TARGET_INTERCEPTOR)
public class TargetInterceptorActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        Button bt_target = findViewById(R.id.bt_target);
        bt_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 应用内简单的跳转
                ARouter.getInstance().build(MyRouter.ACTIVITY_URL_MAIN).navigation();
                finish();
            }
        });
    }
}
