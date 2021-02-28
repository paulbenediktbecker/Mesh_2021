package com.example.frontend;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExampleItem {

    private int mImageResource;
    private String mText1;
    private String mText2;
    private String mIdAdresse;
    private String mIDLot;




    public ExampleItem(int imageResource, String text1, String text2, String iDAdresse, String iDLot) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mIdAdresse = iDAdresse;
        mIDLot = iDLot;
    }
    public int getImageResource() {
        return mImageResource;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() {
        return mText2;
    }
    public String getmIdAdresse() {
        return mIdAdresse;
    }

    public String getmIDLot() {
        return mIDLot;
    }

}