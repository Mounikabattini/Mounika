package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ItemReceivedResBean;
import com.book.donation.apicalls.model.MyClaimItemListResBean;
import com.book.donation.apicalls.model.RejectClaimResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;

public interface IMyClaimItemListView extends IUtopperView{
    void onMyClaimItemListSuccess(MyClaimItemListResBean item);
    void onRejectClaimSuccess(RejectClaimResBean item);
    void onThreadIdSuccess(ThreadIdResBean item);
    void onItemReceivedSuccess(ItemReceivedResBean item);
}
