package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IClaimedPersonListView;
import com.book.donation.apicalls.model.ClaimAcceptResBean;
import com.book.donation.apicalls.model.ClaimedPersonListResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestClaimedPersonPresenter extends BasePresenter<IClaimedPersonListView> {

    GoogleProgressDialog mProgressDialog;

    public void ClaimedPersonListCall(final Context context, String userId, String productId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getAskClaimedPersonList(userId, productId)
                .enqueue(new Callback<ClaimedPersonListResBean>() {
                    @Override
                    public void onResponse(Call<ClaimedPersonListResBean> call, Response<ClaimedPersonListResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onClaimedPersonListSuccess(response.body());
                                }
                            } else {
                                getView().onError(response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ClaimedPersonListResBean> call, Throwable t) {
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

    public void ClaimAcceptCall(final Context context, String claimBy, String claimId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().requestClaimAccept(claimBy, claimId)
                .enqueue(new Callback<ClaimAcceptResBean>() {
                    @Override
                    public void onResponse(Call<ClaimAcceptResBean> call, Response<ClaimAcceptResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onClaimAcceptSuccess(response.body());
                                }
                            } else {
                                getView().onError(response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ClaimAcceptResBean> call, Throwable t) {
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

    public void createThreadIdCall(final Context context, String senderId, String receiverId, String itemId, String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().createThreadID(senderId, receiverId, itemId, type)
                .enqueue(new Callback<ThreadIdResBean>() {
                    @Override
                    public void onResponse(Call<ThreadIdResBean> call, Response<ThreadIdResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onThreadIdSuccess(response.body());

                                }
                            } else {
                                getView().onError(response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ThreadIdResBean> call, Throwable t) {
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
