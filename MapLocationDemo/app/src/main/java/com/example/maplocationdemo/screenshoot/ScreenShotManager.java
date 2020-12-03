package com.example.maplocationdemo.screenshoot;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


/**
 * @author wzq
 * @date 2019/6/03 上午10:16
 * @desc 截屏的处理类
 */
public class ScreenShotManager {

    private Context mContext;
    public static final String TAG = "ScreenShotManager";

    private ContentObserver mInternalObserver;     //内部存储器内容观察者
    private ContentObserver mExternalObserver;     //外部存储器内容观察者


    //匹配各个手机截屏路径的关键字
    private static final String[] KEYWORDS = {
            "screenshot", "screenshots", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap", "截屏"
    };

    //读取媒体数据库时需要读取的列
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };


    public ScreenShotManager(final Context context) {
        mContext = context;
        initManager();
    }

    /**
     * 初始化
     */
    private void initManager() {
        if (mContext == null) {
            return;
        }
        final Handler handler = new Handler(mContext.getMainLooper());
        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, handler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, handler);
    }

    /**
     * 添加监听
     */
    public void startListener() {
        if (mContext == null) {
            return;
        }
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false, mInternalObserver);
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mExternalObserver);
    }

    /**
     * 注销监听
     */
    public void stopListener() {
        if (mContext == null) {
            return;
        }
        mContext.getContentResolver().unregisterContentObserver(mInternalObserver);
        mContext.getContentResolver().unregisterContentObserver(mExternalObserver);
    }

    /**
     * 检查是否大于当前时间五秒(兼容小米)，是则舍弃，反之亦然
     *
     * @param dateTime 图片保存时间
     * @return true 符合预期
     */
    private boolean checkTime(final long dateTime) {
        return System.currentTimeMillis() - dateTime < 5 * 1000;
    }


    /**
     * 判断是否是截屏
     */
    private boolean checkScreenShot(String data) {
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理监听到的事件(当图片库发生变化是会触发)
     *
     * @param contentUri contentUri
     */
    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            // 数据改变时查询数据库中最后加入的一条数据
            cursor = mContext.getContentResolver().query(
                    contentUri,
                    MEDIA_PROJECTIONS,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1"
            );

            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }

            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dataData = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            // 获取行数据
            String data = cursor.getString(dataIndex);
            long dateTime = cursor.getLong(dataData);
            // 处理获取到的第一行数据
            if (checkTime(dateTime)) {
                handleMediaRowData(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    /**
     * 处理监听到的资源
     */
    private void handleMediaRowData(String data) {
        if (checkScreenShot(data)) {
            if (TextUtils.isEmpty(data)) {
                return;
            }

            Intent intent = new Intent(mContext, ShotShareActivity.class);
            intent.putExtra("snapshot_path", data);
            mContext.startActivity(intent);
        } else {
            Log.e(TAG, "Not screenshot event:" + data);
        }
    }


    /**
     * 媒体内容观察者(观察媒体数据库的改变)
     */
    private class MediaContentObserver extends ContentObserver {

        private Uri mContentUri;

        MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            mContentUri = contentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleMediaContentChange(mContentUri);
                }
            }).start();
        }
    }

}
