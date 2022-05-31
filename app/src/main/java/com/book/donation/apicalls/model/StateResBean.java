package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResBean {

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

		@SerializedName("country_id")
		private int countryId;

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}

		public int getCountryId(){
			return countryId;
		}
	}
}