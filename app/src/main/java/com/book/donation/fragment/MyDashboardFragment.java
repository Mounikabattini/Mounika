package com.book.donation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.adapter.MyDashboardAdapter;
import com.book.donation.databinding.FragmentMyDashboardBinding;
import com.google.android.material.tabs.TabLayout;

public class MyDashboardFragment extends Fragment {

    FragmentMyDashboardBinding binding;
    String from;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_dashboard, container, false);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("My Donated Items"));
        //binding.tabLayout.addTab(binding.tabLayout.newTab().setText("My Help Requests"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Donated Item Claims"));
        //binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Help Request Claims"));
        from = getArguments().getString("from");

        final MyDashboardAdapter adapter = new MyDashboardAdapter(getContext(), getChildFragmentManager(), binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (from!=null){
            if (from.equalsIgnoreCase("My Request")){
                binding.viewPager.setCurrentItem(1);
            }else if (from.equalsIgnoreCase("My Claim")){
                binding.viewPager.setCurrentItem(2);
            }else if (from.equalsIgnoreCase("My Help Claim")){
                binding.viewPager.setCurrentItem(3);
            }
        }

        return binding.getRoot();
    }
}
