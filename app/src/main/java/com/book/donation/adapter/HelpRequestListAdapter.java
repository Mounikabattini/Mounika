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
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;

import java.util.ArrayList;

public class HelpRequestListAdapter extends RecyclerView.Adapter<HelpRequestListAdapter.MyViewHolder>{

    ArrayList<AskHelpSubCategoryResBean.AskdataItem> ItemList;
    private Context context;
    private AskHelpItemClickListener listener;
    private String from;

    public interface AskHelpItemClickListener{
        public void OnAskHelpItemClicked(int Position);
    }

    public HelpRequestListAdapter(Context context, ArrayList<AskHelpSubCategoryResBean.AskdataItem> ItemList, String from, AskHelpItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.from = from;
    }

    public void updateList(ArrayList<AskHelpSubCategoryResBean.AskdataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_book_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnAskHelpItemClicked(position);
            }
        });
        if (!from.equalsIgnoreCase("DonorShelf")) {
            holder.imgLike.setVisibility(View.GONE);
        }/*else {
            if (ItemList.get(position).getIsLike().equalsIgnoreCase("1")) {
                holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_liked));
            }else {
                holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
            }
        }*/
        holder.txtBookName.setText(ItemList.get(position).getUserName());
        holder.txtBookDetails.setText(ItemList.get(position).getDescription());
        holder.txtDate.setText(ItemList.get(position).getCreatedAt());
        holder.txtAddressDistance.setText(ItemList.get(position).getAddress());
        holder.imgBook.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_help_request));

        /*holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ItemList.get(position).getIsLike().equalsIgnoreCase("1")) {
                    ItemList.get(position).setIsLike("0");
                    holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
                }else {
                    ItemList.get(position).setIsLike("1");
                    holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_liked));
                }
                listener.OnFavoriteClicked(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook, imgLike;
        public CardView cardRoot;
        public TextView txtBookName, txtBookDetails, txtAddressDistance, txtDate;

        public MyViewHolder(View view) {
            super(view);
            imgBook = view.findViewById(R.id.imgBook);
            cardRoot = view.findViewById(R.id.cardRoot);
            imgLike = view.findViewById(R.id.imgLike);
            txtBookName = view.findViewById(R.id.txtBookName);
            txtBookDetails = view.findViewById(R.id.txtBookDetails);
            txtAddressDistance = view.findViewById(R.id.txtAddressDistance);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}









