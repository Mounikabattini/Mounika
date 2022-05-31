package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.INotificationView;
import com.book.donation.apicalls.model.NotificationResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter extends BasePresenter<INotificationView> {

    GoogleProgressDialog mProgressDialog;

    public void NotificationListCall(final Context context, String userId) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getNotificationList(userId)
                .enqueue(new Callback<NotificationResBean>() {
                    @Override
                    public void onResponse(Call<NotificationResBean> call, Response<NotificationResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onNotificationSuccess(response.body());

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
                    public void onFailure(Call<NotificationResBean> call, Throwable t) {
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
