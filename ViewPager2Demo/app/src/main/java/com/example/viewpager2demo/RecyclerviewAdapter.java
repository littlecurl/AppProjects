package com.example.viewpager2demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private Context context;
    private List<Integer> integerList;

    RecyclerviewAdapter() {
    }

    RecyclerviewAdapter(Context context, List<Integer> integerList) {
        this.context = context;
        this.integerList = integerList;
    }

    /**
     * 引入布局
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_viewpager2,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // 这里必须先声明后初始化
        // 否则外部外部无法引用
        private final TextView textView2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }

    /**
     * 拿到视图之后
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "这里是点击每一行item的响应事件"+position,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        holder.textView2.setText(String.valueOf(integerList.get(position)));
    }

    @Override
    public int getItemCount() {
        return integerList.size();
    }


}