package com.kf.regreen.regreenproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Interface.ApiList;
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

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtoldpwd, edtnewpwd, edtcnewpwd;
    TextView txtsubmit;
    private PreferencesUtils mPreferences;
    Toolbar toolbarSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd_screen);
        Constant.getinstance(ChangePasswordActivity.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(ChangePasswordActivity.this);
        initialization();
    }

    public void initialization() {

        toolbarSignUp = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbarSignUp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtoldpwd = (EditText) findViewById(R.id.edtoldpwd);
        edtnewpwd = (EditText) findViewById(R.id.edtnewpwd);
        edtcnewpwd = (EditText) findViewById(R.id.edtcnewpwd);

        txtsubmit = (TextView) findViewById(R.id.txtsubmit);

        txtsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtoldpwd.getText().toString().trim().length() == 0) {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter old password", Toast.LENGTH_LONG).show();
                } else if (edtnewpwd.getText().toString().trim().length() == 0) {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter new password", Toast.LENGTH_LONG).show();
                } else if (!edtcnewpwd.getText().toString().trim().equalsIgnoreCase(edtnewpwd.getText().toString().trim())) {
                    Toast.makeText(ChangePasswordActivity.this, "New password and retype password does not match.", Toast.LENGTH_LONG).show();
                } else {
                    changePassword();
                }
            }
        });
    }

    public void changePassword() {
        Constant.getinstance(ChangePasswordActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.changepassword(mPreferences.getPrefUserId(),
                edtoldpwd.getText().toString(),
                edtnewpwd.getText().toString(),
                Constant.getinstance(ChangePasswordActivity.this).getDeviceToken(),
                Constant.device_type);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(ChangePasswordActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    int status = jsonresult.getInt(RestApi.PARAMETERS.STATUS);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        finish();
                    }
                    Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(ChangePasswordActivity.this).hide();
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
