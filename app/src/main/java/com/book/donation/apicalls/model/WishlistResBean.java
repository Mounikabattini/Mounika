package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WishlistResBean {

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

		@SerializedName("image")
		private String image;

		@SerializedName("is_like")
		private String isLike;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("description")
		private String description;

		@SerializedName("location")
		private String location;

		@SerializedName("address")
		private String address;

		@SerializedName("id")
		private int id;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("user_name")
		private String userName;

		public String getImage(){
			return image;
		}

		public String getIsLike(){
			return isLike;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getDescription(){
			return description;
		}

		public String getLocation(){
			return location;
		}

		public String getAddress(){
			return address;
		}

		public int getId(){
			return id;
		}

		public String getProductName(){
			return productName;
		}

		public String getUserName(){
			return userName;
		}
	}
}