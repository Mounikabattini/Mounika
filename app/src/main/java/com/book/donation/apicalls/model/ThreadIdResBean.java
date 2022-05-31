package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

public class ThreadIdResBean {

	@SerializedName("userImage")
	private String userImage;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("userName")
	private String userName;

	@SerializedName("status")
	private boolean status;

	public String getUserImage(){
		return userImage;
	}

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getUserName(){
		return userName;
	}

	public boolean isStatus(){
		return status;
	}

	public class Data{

		@SerializedName("thread_id")
		private String threadId;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("reciever_id")
		private String recieverId;

		@SerializedName("id")
		private int id;

		@SerializedName("type")
		private String type;

		@SerializedName("sender_id")
		private String senderId;

		public String getThreadId(){
			return threadId;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getRecieverId(){
			return recieverId;
		}

		public int getId(){
			return id;
		}

		public String getType(){
			return type;
		}

		public String getSenderId(){
			return senderId;
		}
	}
}