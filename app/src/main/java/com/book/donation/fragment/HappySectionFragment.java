package com.book.donation.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.book.donation.activities.HappyDetailsActivity;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.HappyUserAdapter;
import com.book.donation.apicalls.Presenter.HappyDataPresenter;
import com.book.donation.apicalls.View.IHappyDataView;
import com.book.donation.apicalls.model.HappyDataResBean;
import com.book.donation.databinding.FragmentHappySectionBinding;
import com.book.donation.databinding.FragmentWritetofounderBinding;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;

public class HappySectionFragment extends Fragment implements IHappyDataView, CustomBottomSheetDialogFragment.DialogSheetListener
        , HappyUserAdapter.ItemClickListener {

    FragmentHappySectionBinding binding;

    HappyUserAdapter happyUserAdapter;
    ArrayList<HappyDataResBean.DataItem> recentData = new ArrayList<>();

    String from;
    HappyDataPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_happy_section, container, false);

        from = getArguments().getString("from");
        presenter = new HappyDataPresenter();
        presenter.setView(this);


        happyUserAdapter = new HappyUserAdapter(getActivity(), recentData, this);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        binding.recyHappyPersons.setLayoutManager(gridLayoutManager3);
        binding.recyHappyPersons.setItemAnimator(new DefaultItemAnimator());
        binding.recyHappyPersons.setAdapter(happyUserAdapter);

        /*if (from.equalsIgnoreCase("dashboard")){
            binding.fab.setVisibility(View.GONE);
        }else {
            binding.fab.setVisibility(View.VISIBLE);
        }*/

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomBottomSheetDialogFragment(HappySectionFragment.this).show(getActivity().getSupportFragmentManager(), "Dialog");
            }
        });

        callForHappyData();

        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), HappyDetailsActivity.class);
        intent.putExtra("image", recentData.get(position).getImage());
        intent.putExtra("title", recentData.get(position).getTitle());
        intent.putExtra("experience", recentData.get(position).getExperienceText());
        getActivity().startActivity(intent);
    }

    private void callForHappyData(){
        if (NetworkCheck.isConnected(getContext())){
            String user_id= "";
            if (!from.equalsIgnoreCase("dashboard")){
                user_id = new SharedPreferenceData(getContext()).getUser_id();
            }
            presenter.HappyDataCall(getContext(), user_id);
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void onHappyDataSucess(HappyDataResBean item) {
        if (item.isSuccess()){
            recentData.clear();
            recentData.addAll(item.getData());
            happyUserAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }

    @Override
    public void onDialogSheetClose() {
        callForHappyData();
    }

}
