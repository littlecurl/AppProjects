package com.example.slidedemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.example.slidedemo.TransitionHelper;

public abstract class BaseActivity extends AppCompatActivity {

    protected Bundle gettransitionActivityOptionsBundle(Activity activity) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        return transitionActivityOptions.toBundle();
    }

    protected void setFullScreen(boolean removeTitleBar, boolean removeStatusBar){
        if (removeTitleBar){
            //去除标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (removeStatusBar){
            //去除状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    // 转场动画
    protected void setupWindowAnimations(Context context, int transitionId) {
        Transition transition;
        transition = TransitionInflater.from(context).inflateTransition(transitionId);
        getWindow().setEnterTransition(transition);
    }
}
