package com.book.donation.apicalls.Presenter;

import android.content.Context;

import com.book.donation.UTopperApp;
import com.book.donation.apicalls.View.IDonateChatListView;
import com.book.donation.apicalls.model.ChatListResBean;
import com.book.donation.utils.GoogleProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateChatListPresenter extends BasePresenter<IDonateChatListView> {

    GoogleProgressDialog mProgressDialog;

    public void ChatListCall(final Context context, String userId, String type) {
        //getView().enableLoadingBar(context, true);
        mProgressDialog = new GoogleProgressDialog(context);
        mProgressDialog.showDialog();

        UTopperApp.getInstance().getApiService().getDonateChatList(userId, type)
                .enqueue(new Callback<ChatListResBean>() {
                    @Override
                    public void onResponse(Call<ChatListResBean> call, Response<ChatListResBean> response) {
                        //getView().enableLoadingBar(context, false);
                        mProgressDialog.dismiss();
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onChatListSuccess(response.body());

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
                    public void onFailure(Call<ChatListResBean> call, Throwable t) {
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
