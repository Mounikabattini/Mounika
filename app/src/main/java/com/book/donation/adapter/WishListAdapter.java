package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.WishlistResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder>{

    ArrayList<WishlistResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        public void OnItemClicked(int Position);
    }

    public WishListAdapter(Context context, ArrayList<WishlistResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_lay_wish_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgBook);
        if (ItemList.get(position).getUserName()!=null){
            holder.txtBookName.setText(ItemList.get(position).getUserName());
        }else {
            holder.txtBookName.setText(ItemList.get(position).getProductName());
        }
        holder.txtBookDetails.setText(ItemList.get(position).getDescription());
        holder.txtAddressDistance.setText(ItemList.get(position).getAddress());
        holder.txtDate.setText(ItemList.get(position).getCreatedAt());

        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook;
        public CardView cardRoot;
        public TextView txtBookName, txtBookDetails, txtAddressDistance, txtDate;

        public MyViewHolder(View view) {
            super(view);
            imgBook = view.findViewById(R.id.imgBook);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtBookName = view.findViewById(R.id.txtBookName);
            txtBookDetails = view.findViewById(R.id.txtBookDetails);
            txtAddressDistance = view.findViewById(R.id.txtAddressDistance);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}









