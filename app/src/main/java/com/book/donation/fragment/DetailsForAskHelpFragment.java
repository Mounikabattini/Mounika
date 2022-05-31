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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.BuildConfig;
import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.CategoryPresenter;
import com.book.donation.apicalls.Presenter.SubCategoryPresenter;
import com.book.donation.apicalls.View.ICategoryView;
import com.book.donation.apicalls.View.ISubCategoryView;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.BookTypeResBean;
import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.apicalls.model.HelpRequestFeeResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.book.donation.apicalls.model.SubCategoryResBean;
import com.book.donation.databinding.FragmentDetailsForaskHelpBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.FileUtils;
import com.book.donation.utils.NetworkCheck;
import com.book.donation.utils.PermissionCaller;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DetailsForAskHelpFragment extends Fragment implements View.OnClickListener, ICategoryView, ISubCategoryView {

    FragmentDetailsForaskHelpBinding binding;
    ArrayAdapter categoryAdapter, subCategoryAdapter, bindingAdapter, feeTypeAdapter;
    String categoryId="", subCategoryId="";
    String categoryName="", subCategoryName="", bookCategory="", bookCategoryId="", feeType="";
    CategoryPresenter presenter;
    SubCategoryPresenter presenter2;

    ArrayList<String> categoryArray = new ArrayList<>();
    ArrayList<String> categoryIdArray = new ArrayList<>();
    ArrayList<String> subCategoryArray = new ArrayList<>();
    ArrayList<String> subCategoryIdArray = new ArrayList<>();
    ArrayList<String> bookCategoryArray = new ArrayList<>();
    ArrayList<String> bookCategoryIDArray = new ArrayList<>();
    ArrayList<String> bookFeeTypeArray = new ArrayList<>();

    DonorFormResBean donorFormResBean;
    HelpRequestFeeResBean helpRequestFeeResBean;
    boolean isTermClicked = false;
    String feeCategory = "School";
    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public Uri captureMediaFile = null;
    public Uri uriProfile = null;
    private boolean isimageFromGallery = false;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details_forask_help, container, false);

        donorFormResBean = (DonorFormResBean) getArguments().getSerializable("donorDetails");
        helpRequestFeeResBean = new HelpRequestFeeResBean();

        categoryAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,categoryArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }
        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        presenter = new CategoryPresenter();
        presenter.setView(this);

        presenter2 = new SubCategoryPresenter();
        presenter2.setView(this);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.CategoryCall(getActivity(), "A");
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }
        //Setting the ArrayAdapter data on the Spinner
        binding.spinCategory.setAdapter(categoryAdapter);
        binding.spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0){
                    categoryId = categoryIdArray.get(position-1);
                    categoryName = categoryArray.get(position);
                    //presenter2.SubCategoryCall(getContext(), ((MainActivity)getActivity()).profileData.getUser_id(), categoryId);
                    if (categoryName.equalsIgnoreCase("Books")){
                        binding.consBookDetails.setVisibility(View.VISIBLE);
                        binding.edtDescription.setVisibility(View.VISIBLE);
                        binding.consSubCategory.setVisibility(View.VISIBLE);
                        binding.consFeeDetails.setVisibility(View.GONE);
                        //LoadBooksCategory();
                    }else if (categoryName.equalsIgnoreCase("Fees")){
                        binding.consBookDetails.setVisibility(View.GONE);
                        binding.edtDescription.setVisibility(View.GONE);
                        binding.consSubCategory.setVisibility(View.GONE);
                        binding.consFeeDetails.setVisibility(View.VISIBLE);
                    }else{
                        binding.consBookDetails.setVisibility(View.GONE);
                        binding.edtDescription.setVisibility(View.VISIBLE);
                        binding.consSubCategory.setVisibility(View.VISIBLE);
                        binding.consFeeDetails.setVisibility(View.GONE);
                    }
                    if (NetworkCheck.isConnected(getContext())) {
                        presenter2.ASkHelpSubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, ""
                                , "", "", "A", "", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id()
                                , ((MainActivity)getActivity()).profileData.getPINCODE());
                    }else {
                        ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subCategoryAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,subCategoryArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }
        };
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinSubCategory.setAdapter(subCategoryAdapter);
        binding.spinSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0){
                    subCategoryId = subCategoryIdArray.get(position-1);
                    subCategoryName = subCategoryArray.get(position);
                    if (subCategoryName.equalsIgnoreCase("Text book")){
                        binding.spinFictionCategory.setVisibility(View.GONE);
                        binding.imgDownArrow3.setVisibility(View.GONE);
                        binding.edtGradeCourse.setVisibility(View.VISIBLE);
                        binding.edtBoardUniversity.setVisibility(View.VISIBLE);
                    }else {
                        binding.spinFictionCategory.setVisibility(View.VISIBLE);
                        binding.imgDownArrow3.setVisibility(View.VISIBLE);
                        binding.edtGradeCourse.setVisibility(View.GONE);
                        binding.edtBoardUniversity.setVisibility(View.GONE);

                        if (categoryName.equalsIgnoreCase("Books")) {
                            if (NetworkCheck.isConnected(getContext())) {
                                presenter2.BookTypeCall(getContext(), subCategoryId);
                            }else {
                                ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                            }
                        }
                    }
                }else {
                    subCategoryId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bindingAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,bookCategoryArray);
        bindingAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,bookCategoryArray){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = super.getView(position, convertView, parent);

                TextView text = (TextView)view.findViewById(R.id.txtSpinItem);
                text.setTextColor(getResources().getColor(R.color.app_color));

                return view;

            }
        };
        bindingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinFictionCategory.setAdapter(bindingAdapter);
        binding.spinFictionCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0){
                    bookCategory = bookCategoryArray.get(position);
                    bookCategoryId = bookCategoryIDArray.get(position-1);
                }else {
                    bookCategoryId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)radioGroup.findViewById(selectedId);

                if (radioSexButton.getText().equals("School")){
                    feeCategory = "School";
                    binding.consSchoolStudent.setVisibility(View.VISIBLE);
                    binding.consCollegeStudent.setVisibility(View.GONE);
                }else {
                    feeCategory = "College";
                    binding.consSchoolStudent.setVisibility(View.GONE);
                    binding.consCollegeStudent.setVisibility(View.VISIBLE);
                }
            }
        });

        bookFeeTypeArray.add("Select Fees Type");
        bookFeeTypeArray.add("Quater Fees");
        bookFeeTypeArray.add("Semester Fees");
        bookFeeTypeArray.add("Hostel Fees");
        bookFeeTypeArray.add("Academic Fees");
        bookFeeTypeArray.add("Sport Fees");
        bookFeeTypeArray.add("Exam Fees");
        feeTypeAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view,bookFeeTypeArray);
        feeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinSchoolFeeType.setAdapter(feeTypeAdapter);
        binding.spinSchoolFeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0){
                    feeType = bookFeeTypeArray.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.txtTermCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTermClicked){
                    isTermClicked = false;
                    binding.txtCondition.setVisibility(View.GONE);
                    binding.txtTermCondition.setText(Html.fromHtml("<u>"+getResources().getString(R.string.term_condition)+"</u>"));
                }else {
                    isTermClicked = true;
                    binding.txtCondition.setVisibility(View.VISIBLE);
                    binding.txtTermCondition.setText(Html.fromHtml("<u>"+getResources().getString(R.string.less)+"</u>"));
                }
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryId.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_category), Toast.LENGTH_LONG).show();
                }else if (subCategoryId.equalsIgnoreCase("") && !categoryName.equalsIgnoreCase("Fees")){
                    Toast.makeText(getContext(), getResources().getString(R.string.please_select_sub_category), Toast.LENGTH_LONG).show();
                }else {
                    if (categoryName.equalsIgnoreCase("Books")){
                        if (binding.edtAuthor.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), getResources().getString(R.string.please_enter_author), Toast.LENGTH_LONG).show();
                        }else if (bookCategoryId.equalsIgnoreCase("") && !subCategoryName.equalsIgnoreCase("Text book")){
                            Toast.makeText(getContext(), getResources().getString(R.string.please_select_book_category), Toast.LENGTH_LONG).show();
                        }else if (binding.edtBookTitle.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), getResources().getString(R.string.please_enter_item_name), Toast.LENGTH_LONG).show();
                        }else {
                            if (subCategoryName.equalsIgnoreCase("Text books")){
                                if (binding.edtGradeCourse.getText().toString().isEmpty()){
                                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_grade_course), Toast.LENGTH_LONG).show();
                                }else if (binding.edtBoardUniversity.getText().toString().isEmpty()){
                                    Toast.makeText(getContext(), getResources().getString(R.string.please_enter_board_university), Toast.LENGTH_LONG).show();
                                }else {
                                    goFurther();
                                }
                            }else {
                                goFurther();
                            }
                        }
                    }else {
                        if (binding.edtDescription.getText().toString().trim().equalsIgnoreCase("") && !categoryName.equalsIgnoreCase("Fees")){
                            Toast.makeText(getContext(), getResources().getString(R.string.please_enter_description), Toast.LENGTH_LONG).show();
                        }else {
                            goFurther();
                        }
                    }
                }
            }
        });

        binding.btnUploadImage.setOnClickListener(this);

        return binding.getRoot();
    }

    private void goFurther(){

        String bookTitle ="", bookIsbn="", author="", bookCategorySelected="", bookCategoryIdSelected="", gradeLevel="", university="";
        MultipartBody.Part user_image=null;
        if (categoryName.equalsIgnoreCase("Books")){
            bookIsbn = binding.edtISBNNo.getText().toString();
            author = binding.edtAuthor.getText().toString();
            bookTitle = binding.edtBookTitle.getText().toString();

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

        }if (subCategoryName.equalsIgnoreCase("Text book")){
            gradeLevel = binding.edtGradeCourse.getText().toString();
            university = binding.edtBoardUniversity.getText().toString();
        }else {
            bookCategorySelected =  bookCategory;
            bookCategoryIdSelected = bookCategoryId;
        }
        if (categoryName.equalsIgnoreCase("Fees")){
            if (binding.edtFirstName.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_first_name);
            }else if (binding.edtLastName.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_last_name);
            }else if (binding.edtTermDetails.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_term_details);
            }else if (binding.edtParentsName.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_parent_name);
            }else if (binding.edtParentPhone.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_parent_phone);
            }else if (binding.edtParentEmail.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_parent_email);
            }else if (feeType.isEmpty()){
                displayToast(R.string.please_enter_fee_type);
            }else if (binding.edtFeeAmount.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_fee_amount);
            }else if (binding.edtBankAcName.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_account_name);
            }else if (binding.edtBankAcNo.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_account_number);
            }else if (binding.edtBankName.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_bank_name);
            }else if (binding.edtIFSC.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_ifsc_code);
            }else if (binding.edtReasonOfHelp.getText().toString().isEmpty()){
                displayToast(R.string.please_enter_reason);
            }else {
                if (feeCategory.equalsIgnoreCase("School")){
                    if (binding.edtGrade.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_grade_level);
                    }else if (binding.edtSchoolId.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_school_id);
                    }else if (binding.edtSchoolName.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_school_name);
                    }else if (binding.edtSchoolAddress.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_school_address);
                    }else {
                        for (int i=1; i<subCategoryArray.size(); i++){
                            if (subCategoryArray.get(i).equalsIgnoreCase("School Fees")){
                                subCategoryId = subCategoryIdArray.get(i-1);
                            }
                        }

                        goNextFurther(subCategoryId, bookIsbn, author, bookCategoryIdSelected, bookCategorySelected, gradeLevel, university);
                    }
                }else {
                    if (binding.edtYear.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_college_year);
                    }else if (binding.edtCollegeId.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_college_id);
                    }else if (binding.edtPhone.getText().toString().isEmpty() && binding.edtPhone.getText().length()>9){
                        displayToast(R.string.please_enter_valid_phone_number);
                    }else if (binding.edtEmail.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_valid_email);
                    }else if (binding.edtCourse.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_course);
                    }else if (binding.edtInstituteName.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_institute_name);
                    }else if (binding.edtInstituteAddress.getText().toString().isEmpty()){
                        displayToast(R.string.please_enter_institute_address);
                    }else {
                        for (int i=0; i<subCategoryArray.size(); i++){
                            if (subCategoryArray.get(i).equalsIgnoreCase("College Fees")){
                                subCategoryId = subCategoryIdArray.get(i);
                            }
                        }
                        goNextFurther(subCategoryId, bookIsbn, author, bookCategoryIdSelected, bookCategorySelected, gradeLevel, university);
                    }
                }
            }
        }else {

            donorFormResBean.setDetails(user_image, null, bookTitle, "",
                    categoryId, categoryName, subCategoryId, subCategoryName, "",
                    binding.edtDescription.getText().toString(), bookIsbn, author, bookCategoryIdSelected, bookCategorySelected, gradeLevel, university);

            helpRequestFeeResBean.setDetails("", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "");

            Fragment fragment = new ReviewForAskHelpFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("donorDetails", donorFormResBean);
            bundle.putSerializable("feeDetails", helpRequestFeeResBean);
            fragment.setArguments(bundle);
            AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
        }
    }

    private void goNextFurther(String subCategoryId, String bookIsbn, String author, String bookCategoryIdSelected, String bookCategorySelected, String gradeLevel, String course){
        helpRequestFeeResBean.setDetails(binding.edtFirstName.getText().toString(), binding.edtLastName.getText().toString(), binding.edtGrade.getText().toString(),
                binding.edtSchoolId.getText().toString(), binding.edtPhone.getText().toString(), binding.edtEmail.getText().toString(), binding.edtCourse.getText().toString(), binding.edtSchoolName.getText().toString(),
                binding.edtSchoolAddress.getText().toString(), binding.edtTermDetails.getText().toString(), binding.edtParentsName.getText().toString(),
                binding.edtParentPhone.getText().toString(), binding.edtParentEmail.getText().toString(), feeType, binding.edtFeeAmount.getText().toString(),
                binding.edtBankAcName.getText().toString(), binding.edtBankAcNo.getText().toString(), binding.edtBankName.getText().toString(),
                binding.edtIFSC.getText().toString(), binding.edtDDCheckNo.getText().toString(), binding.edtReasonOfHelp.getText().toString());


        donorFormResBean.setDetails(null, null, "", "",
                categoryId, categoryName, subCategoryId, subCategoryName, "",
                binding.edtDescription.getText().toString(), bookIsbn, author, bookCategoryIdSelected, bookCategorySelected, gradeLevel, course);

        Fragment fragment = new ReviewForAskHelpFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("donorDetails", donorFormResBean);
        bundle.putSerializable("feeDetails", helpRequestFeeResBean);
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    private void LoadBooksCategory(){
        subCategoryArray.clear();
        subCategoryArray.add("Select Sub Category");
        subCategoryArray.add("Text books");
        subCategoryArray.add("Competitive exams");
        subCategoryArray.add("Fiction");
        subCategoryArray.add("No Fiction");
        subCategoryArray.add("Others");

        subCategoryIdArray.add("1");
        subCategoryIdArray.add("2");
        subCategoryIdArray.add("3");
        subCategoryIdArray.add("4");
        subCategoryIdArray.add("5");
        if (subCategoryAdapter!=null)
        subCategoryAdapter.notifyDataSetChanged();
    }

    private void displayToast(int msg){
        ((MainActivity)getActivity()).toast(getResources().getString(msg));
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
                        binding.imgImage.setImageURI(null);
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
            case R.id.btnUploadImage:
                //pickImageFromCamera();
                getImage();
                break;

            default:
                break;
        }
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
    public void onCategorySucess(CategoryResBean item) {
        categoryArray.clear();
        categoryIdArray.clear();
        categoryArray.add("Select Category");
        for (int i=0; i<item.getData().size(); i++){
            categoryArray.add(item.getData().get(i).getTitle());
            categoryIdArray.add(""+item.getData().get(i).getId());
        }
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEventSucess(EventResBean item) {

    }

    @Override
    public void onRecentItemsSucess(RecentItemsResBean item) {

    }

    @Override
    public void onSubCategorySucess(SubCategoryResBean item) {
        subCategoryId="";
        subCategoryName="";
        subCategoryArray.clear();
        subCategoryArray.add("Select Sub Category");
        for (int i=0; i<item.getCategory().size(); i++){
            subCategoryArray.add(item.getCategory().get(i).getTitle());
            subCategoryIdArray.add(""+item.getCategory().get(i).getId());
        }
        subCategoryAdapter.notifyDataSetChanged();
        binding.spinSubCategory.setSelection(0);
    }

    @Override
    public void onAskHelpSubCategorySuccess(AskHelpSubCategoryResBean item) {
        subCategoryId="";
        subCategoryName="";
        subCategoryArray.clear();
        subCategoryArray.add("Select Sub Category");
        for (int i=0; i<item.getData().size(); i++){
            subCategoryArray.add(item.getData().get(i).getSubCategoryName());
            subCategoryIdArray.add(""+item.getData().get(i).getSubCategoryId());
        }
        subCategoryAdapter.notifyDataSetChanged();
        binding.spinSubCategory.setSelection(0);
    }

    @Override
    public void onBookTypeSuccess(BookTypeResBean item) {
        if (item.isStatus()){
            bookCategoryArray.clear();
            bookCategoryIDArray.clear();
            binding.spinFictionCategory.setSelection(0);
            bookCategoryArray.add("Select type of book");
            for (int i=0; i<item.getData().size(); i++){
                bookCategoryArray.add(item.getData().get(i).getTitle());
                bookCategoryIDArray.add(""+item.getData().get(i).getId());
            }
            bindingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "No category found", Toast.LENGTH_LONG).show();
    }
}
