package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyClaimItemListResBean {

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

		@SerializedName("image")
		private String image;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("is_claim_requested")
		private String isClaimRequested;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("description")
		private String description;

		@SerializedName("owner_mobile")
		private String ownerMobile;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("product_id")
		private String productId;

		@SerializedName("is_wishlist")
		private String isWishlist;

		@SerializedName("is_claimed")
		private String isClaimed;

		@SerializedName("total_claim")
		private int totalClaim;

		@SerializedName("is_book_donated")
		private String isBookDonated;

		@SerializedName("item_condition")
		private Object itemCondition;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("location")
		private String location;

		@SerializedName("id")
		private int id;

		@SerializedName("claim_id")
		private String claim_id;

		@SerializedName("is_recieved")
		private String is_recieved;

		@SerializedName("is_request_recieved")
		private String isRequestRecieved;

		@SerializedName("claim_by")
		private String claimBy;

		public String getDate(){
			return date;
		}

		public String getImage(){
			return image;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public String getIsClaimRequested(){
			return isClaimRequested;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getDescription(){
			return description;
		}

		public String getOwnerMobile(){
			return ownerMobile;
		}

		public String getProductName(){
			return productName;
		}

		public String getProductId(){
			return productId;
		}

		public String getIsWishlist(){
			return isWishlist;
		}

		public String getIsClaimed(){
			return isClaimed;
		}

		public int getTotalClaim(){
			return totalClaim;
		}

		public String getIsBookDonated(){
			return isBookDonated;
		}

		public Object getItemCondition(){
			return itemCondition;
		}

		public String getSubCategoryName(){
			return subCategoryName;
		}

		public String getLocation(){
			return location;
		}

		public int getId(){
			return id;
		}

		public String getClaim_id(){
			return claim_id;
		}

		public String getIs_recieved(){
			return is_recieved;
		}

		public String getIsRequestRecieved(){
			return isRequestRecieved;
		}

		public String getClaimBy(){
			return claimBy;
		}
	}
}