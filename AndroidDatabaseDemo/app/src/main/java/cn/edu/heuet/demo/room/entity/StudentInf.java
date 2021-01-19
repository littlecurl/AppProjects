package cn.edu.heuet.demo.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "student_inf_room", indices = {@Index(value = "student_no")})
public class StudentInf {
    @PrimaryKey
    @ColumnInfo(name = "student_no")
    @NonNull
    private String studentNo;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @Ignore
    public StudentInf() {
    }


    public StudentInf(String studentNo, String name) {
        this.studentNo = studentNo;
        this.name = name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
