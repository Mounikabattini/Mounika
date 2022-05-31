package com.book.donation.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.HelpRequestDetailsPresenter;
import com.book.donation.apicalls.View.IRequestDetailsView;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.MyHelpRequestResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.book.donation.apicalls.model.RequestDetailsResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.databinding.FragmentHelpRequestDetailsBinding;
import com.book.donation.utils.NetworkCheck;
import com.squareup.picasso.Picasso;

public class HelpRequestDetailsFragment extends Fragment implements View.OnClickListener, IRequestDetailsView {

    FragmentHelpRequestDetailsBinding binding;
    int REQUEST_PHONE_CALL = 1;
    AskHelpSubCategoryResBean.AskdataItem helpRequestData;
    MyHelpRequestResBean.DataItem myHelpRequestData;
    RecentItemsResBean.DataItem recentRequestData;
    String from;
    HelpRequestDetailsPresenter presenter;
    String productId, donorContacts, productType;
    RequestDetailsResBean itemData;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_help_request_details, container, false);

        presenter = new HelpRequestDetailsPresenter();
        presenter.setView(this);
        binding.consRoot.setVisibility(View.GONE);

        from = getArguments().getString("from");
        productType = getArguments().getString("productType");

        if (from.equalsIgnoreCase("MyHelpRequestList")){
            myHelpRequestData = (MyHelpRequestResBean.DataItem) getArguments().getSerializable("HelpRequestDetails");
            /*binding.txtRequestorName.setText(myHelpRequestData.getUserName());
            binding.txtAddressDistance.setText(myHelpRequestData.getAddress());
            binding.txtDescription.setText(myHelpRequestData.getDescription());

            binding.txtName.setText(myHelpRequestData.getUserName());
            binding.txtNumber.setText(myHelpRequestData.getMobileNo());
            binding.txtEmail.setText(myHelpRequestData.getEmail());
            binding.txtRequestedAt.setText(myHelpRequestData.getCreatedAt());*/
            productId = ""+myHelpRequestData.getId();
            //donorContacts = myHelpRequestData.getMobileNo();

        }else if (from.equalsIgnoreCase("RecentHelpRequest")){
            recentRequestData = (RecentItemsResBean.DataItem) getArguments().getSerializable("HelpRequestDetails");
            /*binding.txtRequestorName.setText(recenteRequestData.getUserName());
            binding.txtAddressDistance.setText(recenteRequestData.getAddress());
            binding.txtDescription.setText(recenteRequestData.getDescription());

            binding.txtName.setText(recenteRequestData.getUserName());
            binding.txtNumber.setText(recenteRequestData.getMobileNo());
            binding.txtEmail.setText(recenteRequestData.getEmail());
            binding.txtRequestedAt.setText(recenteRequestData.getCreatedAt());*/
            productId = ""+recentRequestData.getId();
            //donorContacts = recenteRequestData.getMobileNo();

        }else if (from.equalsIgnoreCase("helpClaim")){
            productId = getArguments().getString("product_id");
        }else{
            helpRequestData = (AskHelpSubCategoryResBean.AskdataItem) getArguments().getSerializable("HelpRequestDetails");
            /*binding.txtRequestorName.setText(helpRequestData.getUserName());
            binding.txtAddressDistance.setText(helpRequestData.getAddress());
            binding.txtDescription.setText(helpRequestData.getDescription());

            binding.txtName.setText(helpRequestData.getUserName());
            binding.txtNumber.setText(helpRequestData.getMobileNo());
            binding.txtEmail.setText(helpRequestData.getEmail());
            binding.txtRequestedAt.setText(helpRequestData.getCreatedAt());*/
            productId = ""+helpRequestData.getId();
            //donorContacts = helpRequestData.getMobileNo();

            /*if (helpRequestData.getCreatedBy().equalsIgnoreCase(((MainActivity)getActivity()).profileData.getUser_id())){
                binding.btnClaim.setVisibility(View.GONE);
            }else {
                binding.btnCall.setOnClickListener(this);
                binding.btnWhatapp.setOnClickListener(this);
                binding.btnChat.setOnClickListener(this);
            }*/
        }

        binding.btnClaim.setOnClickListener(this);
        binding.btnCall.setOnClickListener(this);
        binding.btnWhatapp.setOnClickListener(this);
        binding.imgLike.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
        binding.imgthumb.setOnClickListener(this);

        if (NetworkCheck.isConnected(getContext())) {
            presenter.DetailsCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), productId, productType);
        }else {
            ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
        }

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCall:
                CheckForPermission();
                break;
            case R.id.btnWhatapp:
                String url = "https://api.whatsapp.com/send?phone=+91"+donorContacts;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getActivity().startActivity(i);
                break;

            case R.id.imgShare:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "New Help Request!\n\n"+itemData.getData().get(0).getUserName()+"\n"+itemData.getData().get(0).getDescription());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.btnClaim:

                if (NetworkCheck.isConnected(getContext())) {
                    presenter.ClaimHelpCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), productId);
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }

                break;

            case R.id.imgLike:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.AddToWishlistCall(getContext(), "" + itemData.getData().get(0).getId(), ((MainActivity) getActivity()).profileData.getUser_id(), "A");
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;

            case R.id.imgthumb:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.AddToLikeThumbCall(getContext(), "" + itemData.getData().get(0).getId(), ((MainActivity) getActivity()).profileData.getUser_id(), "A");
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;

            default:
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == REQUEST_PHONE_CALL) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                String phone = "+91"+donorContacts;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

    }

    public void CheckForPermission(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else {
            String phone = "+91"+donorContacts;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }
    }

    @Override
    public void onThreadIdSuccess(ThreadIdResBean item) {

    }

    @Override
    public void onClaimSucess(ClaimResBean item) {
        if (item.isSuccess()){
            binding.consExtraButtons.setVisibility(View.VISIBLE);
            binding.btnClaim.setVisibility(View.GONE);
            Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
            donorContacts = item.getData().getMobile();
        }
    }

    @Override
    public void onProductDetailsSucess(RequestDetailsResBean item) {
        if (item.isSuccess()){
            itemData = item;
            binding.consRoot.setVisibility(View.VISIBLE);
            RequestDetailsResBean.DataItem productDetails = item.getData().get(0);
            binding.txtRequestId.setText(""+productDetails.getId());
            binding.txtCategory.setText(productDetails.getCategoryName());
            if (productDetails.getImage()!=null){
                Picasso.get().load(ApiConstants.BASE_IMAGE_URL+productDetails.getImage()).into(binding.imgBook);
            }
            binding.txtRequestorName.setText(productDetails.getUserName());
            binding.txtAddressDistance.setText(productDetails.getAddress());
            binding.txtDescription.setText(productDetails.getDescription());
            binding.txtName.setText(productDetails.getUserName());
            binding.txtNumber.setText(productDetails.getMobileNo());
            binding.txtEmail.setText(productDetails.getEmail());

            if (((MainActivity)getActivity()).profileData.getUser_id().equalsIgnoreCase(productDetails.getCreatedBy())){
                binding.btnClaim.setVisibility(View.GONE);
            }

            if (productDetails.getIsLiked().equalsIgnoreCase("1")){
                binding.imgthumb.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbed));
            }

            if (productDetails.getIsWishlist().equalsIgnoreCase("1"))
                binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked));


            if (productDetails.getIsClaimRequested().equalsIgnoreCase("1")){
                binding.btnClaim.setVisibility(View.GONE);
                binding.consExtraButtons.setVisibility(View.VISIBLE);
                donorContacts = productDetails.getMobileNo();
            }
            binding.txtTotalLikes.setText(""+productDetails.getTotalLikes());
            if (productDetails.getCategoryName().equalsIgnoreCase("Books")){
                binding.consBookDetails.setVisibility(View.VISIBLE);
                binding.txtBookCategory.setText(productDetails.getBookCategory());
                binding.txtIsbn.setText(productDetails.getIsbn());
                binding.txtAuthor.setText(productDetails.getAuthor());
            }else if (productDetails.getCategoryName().equalsIgnoreCase("Fees")){
                binding.txtDescriptionHeading.setVisibility(View.GONE);
                binding.txtDescription.setVisibility(View.GONE);
                binding.consFeesDetails.setVisibility(View.VISIBLE);
                binding.txtFirstName.setText(productDetails.getFirstName());
                binding.txtLastName.setText(productDetails.getLastName());
                binding.txtGradeLevelOrCollegeYear.setText(productDetails.getStudentLevel());
                binding.txtSchoolOrCollegeId.setText(productDetails.getStudentId());
                binding.txtSchoolOrCollegeName.setText(productDetails.getInstituteName());
                binding.txtSchoolOrCollegeAddress.setText(productDetails.getInstituteAddress());
                binding.txtTermDetails.setText(productDetails.getTermDetails());
                binding.txtParentName.setText(productDetails.getParentName());
                binding.txtParentPhone.setText(productDetails.getParentPhone());
                binding.txtParentEmail.setText(productDetails.getParentEmail());
                binding.txtFeeType.setText(productDetails.getFeeType());
                binding.txtAmount.setText(productDetails.getAmount());
                binding.txtAcName.setText(productDetails.getAccountName());
                binding.txtAcNumber.setText(productDetails.getAccountNumber());
                binding.txtBankName.setText(productDetails.getBankName());
                binding.txtIFSC.setText(productDetails.getIfsc());
                binding.txtDDOrChequeNo.setText(productDetails.getDdChequeNo());
                binding.txtReason.setText(productDetails.getReason());
            }
        }
    }

    @Override
    public void onAddToWishlistSuccess(AddToWishlistResBean item) {
        if (item.getMessage().equalsIgnoreCase("Product added to wishlist successfully")) {
            binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked));
        }else {
            binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
        }
        Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubmitLikeSuccess(SubmitLikeThumbResBean item) {
        if (item.isSuccess()){
            if (item.getMessage().equalsIgnoreCase("Thanks for Like")) {
                binding.imgthumb.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbed));
                if (binding.txtTotalLikes.getText().toString().isEmpty()){
                    binding.txtTotalLikes.setText("1");
                }else {
                    binding.txtTotalLikes.setText(""+(Integer.parseInt(binding.txtTotalLikes.getText().toString())+1));
                }
            }else {
                binding.imgthumb.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb));
                binding.txtTotalLikes.setText(""+(Integer.parseInt(binding.txtTotalLikes.getText().toString())-1));
            }
            ((MainActivity)getActivity()).toast(item.getMessage());
        }
    }

    @Override
    public void enableLoadingBar(Context context, boolean enable) {

    }

    @Override
    public void onError(String reason) {

    }
}
