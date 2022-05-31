package com.book.donation.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.apicalls.Presenter.DonorDetailsPresenter;
import com.book.donation.apicalls.View.IDonorDetailsView;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.StateResBean;
import com.book.donation.databinding.ActivitySetlocationBinding;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;

import java.util.ArrayList;

public class SetLocationActivity extends BaseActivity implements IDonorDetailsView {

    ActivitySetlocationBinding binding;
    SharedPreferenceData profileData;
    DonorDetailsPresenter presenter;

    ArrayList<String> stateArray = new ArrayList<>();
    ArrayList<StateResBean.DataItem> stateList = new ArrayList<>();
    ArrayList<String> cityArray = new ArrayList<>();
    ArrayList<CityResBean.DataItem> cityList = new ArrayList<>();
    private ArrayAdapter stateAdapter;
    private String selectedStateId=null;
    private ArrayAdapter cityAdapter;
    private String selectedCityId=null;
    private String selectedCityName=null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setlocation);

        profileData = new SharedPreferenceData(this);

        presenter = new DonorDetailsPresenter();
        presenter.setView(this);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.StateCall(this);
        }else {
            toast(getResources().getString(R.string.please_check_internet_connection));
        }

        LoadStateCity();

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.txtSave:
                if (selectedCityId!=null && selectedStateId!=null && selectedCityName!=null) {
                    new SharedPreferenceData(this).saveLocation(selectedStateId, selectedCityId, selectedCityName);
                    finish();
                }else {
                    toast("Please set location for getting nearby items");
                }
                break;

            case R.id.imgCross:
                finish();
                break;

            default:
                break;
        }
    }

    private void LoadStateCity(){
        stateAdapter = new ArrayAdapter(getContext(), R.layout.spin_drop_down_view, stateArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }};
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinState.setAdapter(stateAdapter);
        binding.spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0) {
                    selectedStateId = stateList.get(position-1).getId();
                    presenter.CityCall(SetLocationActivity.this, selectedStateId);
                }else {
                    selectedStateId = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //load city spinner
        cityAdapter = new ArrayAdapter(getContext(), R.layout.spin_drop_down_view, cityArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }};
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinCity.setAdapter(cityAdapter);
        binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0){
                    selectedCityId = cityList.get(position-1).getId();
                    selectedCityName = cityArray.get(position);
                }else {
                    selectedCityId = null;
                    selectedCityName = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onStateSuccess(StateResBean item) {
        if (item.isStatus()){
            stateList.clear();
            stateArray.clear();
            stateArray.add("Select State");
            stateList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                stateArray.add(item.getData().get(i).getName());
            }
            stateAdapter.notifyDataSetChanged();
            for (int i=0; i<stateList.size(); i++){
                if (stateList.get(i).getId().equalsIgnoreCase((profileData.getState_id()))) {
                    binding.spinState.setSelection(i + 1);
                    selectedStateId = stateList.get(i).getId();
                }
            }
            //presenter2.CityCall(EditProfileActivity.this, selectedStateId);
        }else {
            toast("State list not found");
        }
    }

    @Override
    public void onCitySuccess(CityResBean item) {
        if (item.isStatus()){
            cityList.clear();
            cityArray.clear();
            cityArray.add("Select City");
            cityList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                cityArray.add(item.getData().get(i).getName());
            }
            cityAdapter.notifyDataSetChanged();
            for (int i=0; i<cityList.size(); i++){
                if (cityList.get(i).getId().equalsIgnoreCase((profileData.getCity_id())))
                    binding.spinCity.setSelection(i+1);
            }

        }else {
            toast("City list not found");
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
