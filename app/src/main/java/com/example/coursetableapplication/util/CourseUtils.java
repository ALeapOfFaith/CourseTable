package com.example.coursetableapplication.util;

public class CourseUtils {
    public static int section2Int(String section){
        int i = 1;
        switch (section) {
            case "第1大节":{
                i = 1;
                break;
            }
            case "第2大节":{
                i = 2;
                break;
            }
            case "第3大节":{
                i = 3;
                break;
            }
            case "第4大节":{
                i = 4;
                break;
            }
            case "第5大节":{
                i = 5;
                break;
            }
            case "第6大节":{
                i = 6;
                break;
            }
        }
        return i;
    }

    public static String section2String(int section){
        String i = "第1大节";
        switch (section) {
            case 1:{
                i = "第1大节";
                break;
            }
            case 2:{
                i = "第2大节";
                break;
            }
            case 3:{
                i = "第3大节";
                break;
            }
            case 4:{
                i = "第4大节";
                break;
            }
            case 5:{
                i = "第5大节";
                break;
            }
            case 6:{
                i = "第6大节";
                break;
            }
        }
        return i;
    }

    public static int day2Int(String day) {
        int i = 1;
        switch (day) {
            case "星期一":{
                i = 1;
                break;
            }
            case "星期二":{
                i = 2;
                break;
            }
            case "星期三":{
                i = 3;
                break;
            }
            case "星期四":{
                i = 4;
                break;
            }
            case "星期五":{
                i = 5;
                break;
            }
            case "星期六":{
                i = 6;
                break;
            }
            case "星期日":{
                i = 7;
                break;
            }
        }
        return i;
    }

    public static String day2String(int day) {
        String i = "星期一";
        switch (day) {
            case 1:{
                i = "星期一";
                break;
            }
            case 2:{
                i = "星期二";
                break;
            }
            case 3:{
                i = "星期三";
                break;
            }
            case 4:{
                i = "星期四";
                break;
            }
            case 5:{
                i = "星期五";
                break;
            }
            case 6:{
                i = "星期六";
                break;
            }
            case 7:{
                i = "星期日";
                break;
            }
        }
        return i;
    }

    public static String[] parseCourseInfo(String info, int cellId){
        String[] str = info.split("\n");
        String[] res = new String[8];

        res[0] = str[0];
        res[1] = str[1];
        char ch;
        boolean flag = true;
        res[2] = "";
        res[3] = "";
        for(int i = 0; i < str[2].length(); i++){
            ch = str[2].charAt(i);
            if(flag && ch!='~'){
                res[2] += ch;
            }else if(ch=='~'){
                flag = false;
            }else if(ch!='周'){
                res[3] += ch;
            }
        }
        res[4] = String.valueOf(parseDay(cellId));
        res[5] = String.valueOf(parseTime(cellId));
        res[6] = str[3];
        res[7] = String.valueOf(str[4]);
        return res;
    }

    public static int parseDay(int cellId){
        return (cellId - 1) / 6 + 1;
    }

    public static int parseTime(int cellId){
        return cellId % 6;
    }
}
