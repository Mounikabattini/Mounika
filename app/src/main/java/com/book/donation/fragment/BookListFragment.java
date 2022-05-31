package com.book.donation.fragment;

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
import com.book.donation.adapter.BookListAdapter;
import com.book.donation.adapter.HelpRequestListAdapter;
import com.book.donation.apicalls.Presenter.ProductPresenter;
import com.book.donation.apicalls.View.IProductView;
import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.ProductResBean;
import com.book.donation.databinding.FragmentBookListBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;

public class BookListFragment extends Fragment implements BookListAdapter.ItemClickListener, HelpRequestListAdapter.AskHelpItemClickListener, IProductView {

    FragmentBookListBinding binding;
    BookListAdapter bookListAdapter;
    HelpRequestListAdapter helpRequestListAdapter;
    ArrayList<ProductResBean.DataItem> dataBean = new ArrayList<>();
    AskHelpSubCategoryResBean.DataItem helpData;
    ArrayList<AskHelpSubCategoryResBean.AskdataItem> helpDataBean = new ArrayList<>();

    String from, subCategoryId;
    ProductPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_list, container, false);

        presenter = new ProductPresenter();
        presenter.setView(this);

        from = getArguments().getString("from");

        if (from.equalsIgnoreCase("DonorShelf")) {

            binding.rvBookList.setVisibility(View.VISIBLE);
            bookListAdapter = new BookListAdapter(getActivity(), dataBean, from,this);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvBookList.setLayoutManager(gridLayoutManager1);
            binding.rvBookList.setItemAnimator(new DefaultItemAnimator());
            binding.rvBookList.setAdapter(bookListAdapter);

            subCategoryId = getArguments().getString("subCategoryId");
            if (NetworkCheck.isConnected(getContext())) {
                presenter.ProductsCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), subCategoryId, "D",
                        ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id());
            }else {
                ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
            }
        }else {

            binding.rvHelpRequestList.setVisibility(View.VISIBLE);

            helpData = (AskHelpSubCategoryResBean.DataItem) getArguments().getSerializable("SubCategoryList");
            helpDataBean.clear();
            helpDataBean.addAll(helpData.getAskdata());

            helpRequestListAdapter = new HelpRequestListAdapter(getActivity(), helpDataBean, from,this);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvHelpRequestList.setLayoutManager(gridLayoutManager2);
            binding.rvHelpRequestList.setItemAnimator(new DefaultItemAnimator());
            binding.rvHelpRequestList.setAdapter(helpRequestListAdapter);
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
        if (from.equalsIgnoreCase("DonorShelf")) {
            ArrayList<ProductResBean.DataItem> temp = new ArrayList();
            for(ProductResBean.DataItem d: dataBean){
                if(d.getProductName().toLowerCase().contains(text.toLowerCase())){
                    temp.add(d);
                }
            }
            bookListAdapter.updateList(temp);
        }else {
            ArrayList<AskHelpSubCategoryResBean.AskdataItem> temp = new ArrayList();
            for(AskHelpSubCategoryResBean.AskdataItem d: helpDataBean){
                if(d.getUserName().toLowerCase().contains(text.toLowerCase())){
                    temp.add(d);
                }
            }
            helpRequestListAdapter.updateList(temp);
        }
    }

    @Override
    public void OnItemClicked(int Position) {
        if (!from.equalsIgnoreCase("DonorShelf")) {
            AppUtils.goFragmentAddWithoutBackStack(getContext(), new HelpRequestDetailsFragment());
        }else {
            Fragment fragment = new BookDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("product_id", ""+dataBean.get(Position).getId());
            bundle.putString("product_type", new SharedPreferenceData(getContext()).getSectionType());
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }
    }

    @Override
    public void OnAskHelpItemClicked(int Position) {
        Fragment fragment = new HelpRequestDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", "HelpRequest");
        bundle.putString("productType", new SharedPreferenceData(getContext()).getSectionType());
        bundle.putSerializable("HelpRequestDetails", helpDataBean.get(Position));
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void OnFavoriteClicked(int Position) {
        if (NetworkCheck.isConnected(getContext())) {
            presenter.AddToWishlistCall(getContext(), dataBean.get(Position).getId(), ((MainActivity) getActivity()).profileData.getUser_id(), "D");
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
    }

    @Override
    public void onProductSucess(ProductResBean item) {
        if (item.isSuccess()){
            dataBean.addAll(item.getData());
            bookListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddToWishlistSuccess(AddToWishlistResBean item) {
        if (item.isSuccess()){
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "No Products Found", Toast.LENGTH_LONG).show();
    }
}
