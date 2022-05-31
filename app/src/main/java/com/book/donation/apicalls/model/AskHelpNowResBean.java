package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class AskHelpNowResBean {

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

		@SerializedName("address")
		private String address;

		@SerializedName("category_id")
		private String categoryId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("sub_category_id")
		private String subCategoryId;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("email")
		private String email;

		public String getAddress(){
			return address;
		}

		public String getCategoryId(){
			return categoryId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserName(){
			return userName;
		}

		public String getSubCategoryId(){
			return subCategoryId;
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

		public int getId(){
			return id;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public String getEmail(){
			return email;
		}
	}
}