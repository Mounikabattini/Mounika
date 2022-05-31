package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ClaimResBean {

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

		@SerializedName("owner_id")
		private int ownerId;

		@SerializedName("product_id")
		private String productId;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("id")
		private int id;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getUserId(){
			return userId;
		}

		public int getOwnerId(){
			return ownerId;
		}

		public String getProductId(){
			return productId;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getMobile(){
			return mobile;
		}

		public int getId(){
			return id;
		}
	}
}