package com.example.footsalmanager.Activity;
// 카카오 지도 API 내에서 검색 기능이 없으므로 activity 비 활성화 한 후 버튼 클릭 시에 kakao map 어플 실행 하도록 함

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.footsalmanager.GPS;
import com.example.footsalmanager.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FieldActiviry extends AppCompatActivity {
    LocationManager locationManager = null;
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_activiry);
        GPS gps = new GPS(FieldActiviry.this);


        mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()), true); // 내 현재위치로 맵 위치 이동
        MapPOIItem marker = new MapPOIItem();
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gps.getLatitude(),gps.getLongitude()));// 내 현재위치에 핀 생성
        marker.setItemName("현재 위치"); // 핀 클릭 시 현재 위치 텍스트 표시
        mapView.addPOIItem(marker);

// 줌 레벨 변경
//        mapView.setZoomLevel(7, true);
//// 중심점 변경 + 줌 레벨 변경
//        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
// 줌 인
        mapView.zoomIn(true);
// 줌 아웃
        mapView.zoomOut(true);
        Log.d("Log",mapView.getCurrentLocationTrackingMode().toString());

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
    }
}
