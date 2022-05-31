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
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.fragment.HelpRequestDetailsFragment;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HelpRequestSubCategoryDataAdapter extends RecyclerView.Adapter<HelpRequestSubCategoryDataAdapter.MyViewHolder>{

    ArrayList<AskHelpSubCategoryResBean.AskdataItem> ItemList;
    private Context context;

    public HelpRequestSubCategoryDataAdapter(Context context, ArrayList<AskHelpSubCategoryResBean.AskdataItem> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_school_books, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtItemName.setText(ItemList.get(position).getDescription());
        holder.txtAddressDistance.setText(ItemList.get(position).getAddress());
        if (ItemList.get(position).getImage()!=null){
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
            holder.imgItem.setVisibility(View.VISIBLE);
            holder.imgHelpRequest.setVisibility(View.GONE);
        }else {
            holder.imgItem.setVisibility(View.GONE);
            holder.imgHelpRequest.setVisibility(View.VISIBLE);
        }
        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HelpRequestDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from", "HelpRequest");
                bundle.putString("productType", new SharedPreferenceData(context).getSectionType());
                bundle.putSerializable("HelpRequestDetails", ItemList.get(position));
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(context, fragment);
            }
        });

        if (ItemList.get(position).getDate()!=null)
        holder.txtDate.setText(ItemList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgItem, imgHelpRequest;
        public CardView cardRoot;
        public TextView txtItemName, txtAddressDistance, txtDate;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            imgHelpRequest = view.findViewById(R.id.imgHelpRequest);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtAddressDistance = view.findViewById(R.id.txtAddressDistance);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}








