package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsReBeanOld {

    @SerializedName("data")
    private ProductDetailsResBean.Data data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("base_url")
    private String baseUrl;

    @SerializedName("donordata")
    private ProductDetailsResBean.Donordata donordata;

    @SerializedName("message")
    private String message;

    public ProductDetailsResBean.Data getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getBaseUrl(){
        return baseUrl;
    }

    public ProductDetailsResBean.Donordata getDonordata(){
        return donordata;
    }

    public String getMessage(){
        return message;
    }

    public class Data{

        @SerializedName("date")
        private Object date;

        @SerializedName("image")
        private String image;

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

        @SerializedName("is_wishlist")
        private String isWishlist;

        @SerializedName("is_claimed")
        private String isClaimed;

        @SerializedName("total_claim")
        private int totalClaim;

        @SerializedName("item_condition")
        private String itemCondition;

        @SerializedName("sub_category_name")
        private String subCategoryName;

        @SerializedName("location")
        private String location;

        @SerializedName("id")
        private int id;

        public Object getDate(){
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

        public String getIsClaimed(){
            return isClaimed;
        }

        public int getTotalClaim(){
            return totalClaim;
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

        public int getId(){
            return id;
        }
    }

    public class Donordata{

        @SerializedName("redemption_value")
        private int redemptionValue;

        @SerializedName("pincode")
        private String pincode;

        @SerializedName("device_key")
        private Object deviceKey;

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

        @SerializedName("mode_of_donate")
        private String modeOfDonate;

        @SerializedName("email")
        private String email;

        @SerializedName("fcm_id")
        private Object fcmId;

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

        public String getModeOfDonate(){
            return modeOfDonate;
        }

        public String getEmail(){
            return email;
        }

        public Object getFcmId(){
            return fcmId;
        }
    }
}
