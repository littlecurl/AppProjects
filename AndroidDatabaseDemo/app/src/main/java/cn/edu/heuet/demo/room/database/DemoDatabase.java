package cn.edu.heuet.demo.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cn.edu.heuet.demo.room.dao.StudentDao;
import cn.edu.heuet.demo.room.entity.StudentInf;


@Database(entities = {StudentInf.class}, version = 1, exportSchema = false)
public abstract class DemoDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
}
