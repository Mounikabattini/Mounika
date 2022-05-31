package com.book.donation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.databinding.FragmentWritetofounderBinding;

public class WriteToFounderFragment extends Fragment implements View.OnClickListener {

    FragmentWritetofounderBinding binding;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_writetofounder, container, false);

        binding.btnSubmit.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:
                if (!TextUtils.isEmpty(binding.edtDescription.getText())) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {"bukishfounder@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...");
                    intent.putExtra(Intent.EXTRA_TEXT, binding.edtDescription.getText().toString());
                    //intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
                }else {
                    Toast.makeText(getContext(), "Please write something to mail us", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.edtDescription.setText("");
    }
}
