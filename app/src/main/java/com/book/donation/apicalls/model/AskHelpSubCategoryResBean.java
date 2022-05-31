package com.book.donation.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AskHelpSubCategoryResBean implements Serializable {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("message")
	private String message;

	@SerializedName("banner")
	private List<BannerItem> banner;

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

	public List<BannerItem> getBanner(){
		return banner;
	}

	public class DataItem implements Serializable{

		@SerializedName("sub_category_id")
		private int subCategoryId;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("askdata")
		private List<AskdataItem> askdata;

		public int getSubCategoryId(){
			return subCategoryId;
		}

		public String getSubCategoryName(){
			return subCategoryName;
		}

		public List<AskdataItem> getAskdata(){
			return askdata;
		}
	}

	public class AskdataItem implements Serializable{

		@SerializedName("address")
		private String address;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("image")
		private String image;

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

		@SerializedName("date")
		private String date;

		public String getAddress(){
			return address;
		}

		public String getUserName(){
			return userName;
		}

		public String getMobileNo(){
			return mobileNo;
		}

		public String getProfilePic(){
			return profilePic;
		}

		public String getImage(){
			return image;
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

		public String getDate(){
			return date;
		}
	}

	public class BannerItem {
		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("link")
		private String link;

		@SerializedName("banner")
		private String banner;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("status")
		private int status;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getLink(){
			return link;
		}

		public String getBanner(){
			return banner;
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

		public int getStatus(){
			return status;
		}
	}
}

