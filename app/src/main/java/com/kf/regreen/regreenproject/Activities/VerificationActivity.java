package com.kf.regreen.regreenproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.Article_List;
import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

public class VerificationActivity extends AppCompatActivity {

    RelativeLayout btnVerify;
    PinView edtVerificationCode;
    PreferencesUtils mPreferences;
    TextView txtResend, txtTimer;
    LinearLayout llresendcontent;
    String result = "", TAG = "VerificationScreen";
    ProgressBar pb;
    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Constant.getinstance(this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(VerificationActivity.this);

        edtVerificationCode = (PinView) findViewById(R.id.edtVerificationCode);
        txtResend = (TextView) findViewById(R.id.txtResend);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        btnVerify = (RelativeLayout) findViewById(R.id.btnVerify);
        edtVerificationCode = (PinView) findViewById(R.id.edtVerificationCode);
        llresendcontent = (LinearLayout) findViewById(R.id.llresendcontent);


        edtVerificationCode.setLineColor(
                ResourcesCompat.getColor(getResources(), R.color.green_color, getTheme()));

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtVerificationCode.getText().toString().trim().length() == 6 && mPreferences.getPrefOtp().equals(edtVerificationCode.getText().toString().trim())) {
                    checkVerifyCode();
                } else {
                    mPreferences.setisUserVerified(false);
                    Toast.makeText(VerificationActivity.this, R.string.invalid_otp, Toast.LENGTH_LONG).show();
//                    Constant.customshowAlertwithOneButton(VerificationActivity.this, R.string.invalid_otp, "Ok");
                }

            }
        });


        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerForResendCode();
                resendVerifyCode();
            }
        });
        setTimerForResendCode();
    }

    public void setTimerForResendCode() {
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
        myCountDownTimer.start();
    }

    public void resendVerifyCode() {
        Constant.getinstance(VerificationActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.resendOtp(mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Constant.getinstance(VerificationActivity.this).hide();
                try {
                    result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    int status = jsonresult.optInt(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        JSONObject data = jsonresult.getJSONObject(RestApi.PARAMETERS.RESULT);
                        String otp = data.optString(RestApi.PARAMETERS.OTP);
                        mPreferences.setPrefOtp(otp);
                    }
                    Toast.makeText(VerificationActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void checkVerifyCode() {
        Constant.getinstance(this).show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.verifyUser(mPreferences.getPrefUserId(), edtVerificationCode.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Constant.getinstance(VerificationActivity.this).hide();
                try {
                    result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    int status = jsonresult.getInt(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        mPreferences.setisUserVerified(true);
                        Intent intent = new Intent(VerificationActivity.this, DiscountScreen.class);
                        intent.putExtra(TABPOSITION, 2);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                        finish();
                        /*if (SignUpForm.singupContext != null) {
                            ((Activity) SignUpForm.singupContext).finish();
                        }*/
                    } else {
                        mPreferences.setisUserVerified(false);
                        Toast.makeText(VerificationActivity.this, msg, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            txtTimer.setVisibility(View.VISIBLE);
            llresendcontent.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished / 1000);
            txtTimer.setText("" + progress + " second");
        }

        @Override
        public void onFinish() {
            txtTimer.setVisibility(View.GONE);
            llresendcontent.setVisibility(View.VISIBLE);
        }
    }
}
