package com.book.donation.apicalls.View;


import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.BookTypeResBean;
import com.book.donation.apicalls.model.SubCategoryResBean;

public interface ISubCategoryView extends  IUtopperView{
    void onSubCategorySucess(SubCategoryResBean item);
    void onAskHelpSubCategorySuccess(AskHelpSubCategoryResBean item);
    void onBookTypeSuccess(BookTypeResBean item);
}
