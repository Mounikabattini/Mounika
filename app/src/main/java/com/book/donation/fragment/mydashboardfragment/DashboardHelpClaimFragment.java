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
import com.book.donation.adapter.MyHelpClaimItemListAdapter;
import com.book.donation.apicalls.Presenter.MYHelpClaimItemListPresenter;
import com.book.donation.apicalls.View.IMyClaimItemListView;
import com.book.donation.apicalls.model.ItemReceivedResBean;
import com.book.donation.apicalls.model.MyClaimItemListResBean;
import com.book.donation.apicalls.model.RejectClaimResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.databinding.FragmentDashboardDonateBinding;
import com.book.donation.fragment.HelpRequestDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class DashboardHelpClaimFragment extends Fragment implements MyHelpClaimItemListAdapter.RejectClickListener, IMyClaimItemListView {

    FragmentDashboardDonateBinding binding;
    MyHelpClaimItemListAdapter myHelpClaimItemListAdapter;

    ArrayList<MyClaimItemListResBean.DataItem> dataBean = new ArrayList<>();
    MYHelpClaimItemListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dashboard_donate, container, false);

        presenter = new MYHelpClaimItemListPresenter();
        presenter.setView(this);

        myHelpClaimItemListAdapter = new MyHelpClaimItemListAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvDonateItemList.setLayoutManager(gridLayoutManager1);
        binding.rvDonateItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvDonateItemList.setAdapter(myHelpClaimItemListAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.MyHelpClaimItemListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
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
            myHelpClaimItemListAdapter.updateList(temp);
        }
    }

    @Override
    public void OnRejectClicked(int Position) {
        presenter.RejectClaimCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id(), ""+dataBean.get(Position).getId(), "A");
    }

    @Override
    public void OnChatClicked(int Position) {
        String requestorId = dataBean.get(Position).getCreatedBy();
        if (NetworkCheck.isConnected(getContext())) {
            presenter.createThreadIdCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), requestorId, ""+dataBean.get(Position).getId(), "request");
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void ClickedForDetails(int Position) {
        Fragment fragment = new HelpRequestDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", "helpClaim");
        bundle.putString("product_id", ""+dataBean.get(Position).getId());
        bundle.putString("productType", "A");
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void ItemReceived(int position) {
        presenter.ItemReceivedCall(getContext(), dataBean.get(position).getClaimBy(), ""+dataBean.get(position).getClaim_id());
    }

    @Override
    public void onMyClaimItemListSuccess(MyClaimItemListResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            myHelpClaimItemListAdapter.notifyDataSetChanged();
            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRejectClaimSuccess(RejectClaimResBean item) {
        if (item.isSuccess()){
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            presenter.MyHelpClaimItemListCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id());

            binding.txtNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onThreadIdSuccess(ThreadIdResBean item) {

    }

    @Override
    public void onItemReceivedSuccess(ItemReceivedResBean item) {
        if (item.isSuccess()){
            presenter.MyHelpClaimItemListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
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
        myHelpClaimItemListAdapter.notifyDataSetChanged();
        binding.txtNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setText("You haven't claim any request yet");
        //Toast.makeText(getContext(), "You haven't claimed any item yet", Toast.LENGTH_LONG).show();
    }
}
