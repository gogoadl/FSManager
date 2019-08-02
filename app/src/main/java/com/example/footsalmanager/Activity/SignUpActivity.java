package com.example.footsalmanager.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.footsalmanager.PHPRequest;
import com.example.footsalmanager.R;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    String defaultImageString;
    EditText IDText,PWText,PWTextCheck,NickNameText,EmailText;
    Button btn_OK,btn_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        IDText = findViewById(R.id.txt_ID);
        PWText = findViewById(R.id.txt_PW);
        PWTextCheck = findViewById(R.id.txt_PWCheck);
        NickNameText = findViewById(R.id.txt_NickName);
        EmailText = findViewById(R.id.txt_Email);
        InputFilter[] filter1 = new InputFilter[]{textSetFilter("eng")};
        IDText.setFilters((filter1));
        PWText.setFilters(filter1);
        PWTextCheck.setFilters(filter1);
        EmailText.setFilters(filter1);
        btn_OK = findViewById(R.id.btn_OK);
        btn_Back = findViewById(R.id.btn_Back);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.baseline_account_circle_black_48);
        defaultImageString = getBase64String(bitmap);

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phpReturnMsg;

                if(TextUtils.isEmpty(IDText.getText()) || IDText.getText() == null )
                {
                    Toast.makeText(getApplicationContext(), "아이디를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(hasSpecialCharacter(IDText.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "아이디를 잘못 입력하셨습니다. ", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(PWText.getText()) || PWText.getText() == null )
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력 해 주세요", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(NickNameText.getText()) || PWTextCheck.getText() == null )
                {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력 해 주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!PWText.getText().toString().equals(PWTextCheck.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다..", Toast.LENGTH_SHORT).show();
                }
                else {

                    try {

                        PHPRequest phpRequest = new PHPRequest();
                        phpRequest.queryURL = "fsManager_SignUp.php";
                        phpReturnMsg = phpRequest.execute(IDText.getText().toString(), PWText.getText().toString(), NickNameText.getText().toString(), EmailText.getText().toString(),defaultImageString).get();

                        Toast.makeText(getApplicationContext(), phpReturnMsg, Toast.LENGTH_SHORT).show();
                        Log.d("tag",phpReturnMsg);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "회원가입 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag",e.getMessage());
                    }
                }

            }
        });
    }
    public String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
    public  boolean hasSpecialCharacter(String string) {
        if (Character.isDigit(string.charAt(0)))
        {
            Toast.makeText(getApplicationContext(),"아이디는 숫자로 시작할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (string.length() <= 6)
        {
            Toast.makeText(getApplicationContext(),"아이디는 최소 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int i = 0; i < string.length(); i++) {

            if (!Character.isLetterOrDigit(string.charAt(i))) {
                return true;
            }

        }
        return false;
    }

    public InputFilter textSetFilter(String lang){
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
}



