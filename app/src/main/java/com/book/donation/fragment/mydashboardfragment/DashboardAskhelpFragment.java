package com.book.donation.fragment.mydashboardfragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.activities.MainActivity;
import com.book.donation.R;
import com.book.donation.adapter.DashboardAskHelpAdapter;
import com.book.donation.apicalls.Presenter.MyHelpRequestPresenter;
import com.book.donation.apicalls.View.IMyHelpRequestView;
import com.book.donation.apicalls.model.ItemDeleteResBean;
import com.book.donation.apicalls.model.MyHelpRequestResBean;
import com.book.donation.databinding.FragmentDashboardDonateBinding;
import com.book.donation.fragment.MyRequestDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class DashboardAskhelpFragment extends Fragment implements DashboardAskHelpAdapter.ItemClickListener, IMyHelpRequestView {

    FragmentDashboardDonateBinding binding;
    DashboardAskHelpAdapter dashboardAskHelpAdapter;
    MyHelpRequestPresenter presenter;

    ArrayList<MyHelpRequestResBean.DataItem> dataBean = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dashboard_donate, container, false);
        presenter = new MyHelpRequestPresenter();
        presenter.setView(this);

        dashboardAskHelpAdapter = new DashboardAskHelpAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvDonateItemList.setLayoutManager(gridLayoutManager1);
        binding.rvDonateItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvDonateItemList.setAdapter(dashboardAskHelpAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.MyHelpRequestListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return binding.getRoot();
    }

    void filter(String text){
        if (dataBean.size()>0) {
            ArrayList<MyHelpRequestResBean.DataItem> temp = new ArrayList();
            for (MyHelpRequestResBean.DataItem d : dataBean) {
                if (d.getUserName().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
            dashboardAskHelpAdapter.updateList(temp);
        }
    }

    @Override
    public void OnItemClicked(int Position) {
        ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.GONE);
        Fragment fragment = new MyRequestDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HelpRequestDetails", dataBean.get(Position));
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        //AppUtils.goFragmentAddWithoutBackStack(getContext(), new AskHelpDetailsFragment());
    }

    @Override
    public void OnItemDelete(int Position) {
        presenter.deleteItem(getContext(), ""+dataBean.get(Position).getId(), "A");
    }

    @Override
    public void onMyHelpRequestSuccess(MyHelpRequestResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            dashboardAskHelpAdapter.notifyDataSetChanged();
            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemDeleteSuccess(ItemDeleteResBean item) {
        if (item.isSuccess()){
            presenter.MyHelpRequestListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
        }else {
            ((MainActivity)getActivity()).toast(item.getMessage());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        binding.txtNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setText("You haven't put any help request yet");
        dataBean.clear();
        dashboardAskHelpAdapter.notifyDataSetChanged();
        //Toast.makeText(getContext(), "You have place any help request yet", Toast.LENGTH_LONG).show();
    }
}
