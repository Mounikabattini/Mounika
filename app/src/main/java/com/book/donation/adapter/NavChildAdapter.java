package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;

import java.util.ArrayList;
import java.util.List;

public class NavChildAdapter extends RecyclerView.Adapter<NavChildAdapter.MyViewHolder> {

    Context context;
    private List<String> navItems = new ArrayList<>();
    private ItemClickListener itemClickListener;
    int parentPosition;

    public interface ItemClickListener{
        void onChildItemClick(int Position, String buttonType);
    }


    public NavChildAdapter(Context context, List<String> navItems, int parentPosition, ItemClickListener clickListener) {
        this.context = context;
        this.navItems = navItems;
        this.parentPosition = parentPosition;
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_sub_child_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        viewHolder.txtNavChildName.setText(navItems.get(position));
        viewHolder.consNavChildItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onChildItemClick(parentPosition, navItems.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return navItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout consNavChildItem;
        TextView txtNavChildName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            consNavChildItem = itemView.findViewById(R.id.consNavChildItem);
            txtNavChildName = itemView.findViewById(R.id.txtNavChildName);


        }
    }
}
