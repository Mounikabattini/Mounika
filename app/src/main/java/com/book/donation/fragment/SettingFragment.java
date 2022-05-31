package com.book.donation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.databinding.FragmentSettingBinding;
import com.book.donation.utils.AppUtils;
import com.squareup.picasso.Picasso;

public class SettingFragment extends Fragment implements View.OnClickListener {

    FragmentSettingBinding binding;
    boolean isNooedToRefresh = false;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_setting, container, false);

        binding.txtViewAndEdit.setOnClickListener(this);
        binding.txtUserName.setText(((MainActivity)getActivity()).profileData.getUser_name());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+((MainActivity)getActivity()).profileData.getProfile_image()).into(binding.imgProfile);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtViewAndEdit:
                AppUtils.goNextFragmentReplace(getActivity(),new EditProfileFragment());
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isNooedToRefresh){
            binding.txtUserName.setText(((MainActivity)getActivity()).profileData.getUser_name());
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+((MainActivity)getActivity()).profileData.getProfile_image()).into(binding.imgProfile);
        }
    }
}
