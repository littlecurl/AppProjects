package com.example.uploadtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private Button bt_chose;
    private Button bt_submit;
    private String photoPath;
    private Bitmap bitmap;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_chose = findViewById(R.id.bt_chose);
        textView1 = findViewById(R.id.textView1);
        bt_submit = findViewById(R.id.bt_submit);
        photo = findViewById(R.id.photo);

        bt_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择图片
                chooseImage();
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 上传图片
                String url = "http://10.0.2.2:8080/login_backend_war/files/upload";
                upload(url);
            }
        });

    }

    private void chooseImage() {
        //打开系统提供的图片选择界面
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        //传参以在返回本界面时触发加载图片的功能
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 0x1);
    }

    // 选择图片结束
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    // 获取图片URI
                    Uri uri = data.getData();
                    // 将URI转换为路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);                //  这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);
                    // 压缩成800*480
                    bitmap = BitmapUtils.decodeSampledBitmapFromFd(photoPath, 200, 200);
                    // 设置imageview显示图片
                    photo.setImageBitmap(bitmap);
                    // 设置textview显示图片名
                    textView1.setText(photoPath.substring(photoPath.lastIndexOf("/")).substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void upload(String url) {
        // 将bitmap转为string，并使用BASE64编码达到简单加密目的
        String photo = Utils.BitmapToString(bitmap);
        // 获取到图片的名字
        String name = photoPath.substring(photoPath.lastIndexOf("/")).substring(1);
        // new一个请求参数
        RequestParams params = new RequestParams();
        // 将图片和名字添加到参数中
        params.put("photo", photo);
        params.put("name", name);
        AsyncHttpClient client = new AsyncHttpClient();
        // 调用AsyncHttpClient的post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "上传成功!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "上传失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
