package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ProfileResBean;
import com.book.donation.apicalls.model.UpdateProfileResBean;

public interface IProfileView extends  IUtopperView{
    void onProfileSucess(ProfileResBean item);
    void onUpdateProfileSuccess(UpdateProfileResBean item);
}
