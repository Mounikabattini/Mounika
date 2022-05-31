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
import com.book.donation.apicalls.model.EventResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategroyEventAdapter extends RecyclerView.Adapter<CategroyEventAdapter.MyViewHolder>{

    ArrayList<EventResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        public void OnEventItemClicked(int Position);
    }

    public CategroyEventAdapter(Context context, ArrayList<EventResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_category_event, parent, false);

        return new CategroyEventAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getEventImage()).into(holder.imgItem);
        holder.txtItemName.setText(ItemList.get(position).getEventName());
        holder.txtDateTime.setText(ItemList.get(position).getEventDate());
        holder.txtDescription.setText(ItemList.get(position).getDescription());

        holder.consRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnEventItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgItem;
        public ConstraintLayout consRoot;
        public TextView txtItemName, txtDateTime, txtDescription;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            consRoot = view.findViewById(R.id.consRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtDateTime = view.findViewById(R.id.txtDateTime);
            txtDescription = view.findViewById(R.id.txtDescription);
        }
    }
}






