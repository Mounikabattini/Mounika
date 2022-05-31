package com.book.donation.apicalls.View;


import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ProductResBean;

public interface IProductView extends  IUtopperView{
    void onProductSucess(ProductResBean item);
    void onAddToWishlistSuccess(AddToWishlistResBean item);
}

