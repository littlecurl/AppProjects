package com.example.sqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //根据Layout按钮id得到Java按钮对象
        Button insert = (Button) findViewById(R.id.insert);
        Button insert_cleardata = (Button) findViewById(R.id.insert_cleardata);

        Button update = (Button) findViewById(R.id.update);
        Button update_cleardata = (Button)findViewById(R.id.update_cleardata);

        Button delete = (Button) findViewById(R.id.delete);
        Button delete_cleardata = (Button)findViewById(R.id.delete_cleardata);

        Button query = (Button) findViewById(R.id.query);
        Button clearquery = (Button)findViewById(R.id.clear_query);

        //为所有按钮对象设置监听器
        insert.setOnClickListener(this);
        insert_cleardata.setOnClickListener(this);

        update.setOnClickListener(this);
        update_cleardata.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_cleardata.setOnClickListener(this);

        query.setOnClickListener(this);
        clearquery.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        //因为响应点击按钮事件时要操作文本输入框中的内容
        // 所以要获取相应文本输入框的对象及其中输入内容
        EditText insert_edittext = (EditText)findViewById(R.id.inset_edittext);
        String insert_data = insert_edittext.getText().toString();

        EditText delete_edittext = (EditText)findViewById(R.id.delete_edittext);
        String delete_data = delete_edittext.getText().toString();

        EditText update_before_edittext = (EditText)findViewById(R.id.update_before_edittext);
        String update_before_data = update_before_edittext.getText().toString();
        EditText update_after_edittext = (EditText)findViewById(R.id.update_after_edittext);
        String update_after_data = update_after_edittext.getText().toString();

        TextView textview = (TextView)findViewById(R.id.textview);

        //依靠DatabaseHelper的构造函数创建数据库
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "test_db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //根据响应Click的按钮id进行选择操作
        switch(v.getId()){
            //插入数据按钮
            case R.id.insert:
                //创建存放数据的ContentValues对象
                ContentValues values = new ContentValues();
                values.put("name",insert_data);
                //数据库执行插入命令
                db.insert("user", null, values);
                break;
            //插入数据按钮后面的清除按钮
            case R.id.insert_cleardata:
                insert_edittext.setText("");
                break;

            //删除数据按钮
            case R.id.delete:
                db.delete("user", "name=?", new String[]{delete_data});
                break;
            //删除数据按钮后面的清除按钮
            case R.id.delete_cleardata:
                delete_edittext.setText("");
                break;

            //更新数据按钮
            case R.id.update:
                ContentValues values2 = new ContentValues();
                values2.put("name", update_after_data);
                db.update("user", values2, "name = ?", new String[]{update_before_data});
                break;
            //更新数据按钮后面的清除按钮
            case R.id.update_cleardata:
                update_before_edittext.setText("");
                update_after_edittext.setText("");
                break;

            //查询全部按钮
            case R.id.query:
                //创建游标对象
                Cursor cursor = db.query("user", new String[]{"name"}, null, null, null, null, null);
                //利用游标遍历所有数据对象
                //为了显示全部，把所有对象连接起来，放到TextView中
                String textview_data = "";
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    textview_data = textview_data + "\n" + name;
                }
                textview.setText(textview_data);
				// 关闭游标，释放资源
				cursor.close();
                break;
            //查询全部按钮下面的清除查询按钮
            case R.id.clear_query:
                textview.setText("");
                textview.setHint("查询内容为空");
                break;

            default:
                break;
        }
    }
}
