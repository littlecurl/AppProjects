package cn.edu.heuet.androidcountdown;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CountDownActivity extends AppCompatActivity {
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();
        // 设置全屏一定要在setContentView()之前,否则有可能不起作用
        setContentView(R.layout.activity_count_down);

        TextView countDownText = findViewById(R.id.tv_count_down);

        initCountDown(countDownText);
    }

    // 全屏显示
    private void setFullScreen() {
        // 如果该类是 extends Activity ，下面这句代码起作用
        // 去除ActionBar(因使用的是NoActionBar的主题，故此句有无皆可)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 如果该类是 extends AppCompatActivity， 下面这句代码起作用
        if (getSupportActionBar() != null){ getSupportActionBar().hide(); }
        // 去除状态栏，如 电量、Wifi信号等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // 倒计时逻辑
    private void initCountDown(final TextView countDownText) {
        // 判断当前Activity是否isFinishing()，
        // 避免在finish，所有对象都为null的状态下执行CountDown造成内存泄漏
        if (!isFinishing()) {
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
        // 这里先不放引导页，直接跳到主界面
        startActivity(new Intent(CountDownActivity.this, MainActivity.class));

        // 回收内存
        destoryTimer();
        finish();
    }

    public void destoryTimer() {
        // 避免内存泄漏
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
