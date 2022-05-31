package com.book.donation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.ItemSearchListAdapter;
import com.book.donation.apicalls.Presenter.SearchPresenter;
import com.book.donation.apicalls.View.ISearchView;
import com.book.donation.apicalls.model.SearchItemResBean;
import com.book.donation.databinding.FragmentSearchBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements ISearchView, ItemSearchListAdapter.ItemClickListener {

    FragmentSearchBinding binding;
    ItemSearchListAdapter itemSearchListAdapter;

    ArrayList<SearchItemResBean.DataItem> searchData = new ArrayList<>();
    String sectionType;
    SearchPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search, container, false);

        sectionType = getArguments().getString("sectionType");
        presenter = new SearchPresenter();
        presenter.setView(this);

        itemSearchListAdapter = new ItemSearchListAdapter(getContext(), searchData, this);
        binding.lvProducts.setAdapter(itemSearchListAdapter);

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
                //if (s.length()>0) {
                    if (NetworkCheck.isConnected(getContext())) {
                        presenter.SearchItemCall(getActivity(), new SharedPreferenceData(getContext()).getUser_id(), s.toString(), sectionType);
                    } else {
                        ((MainActivity) getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                    }
               /* }else {
                    binding.lvProducts.setVisibility(View.GONE);
                }*/
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        Fragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_id", ""+searchData.get(position).getId());
        bundle.putString("product_type", sectionType);
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void onSearchItemSucess(SearchItemResBean item) {
        searchData.clear();
        searchData.addAll(item.getData());
        itemSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
