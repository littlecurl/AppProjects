package cn.edu.heuet.shaohua.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseFragmentAdapter;

import org.jetbrains.annotations.NotNull;

import cn.edu.heuet.shaohua.R;

import cn.edu.heuet.shaohua.common.MyFragment;
import cn.edu.heuet.shaohua.databinding.HomeFragmentBinding;
import cn.edu.heuet.shaohua.ui.activity.HomeActivity;
import cn.edu.heuet.shaohua.widget.XCollapsingToolbarLayout;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目炫酷效果示例
 */
public final class HomeFragment extends MyFragment<HomeActivity>
        implements XCollapsingToolbarLayout.OnScrimsListener {

    private BaseFragmentAdapter<MyFragment> mPagerAdapter;
    private HomeFragmentBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getBindingViewId() {
        return R.layout.home_fragment;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initView() {
        binding = getBinding();

        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表 A");
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表 B");
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表 C");
        binding.vpHomePager.setAdapter(mPagerAdapter);
        binding.tlHomeTab.setupWithViewPager(binding.vpHomePager);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), binding.tbHomeTitle);

        //设置渐变监听
        binding.ctlHomeBar.setOnScrimsListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return binding.ctlHomeBar.isScrimsShown();
    }

    /**
     * CollapsingToolbarLayout 渐变回调
     * <p>
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            binding.tvHomeAddress.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black));
            binding.tvHomeHint.setBackgroundResource(R.drawable.home_search_bar_gray_bg);
            binding.tvHomeHint.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black60));
            binding.ivHomeSearch.setSupportImageTintList(ColorStateList.valueOf(getColor(R.color.colorIcon)));
            getStatusBarConfig().statusBarDarkFont(true).init();
        } else {
            binding.tvHomeAddress.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white));
            binding.tvHomeHint.setBackgroundResource(R.drawable.home_search_bar_transparent_bg);
            binding.tvHomeHint.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white60));
            binding.ivHomeSearch.setSupportImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }
}