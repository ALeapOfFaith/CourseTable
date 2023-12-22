package com.example.coursetableapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursetableapplication.R;
import com.example.coursetableapplication.entity.Course;
import com.example.coursetableapplication.helper.MySQLiteHelper;
import com.example.coursetableapplication.util.CourseUtils;

public class AddCourseActivity extends AppCompatActivity {

    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        mySQLiteHelper = new MySQLiteHelper(this);

        Intent intent = getIntent();
        int cellId = intent.getIntExtra("cellId", 1);
        int week = intent.getIntExtra("week", 1);
        Spinner spinner;
        spinner = findViewById(R.id.input_p2_addCourse_startWeek);
        spinner.setSelection(week - 1);
        spinner = findViewById(R.id.input_p2_addCourse_endWeek);
        spinner.setSelection(week - 1);
        spinner = findViewById(R.id.input_p2_addCourse_day);
        spinner.setSelection(CourseUtils.parseDay(cellId) - 1);
        spinner = findViewById(R.id.input_p2_addCourse_time);
        spinner.setSelection(CourseUtils.parseTime(cellId) - 1);

        Button btnYes = findViewById(R.id.button_p2_addCourse_yes);
        Button btnCancel = findViewById(R.id.button_p2_addCourse_cancel);

        btnYes.setOnClickListener(view -> {
            EditText editText;
            Spinner spinner1;
            Course course = new Course();
            editText = findViewById(R.id.input_p2_addCourse_course);
            String s = editText.getText().toString();
            if(!s.equals("")){
                course.setCourseName(s);
                editText = findViewById(R.id.input_p2_addCourse_teacher);
                course.setTeacherName(editText.getText().toString());
                spinner1 = findViewById(R.id.input_p2_addCourse_startWeek);
                course.setStartWeek(Integer.valueOf(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_addCourse_endWeek);
                course.setEndWeek(Integer.valueOf(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_addCourse_day);
                course.setDay(CourseUtils.day2Int(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_addCourse_time);
                course.setSection(CourseUtils.section2Int(spinner1.getSelectedItem().toString()));
                editText = findViewById(R.id.input_p2_addCourse_classroom);
                course.setClassroom(editText.getText().toString());

                mySQLiteHelper.insert(course);

                finish();
            }else{
                Toast.makeText(this, "课程名称不能为空！", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(view -> finish());
    }
}