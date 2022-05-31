package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchItemResBean {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("message")
	private String message;

	public List<DataItem> getData(){
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

	public class DataItem{

		@SerializedName("is_book_donated")
		private String isBookDonated;

		@SerializedName("id")
		private int id;

		@SerializedName("product_name")
		private String productName;

		public String getIsBookDonated(){
			return isBookDonated;
		}

		public int getId(){
			return id;
		}

		public String getProductName(){
			return productName;
		}
	}
}