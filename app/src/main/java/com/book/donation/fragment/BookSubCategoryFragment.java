package com.book.donation.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.adapter.AskHelpSubCategoryAdapter;
import com.book.donation.adapter.BookSubCategoryAdapter;
import com.book.donation.adapter.ViewPagerAdapter;
import com.book.donation.apicalls.Presenter.SubCategoryPresenter;
import com.book.donation.apicalls.View.ISubCategoryView;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.BookTypeResBean;
import com.book.donation.apicalls.model.SubCategoryResBean;
import com.book.donation.databinding.FragmentBookSubcategoryBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookSubCategoryFragment extends Fragment implements BookSubCategoryAdapter.ItemClickListener, AskHelpSubCategoryAdapter.AskHelpItemClickListener, ISubCategoryView {

    FragmentBookSubcategoryBinding binding;
    ViewPagerAdapter viewPagerAdapter;
    BookSubCategoryAdapter bookSubCategoryAdapter;
    AskHelpSubCategoryAdapter askHelpSubCategoryAdapter;

    private List<String> bannerImageList = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;
    ArrayList<String> subCategoryArray = new ArrayList<>();
    ArrayList<String> subCategoryIdArray = new ArrayList<>();
    String subCategoryId="";

    ArrayList<SubCategoryResBean.CategoryItem> dataBean = new ArrayList<>();
    ArrayList<AskHelpSubCategoryResBean.DataItem> helpDataBean = new ArrayList<>();
    String from, categoryId;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    boolean isActivityOpen = false;

    SubCategoryPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_subcategory, container, false);

        from = getArguments().getString("from");
        categoryId = getArguments().getString("categoryId");
        if (categoryId.equalsIgnoreCase("120")){
            binding.imgFilter.setVisibility(View.VISIBLE);
        }

        viewPagerAdapter = new ViewPagerAdapter(bannerImageList, getContext());
        binding.viewPager.setAdapter(viewPagerAdapter);

        presenter = new SubCategoryPresenter();
        presenter.setView(this);
        binding.consRoot.setVisibility(View.GONE);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            binding.SliderDots.addView(dots[i], params);
        }

        if (from.equalsIgnoreCase("DonorShelf")) {
            binding.rvSubCategory.setVisibility(View.VISIBLE);
            bookSubCategoryAdapter = new BookSubCategoryAdapter(getActivity(), dataBean,this);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvSubCategory.setLayoutManager(gridLayoutManager1);
            binding.rvSubCategory.setItemAnimator(new DefaultItemAnimator());
            binding.rvSubCategory.setAdapter(bookSubCategoryAdapter);


            if (NetworkCheck.isConnected(getContext())) {
                presenter.SubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, "", "", "", "1",
                        ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), ((MainActivity)getActivity()).profileData.getPINCODE());
            }else {
                ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
            }
        }else {
            binding.rvAskHelpSubCategory.setVisibility(View.VISIBLE);

            askHelpSubCategoryAdapter = new AskHelpSubCategoryAdapter(getActivity(), helpDataBean,this);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvAskHelpSubCategory.setLayoutManager(gridLayoutManager2);
            binding.rvAskHelpSubCategory.setItemAnimator(new DefaultItemAnimator());
            binding.rvAskHelpSubCategory.setAdapter(askHelpSubCategoryAdapter);

            if (NetworkCheck.isConnected(getContext())) {
                presenter.ASkHelpSubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, "",
                        "", "", "A", "1", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), ((MainActivity)getActivity()).profileData.getPINCODE());
            }else {
                ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
            }
        }

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialog(getActivity());
                if (subCategoryArray.size()==0) {
                    if (NetworkCheck.isConnected(getContext())) {
                        presenter.BookTypeCall(getContext(), "124");
                    }else {
                        ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }else {
                    showFilterDialog(getActivity());
                }
            }
        });

        //loadFilterSpinner();

        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isActivityOpen){
            if (from.equalsIgnoreCase("DonorShelf")) {
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.SubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, "", "", "", "1",
                            ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), ((MainActivity)getActivity()).profileData.getPINCODE());
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
            }else {
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.ASkHelpSubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, "",
                            "", "", "A", "1", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id(), ((MainActivity)getActivity()).profileData.getPINCODE());
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
            }
        }
    }

    public void LoadImageSlider(){
        binding.SliderDots.removeAllViews();
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            binding.SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    try {
                        for (int i = 0; i < dotscount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                        }
                        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == (dotscount - 1)) {
                    currentPage = -1;
                }
                currentPage = currentPage + 1;
                binding.viewPager.setCurrentItem(currentPage);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    public void showFilterDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);
        Spinner category = dialog.findViewById(R.id.spinCategory);
        EditText edtAuthor = dialog.findViewById(R.id.edtAuthor);
        EditText edtIsbn = dialog.findViewById(R.id.edtISBNNo);
        ArrayAdapter subCategoryAdapter = new ArrayAdapter(getContext(),R.layout.spin_drop_down_view, subCategoryArray);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        category.setAdapter(subCategoryAdapter);
        subCategoryId = "";
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position>0) {
                    subCategoryId = subCategoryIdArray.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CardView cardCross = dialog.findViewById(R.id.cardCross);
        cardCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String authoreName = "", isbnNo="";
                if (!edtAuthor.getText().toString().isEmpty()){
                    authoreName = edtAuthor.getText().toString();
                }
                if (!edtIsbn.getText().toString().isEmpty()){
                    isbnNo = edtIsbn.getText().toString();
                }
                if (from.equalsIgnoreCase("DonorShelf")) {
                    if (NetworkCheck.isConnected(getContext())) {
                        dialog.dismiss();
                        presenter.SubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, subCategoryId, authoreName
                                , isbnNo, "1", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id()
                                , ((MainActivity)getActivity()).profileData.getPINCODE());
                    } else {
                        ((MainActivity) getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }else {
                    if (NetworkCheck.isConnected(getContext())) {
                        dialog.dismiss();
                        presenter.ASkHelpSubCategoryCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), categoryId, subCategoryId
                                    , authoreName, isbnNo, "A", "1", ((MainActivity)getActivity()).profileData.getState_id(), ((MainActivity)getActivity()).profileData.getCity_id()
                                , ((MainActivity)getActivity()).profileData.getPINCODE());
                    }else {
                        ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                    }
                }

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    void filter(String text){
        if (from.equalsIgnoreCase("DonorShelf")) {
            ArrayList<SubCategoryResBean.CategoryItem> temp = new ArrayList();
            for(SubCategoryResBean.CategoryItem d: dataBean){
                if(d.getTitle().toLowerCase().contains(text.toLowerCase())){
                    temp.add(d);
                }
            }
            bookSubCategoryAdapter.updateList(temp);
        }else {
            ArrayList<AskHelpSubCategoryResBean.DataItem> temp = new ArrayList();
            for(AskHelpSubCategoryResBean.DataItem d: helpDataBean){
                if(d.getSubCategoryName().toLowerCase().contains(text.toLowerCase())){
                    temp.add(d);
                }
            }
            askHelpSubCategoryAdapter.updateList(temp);
        }
    }

    @Override
    public void OnViewAllClicked(int Position) {
        Fragment fragment = new BookListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("subCategoryId", ""+dataBean.get(Position).getId());
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void OnAskHelpViewAllClicked(int Position) {
        Fragment fragment = new BookListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putSerializable("SubCategoryList", helpDataBean.get(Position));
        fragment.setArguments(bundle);
        AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
    }

    @Override
    public void onSubCategorySucess(SubCategoryResBean item) {
        if (item.isSuccess()){
            isActivityOpen = true;
            binding.consRoot.setVisibility(View.VISIBLE);
            dataBean.clear();
            bannerImageList.clear();
            dataBean.addAll(item.getCategory());
            for (int i=0; i<item.getBanner().size(); i++){
                bannerImageList.add(item.getBanner().get(i).getBanner());
            }
            viewPagerAdapter.notifyDataSetChanged();
            LoadImageSlider();
            if (item.getCategory().size()<1){
                binding.imgNoData.setVisibility(View.VISIBLE);
            }
            bookSubCategoryAdapter = new BookSubCategoryAdapter(getActivity(), dataBean,this);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvSubCategory.setLayoutManager(gridLayoutManager1);
            binding.rvSubCategory.setItemAnimator(new DefaultItemAnimator());
            binding.rvSubCategory.setAdapter(bookSubCategoryAdapter);
        }else {
            binding.imgNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAskHelpSubCategorySuccess(AskHelpSubCategoryResBean item) {
        helpDataBean.clear();
        bannerImageList.clear();
        if (item.isSuccess()){
            isActivityOpen = true;
            binding.consRoot.setVisibility(View.VISIBLE);
            helpDataBean.addAll(item.getData());

            for (int i=0; i<item.getBanner().size(); i++){
                bannerImageList.add(item.getBanner().get(i).getBanner());
            }
            LoadImageSlider();
            if (item.getData().size()<1){
                binding.imgNoData.setVisibility(View.VISIBLE);
            }
            askHelpSubCategoryAdapter = new AskHelpSubCategoryAdapter(getActivity(), helpDataBean,this);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
            binding.rvAskHelpSubCategory.setLayoutManager(gridLayoutManager2);
            binding.rvAskHelpSubCategory.setItemAnimator(new DefaultItemAnimator());
            binding.rvAskHelpSubCategory.setAdapter(askHelpSubCategoryAdapter);
        }else {
            binding.imgNoData.setVisibility(View.VISIBLE);
        }
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBookTypeSuccess(BookTypeResBean item) {
        /*subCategoryArray.clear();
        subCategoryIdArray.clear();*/
        if (item.isStatus()){
            subCategoryArray.add("Select book type");
            for (int i=0; i<item.getData().size(); i++){
                subCategoryArray.add(item.getData().get(i).getTitle());
                subCategoryIdArray.add(""+item.getData().get(i).getId());
            }
            showFilterDialog(getActivity());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
