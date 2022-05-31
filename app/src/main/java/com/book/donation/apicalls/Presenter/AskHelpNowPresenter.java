package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IAskHelpNowView;
import com.book.donation.apicalls.model.AskHelpNowResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.apicalls.model.HelpRequestFeeResBean;
import com.book.donation.utils.GoogleProgressDialog;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskHelpNowPresenter extends BasePresenter<IAskHelpNowView> {

    GoogleProgressDialog mProgressDialog;

    public void AskHelpCall(final Context context, DonorFormResBean donorFormResBean, HelpRequestFeeResBean helpRequestFeeResBean, String userId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        RequestBody reqName = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getDonorName());
        RequestBody reqUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody reqMobileNo = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getMobile());
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getEmail());
        RequestBody reqStateId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getStateId());
        RequestBody reqCityId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getCityId());
        RequestBody reqAddress = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getAddress());
        RequestBody reqPincode = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getPincode());
        RequestBody reqCategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getCategory_id());
        RequestBody reqSubcategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getSub_category_id());
        RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getDescription());
        RequestBody reqItemName = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getItem_name());
        RequestBody reqbookIsbn = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookIsbn());
        RequestBody reqAuthor = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getAuthor());
        RequestBody reqBookCategoryId = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookCategoryId());
        RequestBody reqBookCategory = RequestBody.create(MediaType.parse("multipart/form-data"), donorFormResBean.getBookCategory());
        RequestBody reqFirstName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getFirstName());
        RequestBody reqLastName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getLastName());
        RequestBody reqStudentLevel = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getStudentLevel());
        RequestBody reqStudentId = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getStudentId());
        RequestBody reqStudentPhone = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getStudentPhone());
        RequestBody reqStudentEmail = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getStudentEmail());
        RequestBody reqStudentCourse = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getStudentCourse());
        RequestBody reqInstituteName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getIntituteName());
        RequestBody reqInstituteAddress = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getIntituteAddress());
        RequestBody reqTermDetails = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getTermDetails());
        RequestBody reqParentName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getParentName());
        RequestBody reqParentPhone = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getParentPhone());
        RequestBody reqParentEmail = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getParentEmail());
        RequestBody reqFeeType = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getFeeType());
        RequestBody reqAmount = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getAmount());
        RequestBody reqAccountName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getAccountName());
        RequestBody reqAccountNo = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getAccountNumber());
        RequestBody reqBankName = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getBankName());
        RequestBody reqIfsc = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getIfsc());
        RequestBody reqDDCheque = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getDd_cheque_no());
        RequestBody reqReason = RequestBody.create(MediaType.parse("multipart/form-data"), helpRequestFeeResBean.getReason());

        UTopperApp.getInstance().getApiService().addAskHelpWithImage(reqUserId, reqName, reqMobileNo, reqEmail,reqStateId, reqCityId,
                reqAddress, reqPincode, reqCategoryId, reqSubcategoryId, reqDescription,
                reqItemName, reqbookIsbn, reqAuthor, reqBookCategoryId, reqBookCategory,
                reqFirstName, reqLastName, reqStudentLevel, reqStudentId,
                reqStudentPhone, reqStudentEmail, reqStudentCourse, reqInstituteName,
                reqInstituteAddress, reqTermDetails, reqParentName, reqParentPhone,
                reqParentEmail, reqFeeType, reqAmount, reqAccountName,
                reqAccountNo, reqBankName, reqIfsc, reqDDCheque, reqReason, donorFormResBean.getImage())
                .enqueue(new Callback<AskHelpNowResBean>() {
                    @Override
                    public void onResponse(Call<AskHelpNowResBean> call, Response<AskHelpNowResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onAskHelpNowSuccess(response.body());

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
                    public void onFailure(Call<AskHelpNowResBean> call, Throwable t) {
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
