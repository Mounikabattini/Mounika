package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.DonatNowResBean;

public interface IDonatNowView extends  IUtopperView{
    void onDonatNowSuccess(DonatNowResBean item);
}

