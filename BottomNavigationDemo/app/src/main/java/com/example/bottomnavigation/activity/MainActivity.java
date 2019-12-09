package com.example.bottomnavigation.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bottomnavigation.R;
import com.example.bottomnavigation.adapter.MyFragmentStateAdapter;
import com.example.bottomnavigation.utils.UIUtils;
import com.example.bottomnavigation.viewmodel.MainViewModel;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    public RadioGroup radioGroup;
    private List<Integer> radioButtonIdList = Arrays.asList(R.id.rb_home, R.id.rb_track, R.id.rb_message, R.id.rb_mine);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        /*
        从ViewModel中获取FragmentList数据
        获取ViewModel对象的方式固定为：ViewModelProviders.of(当前activity).get(ViewModel类名.class);
        */
        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        List<Fragment> fragmentList = mainViewModel.getFragmentList();
        viewPager2 = findViewById(R.id.activity_main_viewPager2);
        // 将FragmentList传递给FragmentStateAdapter
        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(this, fragmentList);
        viewPager2.setAdapter(myFragmentStateAdapter);
        /*
        页面切换的监听事件
        一般控件的监听器都是 set什么什么
        ViewPager2是register......
         */
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                radioGroup.check(radioButtonIdList.get(position));
            }
        });
    }

    private void initView() {
        // 初始化UI控件
        radioGroup = findViewById(R.id.activity_main_radiogroup);
        ImageView ivFabPublish = findViewById(R.id.iv_fab_publish);

        RadioButton rbHome = findViewById(R.id.rb_home);
        RadioButton rbTrack = findViewById(R.id.rb_track);
        RadioButton rbMessage = findViewById(R.id.rb_message);
        RadioButton rbMine = findViewById(R.id.rb_mine);

        // RadioButton点击响应事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewPager2.setCurrentItem(radioButtonIdList.indexOf(checkedId));
            }
        });
        // Fab图片点击响应事件
        ivFabPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PubActivity.class);
                startActivity(intent);
            }
        });
        // 四个RadioButton初始化图片、文字
        Drawable dbHome = getResources().getDrawable(R.drawable.selector_home);
        dbHome.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbHome.setCompoundDrawables(null, dbHome, null, null);

        Drawable dbPond = getResources().getDrawable(R.drawable.selector_pond);
        dbPond.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbTrack.setCompoundDrawables(null, dbPond, null, null);

        Drawable dbMsg = getResources().getDrawable(R.drawable.selector_message);
        dbMsg.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbMessage.setCompoundDrawables(null, dbMsg, null, null);

        Drawable dbMe = getResources().getDrawable(R.drawable.selector_person);
        dbMe.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbMine.setCompoundDrawables(null, dbMe, null, null);
    }

}
