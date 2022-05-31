package com.book.donation.apicalls.model.messenger;

public class Sender {
    public DataForFirebaseChatResBean data;
    public Notification notification;
    public String to;

    public Sender(DataForFirebaseChatResBean data, String to, Notification notification) {
        this.data = data;
        this.to = to;
        this.notification = notification;
    }
}