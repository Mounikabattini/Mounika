package com.book.donation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.NotificationAdapter;
import com.book.donation.apicalls.Presenter.NotificationPresenter;
import com.book.donation.apicalls.View.INotificationView;
import com.book.donation.apicalls.model.NotificationResBean;
import com.book.donation.databinding.FragmentNotificationBinding;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements NotificationAdapter.ItemClickListener, INotificationView {

    FragmentNotificationBinding binding;
    NotificationPresenter presenter;

    NotificationAdapter notificationAdapter;
    ArrayList<NotificationResBean.DataItem> dataBean = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_notification, container, false);

        presenter = new NotificationPresenter();
        presenter.setView(this);

        notificationAdapter = new NotificationAdapter(getActivity(), dataBean,this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        binding.rvNotification.setLayoutManager(gridLayoutManager1);
        binding.rvNotification.setItemAnimator(new DefaultItemAnimator());
        binding.rvNotification.setAdapter(notificationAdapter);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.NotificationListCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id());
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        return binding.getRoot();

    }

    @Override
    public void OnItemClicked(int Position) {

    }

    @Override
    public void onNotificationSuccess(NotificationResBean item) {
        if (item.isSuccess()){
            dataBean.clear();
            dataBean.addAll(item.getData());
            notificationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "No Notification found", Toast.LENGTH_LONG).show();
    }
}
