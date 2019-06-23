package com.example.uploadtest;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utils {
    // 将bitmap转成string类型通过Base64
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        // 将bitmap压缩成30%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bao);
        // 将bitmap转化为一个byte数组
        byte[] bs = bao.toByteArray();
        // 将byte数组用BASE64加密
        String photoStr = Base64.encodeToString(bs, Base64.DEFAULT);
        // 返回String
        return photoStr;
    }
}