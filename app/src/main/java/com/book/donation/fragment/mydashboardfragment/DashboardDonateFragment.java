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
import com.book.donation.adapter.DashboardDonateAdapter;
import com.book.donation.apicalls.Presenter.DonateItemDasListPresenter;
import com.book.donation.apicalls.View.IDonateItemDasListView;
import com.book.donation.apicalls.model.DonatItemDasListResBean;
import com.book.donation.apicalls.model.ItemDeleteResBean;
import com.book.donation.databinding.FragmentDashboardDonateBinding;
import com.book.donation.fragment.DonateDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class DashboardDonateFragment extends Fragment implements DashboardDonateAdapter.ItemClickListener, IDonateItemDasListView {

    FragmentDashboardDonateBinding binding;
    DashboardDonateAdapter dashboardDonateAdapter;

    ArrayList<DonatItemDasListResBean.DataItem> dataBean = new ArrayList<>();
    DonateItemDasListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dashboard_donate, container, false);

        presenter = new DonateItemDasListPresenter();
        presenter.setView(this);

        dashboardDonateAdapter = new DashboardDonateAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvDonateItemList.setLayoutManager(gridLayoutManager1);
        binding.rvDonateItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvDonateItemList.setAdapter(dashboardDonateAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.DonateItemDasListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
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
            ArrayList<DonatItemDasListResBean.DataItem> temp = new ArrayList();
            for (DonatItemDasListResBean.DataItem d : dataBean) {
                if (d.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
            dashboardDonateAdapter.updateList(temp);
        }
    }

    @Override
    public void OnItemClicked(int Position) {
        ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.GONE);
        Fragment fragment = new DonateDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("donateItemDetails", dataBean.get(Position));
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void OnItemDelete(int Position) {
        presenter.deleteItem(getContext(), ""+dataBean.get(Position).getId(), "D");
    }

    @Override
    public void onDonateItemDasListSuccess(DonatItemDasListResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            dashboardDonateAdapter.notifyDataSetChanged();
            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemDeleteSuccess(ItemDeleteResBean item) {
        if (item.isSuccess()){
            presenter.DonateItemDasListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
        }else {
            ((MainActivity)getActivity()).toast(item.getMessage());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        binding.txtNoData.setText("You haven't donate any item yet");
        binding.txtNoData.setVisibility(View.VISIBLE);
        dataBean.clear();
        dashboardDonateAdapter.notifyDataSetChanged();
        //Toast.makeText(getContext(), "You haven't donote any item yet", Toast.LENGTH_LONG).show();
    }
}
