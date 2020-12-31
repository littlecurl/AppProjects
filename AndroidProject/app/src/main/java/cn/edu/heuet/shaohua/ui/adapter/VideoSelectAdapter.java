package cn.edu.heuet.shaohua.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.heuet.shaohua.R;

import java.util.List;

import cn.edu.heuet.shaohua.common.MyAdapter;
import cn.edu.heuet.shaohua.helper.CacheDataManager;
import cn.edu.heuet.shaohua.http.glide.GlideApp;
import cn.edu.heuet.shaohua.ui.activity.VideoSelectActivity;
import cn.edu.heuet.shaohua.widget.PlayerView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2020/03/01
 *    desc   : 视频选择适配器
 */
public final class VideoSelectAdapter extends MyAdapter<VideoSelectActivity.VideoBean> {

    private final List<VideoSelectActivity.VideoBean> mSelectVideo;

    public VideoSelectAdapter(Context context, List<VideoSelectActivity.VideoBean> images) {
        super(context);
        mSelectVideo = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ImageView mImageView;
        private CheckBox mCheckBox;
        private TextView mDurationView;
        private TextView mSizeView;

        private ViewHolder() {
            super(R.layout.video_select_item);
            mImageView = (ImageView) findViewById(R.id.iv_video_select_image);
            mCheckBox = (CheckBox) findViewById(R.id.iv_video_select_check);
            mDurationView = (TextView) findViewById(R.id.tv_video_select_duration);
            mSizeView = (TextView) findViewById(R.id.tv_video_select_size);
        }

        @Override
        public void onBindView(int position) {
            VideoSelectActivity.VideoBean bean = getItem(position);
            GlideApp.with(getContext())
                    .load(bean.getVideoPath())
                    .into(mImageView);

            mCheckBox.setChecked(mSelectVideo.contains(getItem(position)));

            // 获取视频的总时长
            mDurationView.setText(PlayerView.conversionTime((int) bean.getVideoDuration()));

            // 获取视频的总大小
            mSizeView.setText(CacheDataManager.getFormatSize(bean.getVideoSize()));
        }
    }

    @Override
    protected RecyclerView.LayoutManager generateDefaultLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }
}