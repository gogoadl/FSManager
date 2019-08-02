package com.example.footsalmanager;

import android.graphics.Bitmap;

public class viewerData { // 데이터 모델

    Bitmap image;
    String nickName,skill,area,currentDate;

    public viewerData(Bitmap newImage, String newNickName, String newSkill, String newArea,String newCurrentDate){
        this.image = newImage;
        this.nickName = newNickName;
        this.skill = newSkill;
        this.area = newArea;
        this.currentDate = newCurrentDate;

    }public viewerData(Bitmap newImage, String newNickName){
        this.image = newImage;
        this.nickName = newNickName;

    }
    public Bitmap getImage() {
        return image;
    }
    public String getNickName()
    {
        return nickName;
    }
    public String getSkill() {
        return skill;
    }
    public String getArea() { return area; }
    public String getCurrentDate() { return currentDate; }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setSkill(String skill) {
        this.skill = skill;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public void setCurrentDate(String currentDate) { this.currentDate = currentDate; }
}
