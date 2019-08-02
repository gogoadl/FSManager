package com.example.footsalmanager.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.footsalmanager.MyPageAdapter;
import com.example.footsalmanager.PHPGetImage;
import com.example.footsalmanager.PHPGetImageLobby;
import com.example.footsalmanager.R;
import com.example.footsalmanager.viewerData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.footsalmanager.Activity.LoginActivity.userData;

public class MyPageActivity extends AppCompatActivity {
    CircleImageView btn_Image;
    TextView text_NickName;
    ImageView  test_Image;
    //String strNickName;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        btn_Image = findViewById(R.id.btn_Image);
        text_NickName = findViewById(R.id.text_NickName);

        //Intent myPageIntent = getIntent();
        //strNickName = myPageIntent.getExtras().getString("NickName");
        text_NickName.setText(userData.getUserNickName());


        recyclerView = findViewById(R.id.recyclerview_MyPage);
        MyPageAdapter myPageAdapter;
        ArrayList<viewerData> myPageData = new ArrayList<>();

        Bitmap myPageImg = BitmapFactory.decodeResource(getResources(), R.mipmap.baseline_account_circle_black_48);
        Bitmap logoutImg = BitmapFactory.decodeResource(getResources(),R.mipmap.baseline_https_black_48dp);
        Bitmap appinfoImg = BitmapFactory.decodeResource(getResources(), R.mipmap.baseline_info_black_48dp);

        viewerData data = new viewerData(myPageImg,"마이페이지");
        viewerData data2 = new viewerData(logoutImg,"로그아웃");
        viewerData data3 = new viewerData(appinfoImg,"앱정보");

        myPageData.add(data);
        myPageData.add(data2);
        myPageData.add(data3);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myPageAdapter = new MyPageAdapter(getApplicationContext(),myPageData);
        recyclerView.setAdapter(myPageAdapter);

        try {

            PHPGetImageLobby phpGetImageLobby = new PHPGetImageLobby();
            phpGetImageLobby.queryURL = "testt.php";
            String str = phpGetImageLobby.execute(text_NickName.getText().toString()).get();
            if (str.equals(" ")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.baseline_account_circle_black_48);
                btn_Image.setImageBitmap(bitmap);
            } else {
                byte[] decodedByteArray = Base64.decode(str, Base64.NO_WRAP);
                ByteArrayInputStream inStream = new ByteArrayInputStream(decodedByteArray);
                Bitmap b = BitmapFactory.decodeStream(inStream); // 데이터를 bitmap 이미지로 변환
                btn_Image.setImageBitmap(b);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btn_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        //String NickName = intent.getExtras().getString("NickName");
        //text_NickName.setText(NickName + " 님 환영합니다");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    data.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    Bitmap resize = Bitmap.createScaledBitmap(img,img.getWidth() / 5,img.getHeight() / 5,false);
                    Uri uri = data.getData();
                    String s = getBase64String(resize);

                    PHPGetImage phpgetImage = new PHPGetImage();
                    try{
                        phpgetImage.queryURL = "fsManager_Image.php";
                        String str = phpgetImage.execute(text_NickName.getText().toString(),s).get();

                        //text_NickName.setText(str);
                        byte[] decodedByteArray = Base64.decode(str,Base64.NO_WRAP);
                        ByteArrayInputStream inStream = new ByteArrayInputStream(decodedByteArray);
                        Bitmap b = BitmapFactory.decodeStream(inStream);
                        test_Image.setImageBitmap(b);


                    }catch (Exception e)
                    {

                    }

                    in.close();
                    // 이미지 표시
                    btn_Image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    btn_Image.setImageBitmap(img);
                    userData.setUserImage(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

}
