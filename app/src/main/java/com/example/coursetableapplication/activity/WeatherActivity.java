package com.example.coursetableapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.coursetableapplication.R;
import com.example.coursetableapplication.util.WeatherUtils;

public class WeatherActivity extends AppCompatActivity {

    //高德地图API
    private String strUrl="https://restapi.amap.com/v3/weather/weatherInfo?key=4b6e80b12df373a06ec3d5c4425f0e6d&city=371000&extensions=base&output=JSON";
    private TextView tv;
    private TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tv=(TextView) findViewById(R.id.txtShow);
        city=(TextView) findViewById(R.id.city);
        new WeatherUtils(WeatherActivity.this,tv,city).execute(strUrl);
    }
}