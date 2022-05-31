package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.ISupportQueryView;
import com.book.donation.apicalls.model.SupportQueryResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportQueryPresenter extends BasePresenter<ISupportQueryView> {

    GoogleProgressDialog mProgressDialog;

    public void submitSupportQuery(final Context context, String userId, String title, String queryMsg) {
        getView().enableLoadingBar(context, true);

        UTopperApp.getInstance().getApiService().submitSupportQuery(userId, title, queryMsg)
                .enqueue(new Callback<SupportQueryResBean>() {
                    @Override
                    public void onResponse(Call<SupportQueryResBean> call, Response<SupportQueryResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSubmitQuerySucess(response.body());

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
                    public void onFailure(Call<SupportQueryResBean> call, Throwable t) {
                        getView().enableLoadingBar(context, false);
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }
}

