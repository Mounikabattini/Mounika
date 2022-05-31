package com.book.donation.activities;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.apicalls.model.LoginResBean;
import com.book.donation.apicalls.model.VerifyOtpResBean;
import com.book.donation.apicalls.Presenter.LoginPresenter;
import com.book.donation.apicalls.View.ILoginView;
import com.book.donation.databinding.ActivityVerifyNumberBinding;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.SharedPreferenceData;
import com.book.donation.utils.SystemUtility;
import com.book.donation.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class VerifyNumberActivity extends BaseActivity implements ILoginView {

    ActivityVerifyNumberBinding binding;
    LoginPresenter presenter;


    String[] country = { "+91", "+45", "+23", "+95", "+92"};
    private EditText[] editTexts;
    String userType;
    String email, deviceKey;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_number);

        presenter = new LoginPresenter();
        presenter.setView(this);
        userType = getIntent().getStringExtra("userType");

        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,country){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }};
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinPreFix.setAdapter(aa);
        binding.spinPreFix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editTexts = new EditText[]{binding.edtOtp1, binding.edtOtp2, binding.edtOtp3, binding.edtOtp4};

        binding.edtOtp1.addTextChangedListener(new PinTextWatcher(0));
        binding.edtOtp2.addTextChangedListener(new PinTextWatcher(1));
        binding.edtOtp3.addTextChangedListener(new PinTextWatcher(2));
        binding.edtOtp4.addTextChangedListener(new PinTextWatcher(3));

        binding.edtOtp1.setOnKeyListener(new PinOnKeyListener(0));
        binding.edtOtp2.setOnKeyListener(new PinOnKeyListener(1));
        binding.edtOtp3.setOnKeyListener(new PinOnKeyListener(2));
        binding.edtOtp4.setOnKeyListener(new PinOnKeyListener(3));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        deviceKey = task.getResult();

                    }
                });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnGetOtp:
                SystemUtility.hideVirtualKeyboard(VerifyNumberActivity.this);
                if(!Utils.isEmailValid(binding.edtEmail.getText().toString().trim())){
                    toast("Please enter email address”.");
                }else {
                    if (NetworkCheck.isConnected(VerifyNumberActivity.this)) {
                        presenter.LoginCall(this, binding.edtEmail.getText().toString(), userType, deviceKey);
                    }else {
                        toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }
                break;

            case R.id.imgBack:
                finish();
                break;

            case R.id.imgBackOtp:
                binding.consOtpVerify.setVisibility(View.GONE);
                binding.consOtpNumber.setVisibility(View.VISIBLE);
                break;

            case R.id.btnSubmit:
                SystemUtility.hideVirtualKeyboard(VerifyNumberActivity.this);
                if(Utils.stringNotEmpty(binding.edtOtp1.getText().toString().trim()) && Utils.stringNotEmpty(binding.edtOtp2.getText().toString().trim())
                && Utils.stringNotEmpty(binding.edtOtp3.getText().toString().trim()) && Utils.stringNotEmpty(binding.edtOtp4.getText().toString().trim())){

                    String otp = binding.edtOtp1.getText().toString()+binding.edtOtp2.getText().toString()
                            +binding.edtOtp3.getText().toString()+binding.edtOtp4.getText().toString();

                    if (NetworkCheck.isConnected(VerifyNumberActivity.this)) {
                        presenter.VerifyOTPCall(this,binding.edtEmail.getText().toString(), otp);
                    }else {
                        toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }else {
                    toast("Please enter valid OTP”.");
                }
                break;

            case R.id.txtSendAgain:
                presenter.LoginCall(this, binding.edtEmail.getText().toString(), userType, deviceKey);
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoginSucess(LoginResBean item) {
        if (item.isSuccess()){
            email = binding.edtEmail.getText().toString();
            binding.consOtpVerify.setVisibility(View.VISIBLE);
            binding.consOtpNumber.setVisibility(View.GONE);
        }
        toast(item.getMsg());
    }

    @Override
    public void onOTPSucess(VerifyOtpResBean item) {
        if (item.isSuccess()){
            new SharedPreferenceData(this).SavedLoginData(item);
            new SharedPreferenceData(this).saveLocation(item.getUser().getState(), item.getUser().getCity(), item.getUser().getCityName());
            Intent intent = new Intent(VerifyNumberActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //finish();
        }else {
            toast(item.getMessage());
        }

        /*Intent intent = new Intent(VerifyNumberActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
    }

    @Override
    public Context getContext() {
        return this;
    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }
}
