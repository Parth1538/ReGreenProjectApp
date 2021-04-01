package com.kf.regreen.regreenproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
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

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

public class LoginScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView txtDoNotHaveAccount, txtSignin, txtForgetPassword;
    RelativeLayout doSignUpContainer;
    private String result = "", TAG = "LoginScreen";
    EditText edtEmailid, edtPassword;
    private PreferencesUtils mPreferences;
    Toolbar toolbarSignUp;
    public static Context loginContext;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    LoginButton loginButton;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private static final int RC_SIGN_IN = 007;
//    Todo  Login Type For User Manual Login=0, Facebook Login=1, Gmail Login=2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edtEmailid = (EditText) findViewById(R.id.edtEmailid);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        txtSignin = (TextView) findViewById(R.id.txtSignin);
        loginButton = (LoginButton) findViewById(R.id.fb_login);
        txtDoNotHaveAccount = (TextView) findViewById(R.id.txtDonotHaveAccount);
        txtForgetPassword = (TextView) findViewById(R.id.txtForgetPassword);

        toolbarSignUp = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbarSignUp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loginContext = LoginScreen.this;
        Constant.getinstance(LoginScreen.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(LoginScreen.this);
        Constant.doColorSpanForSecondString(LoginScreen.this, "Do not have account?", "Sign Up", txtDoNotHaveAccount);

        txtDoNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, SignUpForm.class);
                intent.putExtra("iseditprofile", false);
                startActivity(intent);
                finish();
            }
        });
        initializationGmail();
        initializationFacebook();
        manualLoginUser();

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void manualLoginUser() {
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmailid.getText().toString().trim().length() == 0) {
                    Constant.setError(LoginScreen.this, edtEmailid, "Please enter the mobile number.");
                } else if (edtPassword.getText().toString().trim().length() == 0) {
                    Constant.setError(LoginScreen.this, edtPassword, "Please enter the password.");
                } else {
                    UserDetails.setUser_name(edtEmailid.getText().toString());
                    UserDetails.setPassword(edtPassword.getText().toString());
                    loginUser(0, "", "", "");
                }

            }
        });
    }

    public void initializationFacebook() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
                setResult(RESULT_OK);
                System.out.println("login resutl ==== " + loginResult.toString());
            }

            @Override
            public void onCancel() {
                // App code
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    public void initializationGmail() {
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
/*

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                    }
                });
    }*/

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
//        keytool -list -v -keystore "C:\Users\Vardhman Infonet 4\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            loginUser(2, acct.getId(), acct.getDisplayName(), acct.getEmail());
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
//                        Log.e("getUserDetails...", "" + json_object.toString());
                        try {
                            JSONObject jResult = new JSONObject(json_object.toString());
                            loginUser(1, jResult.getString(RestApi.PARAMETERS.ID), jResult.getString(RestApi.PARAMETERS.NUR_NAME), "");
                        } catch (Exception e) {

                        }
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
//        AppEventsLogger.deactivateApp(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void loginUser(int loginType, String id, String name, String email) {
        Constant.getinstance(LoginScreen.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = null;
        if (loginType == 0) {
            call = restInterface.loginuser(UserDetails.getUser_name(), UserDetails.getPassword(), Constant.getinstance(LoginScreen.this).getDeviceToken(), Constant.device_type);
        } else if (loginType == 1 || loginType == 2) {
            call = restInterface.loginsocialuser(id, email, name, loginType, Constant.getinstance(LoginScreen.this).getDeviceToken(), Constant.device_type);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(LoginScreen.this).hide();
                    result = response.body().string();
                    JSONObject jsonresult = null;
                    jsonresult = new JSONObject(result);
                    int status = jsonresult.getInt(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        JSONObject data = jsonresult.getJSONObject(RestApi.PARAMETERS.RESULT);
                        mPreferences.setIsLogin(true);
                        mPreferences.setPrefUserId(data.optString(RestApi.PARAMETERS.REG_USER_ID));
                        mPreferences.setPrefUserEmail(data.optString(RestApi.PARAMETERS.REG_USER_EMAIL));
                        mPreferences.setPrefUserName(data.optString(RestApi.PARAMETERS.REG_USER_NAME));
                        mPreferences.setPrefFirstName(data.optString(RestApi.PARAMETERS.REG_USER_FIRST_NAME));
                        mPreferences.setPrefLastName(data.optString(RestApi.PARAMETERS.REG_CITY));
                        mPreferences.setPrefUserProfilePic(data.optString(RestApi.PARAMETERS.REG_USER_PROFILEPIC));
                        mPreferences.setPrefUserPhoneNo(data.optString(RestApi.PARAMETERS.REG_USER_PHONE));

                        mPreferences.setPrefUserType(data.optInt(RestApi.PARAMETERS.REG_USER_TYPE));

                        mPreferences.setPrefOtp(data.optString(RestApi.PARAMETERS.OTP));

                        Intent intent = null;
                        Log.e("OTP...", "" + mPreferences.getPrefOtp().length());
                        if (mPreferences.getPrefOtp().length() == 0) {
                            mPreferences.setisUserVerified(true);
                            intent = new Intent(LoginScreen.this, HomeScreenActivity.class);
                            intent.putExtra(TABPOSITION, 2);
                            intent.putExtra("type", 0);
                        } else {
                            mPreferences.setisUserVerified(false);
                            intent = new Intent(LoginScreen.this, VerificationActivity.class);
                        }
                        startActivity(intent);
                        finish();
                      /*  Intent in = new Intent(LoginScreen.this, HomeScreenActivity.class);
                        in.putExtra("TabPosition", 2);
                        startActivity(in);
                        finish();*/
                    } else {
                        Constant.customshowAlertSign(LoginScreen.this, msg, "Ok", edtPassword);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(LoginScreen.this).hide();
                Log.e(TAG, t.toString());
            }
        });
    }
}
