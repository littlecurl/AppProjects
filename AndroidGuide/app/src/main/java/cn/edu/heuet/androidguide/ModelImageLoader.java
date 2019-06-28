package cn.edu.heuet.androidguide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Create by SunnyDay on 2019/03/15
 * Banner 的ImageLoader
 */
public class ModelImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        // Glide相关用法可参考其GitHub官方网址
        Glide.with(context)
                .load(path)
                .into(imageView);
    }
}
