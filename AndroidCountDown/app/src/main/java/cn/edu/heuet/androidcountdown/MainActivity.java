package cn.edu.heuet.androidcountdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countDownFragment();

        TextView tv_to_count_down_activity = findViewById(R.id.tv_to_count_down_activity);
        tv_to_count_down_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CountDownActivity.class));
            }
        });
    }
    private void countDownFragment(){
        // 开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = new CountDownFragment();
        // 没有被加入的话，先加入
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.fl_count_down, currentFragment);
        }
        // 显示Fragment
        ft.show(currentFragment);
        // commit
        ft.commitAllowingStateLoss();
    }
}
