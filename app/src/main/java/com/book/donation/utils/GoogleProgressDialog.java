package com.book.donation.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.book.donation.R;
import com.github.ybq.android.spinkit.SpinKitView;


/**
 * Created by bhuvnesh on 08/07/2021.
 */

public class GoogleProgressDialog {

    ///FOR RUN THIS DIAOLG BOX SUCCESSFULLY YOU HAVE TO COMPILE THIS DEPENDENCY IN GRADLE.BUILD FILE  compile 'com.github.castorflex.smoothprogressbar:library-circular:1.1.0'

    private Context context;
    private ProgressDialog dialog;
    private SpinKitView spinKitView;
    private Interpolator mCurrentInterpolator;


    public GoogleProgressDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        dialog = new ProgressDialog(context, R.style.full_screen_dialog) {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dialog_progress);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                spinKitView = (SpinKitView) findViewById(R.id.spin_kit);

            }

        };

        if (!((Activity)context).isFinishing()){
            dialog.show();
        }

    }

    public void dismiss() {
        if (dialog != null) {
            if (!((Activity)context).isFinishing()){
                dialog.dismiss();
            }

        }
    }



    private int dpToPx(int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, r.getDisplayMetrics());
        return px;
    }
}
