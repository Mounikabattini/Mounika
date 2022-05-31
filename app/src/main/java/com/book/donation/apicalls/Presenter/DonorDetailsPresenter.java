package com.book.donation.apicalls.Presenter;

import android.app.Activity;
import android.widget.Toast;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IDonorDetailsView;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.StateResBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorDetailsPresenter extends BasePresenter<IDonorDetailsView>{

    public void StateCall(final Activity context){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getStates()
                .enqueue(new Callback<StateResBean>() {
                    @Override
                    public void onResponse(Call<StateResBean> call, Response<StateResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onStateSuccess(response.body());

                                }else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<StateResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void CityCall(final Activity context, String stateId){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().getCities(stateId)
                .enqueue(new Callback<CityResBean>() {
                    @Override
                    public void onResponse(Call<CityResBean> call, Response<CityResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCitySuccess(response.body());

                                }else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<CityResBean> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false);
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
