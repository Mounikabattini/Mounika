package com.book.donation.apicalls.Presenter;

import android.app.Activity;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IDonatNowView;
import com.book.donation.apicalls.model.DonatNowResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.utils.GoogleProgressDialog;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonoteNowPresenter extends BasePresenter<IDonatNowView> {

    GoogleProgressDialog mProgressDialog;
    DonorFormResBean donorFormResBean;

    public void DonateNowCall(final Activity context, DonorFormResBean donorFormResBean, String userId) {


        this.donorFormResBean = donorFormResBean;
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        /*MultipartBody.Part user_image = null;
        String fileName = new File(donorFormResBean.getImage().getPath()).getName();
        File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(donorFormResBean.getImage().getPath()).getName());
        if (actualFile != null) {
            user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));
        }else {
            user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        }*/

        RequestBody reqName = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getDonorName());
        RequestBody reqUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody reqMobileNo = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getMobile());
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getEmail());
        RequestBody reqStateId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getStateId());
        RequestBody reqCityId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getCityId());
        RequestBody reqAddress = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getAddress());
        RequestBody reqPincode = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getPincode());
        RequestBody reqItemName = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getItem_name());
        RequestBody reqItemDonote = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getItem_quantity());
        RequestBody reqCategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getCategory_id());
        RequestBody reqSubcategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getSub_category_id());
        RequestBody reqItemCondition = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getCondition());
        RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getDescription());
        RequestBody reqbookIsbn = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookIsbn());
        RequestBody reqAuthor = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getAuthor());
        RequestBody reqBookCategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookCategoryId());
        RequestBody reqBookCategory = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookCategory());
        RequestBody reqGradeLevel = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getGrade_level());
        RequestBody reqUniversity = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getUniversity());


        UTopperApp.getInstance().getApiService().addDonateItem(reqName, reqUserId, reqMobileNo, reqEmail, reqStateId, reqCityId, reqAddress, reqPincode, reqItemName, reqItemDonote, reqCategoryId,
                reqSubcategoryId, reqItemCondition, reqDescription, reqbookIsbn, reqAuthor, reqBookCategoryId, reqBookCategory, reqGradeLevel, reqUniversity, donorFormResBean.getImage())
                .enqueue(new Callback<DonatNowResBean>() {
                    @Override
                    public void onResponse(Call<DonatNowResBean> call, Response<DonatNowResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onDonatNowSuccess(response.body());

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
                    public void onFailure(Call<DonatNowResBean> call, Throwable t) {
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
}

