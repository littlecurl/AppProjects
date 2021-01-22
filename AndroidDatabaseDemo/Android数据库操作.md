# Android数据库操作

需求：Android操作数据库的增、删、改、查

实现：

1、新增学生基本信息

2、删除学生基本信息

3、修改学生基本信息

4、根据学号查姓名、根据姓名查学号、查询所有学生基本信息

运行展示

## 前置条件

1、SQLite不需要第三方依赖

2、Room

```groovy
implementation "androidx.room:room-runtime:2.2.5"
annotationProcessor "androidx.room:room-compiler:2.2.5"
```

3、LitePal

```groovy
implementation 'org.litepal.guolindev:core:3.2.2'
```

4、MySQL

```groovy
implementation project(':xhttp2-lib')

implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
implementation "android.arch.lifecycle:runtime:1.1.1"
```



源码

Github地址：

https://github.com/littlecurl/AppProjects/blob/master/AndroidDatabaseDemo.zip

Gitee地址：

https://gitee.com/littlecurl/AppProjects/blob/master/AndroidDatabaseDemo.zip

## 一、本地

[SQLite](https://www.runoob.com/sqlite/sqlite-tutorial.html)、Room、LitePal

### 0、建库建表

#### 0.1 SQLite 建库建表相关代码

```java
// SQLite建库建表
// 建库：DatabaseHelper 构造方法传入数据库名称
public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
    super(context, name, factory, version);
}
// 在 Application 中进行初始化
public static SQLiteDatabase sqliteDb;
public static DatabaseHelper dbHelper;

dbHelper = new DatabaseHelper(this);
sqliteDb = dbHelper.getWritableDatabase();

// 建表：DatabaseHelper.onCreate() 方法中执行 SQL 语句
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
```

#### 0.2 Room 建库建表相关代码

```java
// ROOM 建库建表
// 建库：使用 @Database 注解
@Database(entities = {StudentInf.class}, version = 1, exportSchema = false)
public abstract class DemoDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
}
// 在Application中调用databaseBuilder()传入数据库名，进行初始化
public static DemoDatabase roomDb;
roomDb = Room.databaseBuilder(this, DemoDatabase.class, "demo_room").build();

// 建表：使用 @Entity 注解
// Entity，又叫 JavaBean，也叫 POJO
// 如果一个类，只有一些属性和getter()、setter()、constructor()、toString()、equals()等方法，即可称之为Entity
// 细分的话，还可以分成：DO(Data Object)、VO(View Object)、Model
@Entity(tableName = "student_inf_room", indices = {@Index(value = "student_no")})
public class StudentInf {
    @PrimaryKey
    @ColumnInfo(name = "student_no")
    @NonNull
    private String studentNo;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    // 省略getter()、setter()、constructor()、toString()、equals()
}
```

#### 0.3 LitePal 建库建表相关代码

```java
// LitePal建库建表
// 建库：在 assets 目录下新建 litepal.xml 配置数据库名、表相关的Model
<?xml version="1.0" encoding="utf-8"?>
<litepal>
    <dbname value="demo_litepal" />
    
    <version value="1" />
    
    <list>
        <mapping class="cn.edu.heuet.demo.litepal.model.Student" />
    </list>
            
    <storage value="external" />
</litepal>
// 然后在 Application 中初始化
// 内部调用了 Operator.initialize(context) ，所以很多操作也可以使用 Operator 
LitePal.initialize(this);

// 建表：model类 extends LitePalSupport
public class Student extends LitePalSupport {
    // LitePal默认会创建上id字段，所以这里不写也行
    private long id;
    // 注意，LitePal会把所有驼峰命名转为统一小写字母
    private String studentNo;
    private String name;
	// 省略getter()、setter()、constructor()、toString()、equals()
}
```

#### 0.4 MySQL 建库建表相关代码

这里不做过多介绍，更多请参考相关书籍、视频、[博客](https://www.liaoxuefeng.com/wiki/1177760294764384)



### 1、新增

​	单个新增、批量新增

​	冲突解决策略（根据返回值判断是否新增成功）

#### 1.1 SQLite 新增相关代码

```java
// SQLite新增：
// 单个新增：DatabaseHelper 中调用 insertWithOnConflict 可以指定冲突策略，这里的 4 表示 " OR IGNORE "
// CONFLICT_VALUES{"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
db.insertWithOnConflict(tableName, null, contentValues, 4);

// 批量新增
多次调用 insertWithOnConflict()
```

#### 1.2 Room 新增相关代码

```java
// 单个新增：StudentDao 中使用 @Insert 注解，可以指定冲突策略
@Insert(onConflict = OnConflictStrategy.IGNORE)
void insert(StudentInf studentInf);

// 批量新增：只需要将参数类型修改为 集合 即可
@Insert(onConflict = OnConflictStrategy.IGNORE)
void insertAll(List<StudentInf> studentInfs);
```

#### 1.3 LitePal 新增相关代码

```java
// 单个新增：初始化 Model 对象完成后，调用父类 save() 方法即可
Student student1 = new Student(1, "sno2021001", "littlecurl");
student1.save();

// 批量新增
多次调用 save() 方法
// 或者使用Operator的saveAll()
Operator.saveAll(students);
```

#### 1.4 MySQL 新增相关代码

这里不做过多介绍，更多请参考相关书籍、视频、[博客](https://www.liaoxuefeng.com/wiki/1177760294764384)

### 2、删除

​	根据主键删除

#### 2.1 SQLite 删除相关代码

```java
// SQLite的删除，在 DatabaseHelper 中调用 delete()
db.delete(STUDENT_INF, "student_no=?", studentNos);
```

#### 2.2 Room 删除相关的代码

```java
// Room的删除，在 StudentDao 中使用 @Delete 注解
@Delete
void delete(StudentInf studentInf);
```

#### 2.3 LitePal 删除相关代码

```java
// 调用 LitePal.deleteAll()
LitePal.deleteAll(Student.class, "studentno=?", getStudentNo());
// 或者调用 Operator.deleteAll()
Operator.deleteAll(Student.class, "studentno=?", getStudentNo());

// 不传任何参数即表示清空表
LitePal.deleteAll(Student.class);
Operator.deleteAll(Student.class);
```

#### 2.4 MySQL 删除相关代码

这里不做过多介绍，更多请参考相关书籍、视频、[博客](https://www.liaoxuefeng.com/wiki/1177760294764384)



### 3、修改 

​	根据主键修改

#### 3.1 SQLite 修改相关代码

```java
// SQLite 的修改：
// 在 DatabaseHelper 中调用 update()
db.update(STUDENT_INF, values, "student_no=?", studentNos);
```

#### 3.2 Room 修改相关代码

```java
// Room 的修改：
// 在 StudentDao 中使用 @Update 注解
@Update
void update(StudentInf studentInf);
```

#### 3.3 LitePal 修改相关代码

```java
// LitePal 的修改
// 初始化model后，调用 updateAll() 这里可以直接拼接参数
Student student = new Student(getStudentNo(), getStudentName());
student.updateAll("studentno=?", getStudentNo());
// 或者使用 Operator
ContentValues values = new ContentValues();
values.put("name",getStudentName());
Operator.updateAll(Student.class,values,"studentno=?", getStudentNo());
```

#### 3.4 MySQL 修改相关代码

这里不做过多介绍，更多请参考相关书籍、视频、[博客](https://www.liaoxuefeng.com/wiki/1177760294764384)



### 4、查询

​	单个查询、批量查询

#### 4.1 SQLite 查询相关代码

```java
// SQLite的查询：
// 在 DatabaseHelper 写好方法
public String getNameByStudentNo(SQLiteDatabase db, String... studentNos) {
    // (String table, String[] columns, 
    // String selection,String[] selectionArgs, String groupBy, String having,String orderBy) 
    Cursor cursor = db.query(STUDENT_INF, new String[]{"name"}, "student_no=?", studentNos, null, null, null);
    StringBuilder nameBuilder = new StringBuilder();
    while (cursor.moveToNext()) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        nameBuilder.append(name).append("\n");
    }
    cursor.close(); // 关闭游标，释放资源
    return nameBuilder.toString();
}
```

#### 4.2 Room 查询相关代码

```java
// Room的查询：
// 在 StudentDao 中使用 @Query 注解，执行 SQL
@Query("SELECT name FROM student_inf_room WHERE student_no = :studentNo")
String getNameByStudentNo(String studentNo);
```

#### 4.3 LitePal 查询相关代码

```java
// LitePal的查询
// 使用 LitePal.findBySQL() 执行 SQL
Cursor cursor = LitePal.findBySQL("SELECT name FROM student WHERE studentno = ?", studentNo);
StringBuilder nameBuilder = new StringBuilder();
while (cursor.moveToNext()) {
    name = cursor.getString(cursor.getColumnIndex("name"));
    nameBuilder.append(name).append("\n");
}
cursor.close(); // 关闭游标，释放资源
result = nameBuilder.toString();
```



#### 4.4 MySQL 查询相关代码

这里不做过多介绍，更多请参考相关书籍、视频、[博客](https://www.liaoxuefeng.com/wiki/1177760294764384)





## 二、远程

Xhttp2 + 花生壳 + Mysql



### Android端

1、打开项目之前修改版本号

2、AndroidManifest.xml 中配置允许 http 访问、配置 Application、配置 Activity

3、多模块引入方式

​	依赖引入无法修改源码，模块引入可以修改源码

4、dataBinding 介绍



### 花生壳

1、CMD黑窗口 查看 IPv4 地址：ipconfig

2、检测内外穿透是否成功

3、有时候需要重启



### 后端

1、MySQL 相关

​	版本

​	可视化工具

2、Maven 相关

​	版本

​	配置

3、MyBatis 与 SpringBoot 等内容请参考龙虾三少的课程[《SpringBoot构建电商基础秒杀项目》](https://www.imooc.com/view/1079)

