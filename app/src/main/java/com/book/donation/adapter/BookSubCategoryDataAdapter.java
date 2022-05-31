package com.book.donation.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.SubCategoryResBean;
import com.book.donation.fragment.BookDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookSubCategoryDataAdapter extends RecyclerView.Adapter<BookSubCategoryDataAdapter.MyViewHolder>{

    ArrayList<SubCategoryResBean.ProductItem> ItemList;
    private Context context;

    public BookSubCategoryDataAdapter(Context context, ArrayList<SubCategoryResBean.ProductItem> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_school_books, parent, false);

        return new BookSubCategoryDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BookDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("product_id", ""+ItemList.get(position).getId());
                bundle.putString("product_type", new SharedPreferenceData(context).getSectionType());
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(context, fragment);
            }
        });
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
        holder.txtItemName.setText(ItemList.get(position).getProductName());
        holder.txtAddressDistance.setText(ItemList.get(position).getAddress());
        holder.txtDate.setText(ItemList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgItem;
        public CardView cardRoot;
        public TextView txtItemName, txtAddressDistance, txtDate;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtAddressDistance = view.findViewById(R.id.txtAddressDistance);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}







