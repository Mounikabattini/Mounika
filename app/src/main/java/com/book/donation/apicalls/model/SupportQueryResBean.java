package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class SupportQueryResBean {

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

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private String userId;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("query")
		private String query;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserId(){
			return userId;
		}

		public String getUserName(){
			return userName;
		}

		public String getQuery(){
			return query;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getTitle(){
			return title;
		}
	}
}