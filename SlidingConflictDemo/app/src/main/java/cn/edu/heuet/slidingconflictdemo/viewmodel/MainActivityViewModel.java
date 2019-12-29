package cn.edu.heuet.slidingconflictdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.slidingconflictdemo.fragment.Fragment_1;
import cn.edu.heuet.slidingconflictdemo.fragment.Fragment_2;
import cn.edu.heuet.slidingconflictdemo.fragment.Fragment_3;
import cn.edu.heuet.slidingconflictdemo.fragment.Fragment_4;

/**
 * ViewModel 为Activity或Fragment提供数据
 */
public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Fragment> getFragmentList(){
        /* 展示10个页面 */
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_1());
        fragmentList.add(new Fragment_2());
        fragmentList.add(new Fragment_3());
        fragmentList.add(new Fragment_4());
        fragmentList.add(new Fragment_1());
        fragmentList.add(new Fragment_2());
        fragmentList.add(new Fragment_3());
        fragmentList.add(new Fragment_4());
        fragmentList.add(new Fragment_1());
        fragmentList.add(new Fragment_2());
        return fragmentList;
    }


}
