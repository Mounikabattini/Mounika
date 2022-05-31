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
import com.book.donation.apicalls.model.CategoryResBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    List<CategoryResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        public void OnItemClicked(int Position);
    }

    public CategoryAdapter(Context context, List<CategoryResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(List<CategoryResBean.DataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgItem);
        holder.txtItemName.setText(ItemList.get(position).getTitle());
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
        public ImageView imgItem;
        public CardView cardRoot;
        public TextView txtItemName;

        public MyViewHolder(View view) {
            super(view);
            imgItem =  view.findViewById(R.id.imgItem);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
        }
    }
}






