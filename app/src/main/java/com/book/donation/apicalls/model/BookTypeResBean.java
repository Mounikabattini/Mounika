package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BookTypeResBean {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public class DataItem{

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		public int getId(){
			return id;
		}

		public String getTitle(){
			return title;
		}
	}
}