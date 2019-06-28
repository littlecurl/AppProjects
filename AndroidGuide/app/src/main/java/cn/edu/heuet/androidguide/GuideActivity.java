package cn.edu.heuet.androidguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘亚恒
 * @since 2019/6/28 10:00
 */
public class GuideActivity extends AppCompatActivity {
    private Banner guide_banner;
    private ImageView iv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreenConfig();
        // 全屏一定要设置在这句之上
        setContentView(R.layout.activity_guide);

        // 获取控件
        guide_banner = findViewById(R.id.guide_banner);
        iv_start = findViewById(R.id.iv_start);

        // 初始化图片资源
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.launcher_01);
        imageList.add(R.drawable.launcher_02);
        imageList.add(R.drawable.launcher_03);

        // 轮播图实现
        initViews(imageList);

    }

    // 全屏显示
    private void fullScreenConfig() {
        // 去除ActionBar
        // 如果该类 extends Activity，使用下面这句
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 如果该类 extends AppCompatActivity，使用下面这句
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 去除状态栏，如 电量、Wifi信号等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initViews(final List<Integer> imageList) {
        // 设置ImageLoader
        guide_banner.setImageLoader(new ModelImageLoader())
                .setImages(imageList)
                .isAutoPlay(true)
                .start();

        // 设置页面选择监听器并实现选中点击事件响应
        guide_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                // 如果轮播到了最后一张图片
                if (i == imageList.size() - 1) {
                    // 显示start按钮图片
                    iv_start.setVisibility(View.VISIBLE);
                    iv_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(GuideActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                } else {
                    iv_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
}
