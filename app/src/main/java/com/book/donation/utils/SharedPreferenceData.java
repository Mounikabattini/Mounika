package com.book.donation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.book.donation.apicalls.model.UpdateProfileResBean;
import com.book.donation.apicalls.model.VerifyOtpResBean;

public class SharedPreferenceData {
    SharedPreferences sharedPreferences, sharedPreferences1;
    Context context;
    private String MyLoginPREFERENCES = "MyLoginPrefs" ;
    private String LOCATION_SET = "LocationSet" ;
    private String DELIVERY_ADDRESS_PREFERENCES = "DeliveryAddressPrefs" ;
    private String USER_ID = "user_id" ;
    private String NAME = "name" ;
    private String FATHER_NAME = "father_name" ;
    private String MOBILE_NO = "mobile_no" ;
    private String EMAIL = "email" ;
    private String ACCESS_TOKEN = "access_token";
    private String PROFILE_IMAGE = "profile_image" ;
    private String SELECTED_DEPART_ID = "selected_depart";
    private String DEFAULT_ADDRESS = "default_address";
    private String ADDRESS_ID = "address_id";
    private String PINCODE = "pincode";
    private String SECTION_TYPE = "section_type";
    private String IS_FIRST_TIME = "is_first_time";
    private String REGISTRATION_NO = "registration_no";
    private String ORGANIZATION_NAME = "organization_name";
    private String WEBSITE = "website";
    private String STATE_ID = "state_id";
    private String CITY_ID = "city_id";
    private String CITY_NAME = "city_name";

    public SharedPreferenceData(Context context){
        this.context = context;
    }

    public void SavedLoginData(VerifyOtpResBean verifyOtpResBean){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        VerifyOtpResBean.User data = verifyOtpResBean.getUser();
        editor.putString(USER_ID, ""+data.getId());
        editor.putString(NAME, data.getName());
        editor.putString(EMAIL, data.getEmail());
        editor.putString(MOBILE_NO, data.getMobile());
        editor.putString(ACCESS_TOKEN, verifyOtpResBean.getAccessToken());
        editor.putString(PROFILE_IMAGE, data.getProfilePic());
        editor.putString(DEFAULT_ADDRESS, data.getAddress());
        editor.putString(PINCODE, data.getPincode());
        editor.putString(REGISTRATION_NO, data.getRegistrationNo());
        editor.putString(ORGANIZATION_NAME, data.getOrganizationName());
        editor.putString(WEBSITE, data.getWebsiteLink());
        editor.putString(STATE_ID, data.getState());
        editor.putString(CITY_ID, data.getCity());
        editor.commit();
    }

    public void SavedUpdateData(UpdateProfileResBean updateProfileResBean, String address, String pincode){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UpdateProfileResBean.Data data = updateProfileResBean.getData();
        editor.putString(USER_ID, ""+data.getUserId());
        editor.putString(NAME, data.getName());
        editor.putString(EMAIL, data.getEmail());
        editor.putString(MOBILE_NO, data.getMobile());
        editor.putString(ACCESS_TOKEN, data.getAccessToken());
        editor.putString(PROFILE_IMAGE, data.getProfilePic());
        editor.putString(DEFAULT_ADDRESS, address);
        editor.putString(PINCODE, pincode);
        editor.commit();
    }

    public void savePincode(String pincode){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PINCODE, pincode);
        editor.commit();
    }

    public void saveLocation(String stateId, String cityId, String cityName){
        sharedPreferences = context.getSharedPreferences(LOCATION_SET, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATE_ID, stateId);
        editor.putString(CITY_ID, cityId);
        editor.putString(CITY_NAME, cityName);
        editor.commit();
    }

    public void saveSectionType(String sectionType){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SECTION_TYPE, sectionType);
        editor.commit();
    }

    public void isAppInstalledFirstTime(boolean isFirstTime){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public void Logout(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        //sharedPreferences1 = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor.clear();
        //editor1.clear();
        editor.commit();
        //editor1.commit();
    }

    public boolean IsLogin(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.contains(USER_ID);
    }

    public String getCityName(){
        sharedPreferences = context.getSharedPreferences(LOCATION_SET, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CITY_NAME, "");
    }

    public String getUser_id(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, "");
    }

    public String getState_id(){
        sharedPreferences = context.getSharedPreferences(LOCATION_SET, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STATE_ID, "");
    }

    public String getCity_id(){
        sharedPreferences = context.getSharedPreferences(LOCATION_SET, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CITY_ID, "");
    }

    public String getUser_name(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME, "");
    }

    public String getMobile_no(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE_NO, "");
    }

    public String getProfile_image(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PROFILE_IMAGE, "");
    }

    public String getEmail(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, "");
    }

    public String getACCESS_TOKEN(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    public String getDeliveryAddress(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DEFAULT_ADDRESS, "");
    }

    public String getPINCODE(){
        sharedPreferences = context.getSharedPreferences(MyLoginPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PINCODE, "");
    }

    public String getCurrentPINCODE(){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PINCODE, "");
    }

    public String getSectionType(){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SECTION_TYPE, "");
    }

    public Boolean getIsAppInstalledFirstTime(){
        sharedPreferences = context.getSharedPreferences(DELIVERY_ADDRESS_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }
}
