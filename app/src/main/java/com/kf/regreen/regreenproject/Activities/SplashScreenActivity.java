package com.kf.regreen.regreenproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private PreferencesUtils mPreferences;
    private String TAG = "SplashScreen", result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPreferences = new PreferencesUtils(SplashScreenActivity.this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callAPI();
        printHashKey(this);

    }

    public void callAPI() {
        if (!mPreferences.isLogin() || !mPreferences.isUserVerified()) {
            mPreferences.deleteSharePreference();
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            i.putExtra(TABPOSITION, 2);
            startActivity(i);
        } else {
            Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
            i.putExtra(TABPOSITION, 2);
            startActivity(i);
        }
        finish();




    }


    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }
}
