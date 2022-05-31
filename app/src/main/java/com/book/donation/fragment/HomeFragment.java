package com.book.donation.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.activities.MainActivity;
import com.book.donation.R;
import com.book.donation.databinding.FragmentHomeBinding;
import com.book.donation.showcaseview.GuideView;
import com.book.donation.showcaseview.config.DismissType;
import com.book.donation.showcaseview.listener.GuideListener;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.SharedPreferenceData;

public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentHomeBinding binding;
    private ShowCaseViewListener listener;
    //boolean show1 = false, show2 = false, show3 = false, show4 = false, show5 = false;

    public HomeFragment(ShowCaseViewListener listener){
        this.listener = listener;
    }

    public interface ShowCaseViewListener{
        void onShowCaseViewClick();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false);

        //showAnimation();

        if (new SharedPreferenceData(getContext()).getIsAppInstalledFirstTime()) {
            ShowIntro(binding.getRoot().getRootView(), "Donor Shelf", "From here you may browse donated items and claim them", R.id.consDonerShelf, 1);
            new SharedPreferenceData(getContext()).isAppInstalledFirstTime(false);
        }else {
            binding.consDonerShelf.setOnClickListener(this);
            binding.consDonate.setOnClickListener(this);
        }


        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.consDonerShelf:
                //boolean show1 = false;
                changeCardColor(binding.consDonerShelf, binding.txtDonerHeading, binding.txtDonerMsg);
                HandleToolbar();
                Fragment fragment1 = new BookCategoryFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("from", "DonorShelf");
                fragment1.setArguments(bundle1);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment1);
                new SharedPreferenceData(getContext()).saveSectionType("D");
                break;

            case R.id.consDonate:
                //boolean show3 = false;
                changeCardColor(binding.consDonate, binding.txtDonateHeading, binding.txtDonateMsg);
                HandleToolbar();
                Fragment fragment3 = new DonorDetailsFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("from", "DonateNow");
                fragment3.setArguments(bundle3);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment3);
                break;
            default:
                break;
        }
    }

    public void ShowIntro(View view1, String title, String text, int viewId, final int type) {
        Typeface font = ResourcesCompat.getFont(getContext(), R.font.roboto_bold);

        GuideView guideView = new GuideView.Builder(getContext())
                .setTitle(title)
                .setContentText(text)
                .setTargetView(view1.findViewById(viewId))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)
                .setTitleTypeFace(font)//optional
                .setDismissType(DismissType.targetView) //optional - default dismissible by TargetView
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                       /*if (type == 1) {
                            ShowIntro(view1, "Help Someone", "From here you may check requested helps and claim them", R.id.consHelpRequest, 2);
                        } else */if (type == 2) {
                            ShowIntro(view1, "Donate", "Using this section you may donate any item", R.id.consDonate, 3);
                        }/* else if (type == 3) {
                            ShowIntro(view1, "Ask Help", "Using this section you may request for some kind of help to our users", R.id.consAskHelp, 4);
                        } else if (type == 4) {
                            ShowIntro(view1, "Happy Section", "This fields contains user's experience who have used our services", R.id.consHappySection, 5);
                        }*/ else {
                           binding.consDonerShelf.setOnClickListener(HomeFragment.this);
                           binding.consDonate.setOnClickListener(HomeFragment.this);
                           listener.onShowCaseViewClick();
                        }
                    }
                })
                .build();

        guideView.show();
    }

    private void HandleToolbar(){
        ((MainActivity)getActivity()).binding.imgMenu.setVisibility(View.GONE);
        ((MainActivity)getActivity()).binding.imgBack.setVisibility(View.VISIBLE);
        //((MainActivity)getActivity()).binding.imgNotification.setVisibility(View.GONE);
    }

    private void changeCardColor(ConstraintLayout constraintLayout, TextView txtHeading, TextView txtMsg){

        binding.consDonerShelf.setBackground(getResources().getDrawable(R.drawable.rounded_pinkcard_dashboard));
        binding.txtDonerHeading.setTextColor(getResources().getColor(R.color.app_color));
        binding.txtDonerMsg.setTextColor(getResources().getColor(R.color.gray2));

        binding.consDonate.setBackground(getResources().getDrawable(R.drawable.rounded_pinkcard_dashboard));
        binding.txtDonateHeading.setTextColor(getResources().getColor(R.color.app_color));
        binding.txtDonateMsg.setTextColor(getResources().getColor(R.color.gray2));

        //constraintLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_app_color_home_card));
        constraintLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_selected_card));
        txtHeading.setTextColor(getResources().getColor(R.color.white));
        txtMsg.setTextColor(getResources().getColor(R.color.white));
    }
}
