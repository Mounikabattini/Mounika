package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IHappySectionView;
import com.book.donation.apicalls.model.HappySectionResBean;
import com.book.donation.utils.GoogleProgressDialog;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HappySectionPresenter extends BasePresenter<IHappySectionView> {

    GoogleProgressDialog mProgressDialog;

    public void addHappyDataCall(final Context context, String userId, String title, String experience, MultipartBody.Part captureMediaFile) {
        getView().enableLoadingBar(context, true);


        /*MultipartBody.Part user_image = null;
        if (captureMediaFile!=null) {
            File file = new File(captureMediaFile.getPath());
            String fileName = file.getName();
            File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            if (file != null) {
                user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("image/*"), actualFile));
            } else {
                Toast.makeText(context, "please take a image", Toast.LENGTH_LONG).show();
                //user_image = MultipartBody.Part.createFormData("profile", "", RequestBody.create(MediaType.parse("image/*"), ""));
            }

        }*/

        RequestBody reqUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody reqTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody reqExperience = RequestBody.create(MediaType.parse("multipart/form-data"), experience);

        UTopperApp.getInstance().getApiService().addExperience(reqUserId, reqTitle, reqExperience, captureMediaFile)
                .enqueue(new Callback<HappySectionResBean>() {
                    @Override
                    public void onResponse(Call<HappySectionResBean> call, Response<HappySectionResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onAddHappySectionSucess(response.body());

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
                    public void onFailure(Call<HappySectionResBean> call, Throwable t) {
                        getView().enableLoadingBar(context, false);
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }
}

