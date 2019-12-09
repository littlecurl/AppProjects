package com.example.bottomnavigation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 主要功能：
 * 实现ViewPager2中Fragment页面的切换
 * 原理：
 * 根据viewPager2的position从FragmentList中选择具体的那个Fragment去展示
 */
public class MyFragmentStateAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
