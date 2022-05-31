package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.ProductDetailsResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;

public interface IProductDetailsView extends  IUtopperView{
    void onProductDetailsSucess(ProductDetailsResBean item);
    void onClaimSucess(ClaimResBean item);
    void onAddToWishlistSuccess(AddToWishlistResBean item);
    void onThreadIdSuccess(ThreadIdResBean item);
    void onSubmitLikeSuccess(SubmitLikeThumbResBean item);
}
