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
import com.book.donation.apicalls.model.MyHelpRequestResBean;

import java.util.ArrayList;

public class DashboardAskHelpAdapter extends RecyclerView.Adapter<DashboardAskHelpAdapter.MyViewHolder>{

    ArrayList<MyHelpRequestResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int Position);
        void OnItemDelete(int Position);
    }

    public DashboardAskHelpAdapter(Context context, ArrayList<MyHelpRequestResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(ArrayList<MyHelpRequestResBean.DataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_dashboard_askhelp, parent, false);

        return new DashboardAskHelpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtRequestorName.setText(ItemList.get(position).getUserName());
        holder.txtMsg.setText(ItemList.get(position).getDescription());
        holder.txtCreatedAt.setText(ItemList.get(position).getCreatedAt());

        if (ItemList.get(position).getIsRequestFullfill().equalsIgnoreCase("1")){
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
        public ConstraintLayout consRoot;
        public TextView txtRequestorName, txtMsg, txtCreatedAt;
        public ImageView imgDelete, imgClaimed;

        public MyViewHolder(View view) {
            super(view);
            consRoot = view.findViewById(R.id.consRoot);
            txtRequestorName = view.findViewById(R.id.txtRequestorName);
            txtMsg = view.findViewById(R.id.txtMsg);
            txtCreatedAt = view.findViewById(R.id.txtCreatedAt);
            imgDelete = view.findViewById(R.id.imgDelete);
            imgClaimed = view.findViewById(R.id.imgClaimed);
        }
    }
}











