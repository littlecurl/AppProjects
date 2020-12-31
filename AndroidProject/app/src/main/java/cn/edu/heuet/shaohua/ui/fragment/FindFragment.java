package cn.edu.heuet.shaohua.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import cn.edu.heuet.shaohua.R;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.SwitchButton;

import cn.edu.heuet.shaohua.aop.SingleClick;
import cn.edu.heuet.shaohua.common.MyFragment;
import cn.edu.heuet.shaohua.databinding.FindFragmentBinding;
import cn.edu.heuet.shaohua.http.glide.GlideApp;
import cn.edu.heuet.shaohua.ui.activity.HomeActivity;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目自定义控件展示
 */
public final class FindFragment
        extends MyFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener
{

    private ImageView mCircleView;
    private SwitchButton mSwitchButton;
    private CountdownView mCountdownView;
    private FindFragmentBinding binding;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int getBindingViewId() {
        return R.layout.find_fragment;
    }

    @Override
    protected void initView() {
        mCircleView = findViewById(R.id.iv_find_circle);
        mSwitchButton = findViewById(R.id.sb_find_switch);
        mCountdownView = findViewById(R.id.cv_find_countdown);
        setOnClickListener(mCountdownView);

        mSwitchButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        GlideApp.with(this)
                .load(R.drawable.example_bg)
                .circleCrop()
                .into(mCircleView);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mCountdownView) {
            toast(R.string.common_code_send_hint);
            mCountdownView.start();
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}