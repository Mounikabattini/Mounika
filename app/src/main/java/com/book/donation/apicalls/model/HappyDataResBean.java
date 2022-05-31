package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HappyDataResBean {

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

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("experience_text")
		private String experienceText;

		@SerializedName("title")
		private String title;

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

		public String getTitle(){
			return title;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}
	}
}