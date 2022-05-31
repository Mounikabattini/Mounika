package com.book.donation.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.book.donation.fragment.mydashboardfragment.DashboardAskhelpFragment;
import com.book.donation.fragment.mydashboardfragment.DashboardClaimFragment;
import com.book.donation.fragment.mydashboardfragment.DashboardDonateFragment;
import com.book.donation.fragment.mydashboardfragment.DashboardHelpClaimFragment;

public class MyDashboardAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyDashboardAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DashboardDonateFragment dashboardDonateFragment = new DashboardDonateFragment();
                return dashboardDonateFragment;
            case 1:
                DashboardAskhelpFragment dashboardAskhelpFragment = new DashboardAskhelpFragment();
                return dashboardAskhelpFragment;
            case 2:
                DashboardClaimFragment dashboardClaimFragment = new DashboardClaimFragment();
                return dashboardClaimFragment;
            case 3:
                DashboardHelpClaimFragment dashboardHelpClaimFragment = new DashboardHelpClaimFragment();
                return dashboardHelpClaimFragment;
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
