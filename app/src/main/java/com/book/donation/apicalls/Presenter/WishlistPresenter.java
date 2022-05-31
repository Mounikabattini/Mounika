package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IWishlistView;
import com.book.donation.apicalls.model.ResponseResBean;
import com.book.donation.apicalls.model.WishlistResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistPresenter extends BasePresenter<IWishlistView> {

    GoogleProgressDialog mProgressDialog;

    public void WishlistCall(final Context context, String userId, String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getWishlist(userId, type)
                .enqueue(new Callback<WishlistResBean>() {
                    @Override
                    public void onResponse(Call<WishlistResBean> call, Response<WishlistResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onWishlistSucess(response.body());

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
                    public void onFailure(Call<WishlistResBean> call, Throwable t) {
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

    public void TestCall(final Context context, String token) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getTestService().getTest(token)
                .enqueue(new Callback<ResponseResBean>() {
                    @Override
                    public void onResponse(Call<ResponseResBean> call, Response<ResponseResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onTestSucess(response.body());

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
                    public void onFailure(Call<ResponseResBean> call, Throwable t) {
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

