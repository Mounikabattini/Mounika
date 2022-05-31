package com.book.donation.apicalls.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventResBean implements Serializable {

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

	public class DataItem implements Serializable{

		@SerializedName("created_on")
		private String createdOn;

		@SerializedName("event_date")
		private String eventDate;

		@SerializedName("event_name")
		private String eventName;

		@SerializedName("event_image")
		private Object eventImage;

		@SerializedName("description")
		private String description;

		@SerializedName("id")
		private int id;

		@SerializedName("event_time")
		private String eventTime;

		public String getCreatedOn(){
			return createdOn;
		}

		public String getEventDate(){
			return eventDate;
		}

		public String getEventName(){
			return eventName;
		}

		public Object getEventImage(){
			return eventImage;
		}

		public String getDescription(){
			return description;
		}

		public int getId(){
			return id;
		}

		public String getEventTime(){
			return eventTime;
		}
	}
}