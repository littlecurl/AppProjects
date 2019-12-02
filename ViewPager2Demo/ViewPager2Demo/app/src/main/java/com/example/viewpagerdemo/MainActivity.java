package com.example.viewpagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.viewpager2demo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<FragmentForViewPager> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_viewpager);

        ViewPager viewPager = findViewById(R.id.viewpager);
        fragments = initFragments();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private List<FragmentForViewPager> initFragments(){
        List<FragmentForViewPager> fragmentList = new ArrayList<>();
        for (int i=0; i< 10; i++){
            fragmentList.add(new FragmentForViewPager(i));
        }
        return fragmentList;
    }
}
