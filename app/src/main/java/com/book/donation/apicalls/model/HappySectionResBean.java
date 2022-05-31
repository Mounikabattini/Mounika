package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class HappySectionResBean{

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

		@SerializedName("image")
		private String image;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("experience_text")
		private String experienceText;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserId(){
			return userId;
		}

		public String getExperienceText(){
			return experienceText;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}
	}
}