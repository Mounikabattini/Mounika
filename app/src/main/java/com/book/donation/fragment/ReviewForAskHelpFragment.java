package com.book.donation.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.book.donation.BR;
import com.book.donation.activities.MainActivity;
import com.book.donation.R;
import com.book.donation.apicalls.Presenter.AskHelpNowPresenter;
import com.book.donation.apicalls.View.IAskHelpNowView;
import com.book.donation.apicalls.model.AskHelpNowResBean;
import com.book.donation.apicalls.model.DonorFormResBean;
import com.book.donation.apicalls.model.HelpRequestFeeResBean;
import com.book.donation.databinding.FragmentReviewForaskHelpBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

public class ReviewForAskHelpFragment extends Fragment implements View.OnClickListener, IAskHelpNowView, HomeFragment.ShowCaseViewListener {

    FragmentReviewForaskHelpBinding binding;
    DonorFormResBean donorFormResBean;
    HelpRequestFeeResBean helpRequestFeeResBean;
    AskHelpNowPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_review_forask_help, container, false);

        presenter = new AskHelpNowPresenter();
        presenter.setView(this);

        donorFormResBean = (DonorFormResBean) getArguments().getSerializable("donorDetails");
        helpRequestFeeResBean = (HelpRequestFeeResBean) getArguments().getSerializable("feeDetails");
        binding.txtCategory.setText(donorFormResBean.getCategory_name());
        binding.txtSubCategory.setText(donorFormResBean.getSub_category_name());
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
        }else if (donorFormResBean.getCategory_name().equalsIgnoreCase("Fees")){
            binding.setVariable(BR.item,helpRequestFeeResBean);
            binding.layBasicField.setVisibility(View.GONE);
            binding.layFeedFields.setVisibility(View.VISIBLE);
            binding.txtFCategory.setText(donorFormResBean.getCategory_name());
        }

        binding.btnSubmit.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.AskHelpCall(getActivity(), donorFormResBean, helpRequestFeeResBean, ((MainActivity) getActivity()).profileData.getUser_id());
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
                ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.GONE);
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                AppUtils.goNextFragmentReplace(getContext(), new HomeFragment(ReviewForAskHelpFragment.this));
            }
        });

        dialog.show();

    }

    @Override
    public void onAskHelpNowSuccess(AskHelpNowResBean item) {
        if (item.isSuccess()){
            showDialog(getActivity());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {
        Toast.makeText(getContext(), "Unable to add you query, Please try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowCaseViewClick() {

    }
}
