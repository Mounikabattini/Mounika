package com.book.donation.apicalls.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestDetailsResBean {

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

	public class DataItem{

		@SerializedName("parent_name")
		private String parentName;

		@SerializedName("parent_email")
		private String parentEmail;

		@SerializedName("account_number")
		private String accountNumber;

		@SerializedName("reason")
		private String reason;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("isbn")
		private String isbn;

		@SerializedName("mobile_no")
		private String mobileNo;

		@SerializedName("image")
		private String image;

		@SerializedName("description")
		private String description;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("fee_type")
		private String feeType;

		@SerializedName("is_wishlist")
		private String isWishlist;

		@SerializedName("is_claimed")
		private String isClaimed;

		@SerializedName("institute_address")
		private String instituteAddress;

		@SerializedName("category_id")
		private int categoryId;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("updated_at")
		private String updatedAt;

		@SerializedName("book_title")
		private String bookTitle;

		@SerializedName("dd_cheque_no")
		private String ddChequeNo;

		@SerializedName("sub_category_id")
		private int subCategoryId;

		@SerializedName("delete_status")
		private String deleteStatus;

		@SerializedName("term_details")
		private String termDetails;

		@SerializedName("account_name")
		private String accountName;

		@SerializedName("bank_name")
		private String bankName;

		@SerializedName("id")
		private int id;

		@SerializedName("student_email")
		private String studentEmail;

		@SerializedName("ifsc")
		private String ifsc;

		@SerializedName("first_name")
		private String firstName;

		@SerializedName("email")
		private String email;

		@SerializedName("is_liked")
		private String isLiked;

		@SerializedName("student_level")
		private String studentLevel;

		@SerializedName("amount")
		private String amount;

		@SerializedName("address")
		private String address;

		@SerializedName("is_claim_requested")
		private String isClaimRequested;

		@SerializedName("author")
		private String author;

		@SerializedName("book_category")
		private String bookCategory;

		@SerializedName("last_name")
		private String lastName;

		@SerializedName("student_id")
		private String studentId;

		@SerializedName("total_likes")
		private int totalLikes;

		@SerializedName("created_by")
		private String createdBy;

		@SerializedName("institute_name")
		private String instituteName;

		@SerializedName("student_phone")
		private String studentPhone;

		@SerializedName("parent_phone")
		private String parentPhone;

		@SerializedName("student_course")
		private String studentCourse;

		@SerializedName("status")
		private String status;

		public String getParentName(){
			return parentName;
		}

		public String getParentEmail(){
			return parentEmail;
		}

		public String getAccountNumber(){
			return accountNumber;
		}

		public String getReason(){
			return reason;
		}

		public String getUserName(){
			return userName;
		}

		public String getIsbn(){
			return isbn;
		}

		public String getMobileNo(){
			return mobileNo;
		}

		public String getImage(){
			return image;
		}

		public String getDescription(){
			return description;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public String getFeeType(){
			return feeType;
		}

		public String getIsWishlist(){
			return isWishlist;
		}

		public String getIsClaimed(){
			return isClaimed;
		}

		public String getInstituteAddress(){
			return instituteAddress;
		}

		public int getCategoryId(){
			return categoryId;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}

		public String getBookTitle(){
			return bookTitle;
		}

		public String getDdChequeNo(){
			return ddChequeNo;
		}

		public int getSubCategoryId(){
			return subCategoryId;
		}

		public String getDeleteStatus(){
			return deleteStatus;
		}

		public String getTermDetails(){
			return termDetails;
		}

		public String getAccountName(){
			return accountName;
		}

		public String getBankName(){
			return bankName;
		}

		public int getId(){
			return id;
		}

		public String getStudentEmail(){
			return studentEmail;
		}

		public String getIfsc(){
			return ifsc;
		}

		public String getFirstName(){
			return firstName;
		}

		public String getEmail(){
			return email;
		}

		public String getIsLiked(){
			return isLiked;
		}

		public String getStudentLevel(){
			return studentLevel;
		}

		public String getAmount(){
			return amount;
		}

		public String getAddress(){
			return address;
		}

		public String getIsClaimRequested(){
			return isClaimRequested;
		}

		public String getAuthor(){
			return author;
		}

		public String getBookCategory(){
			return bookCategory;
		}

		public String getLastName(){
			return lastName;
		}

		public String getStudentId(){
			return studentId;
		}

		public int getTotalLikes(){
			return totalLikes;
		}

		public String getCreatedBy(){
			return createdBy;
		}

		public String getInstituteName(){
			return instituteName;
		}

		public String getStudentPhone(){
			return studentPhone;
		}

		public String getParentPhone(){
			return parentPhone;
		}

		public String getStudentCourse(){
			return studentCourse;
		}

		public String getStatus(){
			return status;
		}
	}
}