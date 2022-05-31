package com.book.donation.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.book.donation.BuildConfig;
import com.book.donation.R;
import com.book.donation.apicalls.Presenter.ProfilePresenter;
import com.book.donation.apicalls.View.IProfileView;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.ProfileResBean;
import com.book.donation.apicalls.model.UpdateProfileResBean;
import com.book.donation.databinding.FragmentEditProfileBinding;
import com.book.donation.utils.FileUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.PermissionCaller;
import com.book.donation.utils.SharedPreferenceData;
import com.book.donation.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditProfileFragment extends Fragment implements View.OnClickListener,IProfileView {

    FragmentEditProfileBinding binding;
    SharedPreferenceData profileData;
    ProfilePresenter presenter;

    public Uri uriProfile = null;
    ProfileResBean serverProfileData;
    private boolean isimageFromGallery= false;

    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public Uri captureMediaFile = null;


    public EditProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile, container, false);

        profileData = new SharedPreferenceData(getActivity());
        presenter = new ProfilePresenter();
        presenter.setView(this);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.ProfileInfoCall(getActivity(), profileData.getUser_id());
        }else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        binding.imgCross.setOnClickListener(this);
        binding.imgCapture.setOnClickListener(this);
        binding.txtSave.setOnClickListener(this);


        return binding.getRoot();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgCross:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.imgCapture:
                getImage();
                break;
            case R.id.txtSave:
                if (uriProfile==null && serverProfileData.getData().getProfilePic().isEmpty()){
                    Toast.makeText(getActivity(), "Please select profile image", Toast.LENGTH_SHORT).show();
                }else if (binding.edtUserName.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please enter user name", Toast.LENGTH_SHORT).show();
                }else if (binding.edtMobile.getText().toString().trim().length() != 10){
                    Toast.makeText(getActivity(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                }else if (binding.edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                }else if (!Utils.isEmailValid(binding.edtEmail.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else if (binding.edtAddress.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_SHORT).show();
                }else if (binding.edtPincode.getText().toString().trim().length() != 6){
                    Toast.makeText(getActivity(), "Please enter valid pincode", Toast.LENGTH_SHORT).show();
                } else if (NetworkCheck.isConnected(getActivity())){

                    MultipartBody.Part user_image=null;

                    if (isimageFromGallery) {
                        String selectedPath = FileUtils.getPath(getActivity(), uriProfile);

                        File file = new File(selectedPath);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                        user_image = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);

                        presenter.UpdateProfileInfoCall(getActivity(), profileData.getUser_id(), binding.edtUserName.getText().toString(), binding.edtMobile.getText().toString(),
                                binding.edtEmail.getText().toString(),
                                 binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString(), user_image);
                    }else {
                        if (uriProfile != null) {
                            String fileName = new File(uriProfile.getPath()).getName();
                            File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(uriProfile.getPath()).getName());
                            user_image = MultipartBody.Part.createFormData("profile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), actualFile));

                            presenter.UpdateProfileInfoCall(getActivity(), profileData.getUser_id(), binding.edtUserName.getText().toString(), binding.edtMobile.getText().toString(), binding.edtEmail.getText().toString()
                                    , binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString(), user_image);
                        }else {
                            //Toast.makeText(this, getResources().getString(R.string.please_select_image), Toast.LENGTH_LONG).show();
                            presenter.UpdateProfileInfoCall(getActivity(), profileData.getUser_id(), binding.edtUserName.getText().toString(), binding.edtMobile.getText().toString(), binding.edtEmail.getText().toString()
                                    , binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString(), user_image);
                        }
                    }
                }else {
                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void getImage() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
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
            captureMediaFile = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", imageFile);

            /*File tempFile = File.createTempFile("image", ".png", new File(SystemUtility.getTempMediaDirectory(this)));
            captureMediaFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);*/
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);

            List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, captureMediaFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                        binding.imgProfile.setImageURI(null);
                        binding.imgProfile.setImageURI(uriProfile);
                    }
                    break;
                case REQUEST_GALLERY:
                    if (data != null) {
                        isimageFromGallery = true;
                        uriProfile = data.getData();
                        binding.imgProfile.setImageURI(null);
                        binding.imgProfile.setImageURI(uriProfile);
                    }
                    break;

            }
        }
    }

    @Override
    public void onProfileSucess(ProfileResBean item) {
        if (item.isSuccess()){
            serverProfileData = item;
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+item.getData().getProfilePic()).into(binding.imgProfile);
            binding.edtUserName.setText(item.getData().getName());
            binding.edtMobile.setText(item.getData().getMobile());
            binding.edtEmail.setText(item.getData().getEmail());
            binding.edtAddress.setText(item.getData().getAddress());
            binding.edtPincode.setText(item.getData().getPincode());
            binding.edtCity.setText(item.getData().getCity());
            binding.edtState.setText(item.getData().getState());
        }
    }

    @Override
    public void onUpdateProfileSuccess(UpdateProfileResBean item) {
        if (item.isSuccess()){
            new SharedPreferenceData(getActivity()).SavedUpdateData(item, binding.edtAddress.getText().toString(), binding.edtPincode.getText().toString());
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}