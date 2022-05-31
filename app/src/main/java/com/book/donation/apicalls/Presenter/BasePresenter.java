package com.book.donation.apicalls.Presenter;

import android.content.Context;
import com.book.donation.apicalls.View.IUtopperView;
import retrofit2.Response;

public abstract class BasePresenter<I extends IUtopperView> {

    I iVocabView;

    public BasePresenter() {
    }

    public I getView() {
        return iVocabView;
    }

    public void setView(I iView) {
        this.iVocabView = iView;
    }


    public boolean handleError(Response response, Context context) {
        /*
         *
         * 400 Bad Request
         * 404 Not Found
         * 408 Request Timeout
         * 500 Internal Server Error
         * 502 Bad Gateway
         * 503 Service Unavailable
         * 504 Gateway Timeout
         * 598 Network Read Timeout
         * 599 Network Connect Timeout
         *
         * */
        if (response.code() == 400 ||response.code() == 401 || response.code() == 404 || response.code() == 500
                || response.code() == 408 || response.code() == 503 || response.code() == 502
                || response.code() == 504 || response.code() == 598 || response.code() == 599) {
            String title = "";
            switch (response.code()) {
                case 400:
                    title = "Bad Request";
                    break;

                 case 401:
                     title = "unauthorized Request";
                    break;

                case 404:
                    title = "Not Found";
                    break;
                case 408:
                    title = "Request Timeout";
                    break;
                case 500:
                    title = "Internal Server Error";
                    break;
                case 502:
                    title = "Bad Gateway";
                    break;
                case 503:
                    title = "Service Unavailable";
                    break;
                case 504:
                    title = "Gateway Timeout";
                    break;
                case 598:
                    title = "Network Read Timeout";
                    break;
                case 599:
                    title = "Network Connect Timeout";
                    break;
            }

            getView().onError(null);
            return true;
        } else if (response.errorBody() != null) {
            try {
                String error = response.errorBody().string();
                getView().onError(response != null ? error : null);
            } catch (Exception e) {
                e.printStackTrace();
                getView().onError(null);
            } finally {
                return true;
            }
        }
        return false;
    }

}
