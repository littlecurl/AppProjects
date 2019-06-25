package cn.edu.heuet.androidcountdown;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class CountDownFragment extends Fragment {

    private View rootView;
    private CountDownTimer timer;
    private ConstraintLayout cl_main_activity;

    // 官方文档里说了，最好每个Fragment都有一个空的构造方法
    public CountDownFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_count_down, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView countDownText = rootView.findViewById(R.id.tv_count_down);
        // 隐藏主布局,注意这里用getActivity()来获取布局
        // rootView 指代的是 R.layout.fragment_count_down
        // getActivity() 指代的是 R.layout.activity_main
        cl_main_activity = getActivity().findViewById(R.id.cl_main_activity);
        cl_main_activity.setVisibility(View.GONE);
        initCountDown(countDownText);
    }

    // 倒计时逻辑
    private void initCountDown(final TextView countDownText) {
        // 判断当前Fragment是否加入到了Activity中
        if (isAdded()) {
            timer = new CountDownTimer(1000 * 6, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO: 耗时操作，如异步登录
                    // ......
                    int time = (int) millisUntilFinished;
                    countDownText.setText(time / 1000 + " 跳过");
                    countDownText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkToJump();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    checkToJump();
                }
            }.start();
        }
    }

    // 首次进入引导页判断
    private void checkToJump() {
        //  TODO：首次安装判断
        // 如果是首次安装打开，则跳至引导页；否则跳至主界面

        // 回收内存
        destoryTimer();
        // 移除Fragment，相当于Activity中的finish()
        if (isAdded() && getFragmentManager() != null) {
            getFragmentManager().beginTransaction().remove(this).commit();
            // 显示主布局
            cl_main_activity.setVisibility(View.VISIBLE);
        }
    }

    private void destoryTimer() {
        // 避免内存泄漏
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
