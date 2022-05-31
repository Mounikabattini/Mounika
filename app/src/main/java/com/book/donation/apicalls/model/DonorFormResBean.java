package com.book.donation.apicalls.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import okhttp3.MultipartBody;

public class DonorFormResBean implements Serializable {

    public void setDonorDetails(String donor_name, String mobile, String email, String stateId, String cityId, String address, String pincode){
        this.donor_name = donor_name;
        this.mobile = mobile;
        this.email = email;
        this.stateId = stateId;
        this.cityId = cityId;
        this.address = address;
        this.pincode = pincode;
    }

    public void setDetails(MultipartBody.Part image, Uri image_uri, String item_name, String item_quantity, String category_id, String category_name, String sub_category_id, String sub_category_name
            , String condition, String description, String book_isbn, String author, String book_category_id, String book_category, String grade_level, String university){
        this.image = image;
        this.image_uri = image_uri;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.category_id = category_id;
        this.category_name = category_name;
        this.sub_category_id = sub_category_id;
        this.sub_category_name = sub_category_name;
        this.condition = condition;
        this.description = description;
        this.book_isbn = book_isbn;
        this.author = author;
        this.book_category_id = book_category_id;
        this.book_category = book_category;
        this.grade_level = grade_level;
        this.university = university;
    }

    @SerializedName("donor_name")
    private String donor_name;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("email")
    private String email;

    @SerializedName("state_id")
    private String stateId;

    @SerializedName("city_id")
    private String cityId;

    @SerializedName("address")
    private String address;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("image")
    private MultipartBody.Part image;

    @SerializedName("image_uri")
    private Uri image_uri;

    @SerializedName("item_quantity")
    private String item_quantity;

    @SerializedName("category_id")
    private String category_id;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("sub_category_id")
    private String sub_category_id;

    @SerializedName("sub_category_name")
    private String sub_category_name;

    @SerializedName("condition")
    private String condition;

    @SerializedName("description")
    private String description;

    @SerializedName("item_name")
    private String item_name;

    @SerializedName("book_isbn")
    private String book_isbn;

    @SerializedName("author")
    private String author;

    @SerializedName("book_category")
    private String book_category;

    @SerializedName("book_category_id")
    private String book_category_id;

    @SerializedName("grade_level")
    private String grade_level;

    @SerializedName("university")
    private String university;

    public String getDonorName(){
        return donor_name;
    }

    public String getMobile(){
        return mobile;
    }

    public String getEmail(){
        return email;
    }

    public String getStateId(){
        return stateId;
    }

    public String getCityId(){
        return cityId;
    }

    public String getAddress(){
        return address;
    }

    public String getPincode(){
        return pincode;
    }

    public MultipartBody.Part getImage(){
        return image;
    }

    public Uri getImage_uri(){
        return image_uri;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_quantity(){
        return item_quantity;
    }

    public String getCategory_id(){
        return category_id;
    }

    public String getSub_category_id(){
        return sub_category_id;
    }

    public String getCategory_name(){
        return category_name;
    }

    public String getSub_category_name(){
        return sub_category_name;
    }

    public String getCondition(){
        return condition;
    }

    public String getDescription(){
        return description;
    }

    public String getBookIsbn() {
        return book_isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookCategory() {
        return book_category;
    }

    public String getBookCategoryId() {
        return book_category_id;
    }

    public String getGrade_level() {
        return grade_level;
    }

    public String getUniversity() {
        return university;
    }

   /* public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPincode(String pincode){
        this.pincode = pincode;
    }

    public void setImage(Uri image){
        this.image = image;
    }

    public void setItem_quantity(String item_quantity){
        this.item_quantity = item_quantity;
    }

    public void setCategory_id(String category_id){
        this.category_id = category_id;
    }

    public void setSub_category_id(String sub_category_id){
        this.sub_category_id = sub_category_id;
    }

    public void setCategory_name(String category_name){
        this.category_name = category_name;
    }

    public void setSub_category_name(String sub_category_name){
        this.sub_category_name = sub_category_name;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public void setDescription(String description){
        this.description = description;
    }*/
}
