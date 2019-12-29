package cn.edu.heuet.slidingconflictdemo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.slidingconflictdemo.adapter.ViewPager2FragmentStateAdapter;
import cn.edu.heuet.slidingconflictdemo.viewmodel.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    public static ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.activity_main_viewPager2);
        // 将TabLayout绑定到ViewPager2上
        // Caused by: java.lang.IllegalStateException: TabLayoutMediator attached before ViewPager2 has an adapter
        // attachTabLayoutOnViewPager2();

        // 从ViewModel中获取FragmentList数据
        final MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        List<Fragment> fragmentList = mainActivityViewModel.getFragmentList();
        // 绑定Fragment视图到ViewPager2上
        ViewPager2FragmentStateAdapter viewPager2FragmentStateAdapter = new ViewPager2FragmentStateAdapter(this, fragmentList);
        viewPager2.setAdapter(viewPager2FragmentStateAdapter);

        // 将TabLayout绑定到ViewPager2上
        attachTabLayoutOnViewPager2();
    }

    private void attachTabLayoutOnViewPager2() {
        final List<String> tabName = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tabName.add("Tab" + i);
        }
        TabLayout tabLayout = findViewById(R.id.tablayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabName.get(position));// 注：Tab 又叫 Indicator 指示器
            }
        });
        tabLayoutMediator.attach();
    }
}
