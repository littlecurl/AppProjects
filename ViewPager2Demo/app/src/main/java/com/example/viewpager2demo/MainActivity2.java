package com.example.viewpager2demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    List<Integer> integerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_viewpager2);

        ViewPager2 viewPager2=findViewById(R.id.viewpager2);
        integerList = initIntegerList();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this,integerList);
        viewPager2.setAdapter(adapter);
    }

    List<Integer> initIntegerList(){
        List<Integer> integerList = new ArrayList<>();
        for (int i=10; i>=0; i--){
            integerList.add(i);
        }
        return integerList;
    }
}
