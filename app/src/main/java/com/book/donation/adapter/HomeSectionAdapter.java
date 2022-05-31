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
import com.book.donation.apicalls.model.HomeSectionResBean;

import java.util.ArrayList;

public class HomeSectionAdapter extends RecyclerView.Adapter<HomeSectionAdapter.MyViewHolder>{

    ArrayList<HomeSectionResBean> ItemList;
    private Context context;
    private ItemClickListener listener;
    private String from;

    public interface ItemClickListener{
        public void OnItemClicked(int Position);
    }

    public HomeSectionAdapter(Context context, ArrayList<HomeSectionResBean> ItemList, String from, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.from = from;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_home_section, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtTitle.setText(ItemList.get(position).getTitle());
        holder.txtMsg.setText(ItemList.get(position).getMsg());
        holder.imgSection.setImageDrawable(context.getResources().getDrawable(ItemList.get(position).getImage()));
        holder.consRoot.setOnClickListener(new View.OnClickListener() {
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
        public ImageView imgSection;
        public ConstraintLayout consRoot;
        public TextView txtTitle, txtMsg;

        public MyViewHolder(View view) {
            super(view);
            imgSection = view.findViewById(R.id.imgSection);
            consRoot = view.findViewById(R.id.consRoot);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtMsg = view.findViewById(R.id.txtMsg);
        }
    }
}








