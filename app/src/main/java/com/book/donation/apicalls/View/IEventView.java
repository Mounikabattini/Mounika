package com.book.donation.apicalls.View;
import com.book.donation.apicalls.model.EventResBean;

public interface IEventView extends  IUtopperView{
    void onEventSucess(EventResBean item);
}
