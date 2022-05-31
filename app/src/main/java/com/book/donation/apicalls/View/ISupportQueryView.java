package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.SupportQueryResBean;

public interface ISupportQueryView  extends  IUtopperView{
    void onSubmitQuerySucess(SupportQueryResBean item);
}

