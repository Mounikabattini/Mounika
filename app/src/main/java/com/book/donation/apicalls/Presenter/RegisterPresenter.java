package com.book.donation.apicalls.Presenter;

import android.app.Activity;
import android.widget.Toast;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.RegisterResBean;
import com.book.donation.apicalls.View.IRegisterView;
import com.book.donation.apicalls.model.StateResBean;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter extends BasePresenter<IRegisterView> {

    public void userRegister(final Activity context, String userName, String gender, String mobile, String email,
                             String business, String registrationNumber, String organizationName, String websiteLink, String stateId, String cityId, String address, String pincode, MultipartBody.Part captureMediaFileProfile, String device_id) {
        getView().enableLoadingBar(context, true);


        /*MultipartBody.Part user_image = null;
        Uri.parse(captureMediaFileProfile.getPath());
        File file = new File(captureMediaFileProfile.getPath());
        String fileName = file.getName();
        File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
        String path = file.getAbsolutePath();
        if (file != null) {
            user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));
        }else {
            user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        }*/


        RequestBody reqUserName = RequestBody.create(MediaType.parse("multipart/form-data"), userName);
        RequestBody reqGender = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reqBusiness = RequestBody.create(MediaType.parse("multipart/form-data"), business);
        RequestBody reqRegistrationNumber = RequestBody.create(MediaType.parse("multipart/form-data"), registrationNumber);
        RequestBody reqOrganizationName = RequestBody.create(MediaType.parse("multipart/form-data"), organizationName);
        RequestBody reqWebsiteLink = RequestBody.create(MediaType.parse("multipart/form-data"), websiteLink);
        RequestBody reqStateId = RequestBody.create(MediaType.parse("multipart/form-data"), stateId);
        RequestBody reqCityId = RequestBody.create(MediaType.parse("multipart/form-data"), cityId);
        RequestBody reqAddress = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody reqPincode = RequestBody.create(MediaType.parse("multipart/form-data"), pincode);
        RequestBody reqDeviceId = RequestBody.create(MediaType.parse("multipart/form-data"), device_id);

        UTopperApp.getInstance().getApiService().registration(reqUserName, reqGender, reqMobile, reqEmail, reqBusiness, reqRegistrationNumber, reqOrganizationName
                , reqWebsiteLink, reqStateId, reqCityId, reqAddress, reqPincode, captureMediaFileProfile, reqDeviceId)
                .enqueue(new Callback<RegisterResBean>() {
                    @Override
                    public void onResponse(Call<RegisterResBean> call, Response<RegisterResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onRegisterSucess(response.body());

                                }
                            } else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResBean> call, Throwable t) {
                        getView().enableLoadingBar(context, false);
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

    public void StateCall(final Activity context){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getStates()
                .enqueue(new Callback<StateResBean>() {
                    @Override
                    public void onResponse(Call<StateResBean> call, Response<StateResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onStateSucess(response.body());

                                }else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<StateResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void CityCall(final Activity context, String stateId){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getCities(stateId)
                .enqueue(new Callback<CityResBean>() {
                    @Override
                    public void onResponse(Call<CityResBean> call, Response<CityResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCitySucess(response.body());

                                }else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<CityResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
