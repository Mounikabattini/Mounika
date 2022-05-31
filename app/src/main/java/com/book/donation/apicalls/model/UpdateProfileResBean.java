package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileResBean {

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

		@SerializedName("gender")
		private String gender;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("name")
		private String name;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("email")
		private String email;

		public String getAccessToken(){
			return accessToken;
		}

		public String getGender(){
			return gender;
		}

		public String getUserId(){
			return userId;
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

		public String getEmail(){
			return email;
		}
	}
}