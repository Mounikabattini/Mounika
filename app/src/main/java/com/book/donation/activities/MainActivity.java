package com.book.donation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.book.donation.R;
import com.book.donation.fragment.EditProfileFragment;
import com.book.donation.fragment.HelpSupportFragment;
import com.book.donation.adapter.NavAdapter;
import com.book.donation.databinding.ActivityMainBinding;
import com.book.donation.fragment.HomeFragment;
import com.book.donation.fragment.EventFragment;
import com.book.donation.fragment.MyDashboardFragment;
import com.book.donation.fragment.NavigationFragment;
import com.book.donation.fragment.NotificationFragment;
import com.book.donation.showcaseview.GuideView;
import com.book.donation.showcaseview.config.DismissType;
import com.book.donation.showcaseview.listener.GuideListener;
import com.book.donation.utils.AppLocationService;
import com.book.donation.utils.AppUtils;
import com.book.donation.utils.SharedPreferenceData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements NavAdapter.ItemClickListener, LocationListener, HomeFragment.ShowCaseViewListener {

    public ActivityMainBinding binding;
    View header;
    RecyclerView rvItemList;
    TextView txtUserName;
    ImageView imgUser;
    FragmentManager fragmentManager;
    public boolean isNeedToOpenHomeFragment = false;
    public SharedPreferenceData profileData;


    protected LocationManager locationManager;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000*60*1;
    Location locationGPS, locationNetwork;

    private String[] navItems = {"My Profile", "Events", "Help & support", "Logout"};
    private int[] navImage = {R.drawable.ic_account, R.drawable.ic_event, R.drawable.ic_help_support, R.drawable.ic_logout};
    boolean doubleBackToExitPressedOnce = false;
    public boolean isNavigationOpen = false;
    AppLocationService appLocationService;
    public String latitude=null, longitude=null;
    List<Address> addresses;
    public String userPincode = null;
    private boolean isMainOpen = false;
    private boolean isPincodeDialogOpen = false;
    private FusedLocationProviderClient client;
    private boolean isMakeClickable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        profileData = new SharedPreferenceData(this);
        appLocationService = new AppLocationService(MainActivity.this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        client = LocationServices.getFusedLocationProviderClient(this);
        //init();
        //LoadContents();
        fragmentManager = this.getSupportFragmentManager();
        binding.imgHome.setColorFilter(getResources().getColor(R.color.selected_bottom_bar), PorterDuff.Mode.MULTIPLY);
        binding.txtHome.setTextColor(getResources().getColor(R.color.selected_bottom_bar));
        AppUtils.goNextFragmentReplace(MainActivity.this, new HomeFragment(this));

        //GetCurrentLocation
        isMainOpen = true;
        binding.txtPincode.setText(profileData.getCityName());

        //Displaying ShowCase for first time
        if (!new SharedPreferenceData(this).getIsAppInstalledFirstTime()) {
            isMakeClickable = true;
        }
        /*new SharedPreferenceData(this).isAppInstalledFirstTime(false);*/
    }

    public void toast(String msg){
        super.toast(msg);
    }

    private void collectCurrentLocation(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                if (locationManager != null) {
                    locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            if (locationGPS != null) {
                getLatLong(locationGPS);
            }else if (locationNetwork != null){
                getLatLong(locationNetwork);
            }else {
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Location location = (Location) o;
                        if (location != null) {
                            getLatLong(location);
                        }else {
                            if (!isPincodeDialogOpen) {
                                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    showSettingsAlert();
                                } else {
                                    isMainOpen = false;
                                    isPincodeDialogOpen = true;
                                    showPincodeDialog(MainActivity.this, binding.txtPincode);
                                }
                            }
                        }
                    }
                });

            }
        }
    }

    public void ShowIntro(String title, String text, int viewId, final int type) {

        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(text)
                .setTargetView(findViewById(viewId))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setDismissType(DismissType.targetView) //optional - default dismissible by TargetView
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                       /* if (type == 1) {
                            ShowIntro("Menu", "It's menu option, from where you may access many functionalities", R.id.imgMenu, 2);
                        } else */if (type == 2) {
                            ShowIntro("Home", "From here you may access main parts of this app", R.id.consHome, 3);
                        }/* else if (type == 3) {
                            ShowIntro("Chat", "From here you may access all type chats of this app", R.id.consChat, 4);
                        } */else if (type == 4) {
                            ShowIntro("Dashboard", "From here you may access your donated items, your Help requests, your claims regarding donate items and help requests", R.id.consMyDashboard, 5);
                        } else if (type == 5) {
                            ShowIntro("Profile", "You may see profile here", R.id.consMyAccount, 6);
                        } else if (type == 6) {
                            ShowIntro("Notification", "You may see all type notification here", R.id.consNotification, 7);
                        } /*else if (type == 5) {
                            ShowIntro("Donor Shelf", "From here you may browse donated items and claim them", R.id.consDonerShelf, 6);
                        } else if (type == 6) {
                            ShowIntro("Help Someone", "From here you may check requested helps and claim them", R.id.consHelpRequest, 7);
                        } else if (type == 7) {
                            ShowIntro("Donate", "Using this section you may donate any item", R.id.consDonate, 8);
                        } else if (type == 8) {
                            ShowIntro("Ask Help", "Using this section you may request for some kind of help to our users", R.id.consAskHelp, 9);
                        } else if (type == 9) {
                            ShowIntro("Happy Section", "This fields contains user's experience who have used our services", R.id.consHappySection, 10);
                        } */else {
                            isMakeClickable = true;
                            return;
                        }
                    }
                })
                .build()
                .show();
    }

    public void getLatLong(Location location){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        latitude = ""+location.getLatitude();
        longitude = ""+location.getLongitude();
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            userPincode = addresses.get(0).getPostalCode();
            if (userPincode!=null) {
                locationManager.removeUpdates(this);
                //binding.txtPincode.setText(userPincode);
                new SharedPreferenceData(MainActivity.this).savePincode(userPincode);
            }else {
                toast("unable to fetch location");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.consHome:
                if (isMakeClickable) {
                    isNavigationOpen = false;
                    binding.imgMenu.setVisibility(View.VISIBLE);
                    binding.imgBack.setVisibility(View.GONE);
                    changeBottomBarColor(binding.imgHome, binding.txtHome);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AppUtils.goNextFragmentReplace(this, new HomeFragment(this));
                }
                break;
            case R.id.consMyDashboard:
                if (isMakeClickable) {
                    isNavigationOpen = false;
                    isNeedToOpenHomeFragment = true;
                    binding.imgMenu.setVisibility(View.VISIBLE);
                    binding.imgBack.setVisibility(View.GONE);
                    changeBottomBarColor(binding.imgMyDashboard, binding.txtMyDashboard);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Fragment fragment1 = new MyDashboardFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("from", "dashboard");
                    fragment1.setArguments(bundle1);
                    AppUtils.goNextFragmentReplace(this, fragment1);
                }
                break;
            case R.id.consMyAccount:
                if (isMakeClickable) {
                    isNavigationOpen = false;
                    isNeedToOpenHomeFragment = true;
                    binding.imgMenu.setVisibility(View.VISIBLE);
                    binding.imgBack.setVisibility(View.GONE);
                    changeBottomBarColor(binding.imgMyAccount, binding.txtMyAccount);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AppUtils.goNextFragmentReplace(this, new EditProfileFragment());
                }
                break;

            case R.id.consNotification:
                if (isMakeClickable) {
                    isNavigationOpen = false;
                    isNeedToOpenHomeFragment = true;
                    binding.imgMenu.setVisibility(View.VISIBLE);
                    binding.imgBack.setVisibility(View.GONE);
                    changeBottomBarColor(binding.imgNotification, binding.txtNotification);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AppUtils.goNextFragmentReplace(this, new NotificationFragment());
                }
                break;
            case R.id.imgMenu:
                //openDrawer();
                /*if (isNavigationOpen){
                    isNavigationOpen = false;
                    getSupportFragmentManager().popBackStack();
                }else {*/
                if (isMakeClickable) {
                    if (!isNavigationOpen) {
                        isNavigationOpen = true;
                        AppUtils.goFragmentAddWithoutBackStack(this, new NavigationFragment());
                    }
                }
                /*}*/
                break;
            /*case R.id.imgNotification:
                if (isMakeClickable) {
                    isNavigationOpen = false;
                    //binding.txtHeading.setVisibility(View.GONE);
                    //binding.imgHeadingLogo.setVisibility(View.VISIBLE);
                    binding.imgMenu.setVisibility(View.GONE);
                    binding.imgBack.setVisibility(View.VISIBLE);
                    binding.imgNotification.setVisibility(View.GONE);
                    AppUtils.goFragmentAddWithoutBackStack(this, new NotificationFragment());
                }
                break;*/
            case R.id.txtPincode:
                startActivity(new Intent(this, SetLocationActivity.class));
                break;
            case R.id.imgBack:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public void changeBottomBarColor(ImageView imgView, TextView txtView){
        //binding.txtHeading.setVisibility(View.GONE);
        //binding.imgHeadingLogo.setVisibility(View.VISIBLE);
        binding.imgHome.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        binding.txtHome.setTextColor(getResources().getColor(R.color.white));
        binding.imgChat.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        binding.txtChat.setTextColor(getResources().getColor(R.color.white));
        binding.imgMyDashboard.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        binding.txtMyDashboard.setTextColor(getResources().getColor(R.color.white));
        binding.imgMyAccount.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        binding.txtMyAccount.setTextColor(getResources().getColor(R.color.white));
        binding.imgNotification.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        binding.txtNotification.setTextColor(getResources().getColor(R.color.white));

        imgView.getDrawable().setColorFilter(getResources().getColor(R.color.selected_bottom_bar), PorterDuff.Mode.MULTIPLY);
        txtView.setTextColor(getResources().getColor(R.color.selected_bottom_bar));
    }

    /*public void init(){
        header = binding.sideNavView.getHeaderView(0);
        rvItemList = header.findViewById(R.id.rvItemList);
        txtUserName = header.findViewById(R.id.txtUserName);
        imgUser = header.findViewById(R.id.imgUser);
        //Picasso.get().load(profileData.getPrpfile_image()).placeholder(R.drawable.demo_profile).into(imgUser);
        txtUserName.setText(profileData.getUser_name());
    }*/

    /*public void LoadContents(){
        NavAdapter navAdapter = new NavAdapter(MainActivity.this, navItems, navImage, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItemList.setLayoutManager(layoutManager);
        rvItemList.setItemAnimator(new DefaultItemAnimator());
        rvItemList.setAdapter(navAdapter);
    }*/

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        showSettingsAlert();
                    }
                });
        alertDialog.show();
    }

   /* public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }*/

    @Override
    public void onResume(){
        super.onResume();
        binding.txtPincode.setText(profileData.getCityName());
        /*if (isMainOpen && profileData.getCurrentPINCODE().equalsIgnoreCase(""))
        collectCurrentLocation();*/
    }

    /*@Override
    public void onStop(){
        super.onStop();
        isMainOpen = false;
    }*/

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        isNavigationOpen = false;
        /*if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {*/

            binding.imgNotification.setVisibility(View.VISIBLE);
            if (backStackEntryCount > 0) {
                binding.consBottomBar.setVisibility(View.VISIBLE);
                if (backStackEntryCount == 1) {
                    binding.imgBack.setVisibility(View.GONE);
                    binding.imgMenu.setVisibility(View.VISIBLE);
                    //binding.txtHeading.setVisibility(View.GONE);
                    //binding.imgHeadingLogo.setVisibility(View.VISIBLE);
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (!(fragment instanceof HomeFragment)) {
                        isNeedToOpenHomeFragment = true;
                    } else {
                        isNeedToOpenHomeFragment = false;
                    }
                    getSupportFragmentManager().popBackStack();
                } else {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            } else {
                if (isNeedToOpenHomeFragment) {
                    isNeedToOpenHomeFragment = false;
                    changeBottomBarColor(binding.imgHome, binding.txtHome);
                    AppUtils.goNextFragmentReplace(MainActivity.this, new HomeFragment(this));
                } else {
                    if (doubleBackToExitPressedOnce) {

                        new AlertDialog.Builder(this)
                                .setIcon(R.drawable.app_logo)
                                .setTitle(getResources().getString(R.string.exit))
                                .setCancelable(false)
                                .setMessage(getResources().getString(R.string.msg_exit))
                                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // stopMusic();
                                        MainActivity.this.finish();
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.no), null)
                                .show();
                    }

                    this.doubleBackToExitPressedOnce = true;
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                }
            }
        //}
    }

    @Override
    public void onItemClick(int Position) {
        if (Position == 0) {
            //closeDrawer();
            //startActivity(new Intent(this, EditProfileActivity.class));
            /*binding.imgBack.setVisibility(View.VISIBLE);
            binding.imgMenu.setVisibility(View.GONE);
            AppUtils.goFragmentAddWithoutBackStack(MainActivity.this, new SettingFragment());*/
        }else if (Position == 1) {
            //closeDrawer();
            //startActivity(new Intent(MainActivity.this, AddressListActivity.class));
        }else if (Position == 2) {
            //closeDrawer();
            binding.imgBack.setVisibility(View.VISIBLE);
            binding.imgMenu.setVisibility(View.GONE);
            AppUtils.goFragmentAddWithoutBackStack(MainActivity.this, new EventFragment());
        }else if (Position == 3) {
            //closeDrawer();
            binding.imgBack.setVisibility(View.VISIBLE);
            binding.imgMenu.setVisibility(View.GONE);
            AppUtils.goFragmentAddWithoutBackStack(MainActivity.this, new HelpSupportFragment());
        }else if (Position == 4) {
            //closeDrawer();
            profileData.Logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onChildItemClick(int position, String buttonType) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    getAlertDialogBuilder("Permission Alert!", "We need location permission for displaying you nearest items", true).show();
                }else {
                    //collectCurrentLocation();
                }
            } else {
                boolean showRationale = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    showRationale = shouldShowRequestPermissionRationale( Manifest.permission.ACCESS_COARSE_LOCATION );
                }
                if (! showRationale) {
                    toast("You have clicked Never ask again for required permissions");
                    finish();
                    //getAlertDialogBuilder("Permission Alert!", "You have clicked Never ask again for required permission", true).show();

                } else {
                    toast("We need location permission for displaying you nearest items");
                    finish();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        //collectCurrentLocation();
    }

    @Override
    public void onProviderEnabled(String provider) {
        //collectCurrentLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onShowCaseViewClick() {
        //ShowIntro("Menu", "It's menu option, from where you may access many functionalities", R.id.imgMenu, 2);
    }



    /*@Override
    public IBinder onBind(Intent arg0) {
        return null;
    }*/
}