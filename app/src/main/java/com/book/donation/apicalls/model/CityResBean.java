package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResBean {

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

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private String id;

		@SerializedName("state_id")
		private int stateId;

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}

		public int getStateId(){
			return stateId;
		}
	}
}