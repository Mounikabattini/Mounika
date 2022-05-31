package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class HomeSectionResBean {

    @SerializedName("title")
    private String title;

    @SerializedName("msg")
    private String msg;

    @SerializedName("image")
    private int image;

    public String getTitle(){
        return title;
    }

    public String getMsg(){
        return msg;
    }

    public int getImage(){
        return image;
    }

}
