package com.example.footsalmanager;

import android.util.Log;

import com.example.footsalmanager.PHPRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PHPGetViewerData extends PHPRequest {
    @Override
    protected String doInBackground(String... params) // String... params = 파라미터로 String형을 제한없이 받는다. 여러개의 파라미터를 받을 수 있게 하는거
    {
        HttpURLConnection httpURLConnection = null;
        String data = "";
        if(params.length != 0)
        {
            try{
                String filterMsg = params[0];
                String requestCode = params[1];

                data += URLEncoder.encode("filterMsg", "UTF-8") + "=" + URLEncoder.encode(filterMsg, "UTF-8");
                data += "&" + URLEncoder.encode("requestCode", "UTF-8") + "=" + URLEncoder.encode(requestCode, "UTF-8");


            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        try {
//            if (!params.equals(""))
//            {
//                String IDText = params[0];
//
//                String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(IDText, "UTF-8");
//
//                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream()); // 주어진 출력 바이트 스트림에 대해 기본 인코딩을 사용하는 객체를 생성한다.
//
//                wr.write(data);
//                wr.flush();
//            }
//            else {
//
//            }
            //String IDText = params[0];
//            String PWText = params[1];
//            String NickNameText = params[2];
//
            //String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(IDText, "UTF-8");
//            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(PWText, "UTF-8");
//            data += "&" + URLEncoder.encode("NickName", "UTF-8") + "=" + URLEncoder.encode(NickNameText, "UTF-8");
//            // php 웹서버에 데이터 전달 ID 변수에 IDText
//            //                          PW 변수에 PWText 전달한다.
            String link = "http://203.232.193.175/" + queryURL;

            URL url = new URL(link);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // 요청메소드를 POST 방식으로 설정
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream()); // 주어진 출력 바이트 스트림에 대해 기본 인코딩을 사용하는 객체를 생성한다.

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder(); // StringBuilder 는 String 이어주는것을 효율적으로 사용

            String line;

            while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                httpURLConnection.disconnect();

                return sb.toString();

        } catch (Exception e) {
            httpURLConnection.disconnect();
            String exceptionString = e.getMessage();

            return exceptionString; //new String("Exception Occure"+ e.getMessage());
        }
    }
}
