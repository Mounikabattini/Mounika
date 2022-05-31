package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResBean {

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("expires_at")
	private String expiresAt;

	@SerializedName("success")
	private boolean success;

	@SerializedName("type")
	private String type;

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("user")
	private User user;

	@SerializedName("message")
	private String message;

	public String getAccessToken(){
		return accessToken;
	}

	public String getExpiresAt(){
		return expiresAt;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getType(){
		return type;
	}

	public String getTokenType(){
		return tokenType;
	}

	public String getMessage(){
		return message;
	}

	public User getUser(){
		return user;
	}

	public class User{

		@SerializedName("redemption_value")
		private int redemptionValue;

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("device_key")
		private Object deviceKey;

		@SerializedName("state")
		private String state;

		@SerializedName("city")
		private String city;

		@SerializedName("city_name")
		private String cityName;

		@SerializedName("address")
		private String address;

		@SerializedName("gender")
		private String gender;

		@SerializedName("redemption_points")
		private int redemptionPoints;

		@SerializedName("device_id")
		private String deviceId;

		@SerializedName("mobile")
		private String mobile;

		@SerializedName("profile_pic")
		private String profilePic;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("wallet_id")
		private Object walletId;

		@SerializedName("user_type")
		private String userType;

		@SerializedName("help_india_id")
		private Object helpIndiaId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("business_organization")
		private String businessOrganization;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("email")
		private String email;

		@SerializedName("fcm_id")
		private Object fcmId;

		@SerializedName("organization_name")
		private String organizationName;

		@SerializedName("website")
		private String website;

		@SerializedName("registration_no")
		private String registrationNo;

		public int getRedemptionValue(){
			return redemptionValue;
		}

		public String getPincode(){
			return pincode;
		}

		public Object getDeviceKey(){
			return deviceKey;
		}

		public String getAddress(){
			return address;
		}

		public String getState(){
			return state;
		}

		public String getCity(){
			return city;
		}

		public String getCityName(){
			return cityName;
		}

		public String getGender(){
			return gender;
		}

		public int getRedemptionPoints(){
			return redemptionPoints;
		}

		public String getDeviceId(){
			return deviceId;
		}

		public String getMobile(){
			return mobile;
		}

		public String getProfilePic(){
			return profilePic;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public Object getWalletId(){
			return walletId;
		}

		public String getUserType(){
			return userType;
		}

		public Object getHelpIndiaId(){
			return helpIndiaId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getBusinessOrganization(){
			return businessOrganization;
		}

		public String getName(){
			return name;
		}

		public int getId(){
			return id;
		}

		public String getEmail(){
			return email;
		}

		public Object getFcmId(){
			return fcmId;
		}

		public String getRegistrationNo(){
			return registrationNo;
		}

		public String getOrganizationName(){
			return organizationName;
		}

		public String getWebsiteLink(){
			return website;
		}
	}
}