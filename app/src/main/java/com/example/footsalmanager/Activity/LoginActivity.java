package com.example.footsalmanager.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.footsalmanager.GetUserData;
import com.example.footsalmanager.OnSingleClickListener;
import com.example.footsalmanager.PHPCheckLogin;
import com.example.footsalmanager.R;

import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText IDText,PWText;
    Button btn_Login,btn_SignUp;
    String result;
    String[] returnStr;
    public static GetUserData userData; // 유저 데이터를 static으로 선언 하여 사용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IDText =  findViewById(R.id.txt_ID);
        PWText =  findViewById(R.id.txt_PW);
        btn_Login = findViewById(R.id.btn_Login);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        userData = new GetUserData();
        InputFilter[] filter = new InputFilter[]{textSetFilter("eng")};
        IDText.setFilters(filter);
        PWText.setFilters(filter);
        //getAppKeyHash(); 어플의 해시키를 얻음 (오픈 api 사용)

        btn_Login.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try{
                    PHPCheckLogin phpCheckLogin = new PHPCheckLogin();
                    phpCheckLogin.queryURL = "fsManager_Login.php";

                    result = phpCheckLogin.execute(IDText.getText().toString(),PWText.getText().toString()).get();
                    returnStr = result.split(" ");

                    if(returnStr[0].equals(IDText.getText().toString()) && returnStr[1].equals((PWText.getText().toString())))
                    {
                        Intent lobbyActivity = new Intent(getApplicationContext(), LobbyActivity.class);
                        Intent MyPageActivity = new Intent(getApplicationContext(), com.example.footsalmanager.Activity.MyPageActivity.class);
//                        lobbyActivity.putExtra("NickName",returnStr[2]); // NickName을 로비 액티비티에 넘겨준다
//                        MyPageActivity.putExtra("NickName",returnStr[2]); // NickName을 로비 액티비티에 넘겨준다
                            byte[] decodedByteArray = Base64.decode(returnStr[3], Base64.NO_WRAP);
                            ByteArrayInputStream inStream = new ByteArrayInputStream(decodedByteArray);
                            Bitmap b = BitmapFactory.decodeStream(inStream); // 데이터를 bitmap 이미지로 변환

                            userData.setUserImage(b);
                            userData.setUserNickName(returnStr[2]);

                        startActivity(lobbyActivity);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_SignUp.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_SignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent_SignUp);
            }
        });
    }
    public InputFilter textSetFilter(String lang){ // EditText에 입력할 수 있는 문자를 제한 하는 필터
        Pattern ps;
        if(lang.equals("email"))
        {
            ps = Pattern.compile(" ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$");
        }
        else if(lang.equals("eng"))
        {
            ps = Pattern.compile("^[a-zA-Z0-9]*$");
        }
        else
        {
            ps = Pattern.compile("^[ㄱ-ㅣ가-힣\\s]*$");
        }
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            if (!ps.matcher(source).matches())
            {
                return "";
            }
            return null;
        };
        return filter;
    }
//    private void getAppKeyHash() { // 해시키를 얻는 함수
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                Log.e("Hash key", something);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Log.e("name not found", e.toString());
//        }
//    }
}

