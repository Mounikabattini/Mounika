package com.book.donation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;

import java.util.ArrayList;
import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder> implements NavChildAdapter.ItemClickListener {

    Context context;
    private String[] navItems;
    private int[] navImages;
    private ItemClickListener itemClickListener;
    boolean isDashboardChildOpen = false, isClaimOpen = false, isChatChildOpen = false;
    //private String[] navDashboardItems = {"My Donate", "My Request", "My Claim"};
    //private String[] navChatItems = {"All Chat", "Donate Chat", "Request Chat"};
    private List<String> navDashboardItems = new ArrayList<>();
    private List<String> navClaimItems = new ArrayList<>();
    private List<String> navChatItems = new ArrayList<>();

    @Override
    public void onChildItemClick(int Position, String buttonType) {
        itemClickListener.onChildItemClick(Position, buttonType);
    }

    public interface ItemClickListener{
        void onItemClick(int Position);
        void onChildItemClick(int position, String buttonType);
    }

    public NavAdapter(Context context, String[] navItems, int[] navImages, ItemClickListener clickListener) {
        this.context = context;
        this.navItems = navItems;
        this.navImages = navImages;
        this.itemClickListener = clickListener;

        navDashboardItems.add("Donation requests");
        //navDashboardItems.add("My Help Request");

        /*navClaimItems.add("Donated items claim");
        navClaimItems.add("Help Request claim");*/

        //navChatItems.add("All Chats");
        /*navChatItems.add("My Donation Chats");
        navChatItems.add("My Help Request Chats");*/
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_child_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.txtNavName.setText(navItems[position]);
        viewHolder.imgNavIcon.setImageDrawable(context.getResources().getDrawable(navImages[position]));

        //Load subMenuItems
        List<String> data = new ArrayList<>();
        NavChildAdapter navChildAdapter = new NavChildAdapter(context, data, position, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        viewHolder.rvChildItemList.setLayoutManager(layoutManager);
        viewHolder.rvChildItemList.setItemAnimator(new DefaultItemAnimator());
        viewHolder.rvChildItemList.setAdapter(navChildAdapter);

        //if (position==2||position==5){
        if (position==2/*||position==3||position==4*/){
            viewHolder.imgArrow.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgArrow.setVisibility(View.GONE);
        }

        viewHolder.layNavItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==2){

                    if (isDashboardChildOpen) {
                        viewHolder.imgArrow.setRotation(0);
                        data.clear();
                        navChildAdapter.notifyDataSetChanged();
                        isDashboardChildOpen = false;
                    }else {
                        viewHolder.imgArrow.setRotation(180);
                        data.addAll(navDashboardItems);
                        navChildAdapter.notifyDataSetChanged();
                        isDashboardChildOpen = true;
                    }
                }/*else if (position==3){
                    if (isClaimOpen) {
                        viewHolder.imgArrow.setRotation(0);
                        data.clear();
                        navChildAdapter.notifyDataSetChanged();
                        isClaimOpen = false;
                    }else {
                        viewHolder.imgArrow.setRotation(180);
                        data.addAll(navClaimItems);
                        navChildAdapter.notifyDataSetChanged();
                        isClaimOpen = true;
                    }
                } else if (position==4){

                    if (isChatChildOpen) {
                        viewHolder.imgArrow.setRotation(0);
                        data.clear();
                        navChildAdapter.notifyDataSetChanged();
                        isChatChildOpen = false;
                    }else {
                        viewHolder.imgArrow.setRotation(180);
                        data.addAll(navChatItems);
                        navChildAdapter.notifyDataSetChanged();
                        isChatChildOpen = true;
                    }
                }*/else {
                    itemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return navItems.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layNavItem;
        TextView txtNavName;
        ImageView imgNavIcon, imgArrow;
        RecyclerView rvChildItemList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layNavItem = itemView.findViewById(R.id.layNavItem);
            rvChildItemList = itemView.findViewById(R.id.rvChildItemList);
            txtNavName = itemView.findViewById(R.id.txtNavName);
            imgNavIcon = itemView.findViewById(R.id.imgNavIcon);
            imgArrow = itemView.findViewById(R.id.imgArrow);

        }
    }
}




