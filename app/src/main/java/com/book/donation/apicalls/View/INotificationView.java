package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.NotificationResBean;

public interface INotificationView extends IUtopperView{
    void onNotificationSuccess(NotificationResBean item);
}
