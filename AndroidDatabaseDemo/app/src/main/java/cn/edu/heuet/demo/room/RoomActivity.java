package cn.edu.heuet.demo.room;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.demo.MyApp;
import cn.edu.heuet.demo.R;
import cn.edu.heuet.demo.databinding.ActivityDemoBinding;
import cn.edu.heuet.demo.room.entity.StudentInf;
import cn.edu.heuet.demo.room.executors.AppExecutors;

public class RoomActivity extends AppCompatActivity {


    private ActivityDemoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setTitle("Room");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);

        roomDemo();
    }


    private void roomDemo() {
        // 初始化按钮
        binding.btInitStudentInf.setOnClickListener(v -> initStudentData());

        // 增
        binding.btInsert.setOnClickListener(v -> new AppExecutors().getDiskIO().execute(() -> {
//            StudentInf studentInf = new StudentInf(getStudentNo(), getStudentName());
//            MyApp.roomDb.studentDao().insert(studentInf);
            long result = MyApp.roomDb.studentDao().insertBySql(getStudentNo(), getStudentName());
            if (result == -1) {
                runOnUiThread(() -> Toast.makeText(RoomActivity.this, "新增失败，有可能是主键冲突 或 必传参数缺失", Toast.LENGTH_SHORT).show());
            }
        }));

        // 删
        binding.btDelete.setOnClickListener(v -> new AppExecutors().getDiskIO().execute(() -> {
            StudentInf studentInf = new StudentInf(getStudentNo(), getStudentName());
            MyApp.roomDb.studentDao().delete(studentInf);
        }));

        // 改
        binding.btUpdate.setOnClickListener(v -> new AppExecutors().getDiskIO().execute(() -> {
            StudentInf studentInf = new StudentInf(getStudentNo(), getStudentName());
            MyApp.roomDb.studentDao().update(studentInf);
        }));

        // 查
        binding.btQuery.setOnClickListener(v -> new AppExecutors().getDiskIO().execute(() -> {
            String result;
            if (!TextUtils.isEmpty(getStudentNo())) {
                result = MyApp.roomDb.studentDao().getNameByStudentNo(getStudentNo());
            } else if (!TextUtils.isEmpty(getStudentName())) {
                result = MyApp.roomDb.studentDao().getStudentNoByName(getStudentName());
            } else {
                result = MyApp.roomDb.studentDao().getAllString();
            }

            runOnUiThread(() -> {
                binding.tvResult.setText("");
                if (TextUtils.isEmpty(result)) {
                    binding.tvResult.setText("查询结果为空");
                } else {
                    binding.tvResult.setText(result);
                }
            });
        }));

    }

    private String getStudentNo() {
        return binding.etStudentNo.getText().toString();
    }

    private String getStudentName() {
        return binding.etStudentName.getText().toString();
    }


    private void initStudentData() {
        // 构建几个学生信息
        StudentInf studentInf1 = new StudentInf("sno2021001", "littlecurl");
        StudentInf studentInf2 = new StudentInf("sno2021002", "刘同学");
        StudentInf studentInf3 = new StudentInf("sno2021003", "丹尼斯·里奇");
        StudentInf studentInf4 = new StudentInf("sno2021004", "肯·汤姆逊");
        StudentInf studentInf5 = new StudentInf("sno2021005", "longway777");

        List<StudentInf> students = new ArrayList<>();
        students.add(studentInf1);
        students.add(studentInf2);
        students.add(studentInf3);
        students.add(studentInf4);
        students.add(studentInf5);
        // 插入学生信息
        new AppExecutors().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                MyApp.roomDb.studentDao().insertAll(students);
            }
        });
    }

}