package com.book.donation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.SupportQueryPresenter;
import com.book.donation.apicalls.View.ISupportQueryView;
import com.book.donation.apicalls.model.SupportQueryResBean;
import com.book.donation.databinding.BottomSheetHappyPersonBinding;
import com.book.donation.databinding.BottomSheetSupportQueryBinding;
import com.book.donation.utils.NetworkCheck;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SupportQueryBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, ISupportQueryView {

    BottomSheetSupportQueryBinding binding;
    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public Uri captureMediaFile = null;
    public Uri uriProfile = null;
    private SupportQueryPresenter presenter;
    ProgressDialog progress;
    private boolean isimageFromGallery = false;

    public interface DialogSheetListener{
        void onDialogSheetClose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_support_query, container, false);

        presenter = new SupportQueryPresenter();
        presenter.setView(this);

        binding.imgCross.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("We are adding your experience...");
        progress.setCancelable(false);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgCross:
                dismiss();;
                break;

            case R.id.btnSubmit:
                if (TextUtils.isEmpty(binding.edtTitle.getText())){
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_enter_query_title));
                }else if (TextUtils.isEmpty(binding.edtQuery.getText())){
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_enter_query));
                }else if (NetworkCheck.isConnected(getContext())){
                    // disable dismiss by tapping outside of the dialog
                    progress.show();
                    presenter.submitSupportQuery(getContext(), ((MainActivity)getActivity()).profileData.getUser_id()
                            , binding.edtTitle.getText().toString(),binding.edtQuery.getText().toString());
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSubmitQuerySucess(SupportQueryResBean item) {
        if (item.isSuccess()){
            progress.dismiss();
            dismiss();
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            progress.dismiss();
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
