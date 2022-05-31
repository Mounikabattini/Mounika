package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.LoginResBean;
import com.book.donation.apicalls.model.VerifyOtpResBean;

public interface ILoginView extends  IUtopperView{
    void onLoginSucess(LoginResBean item);
    void onOTPSucess(VerifyOtpResBean item);
}
