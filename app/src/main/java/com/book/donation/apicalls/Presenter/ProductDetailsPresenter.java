package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IProductDetailsView;
import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.ProductDetailsResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsPresenter extends BasePresenter<IProductDetailsView> {

    GoogleProgressDialog mProgressDialog;

    public void DetailsCall(final Context context, String userID, String productId, String productType) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getProductDetails(userID, productId, productType)
                .enqueue(new Callback<ProductDetailsResBean>() {
                    @Override
                    public void onResponse(Call<ProductDetailsResBean> call, Response<ProductDetailsResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onProductDetailsSucess(response.body());

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
                    public void onFailure(Call<ProductDetailsResBean> call, Throwable t) {
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

    public void ClaimCall(final Context context, String userID, String productId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().claimProduct(userID, productId)
                .enqueue(new Callback<ClaimResBean>() {
                    @Override
                    public void onResponse(Call<ClaimResBean> call, Response<ClaimResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onClaimSucess(response.body());

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
                    public void onFailure(Call<ClaimResBean> call, Throwable t) {
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

    public void AddToWishlistCall(final Context context, String productId, String userId, String type) {
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

    public void AddToLikeThumbCall(final Context context, String productId, String userId, String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().submitLikeTHumb(productId, userId, type)
                .enqueue(new Callback<SubmitLikeThumbResBean>() {
                    @Override
                    public void onResponse(Call<SubmitLikeThumbResBean> call, Response<SubmitLikeThumbResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSubmitLikeSuccess(response.body());

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
                    public void onFailure(Call<SubmitLikeThumbResBean> call, Throwable t) {
                        try {
                            //getView().enableLoadingBar(context, false);
                            mProgressDialog.dismiss();
                            t.printStackTrace();
                            getView().onError(t.getMessage());
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

