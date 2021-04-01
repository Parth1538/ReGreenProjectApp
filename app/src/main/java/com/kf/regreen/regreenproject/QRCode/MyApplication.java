package com.kf.regreen.regreenproject.QRCode;

import android.app.Application;
import android.text.TextUtils;


/**
 * Created by ravi on 31/07/17.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();


    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }



}
