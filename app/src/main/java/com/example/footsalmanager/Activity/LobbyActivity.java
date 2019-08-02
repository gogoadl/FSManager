package com.example.footsalmanager.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footsalmanager.GPS;
import com.example.footsalmanager.OnSingleClickListener;
import com.example.footsalmanager.R;


import static com.example.footsalmanager.Activity.LoginActivity.userData;

public class LobbyActivity extends AppCompatActivity {
    TextView text_NickName;
    ImageView btn_Image;
    byte[] decodedByteArray;
    Button btn_Enroll,btn_Field,btn_Find,btn_Back;
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("log","LobbyActivity onResume");
        setContentView(R.layout.activity_lobby);

        btn_Field = findViewById(R.id.btn_field);
        btn_Find = findViewById(R.id.btn_find);
        btn_Enroll = findViewById(R.id.btn_enroll);
        btn_Back = findViewById(R.id.btn_back);
        btn_Image = findViewById(R.id.btn_Image);

        text_NickName = findViewById(R.id.text_NickName);
//        Intent lobbyIntent = getIntent();
//        String lobbyNickName = lobbyIntent.getExtras().getString("NickName");

        text_NickName.setText(userData.getUserNickName());
        btn_Image.setImageBitmap(userData.getUserImage());

        btn_Image.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_MyPage = new Intent(getApplicationContext(), MyPageActivity.class);
                intent_MyPage.putExtra("NickName",text_NickName.getText());
                startActivity(intent_MyPage);
            }
        });
        btn_Field.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                GPS gps = new GPS(getApplicationContext());
                double lat = gps.getLatitude();
                double lon = gps.getLongitude();
                String url = "daummaps://search?q=풋살장&p="+lat+","+lon;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                try{
                    startActivity(intent);
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"카카오 맵 어플이 깔려있지 않으므로 지도가 실행됩니다.",Toast.LENGTH_LONG).show();
                    Intent intent_Field = new Intent(getApplicationContext(),FieldActiviry.class);
                    startActivity(intent_Field);
                }

            }
        });
        btn_Enroll.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_Enroll = new Intent(getApplicationContext(), EnrollActivity.class);
                intent_Enroll.putExtra("NickName",text_NickName.getText());
                intent_Enroll.putExtra("ByteArray",decodedByteArray);
                startActivity(intent_Enroll);
            }
        });
        btn_Find.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_Viewer = new Intent(getApplicationContext(), ViewerActivity.class);
                startActivity(intent_Viewer);
            }
        });
        btn_Back.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d("log","LobbyActivity onCreate");

        btn_Field = findViewById(R.id.btn_field);
        btn_Find = findViewById(R.id.btn_find);
        btn_Enroll = findViewById(R.id.btn_enroll);
        btn_Back = findViewById(R.id.btn_back);
        btn_Image = findViewById(R.id.btn_Image);

        text_NickName = findViewById(R.id.text_NickName);
//        Intent lobbyIntent = getIntent();
//        String lobbyNickName = lobbyIntent.getExtras().getString("NickName");
        text_NickName.setText(userData.getUserNickName());
        btn_Image.setImageBitmap(userData.getUserImage());


        btn_Image.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) { // 이미지 클릭 시 MyPage로 이동
                Intent intent_MyPage = new Intent(getApplicationContext(), MyPageActivity.class);
                //intent_MyPage.putExtra("NickName",text_NickName.getText());
                startActivity(intent_MyPage);
            }
        });
        btn_Field.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) { // 풋살장 찾기 버튼 클릭 시 카카오 맵 어플이 깔려있을 경우 실행, 없을 경우 지도가 실행된다
                GPS gps = new GPS(getApplicationContext());
                double lat = gps.getLatitude();
                double lon = gps.getLongitude();
                String url = "daummaps://search?q=풋살장&p="+lat+","+lon;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                try{
                    startActivity(intent);
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"카카오 맵 어플이 깔려있지 않으므로 지도가 실행됩니다.",Toast.LENGTH_LONG).show();
                    Intent intent_Field = new Intent(getApplicationContext(),FieldActiviry.class);
                    startActivity(intent_Field);
                }

            }
        });
        btn_Enroll.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_Enroll = new Intent(getApplicationContext(), EnrollActivity.class);
                //intent_Enroll.putExtra("NickName",text_NickName.getText());
                //intent_Enroll.putExtra("ByteArray",decodedByteArray);
                startActivity(intent_Enroll);
            }
        });
        btn_Find.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_Viewer = new Intent(getApplicationContext(), ViewerActivity.class);
                startActivity(intent_Viewer);
            }
        });
        btn_Back.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

}

