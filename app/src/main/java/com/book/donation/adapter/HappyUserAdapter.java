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
import com.book.donation.apicalls.model.HappyDataResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HappyUserAdapter extends RecyclerView.Adapter<HappyUserAdapter.MyViewHolder>{

    ArrayList<HappyDataResBean.DataItem> ItemList;
    private Context context;
    ItemClickListener listener;

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public HappyUserAdapter(Context context, ArrayList<HappyDataResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_happy_section, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgPerson);
        holder.txtExperience.setText(ItemList.get(position).getTitle());

        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
        //return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPerson;
        public TextView txtExperience;
        public ConstraintLayout cardRoot;

        public MyViewHolder(View view) {
            super(view);
            imgPerson = (ImageView) view.findViewById(R.id.imgPerson);
            txtExperience = view.findViewById(R.id.txtExperience);
            cardRoot = view.findViewById(R.id.consRoot);
        }
    }
}







