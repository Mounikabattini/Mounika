package com.book.donation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.book.donation.R;
import com.book.donation.utils.SharedPreferenceData;

public class SplashActivity extends BaseActivity {

    private static int TIME_OUT = 4000;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }else {
                    goFurther();
                }
            }
        }, TIME_OUT);
    }

    private void goFurther(){
        if (new SharedPreferenceData(SplashActivity.this).IsLogin()){
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    getAlertDialogBuilder("Permission Alert!", "We need location permission for displaying you nearest items", true).show();
                }else {
                    goFurther();
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
}
