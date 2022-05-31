package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductResBean {

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

		@SerializedName("date")
		private String date;

		@SerializedName("sub_category_id")
		private String subCategoryId;

		@SerializedName("id")
		private String id;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("description")
		private String description;

		@SerializedName("image")
		private String image;

		@SerializedName("is_wishlist")
		private String isLike;

		@SerializedName("location")
		private String location;

		@SerializedName("short_desc")
		private String shortDesc;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("status")
		private String status;

		public String getDate(){
			return date;
		}

		public String getSubCategoryId(){
			return subCategoryId;
		}

		public String getId(){
			return id;
		}

		public String getSubCategoryName(){
			return subCategoryName;
		}

		public String getDescription(){
			return description;
		}

		public String getImage(){
			return image;
		}

		public String getIsLike(){
			return isLike;
		}

		public String getLocation(){
			return location;
		}

		public String getShortDesc(){
			return shortDesc;
		}

		public String getProductName(){
			return productName;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public String getStatus(){
			return status;
		}

		public void setIsLike(String isLike){
			this.isLike = isLike;
		}
	}
}