package com.example.footsalmanager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PHPRequest extends AsyncTask<String,Void,String> {

    public String queryURL;
    public String returnTxt;
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

    }
    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        returnTxt = s;
    }
    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);

    }
    @Override
    protected String doInBackground(String... params) // String... params = 파라미터로 String형을 제한없이 받는다. 여러개의 파라미터를 받을 수 있게 하는거
    {
        HttpURLConnection httpURLConnection = null;
        String data = "";
        try{
            Log.d("paramsLength",""+params.length);
            if(params.length == 5) {
                String IDText = params[0];
                String PWText = params[1];
                String NickNameText = params[2];
                String EmailText = params[3];
                String ImageText = params[4];

                data += URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(IDText, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(PWText, "UTF-8");
                data += "&" + URLEncoder.encode("NickName", "UTF-8") + "=" + URLEncoder.encode(NickNameText, "UTF-8");
                data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(EmailText, "UTF-8");
                data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(ImageText, "UTF-8");
            }
            else
            {
                String IDText = params[0];
                String PWText = params[1];
                String NickNameText = params[2];
                String EmailText = params[3];
                //String ImageText = params[4];

                data += URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(IDText, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(PWText, "UTF-8");
                data += "&" + URLEncoder.encode("NickName", "UTF-8") + "=" + URLEncoder.encode(NickNameText, "UTF-8");
                data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(EmailText, "UTF-8");
                //data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(ImageText, "UTF-8");
            }
            // php 웹서버에 데이터 전달 ID 변수에 IDText
            //                          PW 변수에 PWText 전달한다.
            String link = "http://203.232.193.175/" + queryURL;

            URL url = new URL(link);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // 요청메소드를 POST 방식으로 설정
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream()); // 주어진 출력 바이트 스트림에 대해 기본 인코딩을 사용하는 객체를 생성한다.

            wr.write(data); //
            wr.flush();
//
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));

            StringBuilder sb = new StringBuilder(); // StringBuilder 는 String 이어주는것을 효율적으로 사용

            String line;

            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            httpURLConnection.disconnect();

            return sb.toString();
        } catch (Exception e)
        {
            httpURLConnection.disconnect();
            String exceptionString = e.getMessage();

            return exceptionString; //new String("Exception Occure"+ e.getMessage());
        }
    }

}