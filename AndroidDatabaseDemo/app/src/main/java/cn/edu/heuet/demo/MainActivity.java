package cn.edu.heuet.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import cn.edu.heuet.demo.databinding.ActivityMainBinding;
import cn.edu.heuet.demo.mysql.MySQLActivity;
import cn.edu.heuet.demo.room.RoomActivity;
import cn.edu.heuet.demo.sqlite.SQLiteActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btSqlite.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SQLiteActivity.class)));
        binding.btRoom.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RoomActivity.class)));
        binding.btMysql.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MySQLActivity.class)));
    }


}