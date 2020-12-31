package com.hjq.widget.rxtool;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class RxClickableSpanShowDialog extends ClickableSpan {

    RxDialog rxDialog;

    public RxClickableSpanShowDialog(RxDialog rxDialog){
        this.rxDialog = rxDialog;
    }

    @Override
    public void onClick(@NonNull View widget) {
        rxDialog.show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);
        ds.setUnderlineText(false);
    }
}
