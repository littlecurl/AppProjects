package cn.edu.heuet.demo.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import cn.edu.heuet.demo.R;
import cn.edu.heuet.demo.databinding.ActivityDemoBinding;

public class SQLiteActivity extends AppCompatActivity {


    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqliteDb;
    private ActivityDemoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);

        sqliteDemo();
    }

    private void sqliteDemo() {
        // 1.初始化数据库，建库建表
        dbHelper = new DatabaseHelper(this);
        sqliteDb = dbHelper.getWritableDatabase();

        // 初始化学生信息按钮
        binding.btInitStudentInf.setOnClickListener(v -> initStudentData());


        // 增
        binding.btInsert.setOnClickListener(v -> {
            long result = dbHelper.insertStudentInf(sqliteDb, getValues());
            if (result == -1) {
                Toast.makeText(this, "新增失败，有可能是主键冲突 或 必传参数缺失", Toast.LENGTH_SHORT).show();
            }
        });

        // 删
        binding.btDelete.setOnClickListener(v -> {
            int result = dbHelper.deleteByStudentNo(sqliteDb, getStudentNo());
            if (result == -1) {
                Toast.makeText(this, "删除失败，主键不存在", Toast.LENGTH_SHORT).show();
            }
        });

        // 改
        binding.btUpdate.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("name", getStudentName());
            int result = dbHelper.updateByStudentNo(sqliteDb, values, getStudentNo());
            if (result == -1) {
                Toast.makeText(this, "修改失败，主键不存在", Toast.LENGTH_SHORT).show();
            }
        });

        // 查
        binding.btQuery.setOnClickListener(v -> {
            String studentNo = getStudentNo();
            String name = getStudentName();
            String result = "";
            if (!TextUtils.isEmpty(studentNo)) {
                result = dbHelper.getNameByStudentNo(sqliteDb, studentNo);
            } else if (!TextUtils.isEmpty(name)) {
                result = dbHelper.getStudentNoByName(sqliteDb, name);
            } else {
                result = dbHelper.getAll(sqliteDb);
            }


            binding.tvResult.setText("");
            if (TextUtils.isEmpty(result)) {
                binding.tvResult.setText("查询结果为空");
            } else {
                binding.tvResult.setText(result);
            }
        });
    }

    private ContentValues getValues() {
        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(getStudentNo())) {
            values.put("student_no", getStudentNo());
        }
        if (!TextUtils.isEmpty(getStudentName())) {
            values.put("name", getStudentName());
        }
        return values;
    }

    private String getStudentNo() {
        return binding.etStudentNo.getText().toString();
    }

    private String getStudentName() {
        return binding.etStudentName.getText().toString();
    }

    private void initStudentData() {
        // 构建几个学生信息
        ContentValues student1 = new ContentValues();
        student1.put("student_no", "sno2021001");
        student1.put("name", "littlecurl");

        ContentValues student2 = new ContentValues();
        student2.put("student_no", "sno2021002");
        student2.put("name", "刘同学");

        ContentValues student3 = new ContentValues();
        student3.put("student_no", "sno2021003");
        student3.put("name", "丹尼斯·里奇");

        ContentValues student4 = new ContentValues();
        student4.put("student_no", "sno2021004");
        student4.put("name", "肯·汤姆逊");

        ContentValues student5 = new ContentValues();
        student5.put("student_no", "sno2021005");
        student5.put("name", "longway777");

        // 插入学生信息
        dbHelper.insertStudentInf(sqliteDb, student1);
        dbHelper.insertStudentInf(sqliteDb, student2);
        dbHelper.insertStudentInf(sqliteDb, student3);
        dbHelper.insertStudentInf(sqliteDb, student4);
        dbHelper.insertStudentInf(sqliteDb, student5);
    }

}