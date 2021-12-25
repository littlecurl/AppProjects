package com.example.weatherdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherdemo.bean.Body;
import com.example.weatherdemo.bean.Forecast;
import com.example.weatherdemo.bean.ResponseData;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.CallBackProxy;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;

import java.util.Arrays;
import java.util.List;

import cn.edu.heuet.editspinner.EditSpinner;

public class MainActivity extends AppCompatActivity {
    private String cityName;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1、读取 XML 数据
        String[] cityCodeArray = getResources().getStringArray(R.array.cityCode);

        String[] cityNameArray = getResources().getStringArray(R.array.cityName);
        List<String> cityNameList = Arrays.asList(cityNameArray);

        // 2、将数据填充给 Spinner 控件
        EditSpinner editSpinner = findViewById(R.id.editspinner);
        editSpinner.setItemData(cityNameList);

        tvContent = findViewById(R.id.tv_content);
        Button btSearch = findViewById(R.id.bt_search);

        // 3、按钮点击响应事件
        btSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 防止快速点击
                if (DoubleClickHelper.isOnDoubleClick(3000)) {
                    Toast.makeText(MainActivity.this, "查询太频繁，手别抖！", Toast.LENGTH_SHORT).show();
                    return;
                }
                cityName = editSpinner.getText();
                if (TextUtils.isEmpty(cityName)) {
                    return;
                }
                // 根据填写的城市名称查到对应的城市代码
                int index = cityNameList.indexOf(cityName);
                if (index == -1) {
                    Toast.makeText(MainActivity.this, "输入城市名称未录入系统,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://t.weather.itboy.net/api/weather/city/" + cityCodeArray[index];
                // Xhttp2 请求网络
                XHttp.get(url)
                        .syncRequest(false)
                        .execute(new CallBackProxy<Body<ResponseData>, ResponseData>(
                                new SimpleCallBack<ResponseData>() {
                                    @Override
                                    public void onSuccess(ResponseData data) {
                                        // 处理 data
                                        dealWithData(data);
                                    }

                                    @Override
                                    public void onError(ApiException e) {
                                        // 处理异常
                                    }
                                }
                        ) {
                        });//最后一定要有 {} 否则解析失败 作者：为中华之崛起而敲代码 https://www.bilibili.com/read/cv8476993?spm_id_from=333.999.0.0 出处：bilibili

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void dealWithData(ResponseData data) {
        if (data == null) {
            Toast.makeText(MainActivity.this, "结果异常！", Toast.LENGTH_SHORT).show();
            return;
        }
        Forecast todayForecast = data.getForecast().get(0);
        String today = buildForecastString(todayForecast, "今天");

        Forecast tomorrowForecast = data.getForecast().get(1);
        String tomorrow = buildForecastString(tomorrowForecast, "明天");

        tvContent.setText(today + tomorrow);
    }

    private String buildForecastString(Forecast forecast, String day) {
        return day + "【" + cityName + "】天气" + "\n" +
                "日期：" + forecast.getYmd() + "丨" +
                forecast.getWeek() + ";\n" +
                "天气类型：" + forecast.getType() + ";\n" +
                "最高温度：" + forecast.getHigh() + ";\n" +
                "最低温度：" + forecast.getLow() + ";\n" +
                "刮风情况：" + forecast.getFl() + "丨" +
                forecast.getFx() +
                ";\n" +
                "天气建议：" + forecast.getNotice() + ";\n\n\n";
    }

}