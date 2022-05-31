package com.book.donation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.ClaimPersonsListAdapter;
import com.book.donation.apicalls.Presenter.ClaimedPersonListPresenter;
import com.book.donation.apicalls.View.IClaimedPersonListView;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.ClaimAcceptResBean;
import com.book.donation.apicalls.model.ClaimedPersonListResBean;
import com.book.donation.apicalls.model.DonatItemDasListResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.databinding.FragmentDonateDetailsBinding;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonateDetailsFragment extends Fragment implements ClaimPersonsListAdapter.ItemClickListener, IClaimedPersonListView {

    FragmentDonateDetailsBinding binding;
    ClaimPersonsListAdapter claimPersonsListAdapter;

    ArrayList<ClaimedPersonListResBean.DataItem> dataBean = new ArrayList<>();
    DonatItemDasListResBean.DataItem donateItemDetails;

    ClaimedPersonListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_donate_details, container, false);

        donateItemDetails  = (DonatItemDasListResBean.DataItem)getArguments().getSerializable("donateItemDetails");
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+donateItemDetails.getImage()).into(binding.imgItem);
        binding.txtDate.setText(donateItemDetails.getCreatedAt());
        binding.txtCategory.setText(donateItemDetails.getCategoryName());
        binding.txtSubCategory.setText(donateItemDetails.getSubCategoryName());
        binding.txtCondition.setText(donateItemDetails.getItemCondition());
        binding.txtDescription.setText(donateItemDetails.getDescription());
        binding.txtDonationId.setText(""+donateItemDetails.getId());

        presenter = new ClaimedPersonListPresenter();
        presenter.setView(this);
        if (NetworkCheck.isConnected(getContext())) {
            presenter.ClaimedPersonListCall(getContext(), new SharedPreferenceData(getContext()).getUser_id(), "" + donateItemDetails.getId());
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        claimPersonsListAdapter = new ClaimPersonsListAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvClaimedPersonList.setLayoutManager(gridLayoutManager1);
        binding.rvClaimedPersonList.setItemAnimator(new DefaultItemAnimator());
        binding.rvClaimedPersonList.setAdapter(claimPersonsListAdapter);

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        //getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
        String requestorId = ""+dataBean.get(Position).getUserId();
        if (NetworkCheck.isConnected(getContext())) {
            presenter.createThreadIdCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), requestorId, ""+donateItemDetails.getId(), "donate");
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void OnClaimedAccepted(int position) {
        if (NetworkCheck.isConnected(getContext())) {
            presenter.ClaimAcceptCall(getContext(), "" + dataBean.get(position).getUserId(), "" + dataBean.get(position).getClaimId());
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void onClaimedPersonListSuccess(ClaimedPersonListResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            claimPersonsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClaimAcceptSuccess(ClaimAcceptResBean item) {
        if (item.isSuccess()){
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onThreadIdSuccess(ThreadIdResBean item) {
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "Still any person haven't claimed this item", Toast.LENGTH_LONG).show();
    }
}
