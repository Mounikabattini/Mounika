package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class AddToWishlistResBean {

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

		@SerializedName("subcategory_id")
		private int subcategoryId;

		@SerializedName("category_id")
		private int categoryId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("user_id")
		private int userId;

		@SerializedName("delete_status")
		private String deleteStatus;

		@SerializedName("product_id")
		private int productId;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("status")
		private String status;

		public int getSubcategoryId(){
			return subcategoryId;
		}

		public int getCategoryId(){
			return categoryId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getUserId(){
			return userId;
		}

		public String getDeleteStatus(){
			return deleteStatus;
		}

		public int getProductId(){
			return productId;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getStatus(){
			return status;
		}
	}
}