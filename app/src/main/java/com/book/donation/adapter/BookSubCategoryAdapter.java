package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.model.SubCategoryResBean;

import java.util.ArrayList;

public class BookSubCategoryAdapter extends RecyclerView.Adapter<BookSubCategoryAdapter.MyViewHolder>{

    ArrayList<SubCategoryResBean.CategoryItem> ItemList;
    private Context context;
    private ItemClickListener listener;
    BookSubCategoryDataAdapter bookSubCategoryDataAdapter;
    ArrayList<SubCategoryResBean.ProductItem> ProductList = new ArrayList<>();

    public interface ItemClickListener{
        public void OnViewAllClicked(int Position);
    }

    public BookSubCategoryAdapter(Context context, ArrayList<SubCategoryResBean.CategoryItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(ArrayList<SubCategoryResBean.CategoryItem> list){
        ItemList = list;
        notifyDataSetChanged();
        bookSubCategoryDataAdapter.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ProductList.clear();
        ProductList.addAll(ItemList.get(position).getProduct());
        if (ProductList.size()==0){
            holder.consSubCategory.setVisibility(View.GONE);
        }else {
            holder.txtSubCategoryTitle.setText(ItemList.get(position).getTitle());
            holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnViewAllClicked(position);
                }
            });

            bookSubCategoryDataAdapter = new BookSubCategoryDataAdapter(context, ProductList);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
            holder.rvSubCategoryData.setLayoutManager(gridLayoutManager1);
            holder.rvSubCategoryData.setItemAnimator(new DefaultItemAnimator());
            holder.rvSubCategoryData.setAdapter(bookSubCategoryDataAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardRoot;
        public TextView txtSubCategoryTitle, txtViewAll;
        public RecyclerView rvSubCategoryData;
        public ConstraintLayout consSubCategory;

        public MyViewHolder(View view) {
            super(view);
            cardRoot = view.findViewById(R.id.cardRoot);
            txtSubCategoryTitle = view.findViewById(R.id.txtSubCategoryTitle);
            txtViewAll = view.findViewById(R.id.txtViewAll);
            rvSubCategoryData = view.findViewById(R.id.rvSubCategoryData);
            consSubCategory = view.findViewById(R.id.consSubCategory);
        }
    }
}







