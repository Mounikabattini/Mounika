package com.book.donation.fragment.mydashboardfragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.book.donation.adapter.MyClaimItemListAdapter;
import com.book.donation.apicalls.Presenter.MyClaimItemListPresenter;
import com.book.donation.apicalls.View.IMyClaimItemListView;
import com.book.donation.apicalls.model.ItemReceivedResBean;
import com.book.donation.apicalls.model.MyClaimItemListResBean;
import com.book.donation.apicalls.model.RejectClaimResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.databinding.FragmentDashboardDonateBinding;
import com.book.donation.fragment.BookDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class DashboardClaimFragment extends Fragment implements MyClaimItemListAdapter.RejectClickListener, IMyClaimItemListView {

    FragmentDashboardDonateBinding binding;
    MyClaimItemListAdapter myClaimItemListAdapter;

    ArrayList<MyClaimItemListResBean.DataItem> dataBean = new ArrayList<>();
    MyClaimItemListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dashboard_donate, container, false);

        presenter = new MyClaimItemListPresenter();
        presenter.setView(this);

        myClaimItemListAdapter = new MyClaimItemListAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvDonateItemList.setLayoutManager(gridLayoutManager1);
        binding.rvDonateItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvDonateItemList.setAdapter(myClaimItemListAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.MyClaimItemListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
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
            ArrayList<MyClaimItemListResBean.DataItem> temp = new ArrayList();
            for (MyClaimItemListResBean.DataItem d : dataBean) {
                if (d.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
            myClaimItemListAdapter.updateList(temp);
        }
    }

    @Override
    public void OnRejectClicked(int Position) {
        presenter.RejectClaimCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id(), ""+dataBean.get(Position).getId(), "D");
    }

    @Override
    public void OnChatClicked(int Position) {
        //getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
        String requestorId = dataBean.get(Position).getCreatedBy();
        if (NetworkCheck.isConnected(getContext())) {
            presenter.createThreadIdCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), requestorId, ""+dataBean.get(Position).getId(), "donate");
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void ClickedForDetails(int Position) {
        Fragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_id", ""+dataBean.get(Position).getId());
        bundle.putString("product_type", "D");
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void ItemReceived(int Position) {
        presenter.ItemReceivedCall(getContext(), dataBean.get(Position).getClaimBy(), ""+dataBean.get(Position).getClaim_id());
    }

    @Override
    public void onMyClaimItemListSuccess(MyClaimItemListResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            myClaimItemListAdapter.notifyDataSetChanged();
            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRejectClaimSuccess(RejectClaimResBean item) {
        if (item.isSuccess()){
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            presenter.MyClaimItemListCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id());

            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onThreadIdSuccess(ThreadIdResBean item) {

    }

    @Override
    public void onItemReceivedSuccess(ItemReceivedResBean item) {
        if (item.isSuccess()){
            presenter.MyClaimItemListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
        }else {
            ((MainActivity)getActivity()).toast(item.getMessage());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        dataBean.clear();
        myClaimItemListAdapter.notifyDataSetChanged();
        binding.txtNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setText("You haven't claim any item yet");
        //Toast.makeText(getContext(), "You haven't claimed any item yet", Toast.LENGTH_LONG).show();
    }
}
