package com.book.donation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.DonorDetailsPresenter;
import com.book.donation.apicalls.View.IDonorDetailsView;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.apicalls.model.StateResBean;
import com.book.donation.databinding.FragmentDonorDetailsBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.SharedPreferenceData;
import com.book.donation.utils.Utils;

import java.util.ArrayList;

public class DonorDetailsFragment extends Fragment implements IDonorDetailsView {

    FragmentDonorDetailsBinding binding;
    String from;
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

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_donor_details, container, false);
        presenter = new DonorDetailsPresenter();
        presenter.setView(this);

        profileData = new SharedPreferenceData(getContext());
        binding.edtUserName.setText(profileData.getUser_name());
        binding.edtMobile.setText(profileData.getMobile_no());
        binding.edtEmail.setText(profileData.getEmail());
        binding.edtAddress.setText(profileData.getDeliveryAddress());
        binding.edtPincode.setText(profileData.getPINCODE());


        from = getArguments().getString("from");

        if (from.equalsIgnoreCase("AskHelp")){
         binding.tctDonorDetails.setText(getResources().getString(R.string.help_seeker));
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtUserName.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_user_name), Toast.LENGTH_LONG).show();
                }else if (binding.edtMobile.getText().toString().trim().length() != 10){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_your_mobile_number), Toast.LENGTH_LONG).show();
                }else if (binding.edtEmail.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_email), Toast.LENGTH_LONG).show();
                }else if (!Utils.isEmailValid(binding.edtEmail.getText().toString())) {
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_valid_email), Toast.LENGTH_LONG).show();
                }else if (binding.edtAddress.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_address), Toast.LENGTH_LONG).show();
                }else if (binding.edtPincode.getText().toString().trim().length() != 6){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_valid_pincode), Toast.LENGTH_LONG).show();
                }else {
                    DonorFormResBean donorFormResBean = new DonorFormResBean();
                    donorFormResBean.setDonorDetails(binding.edtUserName.getText().toString(), binding.edtMobile.getText().toString(), binding.edtEmail.getText().toString(),
                            profileData.getState_id(), profileData.getCity_id(), binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString());
                    if (from.equalsIgnoreCase("AskHelp")) {
                        Fragment fragment = new DetailsForAskHelpFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("donorDetails", donorFormResBean);
                        fragment.setArguments(bundle);
                        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
                    } else {
                        Fragment fragment = new DetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("donorDetails", donorFormResBean);
                        fragment.setArguments(bundle);
                        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
                    }
                }
            }
        });

        //LoadStateCity();
        selectedStateId = ((MainActivity)getActivity()).profileData.getState_id();
        selectedCityId = ((MainActivity)getActivity()).profileData.getCity_id();

        //presenter.StateCall(getActivity());

        return binding.getRoot();
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
                    presenter.CityCall(getActivity(), selectedStateId);
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
                }else {
                    selectedCityId = null;
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
                if (stateList.get(i).getId().equalsIgnoreCase(((MainActivity)getActivity()).profileData.getState_id())) {
                    binding.spinState.setSelection(i + 1);
                    selectedStateId = stateList.get(i).getId();
                }
            }
            presenter.CityCall(getActivity(), selectedStateId);
        }else {
            ((MainActivity)getActivity()).toast("State list not found");
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
                if (cityList.get(i).getId().equalsIgnoreCase(((MainActivity)getActivity()).profileData.getCity_id()))
                    binding.spinCity.setSelection(i+1);
            }

        }else {
            ((MainActivity)getActivity()).toast("City list not found");
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {
        ((MainActivity)getActivity()).enableLoadingBar(context, enable);
    }

    @Override
    public void onError(String reason) {
        ((MainActivity)getActivity()).toast(reason);
    }
}
