package com.kf.regreen.regreenproject.Utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kf.regreen.regreenproject.R;


/**
 * Created by admin on 04/01/2018.
 */

public class CustomProgressDialog {

        public void showCustomDialog(Context context) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(true);
            RelativeLayout mainLayout = (RelativeLayout) dialogView.findViewById(R.id.mainLayout);
            mainLayout.setBackgroundResource(R.drawable.progressdialog);
            AnimationDrawable frameAnimation = (AnimationDrawable)mainLayout.getBackground();
            frameAnimation.start();
            AlertDialog b = dialogBuilder.create();
            b.show();
        }

}
