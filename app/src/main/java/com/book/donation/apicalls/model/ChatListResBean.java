package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ChatListResBean {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private boolean status;

	public List<DataItem> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}

	public class DataItem{

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("thread_id")
		private String threadId;

		@SerializedName("gender")
		private String gender;

		@SerializedName("device_id")
		private String deviceId;

		@SerializedName("business_organization")
		private String businessOrganization;

		@SerializedName("name")
		private String name;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("id")
		private int id;

		@SerializedName("other_id")
		private String other_id;

		@SerializedName("email")
		private String email;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("category_name")
		private String categoryName;

		public String getPincode(){
			return pincode;
		}

		public String getThreadId(){
			return threadId;
		}

		public String getGender(){
			return gender;
		}

		public String getDeviceId(){
			return deviceId;
		}

		public String getBusinessOrganization(){
			return businessOrganization;
		}

		public String getName(){
			return name;
		}

		public String getMobile(){
			return mobile;
		}

		public String getProfilePic(){
			return profilePic;
		}

		public int getId(){
			return id;
		}

		public String getOtherId(){
			return other_id;
		}

		public String getEmail(){
			return email;
		}

		public String getProductName(){
			return productName;
		}

		public String getCategoryName(){
			return categoryName;
		}
	}
}