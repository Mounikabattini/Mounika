package com.book.donation.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.ChatListResBean;
import com.book.donation.apicalls.model.messenger.FriendlyMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMembersAdapter extends RecyclerView.Adapter<ChatMembersAdapter.MyViewHolder>{

    List<ChatListResBean.DataItem> ItemList;
    private Context context;
    private ItemClickListener listener;
    int unreadCount = 0;
    String lastmessage = "", lastMsgId="";
    Date lastMessageDateTime = null;

    public interface ItemClickListener{
        public void OnItemClicked(int Position);
    }

    public ChatMembersAdapter(Context context, List<ChatListResBean.DataItem> ItemList, ItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_lay_chat_members, parent, false);

        return new ChatMembersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+ItemList.get(position).getProfilePic()).into(holder.imgUser);
        holder.txtName.setText(ItemList.get(position).getName());
        if (ItemList.get(position).getProductName()!=null && !ItemList.get(position).getProductName().isEmpty()) {
            holder.txtItemName.setText(ItemList.get(position).getProductName() + " (" + ItemList.get(position).getCategoryName() + ")");
        }else {
            holder.txtItemName.setText("Request Category : " + ItemList.get(position).getCategoryName());
        }
        readMesagges(((MainActivity)context).profileData.getUser_id(), ""+ItemList.get(position).getId(),ItemList.get(position).getThreadId(),((MyViewHolder) holder).txtUnreadCount,((MyViewHolder) holder).txtMsg,
                ((MyViewHolder) holder).txtDate);

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
        public ImageView imgUser;
        public ConstraintLayout consRoot;
        public TextView txtItemName, txtName, txtMsg, txtDate, txtUnreadCount;

        public MyViewHolder(View view) {
            super(view);
            imgUser = (ImageView) view.findViewById(R.id.imgUser);
            consRoot = view.findViewById(R.id.consRoot);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtName= view.findViewById(R.id.txtName);
            txtMsg = view.findViewById(R.id.txtMsg);
            txtDate = view.findViewById(R.id.txtDate);
            txtUnreadCount = view.findViewById(R.id.txtUnreadCount);
        }
    }

    private void readMesagges(final String userid, final String to_user_id, final String thread_id,
                              final TextView txt_unread_count,final TextView txt_message,final TextView txt_time){
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("bukish-db");
        final String MESSAGES_CHILD = "messages_" + thread_id;
        mFirebaseDatabaseReference = mFirebaseDatabaseReference.child(MESSAGES_CHILD);
        mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unreadCount = 0;
                lastmessage= "";
                lastMsgId = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FriendlyMessage chatDetails = snapshot.getValue(FriendlyMessage.class);
                    if (chatDetails.getReceiver_user_id().equals(userid) && chatDetails.getSender_user_id().equals(to_user_id)){

                        if (!chatDetails.isSeen()) {
                            unreadCount++;
                        }
                        if (chatDetails.getText()!=null && !chatDetails.getText().isEmpty())
                            lastmessage = (chatDetails.getText());

                        lastMsgId = chatDetails.getId();
                        try {
                            lastMessageDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa", Locale.getDefault()).parse(chatDetails.getMessageTime());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (unreadCount != 0) {
                    txt_unread_count.setVisibility(View.VISIBLE);
                    txt_unread_count.setText("" + unreadCount);
                } else {
                    txt_unread_count.setVisibility(View.GONE);
                }

                if (lastmessage != null) {
                    txt_message.setVisibility(View.VISIBLE);
                    txt_message.setText(Html.fromHtml(lastmessage));
                }
                if (lastMessageDateTime != null) {
                    try {
                        txt_time.setVisibility(View.VISIBLE);
                        txt_time.setText(new SimpleDateFormat("dd MMM yyyy HH:mm aa",Locale.getDefault()).format(lastMessageDateTime));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}









