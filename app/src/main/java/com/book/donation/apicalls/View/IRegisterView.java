package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.RegisterResBean;
import com.book.donation.apicalls.model.StateResBean;

public interface IRegisterView extends IUtopperView{
    void onRegisterSucess(RegisterResBean item);
    void onStateSucess(StateResBean item);
    void onCitySucess(CityResBean item);
}
