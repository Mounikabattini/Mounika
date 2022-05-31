package com.book.donation.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.databinding.FragmentHelpSupportBinding;
import com.book.donation.utils.PermissionCaller;

public class HelpSupportFragment extends Fragment implements View.OnClickListener, CustomBottomSheetDialogFragment.DialogSheetListener {

    FragmentHelpSupportBinding binding;
    public static final int REQUEST_CAPTURE = 1001;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_help_support, container, false);

        binding.imgCallUs.setOnClickListener(this);
        binding.imgChatUs.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgCallUs:
                if (!PermissionCaller.getInstance(getActivity()).isListOfPermission(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CAPTURE))
                    return;

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:97843 16033"));
                    startActivity(callIntent);

                break;
            case R.id.imgChatUs:
                /*Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"bukishfounder@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                //intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));*/
                new SupportQueryBottomSheetFragment().show(getActivity().getSupportFragmentManager(), "Dialog");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  it is not work here  onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAPTURE) {

            // getPosts(false);

            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Please allow permission to Continue", Toast.LENGTH_SHORT).show();



            }

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:97843 16033"));
                startActivity(callIntent);
            }

        }
    }

    @Override
    public void onDialogSheetClose() {

    }
}
