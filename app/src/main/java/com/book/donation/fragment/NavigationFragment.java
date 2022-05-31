package com.book.donation.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.R;
import com.book.donation.activities.LoginActivity;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.NavAdapter;
import com.book.donation.databinding.FragmentNavigationBinding;
import com.book.donation.utils.AppUtils;

public class NavigationFragment extends Fragment implements NavAdapter.ItemClickListener {

    FragmentNavigationBinding binding;
    //private String[] navItems = {"Home", "My Profile", "My Dashboard", "My Chats", "My WishList", "Events", "Help & support", "About us", "Logout"};
    private String[] navItems = {"Dashboard", "My Profile", "My Claims", "Logout"};
    private int[] navImage = {R.drawable.ic_dashboard, R.drawable.ic_account, R.drawable.ic_claimed, R.drawable.ic_logout};

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_navigation, container, false);

        NavAdapter navAdapter = new NavAdapter(getContext(), navItems, navImage, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvItemList.setLayoutManager(layoutManager);
        binding.rvItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvItemList.setAdapter(navAdapter);

        return binding.getRoot();
    }

    @Override
    public void onItemClick(int Position) {
        ((MainActivity)getActivity()).isNavigationOpen = false;
        if (Position==0){
            /*FragmentManager manager = getActivity().getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
                manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }*/
            DashboardItemChange("My Donate");
            getParentFragmentManager().popBackStack();
        }else if (Position == 1) {
            /*startActivity(new Intent(getActivity(), EditProfileActivity.class));
            getParentFragmentManager().popBackStack();*/

            getParentFragmentManager().popBackStack();
            ((MainActivity)getActivity()).isNeedToOpenHomeFragment = true;
            ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.VISIBLE);
            ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.GONE);
            changeBottomBarColor(((MainActivity)getActivity()).binding.imgMyAccount, ((MainActivity)getActivity()).binding.txtMyAccount);
            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            AppUtils.goNextFragmentReplace(getContext(), new EditProfileFragment());

        }/*else if (Position == 3) {
            DashboardItemChange("My Request");
            getParentFragmentManager().popBackStack();
        }*/else if (Position == 2) {
            DashboardItemChange("My Claim");
            getParentFragmentManager().popBackStack();
        }/*else if (Position == 5) {
            DashboardItemChange("My Help Claim");
            getParentFragmentManager().popBackStack();
        }else if (Position == 5) {
            getParentFragmentManager().popBackStack();
            ((MainActivity)getActivity()).isNeedToOpenHomeFragment = true;
            ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.VISIBLE);
            ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.GONE);
            changeBottomBarColor(((MainActivity)getActivity()).binding.imgMyAccount, ((MainActivity)getActivity()).binding.txtMyAccount);
            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            AppUtils.goNextFragmentReplace(getContext(), new MyWishlistFragment());
        }else if (Position == 6) {
            getParentFragmentManager().popBackStack();
            AppUtils.goFragmentAddWithoutBackStack(getContext(), new EventFragment());
        }else if (Position == 7) {
            getParentFragmentManager().popBackStack();
            AppUtils.goFragmentAddWithoutBackStack(getContext(), new HelpSupportFragment());
        }*//*else if (Position == 8) {
            getParentFragmentManager().popBackStack();
            AppUtils.goFragmentAddWithoutBackStack(getContext(), new WriteToFounderFragment());
        }*//*else if (Position == 8) {
            getParentFragmentManager().popBackStack();
            Fragment fragment = new HappySectionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("from", "navigation");
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }else if (Position == 9) {
            *//*getParentFragmentManager().popBackStack();
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(ApiConstants.ABOUT_US_URL));
            startActivity(viewIntent);*//*
            getParentFragmentManager().popBackStack();
            startActivity(new Intent(getActivity(), AboutUsActivity.class));
        }*/else if (Position == 3) {
            ((MainActivity)getActivity()).profileData.Logout();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }

    public void changeBottomBarColor(ImageView imgView, TextView txtView){
        ((MainActivity)getActivity()).binding.imgHome.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        ((MainActivity)getActivity()).binding.txtHome.setTextColor(getResources().getColor(R.color.white));
        ((MainActivity)getActivity()).binding.imgChat.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        ((MainActivity)getActivity()).binding.txtChat.setTextColor(getResources().getColor(R.color.white));
        ((MainActivity)getActivity()).binding.imgMyDashboard.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        ((MainActivity)getActivity()).binding.txtMyDashboard.setTextColor(getResources().getColor(R.color.white));
        ((MainActivity)getActivity()).binding.imgMyAccount.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        ((MainActivity)getActivity()).binding.txtMyAccount.setTextColor(getResources().getColor(R.color.white));

        imgView.getDrawable().setColorFilter(getResources().getColor(R.color.selected_bottom_bar), PorterDuff.Mode.MULTIPLY);
        txtView.setTextColor(getResources().getColor(R.color.selected_bottom_bar));
    }

    private void DashboardItemChange(String buttonType){
        Fragment fragment = new MyDashboardFragment();
        Bundle bundle = new Bundle();
        if (buttonType.equalsIgnoreCase("My Request")) {
            bundle.putString("from", "My Request");
        } else if (buttonType.equalsIgnoreCase("My Claim")) {
            bundle.putString("from", "My Claim");
        } else if (buttonType.equalsIgnoreCase("My Help Claim")){
            bundle.putString("from", "My Help Claim");
        } else {
            bundle.putString("from", "My Donate");
        }
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).isNeedToOpenHomeFragment = true;
        ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.GONE);
        changeBottomBarColor(((MainActivity)getActivity()).binding.imgMyDashboard, ((MainActivity)getActivity()).binding.txtMyDashboard);
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        AppUtils.goNextFragmentReplace(getContext(), fragment);
    }

    @Override
    public void onChildItemClick(int position, String buttonType) {
        ((MainActivity)getActivity()).isNavigationOpen = false;
        /*if (position==2) {
            if (buttonType.equalsIgnoreCase("Donation requests")) {
                DashboardItemChange("Donation requests");
            } else {
                DashboardItemChange("My Request");
            }
        }else */if (position==2){
            if (buttonType.equalsIgnoreCase("Donated items claim")) {
                DashboardItemChange("My Claim");
            } else {
                DashboardItemChange("My Help Claim");
            }
        }
    }
}