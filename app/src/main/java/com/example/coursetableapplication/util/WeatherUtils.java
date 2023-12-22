package com.example.coursetableapplication.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherUtils extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        InputStream inputStream=null;
        StringBuffer stringBuffer=null;

        try{
            //创建URL对象，接收API
            URL url=new URL(objects[0].toString());
            System.out.println("the URL is:"+objects[0].toString());
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            //判断是否联网
            if (httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();
            }
            //构建一个InputStreamReader对象用于接收输入字符流
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            stringBuffer=new StringBuffer();
            String temp=null;
            while((temp=bufferedReader.readLine())!=null){
                stringBuffer.append(temp);
            }
            //关闭各种流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    private Context context;
    private TextView weatherTv;
    private TextView City;
    public WeatherUtils(Context context, TextView weatherTv, TextView City){
        this.context=context;
        this.weatherTv=weatherTv;
        this.City=City;
    }
    @Override
    protected void onPostExecute(Object o){
        try{
            //创建JSONObject对象，接收字符串
            JSONObject obj=new JSONObject(o.toString());
            //创建lives对象，接收obj中的“lives”对象
            JSONArray lives=obj.getJSONArray("lives");
            //创建todayweather对象，接收数组的第一个对象
            JSONObject todayweather=lives.getJSONObject(0);
            //读取数据
            String city=todayweather.getString("city")+"\n";
            String weather=todayweather.getString("weather")+"\n";
            String temperature=todayweather.getString("temperature")+"℃\n";
            String winddirection=todayweather.getString("winddirection")+"风";
            String windpower=todayweather.getString("windpower")+"级\n";
            String humidity=todayweather.getString("humidity")+"%\n";
            String reporttime=todayweather.getString("reporttime")+"发布\n";
            //设置显示的内容
            City.setText(city);
            weatherTv.setText(reporttime+"天气："+weather+"当前温度："+temperature+"风向："+winddirection+windpower+"湿度："+humidity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
