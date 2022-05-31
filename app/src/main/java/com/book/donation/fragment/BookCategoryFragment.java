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

import com.book.donation.activities.EventDetailsActivity;
import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.BookCategoryAdapter;
import com.book.donation.adapter.CategroyEventAdapter;
import com.book.donation.adapter.RecentItemsAdapter;
import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.apicalls.Presenter.CategoryPresenter;
import com.book.donation.apicalls.View.ICategoryView;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.book.donation.databinding.FragmentBookCategoryBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;
import java.util.List;

public class BookCategoryFragment extends Fragment implements BookCategoryAdapter.ItemClickListener, CategroyEventAdapter.ItemClickListener
        , ICategoryView, RecentItemsAdapter.ItemClickListener {

    FragmentBookCategoryBinding binding;
    BookCategoryAdapter bookCategoryAdapter;
    CategroyEventAdapter eventAdapter;
    RecentItemsAdapter recentItemsAdapter;

    ArrayList<EventResBean.DataItem> dataBean = new ArrayList<>();
    List<CategoryResBean.DataItem> categoryData = new ArrayList<>();
    ArrayList<RecentItemsResBean.DataItem> recentData = new ArrayList<>();

    CategoryResBean categoryDataBean;
    String from, type="A";

    CategoryPresenter presenter;
    boolean isActivityOpen = false;
    //LayoutAnimationController animation, animation2;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_category, container, false);
        int resId = R.anim.layout_animation;
        int resId1 = R.anim.layout_animation1;
        //animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        //animation2 = AnimationUtils.loadLayoutAnimation(getContext(), resId1);

        from = getArguments().getString("from");
        presenter = new CategoryPresenter();
        presenter.setView(this);
        binding.consRoot.setVisibility(View.GONE);

        if (!from.equalsIgnoreCase("DonorShelf"))
        binding.txtTitle.setText("Help Requests");

        binding.txtViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ViewAllCategoriesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from", from);
                bundle.putSerializable("categoryData", categoryDataBean);
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
            }
        });

        bookCategoryAdapter = new BookCategoryAdapter(getActivity(), categoryData,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        binding.rvCatregory.setLayoutManager(gridLayoutManager1);
        binding.rvCatregory.setItemAnimator(new DefaultItemAnimator());
        binding.rvCatregory.setAdapter(bookCategoryAdapter);

        /*eventAdapter = new CategroyEventAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvEvent.setLayoutManager(gridLayoutManager2);
        binding.rvEvent.setItemAnimator(new DefaultItemAnimator());
        binding.rvEvent.setAdapter(eventAdapter);*/

        recentItemsAdapter = new RecentItemsAdapter(getActivity(), recentData, from,this);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        binding.rvRecentDonated.setLayoutManager(gridLayoutManager3);
        binding.rvRecentDonated.setItemAnimator(new DefaultItemAnimator());
        binding.rvRecentDonated.setAdapter(recentItemsAdapter);

        /*((MainActivity)getActivity()).binding.imgHeadingLogo.setVisibility(View.GONE);
        ((MainActivity)getActivity()).binding.txtHeading.setVisibility(View.VISIBLE);*/

        if (from.equalsIgnoreCase("DonorShelf")) {
            type = "D";
        }else {
            binding.txtRecentDonatedItems.setText(R.string.recent_request_list);
        }

        if (NetworkCheck.isConnected(getContext())) {
            presenter.CategoryCall(getActivity(), type);
            //((MainActivity)getActivity()).binding.txtHeading.setText(getResources().getString(R.string.donate_item_category));
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        binding.txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("sectionType", type);
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isActivityOpen) {
            if (NetworkCheck.isConnected(getContext())) {
                presenter.CategoryCall(getActivity(), type);
                //((MainActivity)getActivity()).binding.txtHeading.setText(getResources().getString(R.string.donate_item_category));
            } else {
                ((MainActivity) getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
            }
        }
    }



    @Override
    public void OnItemClicked(int Position) {
        Fragment fragment = new BookSubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("categoryId", ""+categoryData.get(Position).getId());
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    void filter(String text){
        List<CategoryResBean.DataItem> temp = new ArrayList();
        for(CategoryResBean.DataItem d: categoryData){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getTitle().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        bookCategoryAdapter.updateList(temp);
    }

    @Override
    public void OnEventItemClicked(int Position) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("eventData", dataBean.get(Position));
        getActivity().startActivity(intent);
    }

    @Override
    public void OnRecentItemClicked(int Position) {
        if (from.equalsIgnoreCase("DonorShelf")){
            Fragment fragment = new BookDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("product_id", ""+recentData.get(Position).getId());
            bundle.putString("product_type", new SharedPreferenceData(getContext()).getSectionType());
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }else {
            Fragment fragment = new HelpRequestDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("from", "RecentHelpRequest");
            bundle.putString("productType", new SharedPreferenceData(getContext()).getSectionType());
            bundle.putSerializable("HelpRequestDetails", recentData.get(Position));
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }
    }

    @Override
    public void onCategorySucess(CategoryResBean item) {
        if (item.isSuccess()){
            binding.consRoot.setVisibility(View.VISIBLE);
            categoryData.clear();
            categoryData.addAll(item.getData());
            categoryDataBean = item;
            bookCategoryAdapter.notifyDataSetChanged();
            if (from.equalsIgnoreCase("DonorShelf")) {
                presenter.RecentItemsCall(getActivity(), "D", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), (new SharedPreferenceData(getActivity())).getPINCODE());
            }else {
                presenter.RecentItemsCall(getActivity(), "A", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), (new SharedPreferenceData(getActivity())).getPINCODE());
            }
            //binding.rvCatregory.setLayoutAnimation(animation2);
        }
    }

    @Override
    public void onEventSucess(EventResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecentItemsSucess(RecentItemsResBean item) {
        if (item.isSuccess()){

            isActivityOpen = true;
            recentData.clear();
            recentData.addAll(item.getData());
            recentItemsAdapter.notifyDataSetChanged();
            //binding.rvRecentDonated.setLayoutAnimation(animation);
            if (item.getData().size()<1){
                binding.imgNoData.setVisibility(View.VISIBLE);
            }
            //presenter.EventCall(getContext());
        }else {
            recentData.clear();
            recentItemsAdapter.notifyDataSetChanged();
            binding.imgNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
