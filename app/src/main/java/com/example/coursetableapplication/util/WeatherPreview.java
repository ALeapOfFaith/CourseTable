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

public class WeatherPreview extends AsyncTask {
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
    private TextView weatherTp;
    public WeatherPreview(Context context, TextView weatherTp){
        this.context=context;
        this.weatherTp=weatherTp;
    }
    @Override
    protected void onPostExecute(Object o){
        try{

            //创建JSONObject对象，接收字符串
            JSONObject obj=new JSONObject(o.toString());
            //创建forecasts对象，接收obj中的“forecasts”对象
            JSONArray forecasts=obj.getJSONArray("forecasts");
            //创建info对象，接收forecasts数组中第一个对象
            JSONObject info=forecasts.getJSONObject(0);
            //创建casts对象，接收info中的"casts"对象
            JSONArray casts=info.getJSONArray("casts");
            //创建cast1对象，接收数组的第二个对象
            JSONObject cast1=casts.getJSONObject(1);
            //创建cast2对象，接收数组的第三个对象
            JSONObject cast2=casts.getJSONObject(2);
            //创建cast3对象，接收数组的第四个对象
            JSONObject cast3=casts.getJSONObject(3);
            //读取数据
            String date1=cast1.getString("date")+"\n";
            String dayweather1=cast1.getString("dayweather");
            String nightweather1=cast1.getString("nightweather")+"\n";
            String daytemp1=cast1.getString("daytemp")+"℃";
            String nighttemp1=cast1.getString("nighttemp")+"℃"+"\n";
            String date2=cast2.getString("date")+"\n";
            String dayweather2=cast2.getString("dayweather");
            String nightweather2=cast2.getString("nightweather")+"\n";
            String daytemp2=cast2.getString("daytemp")+"℃";
            String nighttemp2=cast2.getString("nighttemp")+"℃"+"\n";
            String date3=cast3.getString("date")+"\n";
            String dayweather3=cast3.getString("dayweather");
            String nightweather3=cast3.getString("nightweather")+"\n";
            String daytemp3=cast3.getString("daytemp")+"℃";
            String nighttemp3=cast3.getString("nighttemp")+"℃"+"\n";
            String reporttime=info.getString("reporttime")+"发布\n";
            //设置显示的内容
            weatherTp.setText(date1+" 早间天气："+dayweather1+" 晚间天气："+nightweather1+" 早间温度"+daytemp1+" 晚间温度"+nighttemp1+date2+" 早间天气："+dayweather2+" 晚间天气："+nightweather2+" 早间温度"+daytemp2+" 晚间温度"+nighttemp2+date3+" 早间天气："+dayweather3+" 晚间天气："+nightweather3+" 早间温度"+daytemp3+" 晚间温度"+nighttemp3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
