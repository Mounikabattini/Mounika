package com.book.donation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.adapter.MyWishlistAdapter;
import com.book.donation.databinding.FragmentMyWishlistBinding;
import com.google.android.material.tabs.TabLayout;

public class MyWishlistFragment extends Fragment {

    FragmentMyWishlistBinding binding;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_wishlist, container, false);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Donate Wishlist"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Help Wishlist"));

        final MyWishlistAdapter adapter = new MyWishlistAdapter(getContext(), getChildFragmentManager(), binding.tabLayout.getTabCount());
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

        return binding.getRoot();
    }
}
