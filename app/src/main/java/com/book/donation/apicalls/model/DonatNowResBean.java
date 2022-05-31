package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class DonatNowResBean {

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

		@SerializedName("image")
		private String image;

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("item_donate")
		private String itemDonate;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("category_id")
		private String categoryId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("sub_category_id")
		private String subCategoryId;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("item_condition")
		private String itemCondition;

		@SerializedName("location")
		private String location;

		@SerializedName("id")
		private int id;

		@SerializedName("slug")
		private String slug;

		@SerializedName("email")
		private String email;

		public String getImage(){
			return image;
		}

		public String getPincode(){
			return pincode;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public String getItemDonate(){
			return itemDonate;
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

		public String getProductName(){
			return productName;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public String getCategoryId(){
			return categoryId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getSubCategoryId(){
			return subCategoryId;
		}

		public String getSubCategoryName(){
			return subCategoryName;
		}

		public String getItemCondition(){
			return itemCondition;
		}

		public String getLocation(){
			return location;
		}

		public int getId(){
			return id;
		}

		public String getSlug(){
			return slug;
		}

		public String getEmail(){
			return email;
		}
	}
}