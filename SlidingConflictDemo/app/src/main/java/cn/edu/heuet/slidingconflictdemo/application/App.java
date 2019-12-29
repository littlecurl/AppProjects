package cn.edu.heuet.slidingconflictdemo.application;

import android.app.Application;

import com.youth.xframe.XFrame;

import cn.edu.heuet.slidingconflictdemo.loader.GlideImageLoader;

/**
 * @ClassName App
 * @Author littlecurl
 * @Date 2019/12/29 16:51
 * @Version 1.0.0
 * @Description 设置在AndroidManifest.xml中android:name=".application.App"
 * 在这里对XFrame框架进行初始化，否则无法使用XFrame
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // XFrame 初始化
        XFrame.init(getApplicationContext());
        /********** 图片加载 **********/
        XFrame.initXImageLoader(new GlideImageLoader(getApplicationContext()));
    }
}
