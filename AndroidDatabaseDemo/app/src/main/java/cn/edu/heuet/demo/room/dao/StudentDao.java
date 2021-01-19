package cn.edu.heuet.demo.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.edu.heuet.demo.MyApp;
import cn.edu.heuet.demo.room.entity.StudentInf;


@Dao
public interface StudentDao {

    @Query("SELECT * FROM student_inf_room")
    List<StudentInf> getAll();

    default String getAllString() {
        List<StudentInf> students = MyApp.roomDb.studentDao().getAll();
        StringBuilder studentBuilder = new StringBuilder();
        for (StudentInf studentInf : students) {
            studentBuilder.append("学号：").append(studentInf.getStudentNo()).append("\n\n");
            studentBuilder.append("姓名：").append(studentInf.getName()).append("\n\n");
            studentBuilder.append("----------------\n\n");
        }
        return studentBuilder.toString();
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<StudentInf> studentInfs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StudentInf studentInf);

    @Query("INSERT OR IGNORE INTO student_inf_room (`student_no`,`name`) VALUES(:studentNo,:name);")
    long insertBySql(String studentNo, String name);

    @Delete
    void delete(StudentInf studentInf);

    @Query("DELETE FROM student_inf_room where 1=1")
    void deleteAll();

    @Query("SELECT student_no FROM student_inf_room WHERE name = :name")
    String getStudentNoByName(String name);

    @Query("SELECT name FROM student_inf_room WHERE student_no = :studentNo")
    String getNameByStudentNo(String studentNo);

    @Update
    void update(StudentInf studentInf);
}
