package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.ClaimedPersonListResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClaimPersonsListAdapter extends RecyclerView.Adapter<ClaimPersonsListAdapter.MyViewHolder>{

    ArrayList<ClaimedPersonListResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        void OnItemClicked(int Position);
        void OnClaimedAccepted(int position);
    }

    public ClaimPersonsListAdapter(Context context, ArrayList<ClaimedPersonListResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_claim_person, parent, false);

        return new ClaimPersonsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getProfilePic()).into(holder.imgPerson);
        holder.txtClaimedPersonName.setText("Name : "+ItemList.get(position).getName());
        holder.txtAddress.setText("Address : "+ItemList.get(position).getAddress());
        holder.txtContact.setText("Contact : "+ItemList.get(position).getMobile());

        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });

        if (ItemList.get(position).getIsClaimed().equalsIgnoreCase("1")) {
            holder.txtClaimAccepted.setClickable(false);
            holder.txtClaimAccepted.setText(context.getResources().getString(R.string.claim_accepted));
        }else if (ItemList.get(position).getIsClaimed().equalsIgnoreCase("2")){
            holder.txtClaimAccepted.setVisibility(View.GONE);
        }else {
            holder.txtClaimAccepted.setText(context.getResources().getString(R.string.claim_accept));
        }

        holder.txtClaimAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtClaimAccepted.setText(context.getResources().getString(R.string.claim_accepted));
                listener.OnClaimedAccepted(position);
            }
        });

    }

    @Override
    public int getItemCount() {
         return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPerson;
        public ConstraintLayout consRoot;
        public AppCompatImageView imgChat;
        public TextView txtClaimedPersonName, txtAddress, txtContact, txtClaimAccepted;

        public MyViewHolder(View view) {
            super(view);
            imgPerson = view.findViewById(R.id.imgPerson);
            consRoot = view.findViewById(R.id.consRoot);
            imgChat = view.findViewById(R.id.imgChat);
            txtClaimedPersonName = view.findViewById(R.id.txtClaimedPersonName);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtContact = view.findViewById(R.id.txtContact);
            txtClaimAccepted = view.findViewById(R.id.txtClaimAccepted);
        }
    }
}










