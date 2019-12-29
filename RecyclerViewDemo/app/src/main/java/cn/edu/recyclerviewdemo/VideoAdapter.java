package cn.edu.recyclerviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    Context mContext;
    List<String> mVideoPathList = new ArrayList<>();

    // 构造方法，初始化视频地址列表
    public VideoAdapter(List<String> videoPath) {
        mVideoPathList = videoPath;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // 声明cardView，它是每个item的根布局
        CardView cardView;
        // 声明CardView中的子元素
        VideoView videoview;
        TextView videoname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 初始化
            cardView = (CardView) itemView;
            videoview = cardView.findViewById(R.id.vv_video_view);
            videoname = cardView.findViewById(R.id.tv_video_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 给RecyclerView填充卡片布局
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_card_view, parent, false);

        // 使用ViewHolder构造方法，初始化卡片布局
        final ViewHolder holder = new ViewHolder(view);

        // 每一张卡片的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String videoUrl = mVideoPathList.get(position);
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);
                intent.putExtra("videoUrl",videoUrl);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // 数据与视图之间进行绑定
        viewHolder.videoname.setText("视频"+position);
        viewHolder.videoview.setVideoURI(Uri.parse(mVideoPathList.get(position)));
        viewHolder.videoview.seekTo(10);
        viewHolder.videoview.pause();
    }

    @Override
    public int getItemCount() {
        // 这里必须返回正确的大小，否则Android无法知道该初始化多少个视图
        return mVideoPathList.size();
    }
}
