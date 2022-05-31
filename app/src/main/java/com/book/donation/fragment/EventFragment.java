package com.book.donation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.activities.EventDetailsActivity;
import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.EventAdapter;
import com.book.donation.apicalls.Presenter.EventPresenter;
import com.book.donation.apicalls.View.IEventView;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.databinding.FragmentEventsBinding;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class EventFragment extends Fragment implements EventAdapter.ItemClickListener, IEventView {

    FragmentEventsBinding binding;
    EventAdapter eventAdapter;
    ArrayList<EventResBean.DataItem> dataBean = new ArrayList<>();
    EventPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_events, container, false);

        presenter = new EventPresenter();
        presenter.setView(this);

        eventAdapter = new EventAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvEvents.setLayoutManager(gridLayoutManager1);
        binding.rvEvents.setItemAnimator(new DefaultItemAnimator());
        binding.rvEvents.setAdapter(eventAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.EventCall(getContext());
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        return binding.getRoot();
    }

    @Override
    public void OnItemClicked(int Position) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("eventData", dataBean.get(Position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onEventSucess(EventResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
