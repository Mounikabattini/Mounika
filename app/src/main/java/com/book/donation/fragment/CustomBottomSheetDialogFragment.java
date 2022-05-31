package com.book.donation.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.book.donation.BuildConfig;
import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.HappySectionPresenter;
import com.book.donation.apicalls.View.IHappySectionView;
import com.book.donation.apicalls.model.HappySectionResBean;
import com.book.donation.databinding.BottomSheetHappyPersonBinding;
import com.book.donation.utils.FileUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.PermissionCaller;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener, IHappySectionView {

    BottomSheetHappyPersonBinding binding;
    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public Uri captureMediaFile = null;
    public Uri uriProfile = null;
    private HappySectionPresenter presenter;
    ProgressDialog progress;
    private boolean isimageFromGallery = false;
    DialogSheetListener listener;

    CustomBottomSheetDialogFragment(DialogSheetListener listener){
        this.listener = listener;
    }

    public interface DialogSheetListener{
        void onDialogSheetClose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_happy_person, container, false);

        presenter = new HappySectionPresenter();
        presenter.setView(this);

        binding.imgCross.setOnClickListener(this);
        binding.btnUploadImage.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("We are adding your experience...");
        progress.setCancelable(false);

        return binding.getRoot();
    }

    public void getImage() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
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

    public void pickImageFromGallery() {
        if (!PermissionCaller.getInstance(getActivity()).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_GALLERY))
            return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), REQUEST_GALLERY);
    }

    public void pickImageFromCamera() {
        if (!PermissionCaller.getInstance(getActivity()).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_CAPTURE))
            return;

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        /*captureMediaFile = getOutputMediaFileUri(1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);*/

        try {
            int random =  (int)(Math.random()*(1000-0+1)+0);
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            path.mkdir();
            String photoFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/images" + random + ".jpg";
            File imageFile = new File(photoFilePath);
            //captureMediaFile = Uri.fromFile(imageFile);
            captureMediaFile = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile);

            /*File tempFile = File.createTempFile("image", ".png", new File(SystemUtility.getTempMediaDirectory(this)));
            captureMediaFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);*/
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);

            List<ResolveInfo> resInfoList = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getContext().grantUriPermission(packageName, captureMediaFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE:
                    if (captureMediaFile != null) {
                        isimageFromGallery = false;
                        uriProfile = captureMediaFile;
                        binding.imgImage.setImageURI(uriProfile);
                    }
                    break;

                case REQUEST_GALLERY:
                    if (data != null) {

                        isimageFromGallery = true;
                        uriProfile = data.getData();
                        binding.imgImage.setImageURI(null);
                        binding.imgImage.setImageURI(uriProfile);

                    }
                    break;

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgCross:
                    dismiss();;
                break;
            case R.id.btnUploadImage:
                getImage();
                break;
            case R.id.btnSubmit:
                if (TextUtils.isEmpty(binding.edtDescription.getText())){
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_enter_your_experience));
                }else if (TextUtils.isEmpty(binding.edtTitle.getText().toString())){
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_enter_title));
                }else if (NetworkCheck.isConnected(getContext())){
                    // disable dismiss by tapping outside of the dialog
                    progress.show();

                    MultipartBody.Part user_image;

                    if (isimageFromGallery) {
                        String selectedPath = FileUtils.getPath(getContext(), uriProfile);

                        File file = new File(selectedPath);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                        user_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                    }else {
                        String fileName = new File(uriProfile.getPath()).getName();
                        File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(uriProfile.getPath()).getName());
                        if (actualFile != null) {
                            user_image = MultipartBody.Part.createFormData("image", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));
                        }else {
                            user_image = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
                        }
                    }

                    presenter.addHappyDataCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id(), binding.edtTitle.getText().toString()
                            , binding.edtDescription.getText().toString(),user_image);
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddHappySectionSucess(HappySectionResBean item) {
        if (item.isSuccess()){
            progress.dismiss();
            listener.onDialogSheetClose();
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