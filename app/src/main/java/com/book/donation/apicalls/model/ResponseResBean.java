package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseResBean {

	@SerializedName("page")
	private String page;

	@SerializedName("countries")
	private List<CountriesItem> countries;

	@SerializedName("users")
	private List<UsersItem> users;

	public String getPage(){
		return page;
	}

	public List<CountriesItem> getCountries(){
		return countries;
	}

	public List<UsersItem> getUsers(){
		return users;
	}

	public class UsersItem{

		@SerializedName("address")
		private String address;

		@SerializedName("user_country")
		private String userCountry;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("latitude")
		private Object latitude;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("last_name")
		private String lastName;

		@SerializedName("email_verified_at")
		private Object emailVerifiedAt;

		@SerializedName("avatar")
		private Object avatar;

		@SerializedName("type")
		private String type;

		@SerializedName("access_token")
		private Object accessToken;

		@SerializedName("password")
		private String password;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("provider")
		private Object provider;

		@SerializedName("phone")
		private String phone;

		@SerializedName("name")
		private String name;

		@SerializedName("provider_id")
		private Object providerId;

		@SerializedName("id")
		private int id;

		@SerializedName("remember_token")
		private Object rememberToken;

		@SerializedName("email")
		private String email;

		@SerializedName("status")
		private String status;

		@SerializedName("longitude")
		private Object longitude;

		public String getAddress(){
			return address;
		}

		public String getUserCountry(){
			return userCountry;
		}

		public String getUserName(){
			return userName;
		}

		public Object getLatitude(){
			return latitude;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getLastName(){
			return lastName;
		}

		public Object getEmailVerifiedAt(){
			return emailVerifiedAt;
		}

		public Object getAvatar(){
			return avatar;
		}

		public String getType(){
			return type;
		}

		public Object getAccessToken(){
			return accessToken;
		}

		public String getPassword(){
			return password;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public Object getProvider(){
			return provider;
		}

		public String getPhone(){
			return phone;
		}

		public String getName(){
			return name;
		}

		public Object getProviderId(){
			return providerId;
		}

		public int getId(){
			return id;
		}

		public Object getRememberToken(){
			return rememberToken;
		}

		public String getEmail(){
			return email;
		}

		public String getStatus(){
			return status;
		}

		public Object getLongitude(){
			return longitude;
		}
	}

	public class CountriesItem{

		@SerializedName("numcode")
		private int numcode;

		@SerializedName("iso")
		private String iso;

		@SerializedName("name")
		private String name;

		@SerializedName("nicename")
		private String nicename;

		@SerializedName("phonecode")
		private int phonecode;

		@SerializedName("id")
		private int id;

		@SerializedName("iso3")
		private String iso3;

		public int getNumcode(){
			return numcode;
		}

		public String getIso(){
			return iso;
		}

		public String getName(){
			return name;
		}

		public String getNicename(){
			return nicename;
		}

		public int getPhonecode(){
			return phonecode;
		}

		public int getId(){
			return id;
		}

		public String getIso3(){
			return iso3;
		}
	}
}