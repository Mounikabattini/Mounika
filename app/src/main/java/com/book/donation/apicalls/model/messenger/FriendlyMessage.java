package com.book.donation.apicalls.model.messenger;

public class FriendlyMessage {

    private String id;
    private String sender_user_id;
    private String sender_user_name;
    private String sender_user_image;
    private String receiver_user_id;
    private String receiver_user_name;
    private String receiver_user_image;
    private String text;
    private String imageUrl;
    private String messageType;
    private String messageTime;
    private String itemToShare;
    private boolean seen = false;
    private boolean isSelect;

    public FriendlyMessage(){
        
    }

    public FriendlyMessage(String sender_user_id, String sender_user_name, String sender_user_image, String receiver_user_id, String receiver_user_name, String receiver_user_image, String text, String itemToShare, String imageUrl, String messageType,
                           String messageTime, boolean seen, boolean isSelect) {
        this.sender_user_id = sender_user_id;
        this.sender_user_name = sender_user_name;
        this.sender_user_image = sender_user_image;
        this.receiver_user_id = receiver_user_id;
        this.receiver_user_name = receiver_user_name;
        this.receiver_user_image = receiver_user_image;
        this.text = text;
        this.itemToShare = itemToShare;
        this.imageUrl = imageUrl;
        this.messageType = messageType;
        this.messageTime = messageTime;
        this.seen = seen;
        this.isSelect = isSelect;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSender_user_id(String sender_user_id) {
        this.sender_user_id = sender_user_id;
    }

    public void setSender_user_name(String sender_user_name) {
        this.sender_user_name = sender_user_name;
    }

    public void setSender_user_image(String sender_user_image) {
        this.sender_user_image = sender_user_image;
    }

    public void setReceiver_user_id(String receiver_user_id) {
        this.receiver_user_id = receiver_user_id;
    }

    public void setReceiver_user_name(String receiver_user_name) {
        this.receiver_user_name = receiver_user_name;
    }

    public void setReceiver_user_image(String receiver_user_image) {
        this.receiver_user_image = receiver_user_image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setItemToShare(String itemToShare) {
        this.itemToShare = itemToShare;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setSelect(boolean isSelect){
        this.isSelect = isSelect;
    }

    public String getId() {
        return id;
    }

    public String getSender_user_id() {
        return sender_user_id;
    }

    public String getSender_user_name() {
        return sender_user_name;
    }

    public String getSender_user_image() {
        return sender_user_image;
    }

    public String getReceiver_user_id() {
        return receiver_user_id;
    }

    public String getReceiver_user_name() {
        return receiver_user_name;
    }

    public String getReceiver_user_image() {
        return receiver_user_image;
    }

    public String getText() {
        return text;
    }

    public String getItemToShare() {
        return itemToShare;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public boolean isSeen() {
        return seen;
    }

    public boolean isSelect(){ return isSelect; }

}

