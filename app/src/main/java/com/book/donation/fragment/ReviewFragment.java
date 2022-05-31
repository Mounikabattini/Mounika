package com.book.donation.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.activities.MainActivity;
import com.book.donation.R;
import com.book.donation.apicalls.Presenter.DonoteNowPresenter;
import com.book.donation.apicalls.View.IDonatNowView;
import com.book.donation.apicalls.model.DonatNowResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.databinding.FragmentReviewBinding;
import com.book.donation.utils.NetworkCheck;

public class ReviewFragment extends Fragment implements View.OnClickListener, IDonatNowView, HomeFragment.ShowCaseViewListener {

    FragmentReviewBinding binding;
    DonorFormResBean donorFormResBean;

    DonoteNowPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_review, container, false);

        donorFormResBean = (DonorFormResBean) getArguments().getSerializable("donorDetails");

        presenter = new DonoteNowPresenter();
        presenter.setView(this);

        /*File actualFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), new File(donorFormResBean.getImage_uri().getPath()).getName());
        binding.imgEvent.setImageBitmap(BitmapFactory.decodeFile(actualFile.getAbsolutePath()));*/
        binding.imgEvent.setImageURI(donorFormResBean.getImage_uri());
        binding.txtQuantity.setText(donorFormResBean.getItem_quantity());
        binding.txtTitle.setText(donorFormResBean.getItem_name());
        binding.txtCategory.setText(donorFormResBean.getCategory_name());
        binding.txtSubCategory.setText(donorFormResBean.getSub_category_name());
        binding.txtCondition.setText(donorFormResBean.getCondition());
        binding.txtDescription.setText(donorFormResBean.getDescription());
        if (donorFormResBean.getCategory_name().equalsIgnoreCase("Books")){
            binding.layIsbnNumber.setVisibility(View.VISIBLE);
            binding.layAuthor.setVisibility(View.VISIBLE);
            binding.txtIsbn.setText(donorFormResBean.getBookIsbn());
            binding.txtAuthor.setText(donorFormResBean.getAuthor());
            if (donorFormResBean.getSub_category_name().equalsIgnoreCase("Text books")){
                binding.layGradeLevel.setVisibility(View.VISIBLE);
                binding.layUniversity.setVisibility(View.VISIBLE);
                binding.txtGradeLevel.setText(donorFormResBean.getGrade_level());
                binding.txtUniversity.setText(donorFormResBean.getUniversity());
            }
        }

        binding.btnSubmit.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnSubmit:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.DonateNowCall(getActivity(), donorFormResBean, ((MainActivity) getActivity()).profileData.getUser_id());
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }

            break;
        }
    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);

        CardView cardCross = dialog.findViewById(R.id.cardCross);
        cardCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                /*((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.GONE);
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                AppUtils.goNextFragmentReplace(getContext(), new HomeFragment(ReviewFragment.this));*/
            }
        });
        dialog.show();
    }

    @Override
    public void onDonatNowSuccess(DonatNowResBean item) {
        if (item.isSuccess()){
            //Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            showDialog(getActivity());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "Unable to add donate item infomation, Please try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowCaseViewClick() {

    }
}
