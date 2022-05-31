package com.book.donation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.databinding.ActivityHappyDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class HappyDetailsActivity extends BaseActivity {

    ActivityHappyDetailsBinding binding;
    String experience ="", image = "", title="";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_happy_details);
        experience = getIntent().getStringExtra("experience");
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");

        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+image).into(binding.imgHappy);
        binding.txtTitle.setText(title);
        binding.txtExperience.setText(experience);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;

            case R.id.imgShare:
                Bitmap bm=((BitmapDrawable)binding.imgHappy.getDrawable()).getBitmap();
                tryToShareImageWithText("New Item Donated!\n\n"+title+"\n"+experience, bm);
                break;

            default:
                break;
        }
    }

    protected void tryToShareImageWithText(String text, Bitmap image) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    /*URL url = new URL(imageurl);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());*/
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(HappyDetailsActivity.this, image));
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share using..."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
