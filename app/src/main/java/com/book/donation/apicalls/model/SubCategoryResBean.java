package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategoryResBean {

	@SerializedName("success")
	private boolean success;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("category")
	private List<CategoryItem> category;

	@SerializedName("banner")
	private List<BannerItem> banner;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getBaseUrl(){
		return baseUrl;
	}

	public List<CategoryItem> getCategory(){
		return category;
	}

	public List<BannerItem> getBanner(){
		return banner;
	}

	public String getMessage(){
		return message;
	}

	public class CategoryItem{

		@SerializedName("product")
		private List<ProductItem> product;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		public List<ProductItem> getProduct(){
			return product;
		}

		public int getId(){
			return id;
		}

		public String getTitle(){
			return title;
		}
	}

	public class ProductItem{

		@SerializedName("date")
		private String date;

		@SerializedName("image")
		private String image;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("total_qty")
		private Object totalQty;

		@SerializedName("ip")
		private Object ip;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("mrp_price")
		private Object mrpPrice;

		@SerializedName("product_name")
		private String productName;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("category_id")
		private int categoryId;

		@SerializedName("updated_at")
		private Object updatedAt;

		@SerializedName("sub_category_id")
		private int subCategoryId;

		@SerializedName("price")
		private Object price;

		@SerializedName("sub_category_name")
		private String subCategoryName;

		@SerializedName("location")
		private String location;

		@SerializedName("id")
		private int id;

		@SerializedName("short_desc")
		private String shortDesc;

		@SerializedName("pro_date")
		private Object proDate;

		@SerializedName("slug")
		private String slug;

		@SerializedName("latest")
		private int latest;

		@SerializedName("status")
		private Object status;

		@SerializedName("address")
		private String address;

		public String getDate(){
			return date;
		}

		public String getImage(){
			return image;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public Object getTotalQty(){
			return totalQty;
		}

		public Object getIp(){
			return ip;
		}

		public String getDescription(){
			return description;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public Object getMrpPrice(){
			return mrpPrice;
		}

		public String getProductName(){
			return productName;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public int getCategoryId(){
			return categoryId;
		}

		public Object getUpdatedAt(){
			return updatedAt;
		}

		public int getSubCategoryId(){
			return subCategoryId;
		}

		public Object getPrice(){
			return price;
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

		public String getShortDesc(){
			return shortDesc;
		}

		public Object getProDate(){
			return proDate;
		}

		public String getSlug(){
			return slug;
		}

		public int getLatest(){
			return latest;
		}

		public Object getStatus(){
			return status;
		}

		public String getAddress(){
			return address;
		}
	}

	public class BannerItem {
		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("link")
		private String link;

		@SerializedName("banner")
		private String banner;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("id")
		private int id;

		@SerializedName("title")
		private String title;

		@SerializedName("status")
		private int status;

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getLink(){
			return link;
		}

		public String getBanner(){
			return banner;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public int getId(){
			return id;
		}

		public String getTitle(){
			return title;
		}

		public int getStatus(){
			return status;
		}
	}
}