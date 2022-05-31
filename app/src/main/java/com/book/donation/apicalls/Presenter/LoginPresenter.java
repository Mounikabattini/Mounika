package com.book.donation.apicalls.Presenter;

import android.app.Activity;
import android.widget.Toast;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.model.LoginResBean;
import com.book.donation.apicalls.model.VerifyOtpResBean;
import com.book.donation.apicalls.View.ILoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<ILoginView> {

    public void LoginCall(final Activity context, String email , String role_id, String device_key){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().login(email,role_id, device_key)
                .enqueue(new Callback<LoginResBean>() {
                    @Override
                    public void onResponse(Call<LoginResBean> call, Response<LoginResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onLoginSucess(response.body());

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
                    public void onFailure(Call<LoginResBean> call, Throwable t) {
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

    public void VerifyOTPCall(final Activity context, String email , String otp){
        getView().enableLoadingBar(context ,true);

        UTopperApp.getInstance().getApiService().verifyOTP(email,otp)
                .enqueue(new Callback<VerifyOtpResBean>() {
                    @Override
                    public void onResponse(Call<VerifyOtpResBean> call, Response<VerifyOtpResBean> response) {
                        getView().enableLoadingBar(context, false);
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onOTPSucess(response.body());

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
                    public void onFailure(Call<VerifyOtpResBean> call, Throwable t) {
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
