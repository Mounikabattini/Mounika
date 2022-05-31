package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.apicalls.View.ICategoryView;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter extends BasePresenter<ICategoryView> {

    GoogleProgressDialog mProgressDialog;

    public void CategoryCall(final Context context, String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getCategories(type)
                .enqueue(new Callback<CategoryResBean>() {
                    @Override
                    public void onResponse(Call<CategoryResBean> call, Response<CategoryResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCategorySucess(response.body());

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
                    public void onFailure(Call<CategoryResBean> call, Throwable t) {
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

    public void EventCall(final Context context) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getEvents()
                .enqueue(new Callback<EventResBean>() {
                    @Override
                    public void onResponse(Call<EventResBean> call, Response<EventResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onEventSucess(response.body());

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
                    public void onFailure(Call<EventResBean> call, Throwable t) {
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

    public void RecentItemsCall(final Context context, String type, String stateId, String cityId, String pincode) {
        //getView().enableLoadingBar(context, true);
        //mProgressDialog = new GoogleProgressDialog(context);
        //mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getRecentItem(type, stateId, cityId, pincode)
                .enqueue(new Callback<RecentItemsResBean>() {
                    @Override
                    public void onResponse(Call<RecentItemsResBean> call, Response<RecentItemsResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        //mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onRecentItemsSucess(response.body());

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
                    public void onFailure(Call<RecentItemsResBean> call, Throwable t) {
                        try {
                            //getView().enableLoadingBar(context, false);
                            //mProgressDialog.dismiss();
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
