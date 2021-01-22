package cn.edu.heuet.demo.litepal;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.litepal.LitePal;
import org.litepal.Operator;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.demo.R;
import cn.edu.heuet.demo.databinding.ActivityDemoBinding;
import cn.edu.heuet.demo.litepal.model.Student;

public class LitePalActivity extends AppCompatActivity {

    private ActivityDemoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LitePal");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);

        litepalDemo();
    }

    private void litepalDemo() {
        // 初始化学生信息按钮
        binding.btInitStudentInf.setOnClickListener(v -> initStudentData());


        // 增
        binding.btInsert.setOnClickListener(v -> {
            Student student = new Student(getStudentNo(), getStudentName());
            boolean success = student.save();
            if (!success) {
                Toast.makeText(this, "新增失败，有可能是主键冲突 或 必传参数缺失", Toast.LENGTH_SHORT).show();
            }
        });

        // 删
        binding.btDelete.setOnClickListener(v -> {
            try {
                // 第一种：以问号传参，字符串参数不需要手动加单引号
                // 注意 LitePal 的数据字段默认都会全部转为小写
                int rowsAffected = LitePal.deleteAll(Student.class, "studentno=?", getStudentNo());
                if (rowsAffected == -1) {
                    Toast.makeText(this, "删除失败，主键不存在", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
            }
        });

        // 改
        binding.btUpdate.setOnClickListener(v -> {
            Student student = new Student(getStudentNo(), getStudentName());
            // 第二种：拼接参数，字符串参数需要手动加引号，但不建议使用，因为有SQL注入的风险
//            int result = student.updateAll("studentno='" + getStudentNo() + "'");
            int result = student.updateAll("studentno=?", getStudentNo());
//            ContentValues values = new ContentValues();
//            values.put("name",getStudentName());
//            Operator.updateAll(Student.class,values,"studentno=?", getStudentNo());
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
                Cursor cursor = LitePal.findBySQL("SELECT name FROM student WHERE studentno = ?", studentNo);
                StringBuilder nameBuilder = new StringBuilder();
                while (cursor.moveToNext()) {
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    nameBuilder.append(name).append("\n");
                }
                cursor.close(); // 关闭游标，释放资源
                result = nameBuilder.toString();
            } else if (!TextUtils.isEmpty(name)) {
                Cursor cursor = LitePal.findBySQL("SELECT studentno FROM student WHERE name = ?", name);
                StringBuilder studentNoBuilder = new StringBuilder();
                while (cursor.moveToNext()) {
                    studentNo = cursor.getString(cursor.getColumnIndex("studentno"));
                    studentNoBuilder.append(studentNo).append("\n");
                }
                cursor.close(); // 关闭游标，释放资源
                result = studentNoBuilder.toString();
            } else {
                Cursor cursor = LitePal.findBySQL("SELECT * FROM student");
                StringBuilder studentBuilder = new StringBuilder();
                while (cursor.moveToNext()) {
                    studentNo = cursor.getString(cursor.getColumnIndex("studentno"));
                    studentBuilder.append("学号：").append(studentNo).append("\n\n");
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    studentBuilder.append("姓名：").append(name).append("\n\n");
                    studentBuilder.append("----------------\n\n");
                }
                cursor.close(); // 关闭游标，释放资源
                result = studentBuilder.toString();
            }


            binding.tvResult.setText("");
            if (TextUtils.isEmpty(result)) {
                binding.tvResult.setText("查询结果为空");
            } else {
                binding.tvResult.setText(result);
            }
        });
    }

    private String getStudentNo() {
        return binding.etStudentNo.getText().toString();
    }

    private String getStudentName() {
        return binding.etStudentName.getText().toString();
    }

    private void initStudentData() {
        // 构建几个学生信息
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1, "sno2021001", "littlecurl");
        Student student2 = new Student(2, "sno2021002", "刘同学");
        Student student3 = new Student(3, "sno2021003", "丹尼斯·里奇");
        Student student4 = new Student(4, "sno2021004", "肯·汤姆逊");
        Student student5 = new Student(5, "sno2021005", "longway777");
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        LitePal.deleteAll(Student.class);
//        Operator.deleteAll(Student.class);
//        student1.save();
//        student2.save();
//        student3.save();
//        student4.save();
//        student5.save();
        Operator.saveAll(students);
    }
}
