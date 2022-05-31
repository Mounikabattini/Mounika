package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class LoginResBean {

	@SerializedName("msg")
	private String msg;

	@SerializedName("success")
	private boolean success;

	public String getMsg(){
		return msg;
	}

	public boolean isSuccess(){
		return success;
	}
}