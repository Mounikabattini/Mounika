package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.RequestDetailsResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;

public interface IRequestDetailsView extends IUtopperView{
    void onThreadIdSuccess(ThreadIdResBean item);
    void onClaimSucess(ClaimResBean item);
    void onProductDetailsSucess(RequestDetailsResBean item);
    void onAddToWishlistSuccess(AddToWishlistResBean item);
    void onSubmitLikeSuccess(SubmitLikeThumbResBean item);
}
