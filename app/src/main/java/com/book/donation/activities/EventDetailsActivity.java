package com.book.donation.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.databinding.ActivityEventDetailsBinding;
import com.squareup.picasso.Picasso;


public class EventDetailsActivity extends AppCompatActivity {

    ActivityEventDetailsBinding binding;
    EventResBean.DataItem eventResBean;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_details);
        eventResBean = (EventResBean.DataItem) getIntent().getSerializableExtra("eventData");
        binding.txtItemName.setText(eventResBean.getEventName());
        binding.txtDateTime.setText("Date : "+eventResBean.getEventDate());
        Picasso.get().load(ApiConstants.BASE_IMAGE_URL+eventResBean.getEventImage()).into(binding.imgEvent);
        binding.txtDescription.setText(eventResBean.getDescription());

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgBack:
                finish();
                break;
            default:
                break;
        }
    }
}
