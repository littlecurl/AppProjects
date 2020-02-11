package cn.edu.heuet.viewpager2demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/**
 * @ClassName MainActivity
 * @Author littlecurl
 * @Date 2020/1/21 15:56
 * @Version 1.0.0
 * @Description TODO
 */
public class MainActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        ViewPager2 vp2 = findViewById(R.id.view_pager);
        vp2.setOffscreenPageLimit(10);
        View view = vp2.getChildAt(0);
        if (view == null) {
            throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        } else {
            RecyclerView recyclerView = (RecyclerView) view;
            int padding = 100;
            recyclerView.setPadding(padding, 0, padding, 0);
            recyclerView.setClipToPadding(false);
            vp2.setAdapter(new Adapter());
        }
    }

    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public ViewHolder(@NotNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_pages, parent, false));
        }
    }

    public static final class Adapter extends androidx.recyclerview.widget.RecyclerView.Adapter {
        public int getItemCount() {
            return 10;
        }

        @NotNull
        public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        public void onBindViewHolder(@NotNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setTag(position);
        }
    }
}
