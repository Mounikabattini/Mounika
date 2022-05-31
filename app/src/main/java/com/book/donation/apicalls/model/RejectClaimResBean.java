package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RejectClaimResBean {

	@SerializedName("data")
	private List<Object> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("message")
	private String message;

	public List<Object> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getBaseUrl(){
		return baseUrl;
	}

	public String getMessage(){
		return message;
	}
}