package com.book.donation.apicalls.model.messenger;

public class DataForFirebaseChatResBean {
    private String Thread_id;
    private String name;
    private String other_user_id;
    private String user_id;
    private String type;
    private String profile_image;

    public DataForFirebaseChatResBean(String Thread_id, String name, String other_user_id, String user_id, String type, String profile_image) {
        this.Thread_id = Thread_id;
        this.name = name;
        this.other_user_id = other_user_id;
        this.user_id = user_id;
        this.type = type;
        this.profile_image = profile_image;
    }

    public DataForFirebaseChatResBean() {
    }

    public String getThread_id() {
        return Thread_id;
    }

    public String getName() {
        return name;
    }

    public String getOther_user_id() {
        return other_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setThread_id(String thread_id) {
        Thread_id = thread_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOther_user_id(String other_user_id) {
        this.other_user_id = other_user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}

