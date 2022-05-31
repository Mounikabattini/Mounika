package com.book.donation.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.book.donation.fragment.wishlistfragments.DonateWishlistFragment;

public class MyWishlistAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyWishlistAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment donateWishlistFragment = new DonateWishlistFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "D");
                donateWishlistFragment.setArguments(bundle);
                return donateWishlistFragment;
            case 1:
                Fragment donateWishlistFragment1 = new DonateWishlistFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "A");
                donateWishlistFragment1.setArguments(bundle1);
                return donateWishlistFragment1;
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

