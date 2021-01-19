package cn.edu.heuet.demo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/*
    参考：《SQLite 语法》https://www.runoob.com/sqlite/sqlite-syntax.html
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo_sqlite";
    private final String STUDENT_INF = "student_inf_sqlite";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * 建库
     *
     * @param context 上下文
     * @param name    数据库名
     */
    public DatabaseHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 建表
     *
     * @param db 数据库变量
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库sql语句 并 执行
        String studentSql = "CREATE TABLE " + STUDENT_INF + "(" +
                // 学生学号
                "student_no     varchar(20)      PRIMARY KEY    NOT NULL," +
                // 学生姓名
                "name           varchar(20)                     NOT NULL," +
                "create_time    DATETIME         DEFAULT CURRENT_TIMESTAMP," +
                "update_time    DATETIME         DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(studentSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 增
     *
     * @param tableName 表名
     * @param values    字段名、值 k v
     * @return 影响行数
     */
    private long insert(SQLiteDatabase db, String tableName, ContentValues values) {
        return db.insertWithOnConflict(tableName, null, values, 4);
    }

    public long insertStudentInf(SQLiteDatabase db, ContentValues values) {
        String studentNo = values.getAsString("student_no");
        String name = values.getAsString("name");
        if (TextUtils.isEmpty(studentNo) || TextUtils.isEmpty(name)) {
            return -1;
        }
        return insert(db, STUDENT_INF, values);
    }

    /**
     * 删
     *
     * @param studentNos 学生学号
     * @return 影响行数
     */
    public int deleteByStudentNo(SQLiteDatabase db, String... studentNos) {
        return db.delete(STUDENT_INF, "student_no=?", studentNos);
    }

    /**
     * 改
     *
     * @param values     修改字段名、值 k v
     * @param studentNos 学号，支持批量修改
     * @return 影响行数
     */
    public int updateByStudentNo(SQLiteDatabase db, ContentValues values, String... studentNos) {
        return db.update(STUDENT_INF, values, "student_no=?", studentNos);
    }

    /**
     * 查
     *
     * @param studentNos 学号
     * @return 学生姓名
     */
    public String getNameByStudentNo(SQLiteDatabase db, String... studentNos) {
        Cursor cursor = db.query(STUDENT_INF, new String[]{"name"}, "student_no=?", studentNos, null, null, null);
        StringBuilder nameBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            nameBuilder.append(name).append("\n");
        }
        cursor.close(); // 关闭游标，释放资源
        return nameBuilder.toString();
    }

    /**
     * 查
     *
     * @param name 姓名
     * @return 学生姓名
     */
    public String getStudentNoByName(SQLiteDatabase db, String... name) {
        Cursor cursor = db.query(STUDENT_INF, new String[]{"student_no"}, "name=?", name, null, null, null);
        StringBuilder studentNoBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String studentNo = cursor.getString(cursor.getColumnIndex("student_no"));
            studentNoBuilder.append(studentNo).append("\n");
        }
        cursor.close(); // 关闭游标，释放资源
        return studentNoBuilder.toString();
    }

    public String getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(STUDENT_INF, null, null, null, null, null, null);
        StringBuilder studentBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String studentNo = cursor.getString(cursor.getColumnIndex("student_no"));
            studentBuilder.append("学号：").append(studentNo).append("\n\n");
            String name = cursor.getString(cursor.getColumnIndex("name"));
            studentBuilder.append("姓名：").append(name).append("\n\n");
            studentBuilder.append("----------------\n\n");
        }
        cursor.close(); // 关闭游标，释放资源
        return studentBuilder.toString();
    }

    public String getNameByStudentNoWithSql(SQLiteDatabase db, String... teacherNos) {
        StringBuilder studentNoBuilder = new StringBuilder();
        for (String teacherNo : teacherNos) {
            studentNoBuilder.append(teacherNo).append(",");
        }
        String no = "";
        if (studentNoBuilder.toString().contains(",")) {
            no = studentNoBuilder.substring(0, studentNoBuilder.length() - 2);
        }
        String sql = "SELECT name" +
                " FROM " + STUDENT_INF +
                " WHERE teacher_no IN(" +
                no +
                ");";
        Cursor cursor = db.rawQuery(sql, null);
        StringBuilder nameBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            nameBuilder.append(name).append("\n");
        }
        cursor.close(); // 关闭游标，释放资源
        return nameBuilder.toString();
    }

}