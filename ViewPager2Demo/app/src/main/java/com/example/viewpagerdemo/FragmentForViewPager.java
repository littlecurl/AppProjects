package com.example.viewpagerdemo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.viewpager2demo.R;


public class FragmentForViewPager extends Fragment {

    private int x;

    public FragmentForViewPager() {
        // Required empty public constructor
    }

    FragmentForViewPager(int x){
        this.x = x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_for_viewpager, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(String.valueOf(x));
        return view;

    }

}
