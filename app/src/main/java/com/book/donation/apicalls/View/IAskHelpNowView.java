package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.AskHelpNowResBean;

public interface IAskHelpNowView extends IUtopperView{
    void onAskHelpNowSuccess(AskHelpNowResBean item);
}
