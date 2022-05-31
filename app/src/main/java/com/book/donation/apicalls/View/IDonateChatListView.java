package com.book.donation.apicalls.View;

import com.book.donation.apicalls.model.ChatListResBean;

public interface IDonateChatListView extends IUtopperView{
    void onChatListSuccess(ChatListResBean item);
}
