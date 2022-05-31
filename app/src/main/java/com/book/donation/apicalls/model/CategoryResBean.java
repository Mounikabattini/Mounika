package com.book.donation.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryResBean implements Serializable {

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

	public class DataItem implements Serializable{

		@SerializedName("image")
		private String image;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("parent_id")
		private int parentId;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("short_desc")
		private String shortDesc;

		@SerializedName("title")
		private String title;

		@SerializedName("sort_order")
		private int sortOrder;

		public String getImage(){
			return image;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getParentId(){
			return parentId;
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

		public String getShortDesc(){
			return shortDesc;
		}

		public String getTitle(){
			return title;
		}

		public int getSortOrder(){
			return sortOrder;
		}
	}
}