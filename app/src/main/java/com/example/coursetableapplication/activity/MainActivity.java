package com.example.coursetableapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.coursetableapplication.R;
import com.example.coursetableapplication.markapp.MainActivity1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //点击按钮1进入“查看天气”功能
        Button btn1 = findViewById(R.id.button_1_1);
        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
        });

        //点击按钮2进入“查看/编辑课表”功能
        Button btn2 = findViewById(R.id.button_1_2);
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseActivity.class);
            startActivity(intent);
        });

        //点击按钮3进入“日记本”功能
        Button btn3 = findViewById(R.id.button_1_3);
        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity1.class);
            startActivity(intent);
        });
    }
}