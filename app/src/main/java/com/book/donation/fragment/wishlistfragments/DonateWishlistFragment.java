package com.book.donation.fragment.wishlistfragments;

import android.content.Context;
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
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.WishListAdapter;
import com.book.donation.apicalls.Presenter.WishlistPresenter;
import com.book.donation.apicalls.View.IWishlistView;
import com.book.donation.apicalls.model.ResponseResBean;
import com.book.donation.apicalls.model.WishlistResBean;
import com.book.donation.databinding.FragmentDonateWishlistBinding;
import com.book.donation.fragment.BookDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class DonateWishlistFragment extends Fragment implements WishListAdapter.ItemClickListener, IWishlistView {

    FragmentDonateWishlistBinding binding;
    WishListAdapter wishListAdapter;
    ArrayList<WishlistResBean.DataItem> dataBean = new ArrayList<>();

    SharedPreferenceData profileData;
    WishlistPresenter presenter;
    String type;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_donate_wishlist, container, false);

        presenter = new WishlistPresenter();
        presenter.setView(this);
        profileData = new SharedPreferenceData(getContext());
        type = getArguments().getString("type");

        wishListAdapter = new WishListAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvWishList.setLayoutManager(gridLayoutManager1);
        binding.rvWishList.setItemAnimator(new DefaultItemAnimator());
        binding.rvWishList.setAdapter(wishListAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.WishlistCall(getContext(), profileData.getUser_id(), type);
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
        //presenter.TestCall(getContext(), "iHhCd9zIWPKGeL8eVIBl2o7AxlRpz49ueHoeK0Sj");
        //presenter.WishlistCall(getContext(), "1");

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.GONE);
        ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.VISIBLE);

        Fragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_id", ""+dataBean.get(Position).getId());
        bundle.putString("product_type", "D");
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);

    }

    @Override
    public void onWishlistSucess(WishlistResBean item) {
        if (item.isSuccess()){
            dataBean.addAll(item.getData());
            wishListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTestSucess(ResponseResBean item) {
        List<ResponseResBean.CountriesItem> data =  item.getCountries();
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        dataBean.clear();
        wishListAdapter.notifyDataSetChanged();
        binding.txtNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setText("You haven't add any item in wishlist yet");
        //Toast.makeText(getContext(), "No wish list found", Toast.LENGTH_LONG).show();
    }
}
