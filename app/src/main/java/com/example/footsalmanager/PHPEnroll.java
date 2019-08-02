package com.example.footsalmanager;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PHPEnroll extends PHPCheckLogin {
    @Override
    protected String doInBackground(String... params) // String... params = 파라미터로 String형을 제한없이 받는다. 여러개의 파라미터를 받을 수 있게 하는거
    {
        HttpURLConnection httpURLConnection = null;

        try{
            String nickNameText = params[0];
            String userImage = params[1];
            String ageText = params[2];
            String phoneText = params[3];
            String areaText = params[4];
            String skillText = params[5];
            String dateText = params[6];
            String contentsText = params[7];
            String CurrentDateText = params[8];


            String data = URLEncoder.encode("NickName","UTF-8")+"="+URLEncoder.encode(nickNameText,"UTF-8");
            data+= "&" + URLEncoder.encode("Image","UTF-8")+"="+URLEncoder.encode(userImage,"UTF-8");
            data+= "&" + URLEncoder.encode("Age","UTF-8")+"="+URLEncoder.encode(ageText,"UTF-8");
            data+= "&" + URLEncoder.encode("Phone","UTF-8")+"="+URLEncoder.encode(phoneText,"UTF-8");
            data+= "&" + URLEncoder.encode("Area","UTF-8")+"="+URLEncoder.encode(areaText,"UTF-8");
            data+= "&" + URLEncoder.encode("Skill","UTF-8")+"="+URLEncoder.encode(skillText,"UTF-8");
            data+= "&" + URLEncoder.encode("Date","UTF-8")+"="+URLEncoder.encode(dateText,"UTF-8");
            data+= "&" + URLEncoder.encode("Contents","UTF-8")+"="+URLEncoder.encode(contentsText,"UTF-8");
            data+= "&" + URLEncoder.encode("CurrentDate","UTF-8")+"="+URLEncoder.encode(CurrentDateText,"UTF-8");
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
