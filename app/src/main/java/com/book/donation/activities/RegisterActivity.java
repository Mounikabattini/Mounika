package com.book.donation.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.RegisterResBean;
import com.book.donation.apicalls.Presenter.RegisterPresenter;
import com.book.donation.apicalls.View.IRegisterView;
import com.book.donation.apicalls.model.StateResBean;
import com.book.donation.databinding.ActivityRegisterBinding;
import com.book.donation.utils.FileUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity  implements IRegisterView {

    ActivityRegisterBinding binding;
    private RadioButton radioSexButton;
    RegisterPresenter presenter;

    public Uri uriProfile = null;
    String gender="Male";
    String userType="individual";
    Bitmap bitmap;
    ArrayList<String> organisationArray = new ArrayList<>();
    private ArrayAdapter bindingAdapter;
    String organisation = "NGO";
    private boolean isimageFromGallery = false;

    ArrayList<String> stateArray = new ArrayList<>();
    ArrayList<StateResBean.DataItem> stateList = new ArrayList<>();
    ArrayList<String> cityArray = new ArrayList<>();
    ArrayList<CityResBean.DataItem> cityList = new ArrayList<>();
    //private ArrayAdapter stateAdapter;
    private String selectedStateId=null;
    //private ArrayAdapter cityAdapter;
    private String selectedCityId=null;
    ArrayAdapter<String> adapterState, adapterCity;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        presenter = new RegisterPresenter();
        presenter.setView(this);

        int selectedId=binding.radioGroup.getCheckedRadioButtonId();
        int selectedId2=binding.radioGroupUserType.getCheckedRadioButtonId();
        RadioButton radioSexButton =(RadioButton)findViewById(selectedId);
        gender = radioSexButton.getText().toString();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=binding.radioGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton =(RadioButton)findViewById(selectedId);
                gender = radioSexButton.getText().toString();
            }
        });

        RadioButton radioUserTypeButton =(RadioButton)findViewById(selectedId2);
        userType = radioUserTypeButton.getText().toString();

        organisationArray.add("NGO");
        organisationArray.add("School");
        organisationArray.add("Company");
        bindingAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,organisationArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

            return view;

        }};
        bindingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner

        if (NetworkCheck.isConnected(this)){
            presenter.StateCall(this);
        }else {
            toast(getResources().getString(R.string.please_check_internet_connection));
        }

    }

    private void LoadStateCity(){

        adapterState = new ArrayAdapter<String>(this, R.layout.spin_drop_down_view, R.id.txtSpinItem, stateArray);
        binding.lvState.setAdapter(adapterState);

        binding.edtSearchState.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length()>0) {
                    binding.lvState.setVisibility(View.VISIBLE);
                    adapterState.getFilter().filter(cs);
                }else {
                    binding.lvState.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        binding.lvState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedStateId = "";
                for (int i=0; i<stateList.size(); i++){
                    if (stateList.get(i).getName().equalsIgnoreCase(binding.lvState.getItemAtPosition(position).toString())){
                        selectedStateId = ""+stateList.get(i).getId();
                    }
                }
                binding.edtSearchState.setText(binding.lvState.getItemAtPosition(position).toString());
                binding.lvState.setVisibility(View.GONE);
                presenter.CityCall(RegisterActivity.this, selectedStateId);
            }
        });

        adapterCity = new ArrayAdapter<String>(this, R.layout.spin_drop_down_view, R.id.txtSpinItem, cityArray);
        binding.lvCity.setAdapter(adapterCity);

        binding.edtSearchCity.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length()>0) {
                    binding.lvCity.setVisibility(View.VISIBLE);
                    adapterCity.getFilter().filter(cs);
                }else {
                    binding.lvCity.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        binding.lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCityId = "";
                for (int i=0; i<cityList.size(); i++){
                    if (cityList.get(i).getName().equalsIgnoreCase(binding.lvCity.getItemAtPosition(position).toString())){
                        selectedCityId = ""+cityList.get(i).getId();
                        binding.edtSearchCity.setText(binding.lvCity.getItemAtPosition(position).toString());
                        binding.lvCity.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnRegister:
                String organisation_type = "NGO";
                /*if (uriProfile==null){
                    toast(getResources().getString(R.string.please_select_profile_image));
                }else */if (binding.edtUserName.getText().toString().trim().equalsIgnoreCase("")){
                    toast(getResources().getString(R.string.please_enter_user_name));
                }else if (binding.edtPhone.getText().toString().trim().length() != 10){
                    toast(getResources().getString(R.string.please_enter_valid_phone_number));
                }else if (binding.edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    toast(getResources().getString(R.string.please_enter_email));
                }else if (!Utils.isEmailValid(binding.edtEmail.getText().toString())) {
                    toast(getResources().getString(R.string.please_enter_valid_email));
                }/*else if (binding.edtOrganisation.getText().toString().trim().equalsIgnoreCase("")|| !userType.equalsIgnoreCase("Organisation")){
                    toast(getResources().getString(R.string.please_enter_organisation));
                }*/else if (selectedStateId==null) {
                    toast(getResources().getString(R.string.please_select_state));
                }else if (selectedCityId==null) {
                    toast(getResources().getString(R.string.please_select_city));
                }else if (binding.edtAddress.getText().toString().trim().equalsIgnoreCase("")) {
                    toast(getResources().getString(R.string.please_enter_address));
                }else if (binding.edtPincode.getText().toString().trim().length() != 6){
                    toast(getResources().getString(R.string.please_enter_valid_pincode));
                }else if (NetworkCheck.isConnected(this)){
                    if (!userType.equalsIgnoreCase("Organisation")) {
                        organisation_type = "NA";
                        CallRegisterCode(organisation_type, "","","");
                    }else {
                        if (binding.edtRgistrationNo.getText().toString().trim().isEmpty()){
                            toast(getResources().getString(R.string.please_enter_organisation_number));
                        }else if (binding.edtOrganizationName.getText().toString().trim().isEmpty()){
                            toast(getResources().getString(R.string.please_enter_organisation_name));
                        }else if (binding.edtWebsitelink.getText().toString().trim().isEmpty()){
                            toast(getResources().getString(R.string.please_enter_organisation_website_link));
                        }else {
                            CallRegisterCode(organisation_type, binding.edtRgistrationNo.getText().toString(), binding.edtOrganizationName.getText().toString(), binding.edtWebsitelink.getText().toString());
                        }
                    }
                }else {
                    toast(getResources().getString(R.string.please_check_internet_connection));
                }

                break;

            case R.id.txtSignin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;

            case R.id.imgCapture:
                getImage();
                break;

            case R.id.imgBack:
                finish();
                break;

            default:
                break;
        }
    }

    private void CallRegisterCode(String organizationType, String registartionNo, String organizationName, String websiteLink){
        MultipartBody.Part user_image;

        if (isimageFromGallery) {
            String selectedPath = FileUtils.getPath(this, uriProfile);

            File file = new File(selectedPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            user_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }else {
            if (uriProfile!=null) {
                String fileName = new File(uriProfile.getPath()).getName();
                File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(uriProfile.getPath()).getName());
                if (actualFile != null) {
                    user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));
                } else {
                    user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
                }
            }else {
                user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
            }
        }

        presenter.userRegister(this, binding.edtUserName.getText().toString(), gender, binding.edtPhone.getText().toString(), binding.edtEmail.getText().toString(),
                organizationType, registartionNo, organizationName, websiteLink, selectedStateId, selectedCityId, binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString(), user_image, "123456789");
    }

    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    public void getImage() {
        final BottomSheetDialog dialog = new BottomSheetDialog(RegisterActivity.this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_photo_dialog);

        TextView textCamera = dialog.findViewById(R.id.textCamera);
        TextView textGallery = dialog.findViewById(R.id.textGallery);

        WindowManager.LayoutParams windowManager = new WindowManager.LayoutParams();
        windowManager.copyFrom(dialog.getWindow().getAttributes());
        windowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.gravity = Gravity.CENTER_HORIZONTAL;
        dialog.getWindow().setAttributes(windowManager);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        assert textGallery != null;
        textGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickImageFromGallery();
                dialog.dismiss();
            }
        });

        assert textCamera != null;
        textCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickImageFromCamera();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE:
                    if (captureMediaFile != null) {

                        try {
                            isimageFromGallery = false;
                            uriProfile = captureMediaFile;
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfile);
                            binding.imgProfile.setImageURI(null);
                            binding.imgProfile.setImageURI(uriProfile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case REQUEST_GALLERY:
                    if (data != null) {
                        try {
                            isimageFromGallery = true;
                            uriProfile = data.getData();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfile);
                            binding.imgProfile.setImageURI(null);
                            binding.imgProfile.setImageURI(uriProfile);
                        }catch(IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

            }
        }
    }

    @Override
    public void onRegisterSucess(RegisterResBean item) {
        if (item.isSuccess()){
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onStateSucess(StateResBean item) {
        if (item.isStatus()){
            stateList.clear();
            stateArray.clear();
            stateList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                stateArray.add(item.getData().get(i).getName());
            }
            LoadStateCity();
        }else {
            toast("State list not found");
        }
    }

    @Override
    public void onCitySucess(CityResBean item) {
        if (item.isStatus()){
            cityList.clear();
            cityArray.clear();
            cityList.addAll(item.getData());
            for (int i=0; i<item.getData().size(); i++){
                cityArray.add(item.getData().get(i).getName());
            }
           adapterCity.notifyDataSetChanged();
        }else {
            toast("City list not found");
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
