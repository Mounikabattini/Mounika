package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class SubmitLikeThumbResBean {

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
        private String userId;

        @SerializedName("owner_id")
        private String ownerId;

        @SerializedName("product_id")
        private String productId;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("status")
        private int status;

        public String getUpdatedAt(){
            return updatedAt;
        }

        public String getUserId(){
            return userId;
        }

        public String getOwnerId(){
            return ownerId;
        }

        public String getProductId(){
            return productId;
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

        public int getStatus(){
            return status;
        }
    }
}