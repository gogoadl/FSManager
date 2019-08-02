package com.example.footsalmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footsalmanager.PHPRequest;
import com.example.footsalmanager.R;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.footsalmanager.Activity.LoginActivity.userData;


public class viewerClickActivity extends AppCompatActivity {
    String getViewerData;
    byte[] getImageData;
    TextView text_NickName,text_Phone,text_Skill,text_Age,text_Date,text_Area;
    EditText text_Contents;
    CircleImageView image_User;
    ImageView image_Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer_click);
        Log.d("log","ViewerClickActivity onCreate");
        Intent intent = getIntent();
        text_NickName = findViewById(R.id.text_NickName);
        image_User = findViewById(R.id.image_User);
        text_Age = findViewById(R.id.text_Age);
        text_Area = findViewById(R.id.text_Area);
        text_Date = findViewById(R.id.text_Date);
        text_Phone = findViewById(R.id.text_Phone);
        text_Skill = findViewById(R.id.text_Skill);
        text_Contents = findViewById(R.id.text_Contents);
        image_Delete = findViewById(R.id.image_Delete);
        getViewerData = intent.getExtras().getString("viewerData");
        getImageData = intent.getByteArrayExtra("imageData");

        image_Delete.setVisibility(View.INVISIBLE);

        String[] strDataArray = getViewerData.split("&");
        ByteArrayInputStream inStream = new ByteArrayInputStream(getImageData);
        Bitmap b = BitmapFactory.decodeStream(inStream);
        image_User.setImageBitmap(b);

        text_NickName.setText(strDataArray[0]);
        text_Age.setText(strDataArray[1]);
        text_Phone.setText(strDataArray[2]);
        text_Area.setText(strDataArray[3]);
        text_Skill.setText(strDataArray[4]);
        text_Date.setText(strDataArray[5]);
        try{
            text_Contents.setText(strDataArray[6]);
        } catch (Exception e)
        {
        }
        text_Phone.setClickable(true);
        text_Phone.setOnClickListener(new TextView.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String dialString = "tel:" + text_Phone.getText().toString();
                Intent dialIntent = new Intent("android.intent.action.DIAL");
                dialIntent.setData(Uri.parse(dialString));
                startActivity(dialIntent);
            }
        });

        if(userData.getUserNickName().equals(strDataArray[0])) // 현재 접속중인 계정의 NickName 과 게시글의 NickName 이 일치한지 확인
        {
            image_Delete.setVisibility(View.VISIBLE);
        }
        image_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(viewerClickActivity.this);
                dialog.setTitle("게시글을 삭제하시겠습니까?");
                dialog.setMessage("");
                dialog.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PHPRequest phpDeleteRequest = new PHPRequest();
                                phpDeleteRequest.queryURL = "fsManager_DeleteView.php";

                                try {
                                    String returnTxt = phpDeleteRequest.execute(strDataArray[0],strDataArray[4],strDataArray[5],strDataArray[6]).get();
                                    Log.d("delete View",returnTxt);
                                    if(returnTxt.equals("success "))
                                    {
                                        Toast.makeText(getApplicationContext(),"게시글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Log.d("DeleteView",returnTxt);
                                    }
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                dialog.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
            }
        });
    }
}
