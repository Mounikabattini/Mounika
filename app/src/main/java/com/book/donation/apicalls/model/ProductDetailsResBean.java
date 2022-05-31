package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsResBean {

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("donordata")
	private Donordata donordata;

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

	public Donordata getDonordata(){
		return donordata;
	}

	public String getMessage(){
		return message;
	}

	public class Data{

		@SerializedName("date")
		private String date;

		@SerializedName("image")
		private String image;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("is_claim_requested")
		private String isClaimRequested;

		@SerializedName("university")
		private String university;

		@SerializedName("author")
		private String author;

		@SerializedName("book_category")
		private String bookCategory;

		@SerializedName("isbn")
		private String isbn;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("description")
		private String description;

		@SerializedName("owner_mobile")
		private String ownerMobile;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("is_wishlist")
		private String isWishlist;

		@SerializedName("is_liked")
		private String isLiked;

		@SerializedName("total_likes")
		private String totalLikes;

		@SerializedName("is_claimed")
		private String isClaimed;

		@SerializedName("total_claim")
		private int totalClaim;

		@SerializedName("is_book_donated")
		private String isBookDonated;

		@SerializedName("book_title")
		private String bookTitle;

		@SerializedName("grade")
		private String grade;

		@SerializedName("item_condition")
		private String itemCondition;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("location")
		private String location;

		@SerializedName("address")
		private String address;

		@SerializedName("id")
		private int id;

		@SerializedName("book_type_name")
		private String bookTypeName;

		public String getDate(){
			return date;
		}

		public String getImage(){
			return image;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public String getIsClaimRequested(){
			return isClaimRequested;
		}

		public String getUniversity(){
			return university;
		}

		public String getAuthor(){
			return author;
		}

		public String getBookCategory(){
			return bookCategory;
		}

		public String getIsbn(){
			return isbn;
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

		public String getIsWishlist(){
			return isWishlist;
		}

		public String getIsLiked(){
			return isLiked;
		}

		public String getTotalLikes(){
			return totalLikes;
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

		public String getBookTitle(){
			return bookTitle;
		}

		public String getGrade(){
			return grade;
		}

		public String getItemCondition(){
			return itemCondition;
		}

		public String getSubCategoryName(){
			return subCategoryName;
		}

		public String getLocation(){
			return location;
		}

		public String getAddress(){
			return address;
		}

		public int getId(){
			return id;
		}

		public String getBookTypeName(){
			return bookTypeName;
		}
	}

	public class Donordata{

		@SerializedName("redemption_value")
		private int redemptionValue;

		@SerializedName("pincode")
		private String pincode;

		@SerializedName("device_key")
		private String deviceKey;

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

		@SerializedName("otp")
		private String otp;

		@SerializedName("wallet_id")
		private String walletId;

		@SerializedName("user_type")
		private String userType;

		@SerializedName("help_india_id")
		private String helpIndiaId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("askhelp")
		private String askhelp;

		@SerializedName("business_organization")
		private String businessOrganization;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("mode_of_donate")
		private String modeOfDonate;

		@SerializedName("email")
		private String email;

		@SerializedName("fcm_id")
		private String fcmId;

		public int getRedemptionValue(){
			return redemptionValue;
		}

		public String getPincode(){
			return pincode;
		}

		public String getDeviceKey(){
			return deviceKey;
		}

		public String getAddress(){
			return address;
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

		public String getOtp(){
			return otp;
		}

		public String getWalletId(){
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

		public String getAskhelp(){
			return askhelp;
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

		public String getModeOfDonate(){
			return modeOfDonate;
		}

		public String getEmail(){
			return email;
		}

		public String getFcmId(){
			return fcmId;
		}
	}
}