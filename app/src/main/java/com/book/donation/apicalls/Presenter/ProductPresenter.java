package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IProductView;
import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ProductResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter extends BasePresenter<IProductView> {

    GoogleProgressDialog mProgressDialog;

    public void ProductsCall(final Context context, String userId, String subCategoryId, String type, String state, String city) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getProducts(userId, subCategoryId, type, state, city)
                .enqueue(new Callback<ProductResBean>() {
                    @Override
                    public void onResponse(Call<ProductResBean> call, Response<ProductResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onProductSucess(response.body());

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
                    public void onFailure(Call<ProductResBean> call, Throwable t) {
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

    public void AddToWishlistCall(final Context context, String productId, String userId,String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().addToWishList(productId, userId, type)
                .enqueue(new Callback<AddToWishlistResBean>() {
                    @Override
                    public void onResponse(Call<AddToWishlistResBean> call, Response<AddToWishlistResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onAddToWishlistSuccess(response.body());

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
                    public void onFailure(Call<AddToWishlistResBean> call, Throwable t) {
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

