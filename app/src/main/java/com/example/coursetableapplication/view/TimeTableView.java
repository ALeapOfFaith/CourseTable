package com.example.coursetableapplication.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.coursetableapplication.activity.AddCourseActivity;
import com.example.coursetableapplication.activity.EditCourseActivity;
import com.example.coursetableapplication.entity.Course;
import com.example.coursetableapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTableView extends LinearLayout {

    //星期
    private String[] weekTitle = {"一", "二", "三", "四", "五", "六", "七"};
    //最大节数
    private int maxSection = 6;
    //线宽
    private int tableLineWidth = 1;
    //数字字体大小
    private int numberSize = 14;
    //标题字体大小
    private int titleSize = 18;
    //课表信息字体大小
    private int courseSize = 12;
    //底部按钮大小
    private int buttonSize = 12;
    //单元格高度
    private int cellHeight = 150;
    //星期标题高度
    private int titleHeight = 30;
    //最左边数字宽度
    private int numberWidth = 20;
    private Context mContext;
    private List<Course> courseList;
    private Map<String, Integer> colorMap = new HashMap<>();
    private Map<Integer, List<Course>> courseMap = new HashMap<>();
    //当前是第weekNum周
    private int weekNum = 1;
    //菜单栏
    private ImageView mCategory;
    //周次信息
    private TextView mWeekTitle;
    private LinearLayout mMainLayout;
    private RelativeLayout mTitleLayout;

    public TimeTableView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public TimeTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    //数据预处理
    private void preprocessorParam() {
        tableLineWidth = dip2px(tableLineWidth);
        cellHeight = dip2px(cellHeight);
        titleHeight = dip2px(titleHeight);
        numberWidth = dip2px(numberWidth);
    }

    //加载数据
    public void loadData() {
        handleData(courseMap, this.getCourseList(), weekNum);
        flushView(courseMap, weekNum);
    }

    //处理数据
    private void handleData(Map<Integer, List<Course>> courseMap, List<Course> courseList, int weekNum) {
        courseMap.clear();
        for (Course c : courseList) {
            if (c.getStartWeek() > weekNum || c.getEndWeek() < weekNum) continue;
            List<Course> courses = courseMap.get(c.getDay());
            if(courses == null){
                courses = new ArrayList<>();
                courseMap.put(c.getDay(), courses);
            }
            courses.add(c);
        }
    }
    //hasCourses()方法用于替代handleData()
    private boolean hasCourses(List<Course> courseList, int weekNum){
        for(Course c: courseList){
            if(c.getStartWeek() <= weekNum && c.getEndWeek() >= weekNum)
                return true;
        }
        return false;
    }

    //初始化视图
    private void initView(){
        preprocessorParam();
        //周次标题
        addWeekTitle(this);
        //星期标签
        addWeekLabel();
        //课程信息
        flushView(null, weekNum);
    }

    //刷新课程视图
    private void flushView(Map<Integer, List<Course>> courseMap, int weekNum) {
        //初始化主布局
        if (null != mMainLayout) removeView(mMainLayout);
        mMainLayout = new LinearLayout(mContext);
        mMainLayout.setOrientation(HORIZONTAL);
        mMainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMainLayout);
        //周次标题
        mWeekTitle.setText("第 " + weekNum + " 周");
        //左侧节次标签
        addLeftNumber(mMainLayout);
        //课程信息
        if (null == courseMap || courseMap.isEmpty()) {//数据为空
//        if(courseList == null || !hasCourses(courseList, weekNum)){
            addVerticalTableLine(mMainLayout);
            TextView emptyLayoutTextView = createTextView("本周无课，或未添加课程信息！", titleSize, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0, getResources().getColor(R.color.textColor), Color.WHITE);
            mMainLayout.addView(emptyLayoutTextView);
        } else {//不为空
//            for (int i = 1; i <= weekTitle.length; i++) {
//                addVerticalTableLine(mMainLayout);
//                addDayCourse(mMainLayout, courseMap, i);
//            }
            addCell(mMainLayout);
            addCourse(courseList);
        }
        invalidate();
    }

    //添加次标题
    private void addWeekTitle(ViewGroup pViewGroup) {
        mTitleLayout = new RelativeLayout(mContext);
        mTitleLayout.setPadding(8, 15, 8, 15);
        mTitleLayout.setBackgroundColor(getResources().getColor(R.color.titleColor));
        //周次信息
        mWeekTitle = new TextView(mContext);
        mWeekTitle.setTextSize(titleSize);
        mWeekTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mWeekTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        mTitleLayout.addView(mWeekTitle);

        //上一周箭头
        ImageView mLeftArrow = new ImageView(mContext);
        mLeftArrow = new ImageView(mContext);
        mLeftArrow.setImageResource(R.drawable.left_arrow);

        RelativeLayout.LayoutParams layoutParams_leftArrow = new RelativeLayout.LayoutParams(dip2px(30), dip2px(30));
        layoutParams_leftArrow.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mLeftArrow.setLayoutParams(layoutParams_leftArrow);

        mLeftArrow.setOnClickListener(view -> {
            if(weekNum == 1){
                Toast.makeText(mContext, "已经是第一周了~", Toast.LENGTH_SHORT).show();
            }else{
                weekNum--;
                loadData();
            }
        });

        mTitleLayout.addView(mLeftArrow);

        //下一周箭头
        ImageView mRightArrow = new ImageView(mContext);
        mRightArrow.setImageResource(R.drawable.right_arrow);

        RelativeLayout.LayoutParams layoutParams_rightArrow = new RelativeLayout.LayoutParams(dip2px(30), dip2px(30));
        layoutParams_rightArrow.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightArrow.setLayoutParams(layoutParams_rightArrow);

        mRightArrow.setOnClickListener(view -> {
            if(weekNum == 20){
                Toast.makeText(mContext, "已经是最后一周了~", Toast.LENGTH_SHORT).show();
            }else{
                weekNum++;
                loadData();
            }
        });

        mTitleLayout.addView(mRightArrow);

        pViewGroup.addView(mTitleLayout);
        addHorizontalTableLine(pViewGroup);
    }

    //添加星期标签
    private void addWeekLabel() {
        LinearLayout mTitleLayout = new LinearLayout(mContext);
        mTitleLayout.setOrientation(HORIZONTAL);
        mTitleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, titleHeight));
        addView(mTitleLayout);

        //空白符
        TextView space = new TextView(mContext);
        space.setLayoutParams(new ViewGroup.LayoutParams(numberWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        space.setBackgroundColor(getResources().getColor(R.color.titleColor));
        mTitleLayout.addView(space);

        //星期
        for (int i = 0; i < weekTitle.length; i++) {
            addVerticalTableLine(mTitleLayout);
            TextView title = createTextView(weekTitle[i], titleSize, 0, ViewGroup.LayoutParams.MATCH_PARENT, 1, getResources().getColor(R.color.textColor), getResources().getColor(R.color.titleColor));
            mTitleLayout.addView(title);
        }
    }

    //添加左侧节次数字
    private void addLeftNumber(ViewGroup pViewGroup) {
        LinearLayout leftLayout = new LinearLayout(mContext);
        leftLayout.setOrientation(VERTICAL);
        leftLayout.setLayoutParams(new LayoutParams(numberWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 1; i <= maxSection; i++) {
            addHorizontalTableLine(leftLayout);
            TextView number = createTextView(String.valueOf(i), numberSize, ViewGroup.LayoutParams.MATCH_PARENT, cellHeight, 1, getResources().getColor(R.color.textColor), Color.WHITE);
            leftLayout.addView(number);
        }

        pViewGroup.addView(leftLayout);
    }

    private void addCell(ViewGroup pViewGroup){
        int id = 1;
        for(int i = 0; i < 7; i++){
            addVerticalTableLine(pViewGroup);
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            linearLayout.setOrientation(VERTICAL);

            for (int j = 0; j < maxSection; j++) {
                addHorizontalTableLine(linearLayout);
                TextView blank = new TextView(mContext);
                blank.setId(id++);
                blank.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, cellHeight));

                blank.setOnLongClickListener(view -> {
                    Toast.makeText(mContext, "addNewCourse", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AddCourseActivity.class);
                    TextView v = (TextView) view;
                    intent.putExtra("cellId", v.getId());
                    intent.putExtra("week", weekNum);
                    mContext.startActivity(intent);
                    return true;
                });

                linearLayout.addView(blank);
            }

            pViewGroup.addView(linearLayout);
        }

    }

    private void addCourse(List<Course> courseList){

        for(Course c: courseList){
            if (c.getStartWeek() > weekNum || c.getEndWeek() < weekNum) continue;

            TextView textView = findViewById( (c.getDay() - 1) * 6 + c.getSection() );
            textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cellHeight + tableLineWidth));
            textView.setTextSize(courseSize);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(getColor(colorMap, c.getCourseName()));
            textView.setText(String.format("%s\n%s\n%d~%d周\n%s\n%d", c.getCourseName(), c.getTeacherName(), c.getStartWeek(), c.getEndWeek(), c.getClassroom(), c.getId()));

            textView.setOnLongClickListener(view -> {
                Intent intent = new Intent(mContext, EditCourseActivity.class);
                TextView v = (TextView) view;
                Toast.makeText(mContext, "editCourse", Toast.LENGTH_LONG).show();
                intent.putExtra("cellId", v.getId());
                intent.putExtra("info", v.getText().toString());
                mContext.startActivity(intent);
                return true;
            });
        }

    }

    //添加垂直线
    private void addVerticalTableLine(ViewGroup pViewGroup) {
        View view = new View(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(tableLineWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setBackgroundColor(getResources().getColor(R.color.viewLine));
        pViewGroup.addView(view);
    }

    //添加水平线
    private void addHorizontalTableLine(ViewGroup pViewGroup) {
        View view = new View(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, tableLineWidth));
        view.setBackgroundColor(getResources().getColor(R.color.viewLine));
        pViewGroup.addView(view);
    }

    //创建TextView
    private TextView createTextView(String content, int size, int width, int height, int weight, int color, int bkColor) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new LayoutParams(width, height, weight));
        if(bkColor != -1)textView.setBackgroundColor(bkColor);
        textView.setTextColor(color);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(size);
        textView.setText(content);
        return textView;
    }

    private int getColor(Map<String, Integer> map, String name) {
        Integer tip = map.get(name);
        if (null != tip) {
            return tip;
        } else {
            int i = getResources().getColor(color[map.size() % color.length]);
            map.put(name, i);
            return i;
        }
    }

    private int dip2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    private int color[] = {
            R.color.one, R.color.two, R.color.three,
            R.color.four, R.color.five, R.color.six,
            R.color.seven, R.color.eight, R.color.nine,
            R.color.ten, R.color.eleven, R.color.twelve,
            R.color.thirteen, R.color.fourteen, R.color.fifteen
    };

    public void setMaxSection(int maxSection) {
        this.maxSection = maxSection;
    }

    public void setTableLineWidth(int tableLineWidth) {
        this.tableLineWidth = tableLineWidth;
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public void setNumberWidth(int numberWidth) {
        this.numberWidth = numberWidth;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }
}