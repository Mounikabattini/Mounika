package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;

import java.util.ArrayList;

public class AskHelpSubCategoryAdapter extends RecyclerView.Adapter<AskHelpSubCategoryAdapter.MyViewHolder>{

    ArrayList<AskHelpSubCategoryResBean.DataItem> ItemList;
    private Context context;
    private AskHelpItemClickListener listener;
    HelpRequestSubCategoryDataAdapter helpRequestSubCategoryDataAdapter;

    public interface AskHelpItemClickListener{
        public void OnAskHelpViewAllClicked(int Position);
    }

    public AskHelpSubCategoryAdapter(Context context, ArrayList<AskHelpSubCategoryResBean.DataItem> ItemList, AskHelpItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(ArrayList<AskHelpSubCategoryResBean.DataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtSubCategoryTitle.setText(ItemList.get(position).getSubCategoryName());

        holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnAskHelpViewAllClicked(position);
            }
        });


        ArrayList<AskHelpSubCategoryResBean.AskdataItem> HelpRequestList = new ArrayList<>();
        HelpRequestList.addAll(ItemList.get(position).getAskdata());
        helpRequestSubCategoryDataAdapter = new HelpRequestSubCategoryDataAdapter(context, HelpRequestList);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
        holder.rvSubCategoryData.setLayoutManager(gridLayoutManager1);
        holder.rvSubCategoryData.setItemAnimator(new DefaultItemAnimator());
        holder.rvSubCategoryData.setAdapter(helpRequestSubCategoryDataAdapter);
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardRoot;
        public TextView txtSubCategoryTitle, txtViewAll;
        public RecyclerView rvSubCategoryData;

        public MyViewHolder(View view) {
            super(view);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtSubCategoryTitle = view.findViewById(R.id.txtSubCategoryTitle);
            txtViewAll = view.findViewById(R.id.txtViewAll);
            rvSubCategoryData = view.findViewById(R.id.rvSubCategoryData);
        }
    }
}







