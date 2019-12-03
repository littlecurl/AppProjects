package com.example.arouterdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.R;
import com.example.arouterdemo.bean.TestObj;
import com.example.arouterdemo.router.MyRouter;
import com.example.arouterdemo.service.SingleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 在支持路由的页面上添加注解(必选)
@Route(path = MyRouter.ACTIVITY_URL_MAIN)
public class MainActivity extends BaseActivity {

    private static Activity activity;

    public static Activity getThis() {
        return activity;
    }

    /****************
     * (推荐)使用依赖注入的方式发现服务,
     * 通过注解标注字段,即可使用，无需主动获取
     * Autowired注解中标注name之后，将会使用byName的方式注入对应的字段，
     * 不设置name属性，会默认使用byType的方式发现服务
     * (当同一接口有多个实现的时候，必须使用byName的方式发现服务)
     ****************/
    @Autowired(name = "/yourservicegroupname/single")
    SingleService singleService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        /**************** 带参数跳转 ****************/
        /**************** 传递自定义类要有JSONServiceImpl实现类 ****************/
        Button bt_with_arguments = findViewById(R.id.bt_with_arguments);
        bt_with_arguments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TestObj> list = new ArrayList<>();
                list.add(new TestObj(8, "Jack"));

                Map<String, TestObj> map = new HashMap<>();
                map.put("9", new TestObj(9, "Nine"));

                // 跳转并携带参数
                ARouter.getInstance().build(MyRouter.ACTIVITY_URL_TARGET)
                        .withByte("key1", Byte.valueOf("1"))
                        .withShort("key2", Short.valueOf("2"))
                        .withInt("key3", 3)
                        .withLong("key4", 4L)
                        .withFloat("key5", 5.0F)
                        .withDouble("key6", 6.0D)
                        .withChar("key7", '7')
                        .withBoolean("key8", true)
                        .withString("key9", "9")
                        // 以下三个要想传递过去，必须存在com.example.arouterdemo.service.JsonServiceImpl实现类
                        .withObject("key10", new TestObj(10, "Rose"))
                        .withObject("key11", list)
                        .withObject("key12", map)
                        .navigation();
                finish();
            }
        });

        /**************** 带动画跳转 ****************/
        /**************** 新旧跳转方式不同，不过，基本上已经没有 API < 16 的手机了 ****************/
        Button bt_with_animation = findViewById(R.id.bt_with_animation);
        bt_with_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 新版动画
                if (Build.VERSION.SDK_INT >= 16) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.
                            makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);

                    ARouter.getInstance()
                            .build(MyRouter.ACTIVITY_URL_TARGET)
                            .withOptionsCompat(compat)
                            .navigation();
                    finish();
                }
                // 旧版动画
                else {
                    Toast.makeText(MainActivity.this, "API < 16,不支持新版本动画,", Toast.LENGTH_SHORT).show();
                    ARouter.getInstance()
                            .build(MyRouter.ACTIVITY_URL_TARGET)
                            .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                            .navigation(MainActivity.this);
                    finish();
                }
            }
        });

        /**************** 带拦截器跳转 ****************/
        /**************** 要有对应的拦截器拦截此次跳转 ****************/
        Button bt_with_interceptor = findViewById(R.id.bt_with_interceptor);
        bt_with_interceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(MyRouter.ACTIVITY_URL_TARGET_INTERCEPTOR)
                        .navigation(MainActivity.this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {

                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                // 拦截器写在了 com.example.arouterdemo.interceptor.Test1Interceptor
                                Log.d("ARouter", "被拦截了");
                            }
                        });
            }
        });

        /**************** 处理跳转结果 ****************/
        /**************** 目前没有 "/xxx/xxx"  这个路由，所以会触发跳转失败 ****************/
        Button bt_with_result = findViewById(R.id.bt_with_result);
        bt_with_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/xxx/xxx").navigation(MainActivity.this, new NavCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.d("ARouter", "找到了");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.d("ARouter", "找不到了");
                        // 这里的弹窗仅做举例，代码写法不具有可参考价值
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.getThis());
                        alertDialog.setCancelable(true);
                        alertDialog.setTitle("温馨提醒");
                        alertDialog.setMessage("页面没有找到，跳转失败！");
                        alertDialog.setNeutralButton("跳不了，不跳了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        alertDialog.create().show();
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.d("ARouter", "跳转完了");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.d("ARouter", "被拦截了");
                    }
                });
            }
        });

        /****************
         * (推荐)使用依赖注入的方式发现服务,
         * 通过注解标注字段,即可使用，无需主动获取
         * Autowired注解中标注name之后，将会使用byName的方式注入对应的字段，
         * 不设置name属性，会默认使用byType的方式发现服务
         * (当同一接口有多个实现的时候，必须使用byName的方式发现服务)
         ****************/
        Button bt_with_inject = findViewById(R.id.bt_with_inject);
        bt_with_inject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleService.sayHello(", World!");
            }
        });

        /****************
         * 获取Fragment
         * 这里仅仅是单独的获取Fragment，并没有使用ViewPager或者其他进行展示
         * ****************/
        Button bt_with_fragment = findViewById(R.id.bt_with_fragment);
        bt_with_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
                Toast.makeText(MainActivity.this, "找到Fragment:" + fragment.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /****************
         * 带请求码进行跳转
         * ****************/
        Button bt_with_request_code = findViewById(R.id.bt_with_request_code);
        bt_with_request_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(MyRouter.ACTIVITY_URL_TARGET)
                        .navigation(MainActivity.this, 666);
            }
        });
    }

    /**
     * 只会调用一次，除非卸载重装App
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666:
                Toast.makeText(MainActivity.this, "activityResult"+String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
