package com.example.footsalmanager.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.footsalmanager.PHPGetViewerData;
import com.example.footsalmanager.R;
import com.example.footsalmanager.RecyclerViewAdapter;
import com.example.footsalmanager.viewerData;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ViewerActivity extends AppCompatActivity {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    Spinner spinner_Search;
    Button btn_Search;
    EditText text_Search;

    String str = "";
    String imageStr = "";

    ArrayList<String> putViewerList = new ArrayList<>();
    ArrayList<byte[]> putImageList = new ArrayList<>();
    ArrayList<viewerData> viewerDatas = new ArrayList<>();

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("log","ViewerActivity onRestart");

        PHPGetViewerData phpGetViewerData = new PHPGetViewerData();
        phpGetViewerData.queryURL = "fsManager_getViewerData.php";

        try {

            str = phpGetViewerData.execute().get();
            //String s[] = str.split("|");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try{
            PHPGetViewerData phpGetImageData = new PHPGetViewerData();
            phpGetImageData.queryURL = "fsmanager_getImageData.php";
            imageStr = phpGetImageData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        viewerDatas.clear();
        setData();
        setRecyclerView();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("log","ViewerActivity onResume");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        Log.d("log","ViewerActivity onCreate");
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.RecyclerView);
        spinner_Search = findViewById(R.id.spinner_Search);
        btn_Search = findViewById(R.id.btn_Search);
        text_Search = findViewById(R.id.text_Search);

        String[] searchList = {"지역별","실력별"};
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,searchList);
        spinner_Search.setAdapter(searchAdapter);


        PHPGetViewerData phpGetViewerData = new PHPGetViewerData();
        phpGetViewerData.queryURL = "fsManager_getViewerData.php";

        btn_Search.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Log.d("log","btn_Search onClick");
                PHPGetViewerData phpGetVFilteredData = new PHPGetViewerData();
                phpGetVFilteredData.queryURL = "fsManager_getViewerData.php";

                PHPGetViewerData phpGetVFilteredImage = new PHPGetViewerData();
                phpGetVFilteredImage.queryURL = "fsManager_getImageData.php";

                if(spinner_Search.getSelectedItem().equals("지역별"))
                {
                    try {
                        String text = text_Search.getText().toString();
                        str = phpGetVFilteredData.execute(text_Search.getText().toString(), "1").get();
                        imageStr = phpGetVFilteredImage.execute(text_Search.getText().toString(),"1").get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try {
                        str = phpGetVFilteredData.execute(text_Search.getText().toString(), "2").get();
                        imageStr = phpGetVFilteredImage.execute(text_Search.getText().toString(),"2").get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                viewerDatas.clear();
                setData();
                if(viewerDatas.size() == 0)
                {
                    Toast.makeText(getApplicationContext(),"현재 등록된 게시글이 없습니다!",Toast.LENGTH_SHORT).show();
                }
                setRecyclerView();

            }
        });

        try {

            str = phpGetViewerData.execute().get();
            //String s[] = str.split("|");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try{
            PHPGetViewerData phpGetImageData = new PHPGetViewerData();
            phpGetImageData.queryURL = "fsmanager_getImageData.php";
            imageStr = phpGetImageData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        viewerDatas.clear();

        setData();
        setRecyclerView();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull final RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                        View view = layoutManager.findViewByPosition(recyclerView.getChildAdapterPosition(child));
                        if (recyclerView.getChildLayoutPosition(child) != -1) {
                            TextView text = view.findViewById(R.id.text_NickName);
                            String str = text.getText().toString();

                            Intent intent_viewer = new Intent(getApplicationContext(), viewerClickActivity.class);

                            intent_viewer.putExtra("viewerData", putViewerList.get(recyclerView.getChildAdapterPosition(child)));
                            intent_viewer.putExtra("imageData", putImageList.get(recyclerView.getChildAdapterPosition(child)));
                            startActivity(intent_viewer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }

                        Log.d("tag", "" + recyclerView.getChildAdapterPosition(child)); // 몇번째 아이템인지 확인 할 수 있음

                    }

                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) { }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("log","onRefresh run");
                PHPGetViewerData phpGetViewerData = new PHPGetViewerData();
                phpGetViewerData.queryURL = "fsManager_getViewerData.php";

                try {

                    str = phpGetViewerData.execute().get();
                    //String s[] = str.split("|");

            } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try{
                    PHPGetViewerData phpGetImageData = new PHPGetViewerData();
                    phpGetImageData.queryURL = "fsmanager_getImageData.php";
                    imageStr = phpGetImageData.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                viewerDatas.clear();

                setData();
                if(viewerDatas.size() == 0)
                {
                    Toast.makeText(getApplicationContext(),"현재 등록된 게시글이 없습니다!",Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
                setRecyclerView();


            }
        });

    }


    void setRecyclerView(){
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),viewerDatas);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    ArrayList setData(){                                // 데이터를 뷰어에 추가


        String[] StringData = str.split("/\\?/"); // ? 특수문자 처리하여 /?/ 구분자로 문자열 자르기
        String[] ImageData = imageStr.split(" ");
        int rows = ImageData.length;

        for(int i = 1; i<=rows; i++ ){
            byte[] decodedByteArray = Base64.decode(ImageData[i-1],Base64.NO_WRAP);
            ByteArrayInputStream inStream = new ByteArrayInputStream(decodedByteArray);
            Bitmap b = BitmapFactory.decodeStream(inStream);

            putImageList.add(decodedByteArray);
            for(int j =(i-1)*8; j<i*8 ; j++)
            {
                String NickName = StringData[j]; j++;
                String Age = StringData[j]; j++;
                String Phone = StringData[j]; j++;
                String Area = StringData[j]; j++;
                String Skill = StringData[j]; j++;
                String Date = StringData[j]; j++;
                String Contents = StringData[j]; j++;
                String CurrentDate = StringData[j];
                viewerData data = new viewerData(b,NickName,Skill,Area,CurrentDate);
                putViewerList.add(NickName + "&" + Age  + "&" + Phone + "&" +  Area + "&" + Skill + "&" + Date + "&" + Contents + "&" + CurrentDate);
                viewerDatas.add(data);
                if(j == rows * 8 - 1)
                    break;
            }
        }

        return putViewerList;
    }
}
