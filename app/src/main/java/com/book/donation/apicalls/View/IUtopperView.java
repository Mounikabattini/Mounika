package com.book.donation.apicalls.View;

import android.content.Context;

public interface IUtopperView {

    Context getContext();
    void enableLoadingBar(Context context, boolean enable);
    void onError(String reason);
}
