package cn.edu.heuet.demo.mysql.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.CallBack;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.demo.MyApp;
import cn.edu.heuet.demo.mysql.network.NetworkConstant;
import cn.edu.heuet.demo.room.RoomActivity;
import cn.edu.heuet.demo.room.entity.StudentInf;

/**
 * @ClassName MysqlViewModel
 * @Author littlecurl
 * @Date 2021/1/19 2:54
 * @Version 1.0.0
 * @Description TODO
 */
public class MysqlViewModel extends AndroidViewModel {

    public MysqlViewModel(@NonNull Application application) {
        super(application);
    }

    public void initStudent(CallBack callBack) {
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

        XHttp.post(NetworkConstant.BATCH_INSERT_URL)
                .upObjectAsJson(students)
                .execute(callBack);
    }

    public void insert(StudentInf studentInf, CallBack callBack) {
        XHttp.post(NetworkConstant.INSERT_URL)
                .upObjectAsJson(studentInf)
                .execute(callBack);
    }

    public void delete(String studentNo, CallBack callBack) {
        XHttp.post(NetworkConstant.DELETED_URL)
                .params("studentNo", studentNo)
                .execute(callBack);
    }

    public void update(StudentInf studentInf, CallBack callBack) {
        XHttp.post(NetworkConstant.DELETED_URL)
                .upObjectAsJson(studentInf)
                .execute(callBack);
    }

    public void query(StudentInf studentInf, CallBack callBack) {
        XHttp.get(NetworkConstant.QUERY_URL)
                .params("studentNo", studentInf.getStudentNo())
                .params("name", studentInf.getName())
                .execute(callBack);
    }

}
