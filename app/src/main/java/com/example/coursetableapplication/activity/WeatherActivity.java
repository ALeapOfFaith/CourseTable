package com.example.coursetableapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.coursetableapplication.R;
import com.example.coursetableapplication.util.WeatherPreview;
import com.example.coursetableapplication.util.WeatherUtils;

public class WeatherActivity extends AppCompatActivity {

    private String strUrl1="https://restapi.amap.com/v3/weather/weatherInfo?key=4b6e80b12df373a06ec3d5c4425f0e6d&city=371000&extensions=base&output=JSON";//实时天气API
    private String strUrl2="https://restapi.amap.com/v3/weather/weatherInfo?key=4b6e80b12df373a06ec3d5c4425f0e6d&city=371000&extensions=all&output=JSON";//天气预报API
    private TextView tv,city,tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tv=(TextView) findViewById(R.id.txtShow);
        city=(TextView) findViewById(R.id.city);
        tp=(TextView)findViewById(R.id.previewtx);
        new WeatherUtils(WeatherActivity.this,tv,city).execute(strUrl1);
        new WeatherPreview(WeatherActivity.this,tp).execute(strUrl2);
    }
}