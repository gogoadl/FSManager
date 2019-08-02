package com.example.footsalmanager;

import android.graphics.Bitmap;

public class GetUserData {
    private String userID;
    private String userPW;
    private String userNickName;
    private String userEmail;
    private Bitmap userImage;

    public GetUserData()
    {
        this.userNickName = null;
        this.userImage = null;
        this.userID = null;
        this.userPW = null;
        this.userEmail = null;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }
}
