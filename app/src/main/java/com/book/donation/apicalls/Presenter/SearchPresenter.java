package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.ISearchView;
import com.book.donation.apicalls.model.SearchItemResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter extends BasePresenter<ISearchView> {

    GoogleProgressDialog mProgressDialog;

    public void SearchItemCall(final Context context, String userId, String keyword, String type) {

        UTopperApp.getInstance().getApiService().getSearchItems(userId, keyword, type)
                .enqueue(new Callback<SearchItemResBean>() {
                    @Override
                    public void onResponse(Call<SearchItemResBean> call, Response<SearchItemResBean> response) {

                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSearchItemSucess(response.body());

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
                    public void onFailure(Call<SearchItemResBean> call, Throwable t) {
                        try {

                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
