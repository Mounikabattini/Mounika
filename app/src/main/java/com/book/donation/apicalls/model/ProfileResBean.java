package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ProfileResBean {

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

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("address")
		private String address;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("name")
		private String name;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("email")
		private String email;

		@SerializedName("business_organization")
		private String businessOrganization;

		@SerializedName("registration_no")
		private String registrationNo;

		@SerializedName("organization_name")
		private String organizationName;

		@SerializedName("website")
		private String website;

		@SerializedName("city")
		private String city;

		@SerializedName("state")
		private String state;

		public String getPincode(){
			return pincode;
		}

		public String getAddress(){
			return address;
		}

		public String getProfilePic(){
			return profilePic;
		}

		public String getName(){
			return name;
		}

		public String getMobile(){
			return mobile;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getEmail(){
			return email;
		}

		public String getBusinessOrganization(){
			return businessOrganization;
		}

		public String getRegistrationNo(){
			return registrationNo;
		}

		public String getOrganization_name(){
			return organizationName;
		}

		public String getWebsite(){
			return website;
		}

		public String getCity(){
			return city;
		}

		public String getState(){
			return state;
		}
	}
}