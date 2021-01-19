package cn.edu.heuet.demo.mysql;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;

import cn.edu.heuet.demo.R;
import cn.edu.heuet.demo.databinding.ActivityDemoBinding;
import cn.edu.heuet.demo.mysql.viewmodel.MysqlViewModel;
import cn.edu.heuet.demo.room.entity.StudentInf;

public class MySQLActivity extends AppCompatActivity {

    private ActivityDemoBinding binding;
    private MysqlViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);

        mysqlDemo();
    }

    private void mysqlDemo() {
        viewModel = new ViewModelProvider(this).get(MysqlViewModel.class);

        // 初始化数据
        binding.btInitStudentInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.initStudent(new SimpleCallBack<Long>() {
                    @Override
                    public void onSuccess(Long response) throws Throwable {
                        if (response == -1) {
                            Toast.makeText(MySQLActivity.this, "新增失败，有可能是主键冲突 或 必传参数缺失", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });
            }
        });

        // 新增
        binding.btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insert(getStudentInf(), new SimpleCallBack<Long>() {
                    @Override
                    public void onSuccess(Long result) throws Throwable {
                        if (result == -1) {
                            Toast.makeText(MySQLActivity.this, "新增失败，有可能是主键冲突 或 必传参数缺失", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        Toast.makeText(getApplication(), e.getDisplayMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 删除
        binding.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(getStudentNo(), new SimpleCallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer response) throws Throwable {

                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });
            }
        });

        // 修改
        binding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(getStudentInf(), new SimpleCallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer response) throws Throwable {

                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });
            }
        });


        // 查询
        binding.btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.query(getStudentInf(), new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String result) throws Throwable {
                        binding.tvResult.setText("");
                        if (TextUtils.isEmpty(result)) {
                            binding.tvResult.setText("查询结果为空");
                        } else {
                            binding.tvResult.setText(result);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });
            }
        });
    }

    private StudentInf getStudentInf() {
        return new StudentInf(getStudentNo(), getStudentName());
    }

    private String getStudentNo() {
        return binding.etStudentNo.getText().toString();
    }

    private String getStudentName() {
        return binding.etStudentName.getText().toString();
    }

}