package com.book.donation.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.messenger.FriendlyMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<FriendlyMessage, RecyclerView.ViewHolder> {
    private String userID;
    private FirebaseRecyclerOptions<FriendlyMessage> options;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    String TAG = "";
    Context context;
    ProgressBar mProgressBar;
    private String friendImageurl = "";
    private String SenderPic;
    public String MESSAGES_CHILD = "";
    public String threadId = "";
    public onClickIteam onClickIteam;
    public ImageView img_delete;
    public HashMap<String,Boolean> isSelectList = new HashMap<>();
    List<FriendlyMessage> list  = new ArrayList<>();

    public interface onClickIteam{
        public void onClick(boolean isSelect);
    }

    public CustomFirebaseRecyclerAdapter(@NonNull FirebaseRecyclerOptions<FriendlyMessage> options, String TAG, ProgressBar mProgressBar,
                                         String userID, String friendImageurl, String SenderPic, String threadId, ImageView img_delete, onClickIteam onClickIteam, Context context) {
        super(options);
        this.options = options;
        this.TAG = TAG;
        this.mProgressBar = mProgressBar;
        this.context = context;
        this.userID = userID;
        this.friendImageurl = friendImageurl;
        this.SenderPic = SenderPic;
        this.threadId = threadId;
        this.onClickIteam = onClickIteam;
        this.img_delete = img_delete;
    }

    @Override
    public int getItemViewType(int position) {
        FriendlyMessage friendlyMessage = options.getSnapshots().get(position);
        String user_id = friendlyMessage.getSender_user_id();
        String sender_profile_ic = friendlyMessage.getSender_user_image();

        if (user_id.equalsIgnoreCase(userID)) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;

        }

    }

    @Override
    protected void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position, FriendlyMessage friendlyMessage) {
        mProgressBar.setVisibility(View.INVISIBLE);
        switch (viewHolder.getItemViewType()) {
            case TYPE_ONE:
                InitLayoutUser((MessageViewHolderUser) viewHolder, friendlyMessage, position);
                break;
            case TYPE_TWO:
                InitLayoutFriend((MessageViewHolderFriend) viewHolder, friendlyMessage, position);
                break;
            default:
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == TYPE_ONE) {
            return new MessageViewHolderUser(inflater.inflate(R.layout.list_item_chat_b, viewGroup, false));
        } else if (viewType == TYPE_TWO) {
            return new MessageViewHolderFriend(inflater.inflate(R.layout.list_item_chat_a, viewGroup, false));
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    public static class MessageViewHolderUser extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeTextView;
        ImageView messageImageView;
        ImageView statusImageView;
        CircleImageView imageUserProfile;
        LinearLayout ll_user;
        LinearLayout layRoot;
        TextView txtSenderName;

        public MessageViewHolderUser(View v) {
            super(v);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            messageImageView = itemView.findViewById(R.id.messageImageView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
            txtSenderName = itemView.findViewById(R.id.txtSenderName);
            imageUserProfile = itemView.findViewById(R.id.imageUserProfile);
            ll_user = itemView.findViewById(R.id.ll_user);
            layRoot = itemView.findViewById(R.id.layRoot);
        }
    }

    public static class MessageViewHolderFriend extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeTextView;
        ImageView messageImageView;
        CircleImageView sender_Image_pic;
        TextView txtSenderName;
        LinearLayout layRoot;

        public MessageViewHolderFriend(View v) {
            super(v);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            messageImageView = itemView.findViewById(R.id.messageImageView);
            sender_Image_pic = itemView.findViewById(R.id.sender_Image_pic);
            txtSenderName = itemView.findViewById(R.id.txtSenderName);
            layRoot = itemView.findViewById(R.id.layRoot);
        }
    }

    private void InitLayoutUser(final MessageViewHolderUser viewHolder, final FriendlyMessage friendlyMessage, int position) {
        displayLayout(friendlyMessage, viewHolder.layRoot, viewHolder.txtSenderName, viewHolder.messageTextView, null, null, viewHolder.messageImageView, viewHolder.timeTextView, viewHolder.statusImageView);
        if (!TextUtils.isEmpty(SenderPic)) {
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+SenderPic).into(viewHolder.imageUserProfile);
        } else {
            viewHolder.imageUserProfile.setImageResource(R.drawable.demo_profile);
        }



        if (friendlyMessage.isSelect()){
            viewHolder.ll_user.setBackgroundResource(R.drawable.receive_chat_message_background_is_select);

        }
        else {
            viewHolder.ll_user.setBackgroundResource(R.drawable.receive_chat_message_background);
        }

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               /* if (friendlyMessage.isSelect()){
                    friendlyMessage.setSelect(false);
                    viewHolder.ll_user.setBackgroundResource(R.drawable.receive_chat_message_background);
                    isSelectMessage(friendlyMessage.getId(),false);
                }
                else {
                    friendlyMessage.setSelect(true);
                    viewHolder.ll_user.setBackgroundResource(R.drawable.receive_chat_message_background_is_select);
                    isSelectMessage(friendlyMessage.getId(),true);
                }*/


                return true;
            }
        });

        if (friendlyMessage.getMessageType().equalsIgnoreCase("text")) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (friendlyMessage.getText().contains("https://youtu.be") || friendlyMessage.getText().contains("https://www.youtube.com/")){
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(friendlyMessage.getText())));
                    }

                }
            });
        }
    }

    private void isSelectMessage(String id,boolean isSelect) {
        if (isSelect){
            isSelectList.put(id,isSelect);
        }
        else {
            isSelectList.remove(id);
        }

        boolean flag= false;
        for (String key : isSelectList.keySet()) {
            boolean value = isSelectList.get(key);
            if (value){
                flag = true;
            }
        }

        if (flag){
            img_delete.setVisibility(View.VISIBLE);
        }
        else {
            img_delete.setVisibility(View.GONE);
        }

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMessages();
            }
        });
    }

    private void deleteMessages() {
        final Dialog dialog = new Dialog(context, R.style.SheetDialog);
        dialog.setContentView(R.layout.customalertdailog);
        Button no = (Button) dialog.findViewById(R.id.bt_no);
        Button yes = (Button) dialog.findViewById(R.id.bt_yes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String  MESSAGES_CHILD = "messages_"+threadId;
                for (String key : isSelectList.keySet()) {
                    DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("m8s-world");
                    DatabaseReference databaseReference = mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(String.valueOf(key));
                    databaseReference.removeValue();
                }
                isSelectList.clear();
                dialog.dismiss();
                img_delete.setVisibility(View.GONE);
                notifyDataSetChanged();*/
            }
        });
        dialog.show();
    }


    private void displayLayout(FriendlyMessage friendlyMessage, LinearLayout layRoot, TextView txtSenderName, TextView messageTextView, View view, TextView txtItemText, final ImageView messageImageView, final TextView timeTextView, final ImageView statusImageView) {
        if (friendlyMessage.getMessageType() == null) {
            return;
        } else if (friendlyMessage.getMessageType().equalsIgnoreCase("text")) {
            layRoot.setVisibility(View.VISIBLE);
            setText(friendlyMessage, messageImageView, null, messageTextView, "text");

        } else {
            layRoot.setVisibility(View.VISIBLE);
            setImageAudioVideo(friendlyMessage, messageImageView, messageTextView, "imageAudioVideo");
        }
        if (txtSenderName!=null){
            txtSenderName.setText(friendlyMessage.getSender_user_name());

        }

        try {
            Date parsedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa", Locale.getDefault()).parse(friendlyMessage.getMessageTime());
            //timeTextView.setText(new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(parsedDate));
            timeTextView.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm aa", Locale.getDefault()).format(parsedDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (statusImageView != null)
            if (friendlyMessage.isSeen())
                statusImageView.setImageDrawable(messageImageView.getContext().getResources().getDrawable(R.drawable.ic_message_seen));
            else
                statusImageView.setImageDrawable(messageImageView.getContext().getResources().getDrawable(R.drawable.ic_message_delivered));
    }

    private void InitLayoutFriend(final MessageViewHolderFriend viewHolder, FriendlyMessage friendlyMessage, int position) {
        displayLayout(friendlyMessage, viewHolder.layRoot, viewHolder.txtSenderName, viewHolder.messageTextView,null, null, viewHolder.messageImageView, viewHolder.timeTextView, null);

        if (!TextUtils.isEmpty(friendlyMessage.getSender_user_image())) {
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+friendlyMessage.getSender_user_image()).into(viewHolder.sender_Image_pic);
        } else {
            viewHolder.sender_Image_pic.setImageResource(R.drawable.demo_profile);
        }
    }

    private void setText(FriendlyMessage friendlyMessage, ImageView messageImageView, TextView txtItemText, TextView messageTextView, String type){
        //String[] ItemShareText = (new Base64Convertor()).Base64ToSimpleString(friendlyMessage.getText()).split("<br>");

        messageTextView.setClickable(true);
        messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
        messageTextView.setText(friendlyMessage.getText());
        messageTextView.setVisibility(TextView.VISIBLE);
        if (!type.equalsIgnoreCase("ItemShare")){
            messageImageView.setVisibility(ImageView.GONE);
        }else {
            List<String> ItemShareText= Arrays.asList(friendlyMessage.getItemToShare().split("<br>"));
            /*txtItemText.setClickable(false);
            txtItemText.setMovementMethod(LinkMovementMethod.getInstance());*/
            txtItemText.setText(Html.fromHtml(ItemShareText.get(0)+"<br>"+ItemShareText.get(1)+"<br>"+ItemShareText.get(2)));
            txtItemText.setVisibility(TextView.VISIBLE);
        }
    }

    private void setImageAudioVideo(FriendlyMessage friendlyMessage, final ImageView messageImageView, TextView messageTextView, String type){
        final String[] imageUrl = {friendlyMessage.getImageUrl()};
        if (imageUrl[0].startsWith("gs://")) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl[0]);
            storageReference.getDownloadUrl().addOnCompleteListener(
                    new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                imageUrl[0] = task.getResult().toString();
                                Picasso.get().load(imageUrl[0]).into(messageImageView);
                            }
                        }
                    });
        }

        if (friendlyMessage.getMessageType().equalsIgnoreCase("mp3")) {
            messageImageView.setImageResource(R.drawable.audio_logo);
        } else {
            Picasso.get().load(imageUrl[0]).into(messageImageView);
        }


        if (friendlyMessage.getMessageType().equalsIgnoreCase("mp4")
                || friendlyMessage.getMessageType().equalsIgnoreCase("avi")
                || friendlyMessage.getMessageType().equalsIgnoreCase("3gp")
                || friendlyMessage.getMessageType().equalsIgnoreCase("mkv")) {

            messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoIntent = new Intent();
                    videoIntent.setAction(Intent.ACTION_VIEW);
                    videoIntent.setDataAndType(Uri.parse(imageUrl[0]), "video/*");
                    context.startActivity(videoIntent);
                }
            });

        } else if (friendlyMessage.getMessageType().equalsIgnoreCase("mp3")) {
            Log.d("messageType", "--->" + friendlyMessage.getMessageType());
            Log.d("messageType", "--->" + imageUrl[0]);
            messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent videoIntent = new Intent();
                    videoIntent.setAction(Intent.ACTION_VIEW);
                    videoIntent.setDataAndType(Uri.parse(imageUrl[0]), "audio/*");
                    context.startActivity(videoIntent);

                }
            });

        } else if (friendlyMessage.getMessageType().equalsIgnoreCase("txt")) {
            messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent docIntent = new Intent();
                    docIntent.setAction(Intent.ACTION_VIEW);
                    docIntent.setDataAndType(Uri.parse(imageUrl[0]), "text/plain");
                    context.startActivity(docIntent);
                }
            });

        } else if (friendlyMessage.getMessageType().equalsIgnoreCase("docx")
                || friendlyMessage.getMessageType().equalsIgnoreCase("doc")) {

            messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent docIntent = new Intent();
                    docIntent.setAction(Intent.ACTION_VIEW);
                    docIntent.setDataAndType(Uri.parse(imageUrl[0]), "text/plain");
                    context.startActivity(docIntent);
                }
            });

        } else if (friendlyMessage.getMessageType().equalsIgnoreCase("pdf")) {
            messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pdfIntent = new Intent();
                    pdfIntent.setAction(Intent.ACTION_VIEW);
                    pdfIntent.setDataAndType(Uri.parse(imageUrl[0]), "application/pdf");
                    context.startActivity(pdfIntent);
                }
            });
        }


        messageImageView.setVisibility(ImageView.VISIBLE);
        if (!type.equalsIgnoreCase("ItemShare"))
        messageTextView.setVisibility(TextView.GONE);
    }
}
