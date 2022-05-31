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
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecentItemsAdapter extends RecyclerView.Adapter<RecentItemsAdapter.MyViewHolder>{

    ArrayList<RecentItemsResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;
    private String from;

    public interface ItemClickListener{
        public void OnRecentItemClicked(int Position);
    }

    public RecentItemsAdapter(Context context, ArrayList<RecentItemsResBean.DataItem> ItemList, String from, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.from = from;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_recent_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (from.equalsIgnoreCase("DonorShelf")){
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).
                    placeholder(context.getResources().getDrawable(R.drawable.ic_no_data_found)).into(holder.imgBook);
            holder.txtBookName.setText(ItemList.get(position).getProductName());
        }else {
            if (ItemList.get(position).getImage()!=null){
                Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgBook);
            }else {
                holder.imgBook.setVisibility(View.GONE);
                holder.imgHelp.setVisibility(View.VISIBLE);
                holder.imgHelp.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_help_board));
            }
            holder.txtBookName.setText(ItemList.get(position).getUserName());
        }

        holder.txtDate.setText(ItemList.get(position).getCreatedAt());
        holder.txtBookDetails.setText(ItemList.get(position).getDescription());
        holder.txtAddressDistance.setText(ItemList.get(position).getAddress());

        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnRecentItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ItemList.size()>9){
            return 9;
        }else {
            return ItemList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook, imgHelp;
        public CardView cardRoot;
        public TextView txtBookName, txtDate, txtBookDetails, txtAddressDistance;

        public MyViewHolder(View view) {
            super(view);
            imgBook = (ImageView) view.findViewById(R.id.imgBook);
            imgHelp = (ImageView) view.findViewById(R.id.imgHelp);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtBookName = view.findViewById(R.id.txtBookName);
            txtDate = view.findViewById(R.id.txtDate);
            txtBookDetails = view.findViewById(R.id.txtBookDetails);
            txtAddressDistance = view.findViewById(R.id.txtAddressDistance);
        }
    }
}






