package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmailid;
    TextView txtsend;
    private PreferencesUtils mPreferences;
    Toolbar toolbarSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd_screen);
        Constant.getinstance(ForgotPasswordActivity.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(ForgotPasswordActivity.this);
        initialization();
    }

    public void initialization() {

        toolbarSignUp = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbarSignUp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtEmailid = (EditText) findViewById(R.id.edtEmailid);
        txtsend = (TextView) findViewById(R.id.txtsend);

        txtsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmailid.getText().length() == 0) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter mobile or email address", Toast.LENGTH_LONG).show();
                } else {
                    sendpassword(edtEmailid.getText().toString());
                }
            }
        });
    }

    public void sendpassword(String email) {
        Constant.getinstance(ForgotPasswordActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.forgotpassword(email, Constant.getinstance(ForgotPasswordActivity.this).getDeviceToken(), Constant.device_type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(ForgotPasswordActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    int status = jsonresult.getInt(RestApi.PARAMETERS.STATUS);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        finish();
                    }
                    Toast.makeText(ForgotPasswordActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(ForgotPasswordActivity.this).hide();
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
}
