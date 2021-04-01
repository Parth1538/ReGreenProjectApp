package com.kf.regreen.regreenproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.kf.regreen.regreenproject.bsimagepicker.BSImagePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;
import static com.kf.regreen.regreenproject.Utils.Constant.device_type;

/**
 * Created by Kundan Firke on 28/12/2017.
 */

public class SignUpForm extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener {

    private EditText edt_first_name, edt_mobile_num, edtCity, edt_email, edtPassword;
    private RadioGroup rg_gender;
    RelativeLayout orContainer, exploreNurseriesAndArticles, doSignInContainer;
    private RadioButton rbMale, rbFeMale;
    private TextView txtNext, toolbar_title;
    TextView txtAlreadyHaveAccountC;
    ImageView imgUploadPic;
    String TAG = "SignUpForm", result = "";
    private PreferencesUtils mPreferences;
    Calendar myCalendar;
    ImageView img_back;
    Toolbar toolbarSignUp;
    String globalMyFormat = "", b_date = "";
    public static Context singupContext;
    TextView txtNurseriesC, txtArticlesC;
    String filePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);
        initialization();
        setOnClickListner();

    }

    public void initialization() {

        toolbarSignUp = (Toolbar) findViewById(R.id.toolbarSignUp);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbarSignUp);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Constant.getinstance(SignUpForm.this).PhimpmeProgressBarHandler();
        myCalendar = Calendar.getInstance();
        singupContext = SignUpForm.this;
        mPreferences = new PreferencesUtils(SignUpForm.this);

        txtNurseriesC = (TextView) findViewById(R.id.txtNurseries);
        txtArticlesC = (TextView) findViewById(R.id.txtArticles);

        edt_first_name = (EditText) findViewById(R.id.edtFirstName);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edt_mobile_num = (EditText) findViewById(R.id.edtMobileNumber);
        edt_email = (EditText) findViewById(R.id.edtEmailidSignUp);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        rg_gender = (RadioGroup) findViewById(R.id.rgGender);
        rbFeMale = (RadioButton) findViewById(R.id.rbFeMale);
        rbMale = (RadioButton) findViewById(R.id.rbMale);

        imgUploadPic = (ImageView) findViewById(R.id.imgUploadPic);

        txtNext = (TextView) findViewById(R.id.txtNext);
        orContainer = (RelativeLayout) findViewById(R.id.orContainer);
        exploreNurseriesAndArticles = (RelativeLayout) findViewById(R.id.exploreNurseriesAndArticles);
        doSignInContainer = (RelativeLayout) findViewById(R.id.doSignInContainer);

        txtAlreadyHaveAccountC = (TextView) findViewById(R.id.txtAlreadyHaveAccount);
        Constant.doColorSpanForSecondString(SignUpForm.this, "Already have an account?", "Sign In", txtAlreadyHaveAccountC);
        txtNurseriesC.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        txtArticlesC.setTextColor(getResources().getColor(android.R.color.holo_blue_light));

        if (getIntent().getBooleanExtra("iseditprofile", true)) {
            edt_mobile_num.setText(mPreferences.getPrefUserPhoneNo());
            edt_email.setText(mPreferences.getPrefUserEmail());
            edt_first_name.setText(mPreferences.getPrefFirstName());
            edtCity.setText(mPreferences.getPrefLastName());
            if (mPreferences.getPrefUserEmail().isEmpty() || mPreferences.getPrefUserEmail().length() == 0) {
                edt_email.setEnabled(true);
            } else {
                edt_email.setEnabled(false);
            }
            if (mPreferences.getPrefUserPhoneNo().isEmpty() || mPreferences.getPrefUserPhoneNo().length() == 0) {
                edt_mobile_num.setEnabled(true);
            } else {
                edt_mobile_num.setEnabled(false);
            }
            if (mPreferences.getPrefUserProfileGender().equalsIgnoreCase("1")) {
                rbMale.setChecked(true);
            } else {
                rbFeMale.setChecked(true);
            }
            edtPassword.setVisibility(View.GONE);
            orContainer.setVisibility(View.GONE);
            exploreNurseriesAndArticles.setVisibility(View.GONE);
            doSignInContainer.setVisibility(View.GONE);
            txtNext.setText("Update");
            toolbar_title.setText("Profile Update");
//            getSupportActionBar().setTitle("Profile Update");

            Picasso.with(this)
                    .load(mPreferences.getPrefUserProfilePic()).error(R.drawable.upload_photo)
                    .into(imgUploadPic);
        }
       /* else{
            getSupportActionBar().setTitle("Sign Up");
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skip1, menu);
        //skipMenu
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.skipMenu);
        if (getIntent().getBooleanExtra("iseditprofile", false)) {
            register.setVisible(false);
        } else {
            register.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skipMenu:
                mPreferences.setIsLogin(false);
                startActivity(new Intent(SignUpForm.this, HomeScreenActivity.class));
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return true;
    }

    public void setOnClickListner() {
        txtAlreadyHaveAccountC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpForm.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_first_name.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUpForm.this, "Please enter the first name.", Toast.LENGTH_LONG).show();
                } else if (edt_mobile_num.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUpForm.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                } else if (edt_mobile_num.getText().toString().trim().length() <= 9) {
                    Toast.makeText(SignUpForm.this, "Please enter valid mobile number.", Toast.LENGTH_LONG).show();
                } else if (!Constant.isValidEmail(edt_email.getText().toString().trim())) {
                    Toast.makeText(SignUpForm.this, "Please enter  valid email address.", Toast.LENGTH_LONG).show();
                } else if (edtPassword.getVisibility() == View.VISIBLE && edtPassword.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignUpForm.this, "Please enter password.", Toast.LENGTH_LONG).show();
                } else if (edtPassword.getVisibility() == View.VISIBLE && edtPassword.getText().toString().trim().length() <= 6) {
                    Toast.makeText(SignUpForm.this, "Please enter more then six character password.", Toast.LENGTH_LONG).show();
                } else {
                    UserDetails.setPhone_no(edt_mobile_num.getText().toString().trim());
                    UserDetails.setFirst_name(edt_first_name.getText().toString().trim());
                    UserDetails.setEmail(edt_email.getText().toString().trim());
                    UserDetails.setLast_name(edtCity.getText().toString().trim());
                    UserDetails.setPassword(edtPassword.getText().toString().trim());
                    if (rbMale.isChecked()) {
                        UserDetails.setSex("1");
                    } else {
                        UserDetails.setSex("2");
                    }

                    registerUser();
                }
            }
        });

        txtNurseriesC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpForm.this, HomeScreenActivity.class);
                intent.putExtra("TabPosition", 0);
                startActivity(intent);
                finish();
            }
        });

        txtArticlesC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpForm.this, HomeScreenActivity.class);
                intent.putExtra("TabPosition", 3);
                startActivity(intent);
                finish();
            }
        });
        imgUploadPic.bringToFront();
        imgUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                        .build();
                pickerDialog.show(getSupportFragmentManager(), "picker");
            }
        });
    }

    public void registerUser() {
        Constant.getinstance(SignUpForm.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call;
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getFirst_name());
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getSex());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getLast_name());


        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getEmail());
        RequestBody phone_no = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getPhone_no());

        if (getIntent().getBooleanExtra("iseditprofile", true)) {
            MultipartBody.Part body = null;
            if (filePath.length() > 0) {
                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                body =
                        MultipartBody.Part.createFormData(RestApi.PARAMETERS.BILL_IMAGE, "profilepics.png", reqFile);
            }

            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), mPreferences.getPrefUserId());
            call = restInterface.profielUpdate(user_id, first_name, email, phone_no,
                    gender,
                    city,
                    body);

        } else {
            //image
            MultipartBody.Part body = null;
            if (filePath.length() > 0) {
                File file = new File(filePath);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                body =
                        MultipartBody.Part.createFormData(RestApi.PARAMETERS.BILL_IMAGE, "profilepics.png", reqFile);
            }
            RequestBody device_token = RequestBody.create(MediaType.parse("text/plain"), Constant.getinstance(SignUpForm.this).getDeviceToken());
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), UserDetails.getPassword());
            RequestBody device_type1 = RequestBody.create(MediaType.parse("text/plain"), device_type);
            call = restInterface.registeruser(first_name,
                    email,
                    phone_no,
                    password,
                    gender,
                    city,
                    device_token, device_type1, body);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(SignUpForm.this).hide();
                    result = response.body().string();
                    JSONObject objJson = new JSONObject(result);
                    String msg = objJson.optString(RestApi.PARAMETERS.MESSAGE);
                    int status = objJson.optInt(RestApi.PARAMETERS.STATUS);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        Toast.makeText(SignUpForm.this, msg, Toast.LENGTH_LONG).show();
                        JSONObject data = objJson.getJSONObject(RestApi.PARAMETERS.RESULT);
                        mPreferences.setIsLogin(true);
                        mPreferences.setPrefUserId(data.optString(RestApi.PARAMETERS.REG_USER_ID));
                        mPreferences.setPrefUserEmail(data.optString(RestApi.PARAMETERS.REG_USER_EMAIL));
                        mPreferences.setPrefUserName(data.optString(RestApi.PARAMETERS.REG_USER_NAME));
                        mPreferences.setPrefFirstName(data.optString(RestApi.PARAMETERS.REG_USER_FIRST_NAME));
                        mPreferences.setPrefLastName(data.optString(RestApi.PARAMETERS.REG_CITY));
                        mPreferences.setPrefUserPhoneNo(data.optString(RestApi.PARAMETERS.REG_USER_PHONE));
                        mPreferences.setPrefOtp(data.optString(RestApi.PARAMETERS.OTP));
                        mPreferences.setPrefUserProfileGender(data.optString(RestApi.PARAMETERS.REG_USER_GENDER));
                        mPreferences.setPrefUserProfilePic(data.optString(RestApi.PARAMETERS.REG_USER_PROFILEPIC));

                        Constant.getinstance(SignUpForm.this).hide();

                        if (getIntent().getBooleanExtra("iseditprofile", true)) {
                            finish();
                        } else {
                            Intent intent = null;
                            if (data.optString(RestApi.PARAMETERS.OTP).equals("")) {
                                intent = new Intent(SignUpForm.this, HomeScreenActivity.class);
                                intent.putExtra(TABPOSITION, 2);
                                intent.putExtra("type", 0);
                            } else {
                                intent = new Intent(SignUpForm.this, VerificationActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Constant.customshowAlertSign(SignUpForm.this, msg, "Ok", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(SignUpForm.this).hide();
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

    @Override
    public void onSingleImageSelected(Uri uri) {
        filePath = uri.getPath();
        Picasso.with(this).load(uri).into(imgUploadPic);
    }

}
