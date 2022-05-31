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
import java.util.List;

public class RequestedPersonListAdapter extends RecyclerView.Adapter<RequestedPersonListAdapter.MyViewHolder>{

    List<String> ItemList;
    private Context context;
    private ItemClickListener listener;

    public interface ItemClickListener{
        public void OnItemClicked(int Position);
    }

    public RequestedPersonListAdapter(Context context, List<String> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_requested_person, parent, false);

        return new RequestedPersonListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        /*Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getProfilePic()).into(holder.imgPerson);
        holder.txtClaimedPersonName.setText("Name : "+ItemList.get(position).getName());
        holder.txtAddress.setText("Address : "+ItemList.get(position).getAddress());
        holder.txtContact.setText("Contact : "+ItemList.get(position).getMobile());*/
        holder.consRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
        //return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPerson;
        public ConstraintLayout consRoot;
        public AppCompatImageView imgChat;
        public TextView txtClaimedPersonName, txtAddress, txtContact;

        public MyViewHolder(View view) {
            super(view);
            imgPerson = view.findViewById(R.id.imgPerson);
            consRoot = view.findViewById(R.id.consRoot);
            imgChat = view.findViewById(R.id.imgChat);
            txtClaimedPersonName = view.findViewById(R.id.txtClaimedPersonName);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtContact = view.findViewById(R.id.txtContact);
        }
    }
}











