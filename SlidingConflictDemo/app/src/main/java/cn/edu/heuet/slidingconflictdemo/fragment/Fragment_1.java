package cn.edu.heuet.slidingconflictdemo.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.edu.heuet.slidingconflictdemo.R;
import cn.edu.heuet.slidingconflictdemo.adapter.MyXRecyclerViewAdapter;
import cn.edu.heuet.slidingconflictdemo.bean.ArticleItem;
import cn.edu.heuet.slidingconflictdemo.loader.SimpleGlideImageLoader;
import cn.edu.heuet.slidingconflictdemo.viewmodel.Fragment1ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Banner top_banner;
    private List<String> bannerImages;
    private List<String> bannerTitles;
    private SwipeRefreshLayout mSwipeLayout;

    public Fragment_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // 初始化下拉刷新控件
        initSwipeRefreshLayout(view);

        // 初始化RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview1);
        // 布局管理器 两列、垂直
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 从ViewModel中获取FragmentList数据
        final Fragment1ViewModel fragment1ViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(Fragment1ViewModel.class);
        bannerImages = fragment1ViewModel.getImages();
        bannerTitles = fragment1ViewModel.getTitles();
        List<ArticleItem> articleItems = fragment1ViewModel.getArticleItems();
        // 适配器绑定Item数据（继承了XFrame中的XRecyclerViewAdapter）
        MyXRecyclerViewAdapter adapter = new MyXRecyclerViewAdapter(recyclerView, articleItems);
        // HeaderView（轮播图）
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_1_header, recyclerView, false);
        initHeaderView(headerView);
        // FooterView
        View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_1_footer, recyclerView, false);
        // 添加HeaderView、FooterView
        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);

        // 设置适配器
        recyclerView.setAdapter(adapter);

        return view;
    }

    // 初始化刷新控件
    private void initSwipeRefreshLayout(View view) {
        mSwipeLayout = view.findViewById(R.id.fragment_1_srl);
        mSwipeLayout.setOnRefreshListener(this);
    }
    @Override
    public void onRefresh() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: 刷新数据

                // 暂停刷新
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
    }


    private void initHeaderView(View headerView) {
        top_banner = headerView.findViewById(R.id.top_banner);
        initBanner(top_banner, bannerImages, bannerTitles);
    }

    private void initBanner(Banner banner, List<String> images, List<String> titles) {
        // 滚动时间在 fragment_1_header.xml中设置
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE) // 设置banner样式
                .setImageLoader(new SimpleGlideImageLoader()) // 设置图片加载器
                .setImages(images) // 设置图片集合
                .setBannerTitles(titles) // 设置标题集合（当banner样式有显示title时）
                .setBannerAnimation(Transformer.Default) // 设置banner动画效果
                .isAutoPlay(true) // 设置自动轮播，默认为true
                .setDelayTime(3500) // 设置轮播时间
                .setIndicatorGravity(BannerConfig.CENTER) //设置指示器位置（当banner模式中有指示器时）
                .setViewPagerIsScroll(true)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        XToast.info("Banner " + position);
                    }
                })
                .start(); //banner设置方法全部调用完毕时最后调用
        ArrayList<View> views = new ArrayList<>();
        views.add(banner);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        top_banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        top_banner.stopAutoPlay();
    }


}
