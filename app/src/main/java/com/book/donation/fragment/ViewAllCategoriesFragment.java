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
import com.book.donation.adapter.CategoryAdapter;
import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.databinding.FragmentViewallCategoriesBinding;
import com.book.donation.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewAllCategoriesFragment extends Fragment implements CategoryAdapter.ItemClickListener {

    FragmentViewallCategoriesBinding binding;

    String from;
    CategoryResBean categoryDataBean;
    List<CategoryResBean.DataItem> categoryData = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_viewall_categories, container, false);
        from = getArguments().getString("from");
        categoryDataBean = (CategoryResBean) getArguments().getSerializable("categoryData");
        categoryData.addAll(categoryDataBean.getData());

        categoryAdapter = new CategoryAdapter(getActivity(), categoryData,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        binding.recyCategories.setLayoutManager(gridLayoutManager1);
        binding.recyCategories.setItemAnimator(new DefaultItemAnimator());
        binding.recyCategories.setAdapter(categoryAdapter);

        return binding.getRoot();
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
}
