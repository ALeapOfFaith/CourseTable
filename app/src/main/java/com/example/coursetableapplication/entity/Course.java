package com.example.coursetableapplication.entity;

import java.io.Serializable;

public class Course implements Cloneable, Serializable {
    //课程的数据结构
    private int id;
    private String courseName;//课程名
    private String teacherName;//教师名
    private int startWeek;//开始周次
    private int endWeek;//结束周次
    private int day;//星期几
    private int section;//节次
    private String classroom;//教室


    public Course() {
    }

    public Course(String courseName, String teacherName, int startWeek, int endWeek, int day, int section, String classroom) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.day = day;
        this.section = section;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }


    public Course clone() {
        try {
            return (Course) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public String toString() {
//        return "Course{" +
//                "id=" + id +
//                ", courseName='" + courseName + '\'' +
//                ", teacherName='" + teacherName + '\'' +
//                ", classroom='" + classroom + '\'' +
//                ", startWeek=" + startWeek +
//                ", endWeek=" + endWeek +
//                ", day=" + day +
//                ", section=" + section +
//                '}';
//    }


//    public String toTime(){
//        return String.format("%d:%d:%s", day, section, classroom);
////        return String.format("%d:%d:%s:%s", day, section, weekType, classroom);
//    }

//    public static Course toCourse(List<Course> courseList, int id){
//        if(null == courseList)return null;
//        Course course = new Course();
//        course.setId(id);
//        StringBuffer sb = new StringBuffer();
//        for(int i = 0, len = courseList.size(); i < len; i++){
//            sb.append(courseList.get(i).toTime());
//            if(i != len - 1)sb.append(";");
//        }
//        return course;
//    }
}