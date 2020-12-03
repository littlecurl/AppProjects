package com.example.maplocationdemo.screenshoot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.maplocationdemo.R;

public class ShotShareActivity extends AppCompatActivity {

    private ImageView ivContent;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_share);
        String originPath = getIntent().getStringExtra("snapshot_path");
        initIvContent(originPath);
    }

    private void initIvContent(final String path) {
        ivContent = findViewById(R.id.iv_content);
        //状态栏的高度
        int statusHeight = Utils.getStatusBarHeight(this);
        //虚拟导航栏的高度
        int navHeight = Utils.getNavigationBarHeight(this);
        float width = Utils.getScreenWidth(this) - Utils.dp2px(this, 116);
        float ratio = (float) Utils.div(Utils.getScreenWidth(this), Utils.getScreenHeight(this) - statusHeight - navHeight, 2);
        float height = width / ratio;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) height;
        ivContent.setLayoutParams(layoutParams);
        //魅族手机生成的截图文件带有 "-" 的命名，会导致获取bitmap为null。利用Glide生成的bitmap
        loadImage(path, statusHeight, navHeight);

        ImageView ivContent = findViewById(R.id.iv_content);
        LinearLayout llShare = findViewById(R.id.ll_share);
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedToQQ(ShotShareActivity.this, null, path);
//                sharedToWx(ShotShareActivity.this, null, path);
                Toast.makeText(ShotShareActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void sharedToQQ(Context context, Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //intent.setType("text/plain");  //文本分享
        intent.setType("image/*");
        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, filePath);
        }
        intent.setPackage("com.tencent.mobileqq");
        intent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ
        context.startActivity(intent);
    }

    public static void sharedToWx(Context context, Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //intent.setType("text/plain");  //文本分享
        intent.setType("image/*");
        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, filePath);
        }
        intent.setPackage("com.tencent.mm");
        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信
        context.startActivity(intent);
    }

    /**
     * 加载截屏文件，为了防止加载失败，可以重复加载(最多5次)
     *
     * @param path         截屏文件路径
     * @param statusHeight 状态栏高度
     * @param navHeight    虚拟导航栏高度
     */
    private void loadImage(final String path, final int statusHeight, final int navHeight) {
        Glide.with(this).asBitmap().load(path).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                //源文件生成的bitmap
                //裁剪bitmap,去掉状态栏和底部的菜单栏(x+width must be < bitmap.width())
                try {
                    Bitmap resultBitmap = Bitmap.createBitmap(resource, 0, statusHeight, resource.getWidth(), resource.getHeight() - statusHeight - navHeight);
                    ivContent.setImageBitmap(resultBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                //加载失败的情况下重新加载一次,最多加载五次
                if (count <= 5) {
                    count = count + 1;
                    loadImage(path, statusHeight, navHeight);
                }

            }
        });
    }
}
