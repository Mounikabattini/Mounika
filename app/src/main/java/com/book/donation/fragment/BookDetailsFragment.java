package com.book.donation.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.book.donation.R;
import com.book.donation.activities.MainActivity;
import com.book.donation.apicalls.Presenter.ProductDetailsPresenter;
import com.book.donation.apicalls.View.IProductDetailsView;
import com.book.donation.apicalls.api.ApiConstants;
import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.ProductDetailsResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.databinding.FragmentBookDetailsBinding;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.NetworkCheck;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class BookDetailsFragment extends Fragment implements View.OnClickListener, IProductDetailsView {

    FragmentBookDetailsBinding binding;
    int REQUEST_PHONE_CALL = 1;
    ProductDetailsPresenter presenter;
    String productId, donorContacts, productType;

    ProductDetailsResBean itemData;
    String pincode;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_details, container, false);

        productId = getArguments().getString("product_id");
        productType = getArguments().getString("product_type");

        presenter = new ProductDetailsPresenter();
        presenter.setView(this);
        binding.consRoot.setVisibility(View.GONE);

        binding.btnClaim.setOnClickListener(this);
        binding.btnCall.setOnClickListener(this);
        binding.btnWhatapp.setOnClickListener(this);
        //binding.btnChat.setOnClickListener(this);
        binding.imgLike.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
        binding.imgthumb.setOnClickListener(this);
        binding.imgLocation.setOnClickListener(this);

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
            case R.id.btnClaim:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.ClaimCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), productId);
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;
            case R.id.btnCall:
                CheckForPermission();
                break;
            case R.id.btnWhatapp:
                String url = "https://api.whatsapp.com/send?phone=+91"+donorContacts;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getActivity().startActivity(i);
                break;
           /* case R.id.btnChat:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.createThreadIdCall(getContext(), ((MainActivity) getActivity()).profileData.getUser_id(), "" + itemData.getDonordata().getId(), productId, "donate");
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;*/

            case R.id.imgLike:
                if (NetworkCheck.isConnected(getContext())) {
                    presenter.AddToWishlistCall(getContext(), "" + itemData.getData().getId(), ((MainActivity) getActivity()).profileData.getUser_id(), "D");
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;

            case R.id.imgShare:

                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "New Item Donated!\n\n"+itemData.getData().getProductName()+"\n"+itemData.getData().getDescription());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/
                Bitmap bm=((BitmapDrawable)binding.imgBook.getDrawable()).getBitmap();
                tryToShareImageWithText("New Item Donated!\n\n"+itemData.getData().getProductName()+"\n"+itemData.getData().getDescription(), bm);
                //tryToShareImageWithText("New Item Donated!\n\n"+itemData.getData().getProductName()+"\n"+itemData.getData().getDescription(), ApiConstants.BASE_IMAGE_URL+itemData.getData().getImage());
                break;

            case R.id.imgthumb:
                if (NetworkCheck.isConnected(getContext())) {
                    if (itemData!=null) {
                        presenter.AddToLikeThumbCall(getContext(), "" + itemData.getData().getId(), ((MainActivity) getActivity()).profileData.getUser_id(), "D");
                    }else {
                        ((MainActivity)getActivity()).toast(getResources().getString(R.string.item_details_not_found));
                    }
                }else {
                    ((MainActivity)getActivity()).toast(getResources().getString(R.string.please_check_internet_connection));
                }
                break;

            case R.id.imgLocation:
                Fragment fragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("pincode", pincode);
                fragment.setArguments(bundle);
                AppUtils.goFragmentAddWithoutBackStack(getContext(), fragment);
                break;

            default:
                break;

        }
    }

    protected void tryToShareImageWithText(String text, Bitmap image) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    /*URL url = new URL(imageurl);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());*/
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), image));
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share using..."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == REQUEST_PHONE_CALL) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91"+donorContacts, null));
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
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91"+donorContacts, null));
            startActivity(intent);
        }
    }

    @Override
    public void onProductDetailsSucess(ProductDetailsResBean item) {
        if (item.isSuccess()){
            itemData = item;
            pincode = itemData.getDonordata().getPincode();
            binding.consRoot.setVisibility(View.VISIBLE);
            ProductDetailsResBean.Data productDetails = item.getData();
            Picasso.get().load(ApiConstants.BASE_IMAGE_URL+productDetails.getImage()).into(binding.imgBook);
            binding.txtDonationId.setText(""+productDetails.getId());
            binding.txtCategory.setText(productDetails.getCategoryName());
            binding.txtBookName.setText(productDetails.getProductName());
            binding.txtAddressDistance.setText(productDetails.getAddress());
            if (productDetails.getIsWishlist().equalsIgnoreCase("1"))
                binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked));

            if (productDetails.getIsLiked().equalsIgnoreCase("1"))
                binding.imgthumb.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumbed));


            binding.txtDescription.setText(productDetails.getDescription());
            binding.txtTotalLikes.setText(productDetails.getTotalLikes());

            ProductDetailsResBean.Donordata donorDetails = item.getDonordata();
            binding.txtDonorName.setText(donorDetails.getName());
            binding.txtNumber.setText(donorDetails.getMobile());
            binding.txtDonorAddress.setText(donorDetails.getAddress());
            binding.txtEmail.setText(donorDetails.getEmail());

            if (((MainActivity)getActivity()).profileData.getUser_id().equalsIgnoreCase(""+item.getDonordata().getId())){
                binding.btnClaim.setVisibility(View.GONE);
            }

            if (productDetails.getIsClaimRequested().equalsIgnoreCase("1")){
                binding.btnClaim.setVisibility(View.GONE);
                binding.consExtraButtons.setVisibility(View.VISIBLE);
                donorContacts = item.getDonordata().getMobile();
            }

            if (item.getData().getCategoryName().equalsIgnoreCase("Books")){
                binding.consBookDetails.setVisibility(View.VISIBLE);
                binding.txtBookCategory.setText(item.getData().getBookCategory());
                binding.txtIsbn.setText(item.getData().getIsbn());
                binding.txtAuthor.setText(item.getData().getAuthor());
                binding.txtCondition.setText(item.getData().getItemCondition());
            }
        }
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
    public void onAddToWishlistSuccess(AddToWishlistResBean item) {
        if (item.getMessage().equalsIgnoreCase("Product added to wishlist successfully")) {
            binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked));
        }else {
            binding.imgLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
        }
        Toast.makeText(getContext(), item.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onThreadIdSuccess(ThreadIdResBean item) {

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
        Toast.makeText(getContext(), reason, Toast.LENGTH_LONG).show();
    }
}
