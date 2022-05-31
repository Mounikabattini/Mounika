package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ClaimAcceptResBean {

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
		private int userId;

		@SerializedName("owner_id")
		private int ownerId;

		@SerializedName("delete_status")
		private String deleteStatus;

		@SerializedName("product_id")
		private int productId;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("claim_by")
		private String claimBy;

		@SerializedName("is_claimed")
		private String isClaimed;

		@SerializedName("status")
		private String status;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public int getUserId(){
			return userId;
		}

		public int getOwnerId(){
			return ownerId;
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

		public String getClaimBy(){
			return claimBy;
		}

		public String getIsClaimed(){
			return isClaimed;
		}

		public String getStatus(){
			return status;
		}
	}
}