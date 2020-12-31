package com.hjq.widget.rxtool;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.widget.R;


/**
 * @author vondear
 * @date 2016/7/19
 * 确认 弹出框
 */
public class RxDialogSure extends RxDialog {

    private ImageView mIvLogo;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvSure;

    public RxDialogSure(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }


    public RxDialogSure(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public RxDialogSure(Context context) {
        super(context);
        initView();
    }

    public RxDialogSure(Context context, String title, String content) {
        super(context);
        initView();
        mTvTitle.setText(title);
        mTvContent.setText(content);
        setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public RxDialogSure(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public ImageView getLogoView() {
        return mIvLogo;
    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public void setSureListener(View.OnClickListener listener) {
        mTvSure.setOnClickListener(listener);
    }

    public TextView getContentView() {
        return mTvContent;
    }

    public void setLogo(int resId) {
        mIvLogo.setImageResource(resId);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setSure(String content) {
        mTvSure.setText(content);
    }

    public void setContent(String str) {
        //当内容为网址的时候，内容变为可点击
        if (URLUtil.isValidUrl(str)) {
            // 响应点击事件的话必须设置以下属性
            mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
            mTvContent.setText(RxTextTool.getBuilder("").setBold().append(str).setUrl(str).create());
        } else {
            mTvContent.setText(str);
        }

    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure, null);
        mTvSure = dialogView.findViewById(R.id.tv_sure);
        mTvTitle = dialogView.findViewById(R.id.tv_title);
        mTvTitle.setTextIsSelectable(true);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setTextIsSelectable(true);
        mIvLogo = dialogView.findViewById(R.id.iv_logo);
        setContentView(dialogView);
    }

}
