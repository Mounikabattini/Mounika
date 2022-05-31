package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ClaimAcceptResBean;
import com.book.donation.apicalls.model.ClaimedPersonListResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;

public interface IClaimedPersonListView extends IUtopperView{
    void onClaimedPersonListSuccess(ClaimedPersonListResBean item);
    void onClaimAcceptSuccess(ClaimAcceptResBean item);
    void onThreadIdSuccess(ThreadIdResBean item);
}
