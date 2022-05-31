package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.StateResBean;

public interface IDonorDetailsView extends IUtopperView{
    void onStateSuccess(StateResBean item);
    void onCitySuccess(CityResBean item);
}
