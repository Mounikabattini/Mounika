package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ClaimedPersonListResBean {

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

		@SerializedName("address")
		private String address;

		@SerializedName("user_id")
		private int userId;

		@SerializedName("claim_id")
		private int claimId;

		@SerializedName("name")
		private String name;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("is_claimed")
		private String isClaimed;

		public String getAddress(){
			return address;
		}

		public int getUserId(){
			return userId;
		}

		public int getClaimId(){
			return claimId;
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

		public String getIsClaimed(){
			return isClaimed;
		}
	}
}