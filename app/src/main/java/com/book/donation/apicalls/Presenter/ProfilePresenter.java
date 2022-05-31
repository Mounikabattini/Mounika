package com.book.donation.apicalls.Presenter;

import android.app.Activity;
import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IProfileView;
import com.book.donation.apicalls.model.ProfileResBean;
import com.book.donation.apicalls.model.UpdateProfileResBean;
import com.book.donation.utils.GoogleProgressDialog;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<IProfileView> {

    GoogleProgressDialog mProgressDialog;

    public void ProfileInfoCall(final Context context, String userId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getProfileInfo(userId)
                .enqueue(new Callback<ProfileResBean>() {
                    @Override
                    public void onResponse(Call<ProfileResBean> call, Response<ProfileResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onProfileSucess(response.body());

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
                    public void onFailure(Call<ProfileResBean> call, Throwable t) {
                        try {
                            //getView().enableLoadingBar(context, false);
                            mProgressDialog.dismiss();
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void     UpdateProfileInfoCall(final Activity context, String userId, String userName, String mobile, String email,
                              String address, String pincode, MultipartBody.Part captureMediaFileProfile) {
        getView().enableLoadingBar(context, true);


        /*MultipartBody.Part user_image = null;
        if (captureMediaFileProfile!=null) {
            File file = new File(captureMediaFileProfile.getPath());
            String fileName = file.getName();
            File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            if (file != null) {
                user_image = MultipartBody.Part.createFormData("profile", fileName, RequestBody.create(MediaType.parse("image/*"), actualFile));
            } else {
                user_image = MultipartBody.Part.createFormData("profile", "", RequestBody.create(MediaType.parse("image/*"), ""));
            }

        }*/

        RequestBody reqUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody reqUserName = RequestBody.create(MediaType.parse("multipart/form-data"), userName);
        RequestBody reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reqAddress = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody reqPincode = RequestBody.create(MediaType.parse("multipart/form-data"), pincode);

        UTopperApp.getInstance().getApiService().updateProfileInfo(reqUserId, reqUserName, reqMobile, reqEmail
                , reqAddress, reqPincode, captureMediaFileProfile)
                .enqueue(new Callback<UpdateProfileResBean>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResBean> call, Response<UpdateProfileResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onUpdateProfileSuccess(response.body());

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
                    public void onFailure(Call<UpdateProfileResBean> call, Throwable t) {
                        getView().enableLoadingBar(context, false);
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }
}

