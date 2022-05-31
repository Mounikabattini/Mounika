package com.book.donation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.R;
import com.book.donation.adapter.RequestedPersonListAdapter;
import com.book.donation.databinding.FragmentAskHelpDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class AskHelpDetailsFragment extends Fragment implements RequestedPersonListAdapter.ItemClickListener {

    FragmentAskHelpDetailsBinding binding;
    RequestedPersonListAdapter requestedPersonListAdapter;

    List<String> dataBean = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_ask_help_details, container, false);

        requestedPersonListAdapter = new RequestedPersonListAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvDonateItemList.setLayoutManager(gridLayoutManager1);
        binding.rvDonateItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvDonateItemList.setAdapter(requestedPersonListAdapter);

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        //getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
    }
}
