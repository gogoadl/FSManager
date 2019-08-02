package com.example.footsalmanager.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.footsalmanager.OnSingleClickListener;
import com.example.footsalmanager.PHPEnroll;
import com.example.footsalmanager.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.footsalmanager.Activity.LoginActivity.userData;

public class EnrollActivity extends AppCompatActivity {
    Calendar c = Calendar.getInstance();
    EditText text_Date,text_Time,text_Phone,text_contents;
    TextView text_NickName;
    RadioGroup group_Skill;
    RadioButton rb_High,rb_Middle,rb_Low;
    ArrayAdapter<CharSequence> arr_Do,arr_Si;
    Spinner spinner_Do,spinner_Si,spinner_Age;
    String rb_CheckedString,currentDate;
    String image_User;
    Button btn_OK,btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        currentDate = getCurrentDate(c);
        btn_Back = findViewById(R.id.btn_back); btn_OK = findViewById(R.id.btn_ok);
        text_NickName = findViewById(R.id.text_NickName); text_Phone = findViewById(R.id.text_Phone);
        text_Date = findViewById(R.id.text_Date); text_Time = findViewById(R.id.text_Time);
        text_contents = findViewById(R.id.text_contents); spinner_Age = findViewById(R.id.spinner_Age);
        spinner_Do = findViewById(R.id.spinner_Do); spinner_Si = findViewById(R.id.spinner_Si);
        group_Skill = findViewById(R.id.group_Skill); rb_High = findViewById(R.id.rb_High);
        rb_Middle = findViewById(R.id.rb_Middle); rb_Low = findViewById(R.id.rb_Low);
        rb_Low.setChecked(true); rb_CheckedString = rb_Low.getText().toString();
        group_Skill.check(R.id.rb_Low);


        String[] ageList = {"연령선택","10대","20대","30대","40대","50대"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,ageList);

        text_NickName.setText(userData.getUserNickName());
        image_User = getBase64String(userData.getUserImage());

        group_Skill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_High:
                        rb_CheckedString = rb_High.getText().toString();
                        break;
                    case R.id.rb_Middle:
                        rb_CheckedString = rb_Middle.getText().toString();
                        break;
                    case R.id.rb_Low:
                        rb_CheckedString = rb_Low.getText().toString();
                        break;
                }
            }
        });

        text_Phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        spinner_Age.setAdapter(adapter);
        spinner_Age.setSelection(0,true);
        View v = spinner_Age.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);
        spinner_Age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        text_Date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EnrollActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            try {
                                //Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                text_Date.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.getDatePicker().setCalendarViewShown(false);
                    datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    datePickerDialog.show();

                }
                return v.performClick();

            }
        });

        text_Time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final TimePickerDialog timePickerDialog = new TimePickerDialog(EnrollActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            try{
                                String AM_PM;
                                if (hourOfDay < 12)
                                {
                                    AM_PM = "오전 ";
                                    text_Time.setText(AM_PM + hourOfDay + "시 " + minute + "분");
                                }else{
                                    AM_PM = "오후 ";
                                    text_Time.setText(AM_PM + (hourOfDay - 12) + "시 " + minute + "분");
                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);

                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    timePickerDialog.show();

                }
                return v.performClick();
            }
        });

        arr_Do = ArrayAdapter.createFromResource(this,R.array.spinner_Do,android.R.layout.simple_spinner_dropdown_item);
        arr_Do.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Do.setAdapter(arr_Do);


        spinner_Do.setSelection(0,true);
        View v2 = spinner_Do.getSelectedView();
        ((TextView)v2).setTextColor(Color.WHITE);

        spinner_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setTextColor(Color.WHITE);
                if(arr_Do.getItem(position).equals("서울특별시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this,R.array.spinner_Seoul,android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("부산광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Busan, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("대구광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Daegu, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("인천광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Incheon, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("광주광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Gwangju, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("대전광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Daejeon, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("울산광역시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Ulsan, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("경기도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("강원도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Gangwon, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("충청북도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Chungbuk, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("충청남도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Chungnam, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("전라북도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Jeonbuk, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("전라남도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Jeonnam, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("경상북도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Gyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("경상남도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Gyeongnam, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("제주특별자치도"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Jeju, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("세종특별자치시"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Sejong, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }else if(arr_Do.getItem(position).equals("시/도 선택"))
                {
                    arr_Si = ArrayAdapter.createFromResource(EnrollActivity.this, R.array.spinner_Empty, android.R.layout.simple_spinner_dropdown_item);
                    spinner_Si.setAdapter(arr_Si);
                }
                spinner_Si.setSelection(0,true);
                View v3 = spinner_Si.getSelectedView();
                ((TextView)v3).setTextColor(Color.WHITE);
                spinner_Si.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView)view).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_Back.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        btn_OK.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if(spinner_Age.getSelectedItem().equals("연령선택"))
                {
                    Toast.makeText(getApplicationContext(),"연령대를 선택 해주세요",Toast.LENGTH_SHORT).show();
                }else if(text_Date.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"날짜를 선택 해주세요",Toast.LENGTH_SHORT).show();
                }else if(text_Time.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"시간을 선택 해주세요",Toast.LENGTH_SHORT).show();
                }else if(spinner_Do.getSelectedItem().equals("시/도 선택"))
                {
                    Toast.makeText(getApplicationContext(),"지역을 선택 해주세요",Toast.LENGTH_SHORT).show();
                }else if(text_Phone.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"전화번호를 입력 해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(text_contents.getText().toString().equals(""))
                {
                    text_contents.setText(text_contents.getHint().toString());
                }else{

                    String result;
                    try {
                        PHPEnroll phpEnroll = new PHPEnroll();
                        phpEnroll.queryURL = "fsManager_Enroll.php";
                        result = phpEnroll.execute(text_NickName.getText().toString(), image_User, spinner_Age.getSelectedItem().toString(), text_Phone.getText().toString(),
                                spinner_Do.getSelectedItem().toString() + spinner_Si.getSelectedItem().toString(), rb_CheckedString,
                                text_Date.getText().toString() + text_Time.getText().toString(), text_contents.getText().toString(),currentDate).get();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
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
    public String getCurrentDate(Calendar c)
    {
        int year = c.get(Calendar.YEAR);
        int mon = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String date = year + "/" + mon + "/" + day;
        Log.d("currentTime",year + "/" + mon + "/" + day);
        return date;

    }

}
