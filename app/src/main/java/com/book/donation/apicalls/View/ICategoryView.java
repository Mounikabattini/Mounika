package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;

public interface ICategoryView extends  IUtopperView{
    void onCategorySucess(CategoryResBean item);
    void onEventSucess(EventResBean item);
    void onRecentItemsSucess(RecentItemsResBean item);
}
