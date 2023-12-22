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

public class EditCourseActivity extends AppCompatActivity {

    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        mySQLiteHelper = new MySQLiteHelper(this);

        Button btnYes = findViewById(R.id.button_p2_editCourse_yes);
        Button btnDelete = findViewById(R.id.button_p2_editCourse_delete);
        Button btnCancel = findViewById(R.id.button_p2_editCourse_cancel);

        Intent intent = getIntent();
        String s = intent.getStringExtra("info");
        int cellId = intent.getIntExtra("cellId", 1);
        String[] info = CourseUtils.parseCourseInfo(s, cellId);

//        Log.d("test", info.toString());

        EditText editText = findViewById(R.id.input_p2_editCourse_course);
        editText.setText(info[0]);
        editText = findViewById(R.id.input_p2_editCourse_teacher);
        editText.setText(info[1]);

        Spinner spinner = findViewById(R.id.input_p2_editCourse_startWeek);
        spinner.setSelection(Integer.parseInt(info[2]) - 1);
        spinner = findViewById(R.id.input_p2_editCourse_endWeek);
        spinner.setSelection(Integer.parseInt(info[3]) - 1);
        spinner = findViewById(R.id.input_p2_editCourse_day);
        spinner.setSelection(Integer.parseInt(info[4]) - 1);
        spinner = findViewById(R.id.input_p2_editCourse_time);
        spinner.setSelection(Integer.parseInt(info[5]) - 1);

        editText = findViewById(R.id.input_p2_editCourse_classroom);
        editText.setText(info[6]);

        int courseId = Integer.parseInt(info[7]);

        btnYes.setOnClickListener(view -> {
            Course course = new Course();
            course.setId(courseId);

            EditText editText1;
            Spinner spinner1;
            editText1 = findViewById(R.id.input_p2_editCourse_course);
            String s1 = editText1.getText().toString();
            if(!s1.equals("")) {
                course.setCourseName(editText1.getText().toString());
                editText1 = findViewById(R.id.input_p2_editCourse_teacher);
                course.setTeacherName(editText1.getText().toString());
                spinner1 = findViewById(R.id.input_p2_editCourse_startWeek);
                course.setStartWeek(Integer.valueOf(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_editCourse_endWeek);
                course.setEndWeek(Integer.valueOf(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_editCourse_day);
                course.setDay(CourseUtils.day2Int(spinner1.getSelectedItem().toString()));
                spinner1 = findViewById(R.id.input_p2_editCourse_time);
                course.setSection(CourseUtils.section2Int(spinner1.getSelectedItem().toString()));
                editText1 = findViewById(R.id.input_p2_editCourse_classroom);
                course.setClassroom(editText1.getText().toString());
                mySQLiteHelper.update(course);
                finish();
            }else{
                Toast.makeText(this, "课程名称不能为空！", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(view -> {
            mySQLiteHelper.delete(courseId);
            finish();
        });

        btnCancel.setOnClickListener(view -> finish());
    }
}