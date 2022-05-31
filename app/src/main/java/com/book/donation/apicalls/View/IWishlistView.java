package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ResponseResBean;
import com.book.donation.apicalls.model.WishlistResBean;

public interface IWishlistView extends  IUtopperView{
    void onWishlistSucess(WishlistResBean item);
    void onTestSucess(ResponseResBean item);
}