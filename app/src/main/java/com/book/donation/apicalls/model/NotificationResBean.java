package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationResBean {

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

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("delete_status")
		private String deleteStatus;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("status")
		private String status;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getDeleteStatus(){
			return deleteStatus;
		}

		public String getDescription(){
			return description;
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

		public String getStatus(){
			return status;
		}
	}
}