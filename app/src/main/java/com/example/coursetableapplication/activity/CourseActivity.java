package com.example.coursetableapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.coursetableapplication.R;
import com.example.coursetableapplication.helper.MySQLiteHelper;
import com.example.coursetableapplication.entity.Course;
import com.example.coursetableapplication.view.TimeTableView;

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
    private TimeTableView timeTable;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        timeTable = findViewById(R.id.timeTable);

        timeTable.setCourseList(acquireData());
        timeTable.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        timeTable.setCourseList(acquireData());
        timeTable.loadData();
    }

    private List<Course> acquireData() {
        List<Course> courses;
//        sp = getSharedPreferences("config", MODE_PRIVATE);
        if (sp.getBoolean("isFirstUse", true)) {//首次使用
            mySQLiteHelper.insert((new Course("形式与政策", "布x", 1, 4, 1, 1, "N403")));
            mySQLiteHelper.insert((new Course("计算机网络与通讯", "周xx", 1, 8, 1, 4, "H425")));
            mySQLiteHelper.insert((new Course("电子商务", "周xx", 1, 8, 2, 1, "H328")));
            mySQLiteHelper.insert((new Course("数据仓库与数据挖掘", "胡xx", 1, 8, 2, 2, "M106")));
            mySQLiteHelper.insert((new Course("Android移动应用开发", "周xx", 1, 8, 2, 4, "研究院中403")));

            courses= mySQLiteHelper.listAllCourses();

            sp.edit().putBoolean("isFirstUse", false).apply();
        }else {
            courses = mySQLiteHelper.listAllCourses();
        }
        return courses;
    }



}