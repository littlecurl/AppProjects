package com.example.bottomnavigation.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavigation.R;
import com.example.bottomnavigation.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/28.
 * Describe:
 */
public class PubActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBtnPub;
    private LinearLayout mLlArticle,mLlBlog, mLlTweet;
    private List<LinearLayout> mLays = new ArrayList();
    private RelativeLayout mRlMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);
        initWindow();
        initViews();
    }

    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, PubActivity.class));
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    private void initViews() {
        mBtnPub = findViewById(R.id.btn_pub);
        mLlArticle = findViewById(R.id.ll_pub_article);
        mLlBlog = findViewById(R.id.ll_pub_blog);
        mLlTweet = findViewById(R.id.ll_pub_tweet);
        mRlMain = findViewById(R.id.rl_main);
        mLays.add(mLlArticle);
        mLays.add(mLlBlog);
        mLays.add(mLlTweet);
        mLlArticle.setOnClickListener(this);
        mLlBlog.setOnClickListener(this);
        mLlTweet.setOnClickListener(this);
        mRlMain.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBtnPub.animate()
                .rotation(135.0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
        show(0);
        show(1);
        show(2);
    }

    private void show(int position) {
        int angle = 30 + position * 60;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * UIUtils.dipTopx(this, 100);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * UIUtils.dipTopx(this, position != 1 ? 160 : 100);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays.get(position), "translationX", 0, x);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays.get(position), "translationY", 0, y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    private void close() {
        mBtnPub.clearAnimation();
        mBtnPub.animate()
                .rotation(0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mBtnPub.setVisibility(View.GONE);
                        finish();
                    }
                })
                .start();
    }

    private void dismiss() {
        close();
        close(0);
        close(1);
        close(2);
    }

    private void close(final int position) {
        int angle = 30 + position * 60;
        float x = (float) Math.cos(angle * (Math.PI / 180)) * UIUtils.dipTopx(this, 100);
        float y = (float) -Math.sin(angle * (Math.PI / 180)) * UIUtils.dipTopx(this, position != 1 ? 160 : 100);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays.get(position), "translationX", x, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays.get(position), "translationY", y, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLays.get(position).setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pub_article:
                break;
            case R.id.ll_pub_blog:
                break;
            case R.id.ll_pub_tweet:
                break;
            case R.id.rl_main:
                dismiss();
                break;
        }
    }
}
