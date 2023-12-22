package com.example.coursetableapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.coursetableapplication.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private String tableName = "sc";
    public MySQLiteHelper(Context context){
        super(context, "course_table.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table sc(" +
                "_id integer primary key autoincrement," +
                "courseName varchar(50)," +
                "teacherName varchar(50)," +
                "classroom varchar(50), " +
                "day integer," +
                "section integer," +
                "startWeek integer," +
                "endWeek integer" +
                ")";
        db.execSQL(sql);
    }

    public long insert(@NonNull Course course){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseName", course.getCourseName());
        if(!TextUtils.isEmpty(course.getTeacherName())){
            values.put("teacherName", course.getTeacherName());
        }
        if(course.getStartWeek() != 0) {
            values.put("startWeek", course.getStartWeek());
        }
        if(course.getEndWeek() != 0) {
            values.put("endWeek", course.getEndWeek());
        }
        if(course.getDay() != 0) {
            values.put("day", course.getDay());
        }
        if(course.getSection() != 0) {
            values.put("section", course.getSection());
        }
        if(!course.getClassroom().equals("")) {
            values.put("classroom", course.getClassroom());
        }
        long count = database.insert(tableName, null, values);
        database.close();
        return count;
    }

    public int delete(int id){
        SQLiteDatabase database = getWritableDatabase();
        int count = database.delete(tableName, "_id=?", new String[]{String.valueOf(id)});
        database.close();
        return count;
    }

    public int update(Course course){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseName", course.getCourseName());
        if(!TextUtils.isEmpty(course.getTeacherName())){
            values.put("teacherName", course.getTeacherName());
        }
        if(course.getStartWeek() != 0) {
            values.put("startWeek", course.getStartWeek());
        }
        if(course.getEndWeek() != 0) {
            values.put("endWeek", course.getEndWeek());
        }
        if(course.getDay() != 0) {
            values.put("day", course.getDay());
        }
        if(course.getSection() != 0) {
            values.put("section", course.getSection());
        }
        if(!course.getClassroom().equals("")) {
            values.put("classroom", course.getClassroom());
        }
        int count = database.update(tableName, values, "_id=?", new String[]{String.valueOf(course.getId())});
        database.close();
        return count;
    }

    public List<Course> listAllCourses(){
        List<Course> list = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor data = database.query(tableName, null, null, null, null, null, null);
        if(data.getCount() > 0){
            while(data.moveToNext()) {
                Course course = new Course();

                course.setId(data.getInt(0));
                course.setCourseName(data.getString(1));
                course.setTeacherName(data.getString(2));
                course.setClassroom(data.getString(3));
                course.setDay(data.getInt(4));
                course.setSection(data.getInt(5));
                course.setStartWeek(data.getInt(6));
                course.setEndWeek(data.getInt(7));

                list.add(course);
            }
        }
        database.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}