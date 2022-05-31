package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ItemDeleteResBean;
import com.book.donation.apicalls.model.MyHelpRequestResBean;

public interface IMyHelpRequestView extends IUtopperView{
    void onMyHelpRequestSuccess(MyHelpRequestResBean item);
    void onItemDeleteSuccess(ItemDeleteResBean item);
}
