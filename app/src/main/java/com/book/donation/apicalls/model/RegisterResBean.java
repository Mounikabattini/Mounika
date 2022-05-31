package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResBean {

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("message")
	private String message;

	public Data getData(){
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

	public class Data{

		@SerializedName("access_token")
		private String accessToken;

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("address")
		private String address;

		@SerializedName("name")
		private String name;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("base_url")
		private String baseUrl;

		@SerializedName("id")
		private int id;

		@SerializedName("email")
		private String email;

		public String getAccessToken(){
			return accessToken;
		}

		public String getPincode(){
			return pincode;
		}

		public String getAddress(){
			return address;
		}

		public String getName(){
			return name;
		}

		public String getMobile(){
			return mobile;
		}

		public String getProfilePic(){
			return profilePic;
		}

		public String getBaseUrl(){
			return baseUrl;
		}

		public int getId(){
			return id;
		}

		public String getEmail(){
			return email;
		}
	}
}