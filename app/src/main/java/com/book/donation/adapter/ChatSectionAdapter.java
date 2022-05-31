package com.book.donation.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ChatSectionAdapter  extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public ChatSectionAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            /*case 0:
                AllChatFragment allChatFragment = new AllChatFragment();
                return allChatFragment;*/
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
