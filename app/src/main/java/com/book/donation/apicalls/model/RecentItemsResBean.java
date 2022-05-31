package com.book.donation.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RecentItemsResBean implements Serializable {

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

		@SerializedName("location")
		private String location;

		@SerializedName("address")
		private String address;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("category_id")
		private int categoryId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("sub_category_id")
		private int subCategoryId;

		@SerializedName("delete_status")
		private String deleteStatus;

		@SerializedName("id")
		private int id;

		@SerializedName("email")
		private String email;

		@SerializedName("status")
		private String status;

		@SerializedName("image")
		private String image;

		@SerializedName("product_name")
		private String productName;

		public String getLocation(){
			return location;
		}

		public String getAddress(){
			return address;
		}

		public String getUserName(){
			return userName;
		}

		public String getMobileNo(){
			return mobileNo;
		}

		public String getDescription(){
			return description;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public int getCategoryId(){
			return categoryId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getSubCategoryId(){
			return subCategoryId;
		}

		public String getDeleteStatus(){
			return deleteStatus;
		}

		public int getId(){
			return id;
		}

		public String getEmail(){
			return email;
		}

		public String getStatus(){
			return status;
		}

		public String getImage(){
			return image;
		}

		public String getProductName(){
			return productName;
		}
	}
}