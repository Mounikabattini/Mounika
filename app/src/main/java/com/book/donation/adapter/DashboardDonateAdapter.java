package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.DonatItemDasListResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardDonateAdapter extends RecyclerView.Adapter<DashboardDonateAdapter.MyViewHolder>{

    ArrayList<DonatItemDasListResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int Position);
        void OnItemDelete(int Position);
    }

    public DashboardDonateAdapter(Context context, ArrayList<DonatItemDasListResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(ArrayList<DonatItemDasListResBean.DataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_dashboard_donate, parent, false);

        return new DashboardDonateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgBook);
        holder.txtBookName.setText(ItemList.get(position).getProductName());
        holder.txtDate.setText(ItemList.get(position).getCreatedAt());
        holder.txtMsg.setText(ItemList.get(position).getDescription());
        holder.txtClaimedQty.setText(""+ItemList.get(position).getTotalClaim());
        if (ItemList.get(position).getIsBookDonated().equalsIgnoreCase("1")){
            holder.imgClaimed.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.GONE);
        }else {
            holder.imgClaimed.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.VISIBLE);
        }

        holder.consRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook, imgClaimed, imgDelete;
        public ConstraintLayout consRoot;
        public TextView txtBookName, txtMsg, txtDate, txtClaimedQty;

        public MyViewHolder(View view) {
            super(view);
            imgBook = view.findViewById(R.id.imgBook);
            imgClaimed = view.findViewById(R.id.imgClaimed);
            imgDelete = view.findViewById(R.id.imgDelete);
            consRoot = view.findViewById(R.id.consRoot);
            txtMsg = view.findViewById(R.id.txtMsg);
            txtBookName = view.findViewById(R.id.txtBookName);
            txtDate = view.findViewById(R.id.txtDate);
            txtClaimedQty = view.findViewById(R.id.txtClaimedQty);
        }
    }
}










