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
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.MyClaimItemListResBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyClaimItemListAdapter extends RecyclerView.Adapter<MyClaimItemListAdapter.MyViewHolder>{

    ArrayList<MyClaimItemListResBean.DataItem> ItemList;
    private Context context;
    private RejectClickListener listener;

    public interface RejectClickListener{
        void OnRejectClicked(int Position);
        void OnChatClicked(int Position);
        void ClickedForDetails(int Position);
        void ItemReceived(int Position);
    }


    public MyClaimItemListAdapter(Context context, ArrayList<MyClaimItemListResBean.DataItem> ItemList, RejectClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(ArrayList<MyClaimItemListResBean.DataItem> list){
        ItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_dashboard_claim, parent, false);

        return new MyClaimItemListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getImage()).into(holder.imgBook);
        holder.txtBookName.setText(ItemList.get(position).getProductName());
        holder.txtMsg.setText(ItemList.get(position).getDescription());
        holder.txtNumber.setText("Contacts : "+ItemList.get(position).getOwnerMobile());

        if (ItemList.get(position).getClaimBy().equalsIgnoreCase(((MainActivity)context).profileData.getUser_id())){
            holder.txtReject.setVisibility(View.GONE);
            holder.imgCongratulations.setVisibility(View.VISIBLE);
            holder.txtReceived.setVisibility(View.VISIBLE);
            if (ItemList.get(position).getIs_recieved().equalsIgnoreCase("1")){
                holder.txtReceived.setText("Complete");
            }else {
                holder.txtReceived.setText("Received");
            }
        }else {
            holder.txtReject.setVisibility(View.VISIBLE);
            holder.imgCongratulations.setVisibility(View.GONE);
            holder.txtReceived.setVisibility(View.GONE);
        }

        holder.txtReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnRejectClicked(position);
            }
        });

        holder.txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnChatClicked(position);
            }
        });

        holder.consRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ClickedForDetails(position);
            }
        });

        holder.txtReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ItemList.get(position).getIs_recieved().equalsIgnoreCase("0")) {
                    listener.ItemReceived(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook, imgCongratulations;
        public ConstraintLayout consRoot;
        public TextView txtBookName, txtMsg, txtNumber, txtReject, txtChat, txtReceived;

        public MyViewHolder(View view) {
            super(view);
            imgBook = view.findViewById(R.id.imgBook);
            consRoot = view.findViewById(R.id.consRoot);
            txtBookName = view.findViewById(R.id.txtBookName);
            txtMsg = view.findViewById(R.id.txtMsg);
            txtNumber = view.findViewById(R.id.txtNumber);
            txtReject = view.findViewById(R.id.txtReject);
            imgCongratulations = view.findViewById(R.id.imgCongratulations);
            txtChat = view.findViewById(R.id.txtChat);
            txtReceived = view.findViewById(R.id.txtReceived);
        }
    }
}
