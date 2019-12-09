package com.example.bottomnavigation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.example.bottomnavigation.fragment.Fragment_1_Home;
import com.example.bottomnavigation.fragment.Fragment_2_Track;
import com.example.bottomnavigation.fragment.Fragment_3_Message;
import com.example.bottomnavigation.fragment.Fragment_4_Mine;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel顾名思义：负责提供View的Model
 * 换句话说就是为页面提供数据
 */
public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Fragment> getFragmentList(){
        /* 展示五个页面，页面顺序不能乱 */
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_1_Home());
        fragmentList.add(new Fragment_2_Track());
        fragmentList.add(new Fragment_3_Message());
        fragmentList.add(new Fragment_4_Mine());
        return fragmentList;
    }
}
