package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.DonatItemDasListResBean;
import com.book.donation.apicalls.model.ItemDeleteResBean;

public interface IDonateItemDasListView extends IUtopperView{
    void onDonateItemDasListSuccess(DonatItemDasListResBean item);
    void onItemDeleteSuccess(ItemDeleteResBean item);
}
