package com.kf.regreen.regreenproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kf.regreen.regreenproject.Activities.LoginScreen;
import com.kf.regreen.regreenproject.Activities.SignUpForm;
import com.kf.regreen.regreenproject.Interface.ProgressDisplay;
import com.kf.regreen.regreenproject.Model.UserList;
import com.kf.regreen.regreenproject.R;
import com.rw.loadingdialog.LoadingView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by admin on 26/12/2017.
 */

public class Constant {

    public static final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 12;
    public static final int REQUEST_PERMISSION_READ_PHONE_STATE = 14;
    public static final int RESPONSE_SETTINGS = 19;
    public static final int REQUEST_PERMISSION_CAMERA = 13;
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String SHARED_PREF = "ah_firebase";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    private ProgressBar mProgressBar;
    RelativeLayout rl;
    LoadingView loadingView;
    public static Constant objconstant;
    public static Activity mactivity;
    public static String device_type = "0";
    public static String TABPOSITION = "TabPosition";

    public static Constant getinstance(Activity activity) {
        mactivity = activity;
        if (objconstant == null) {
            objconstant = new Constant();
        }
        return objconstant;
    }

    public static OkHttpClient gethttpclient() {

        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = null;
        try {
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        builder.readTimeout(120, TimeUnit.SECONDS);
        builder.connectTimeout(120, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
// builder.addInterceptor(new Interceptor() {
// @Override
// public okhttp3.Response intercept(Chain chain) throws IOException {
// okhttp3.Response response = chain.proceed(chain.request());
// Request request = chain.request();
// return chain.proceed(request);
// }
// });

// OkHttpClient client = new OkHttpClient();
// client.sslSocketFactory(sslSocketFactory);

        return builder.build();

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void setError(Context context, EditText editText, String msg) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(300);
        Drawable error = (Drawable) context.getResources().getDrawable(R.drawable.ic_error21);
        error.setBounds(0, 0, error.getIntrinsicWidth(), error.getIntrinsicHeight());
        editText.setError(msg, error);
    }

    public static boolean isNotNull(String str) {
        return !(str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0);
    }

    public static void customshowAlertwithOneButton(@NonNull final Context context, @StringRes int resId, String positiveButtonmsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((Activity) context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    public static void customshowAlert(@NonNull final Context context, @StringRes int resId, String positiveButtonmsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
//                ((Activity)context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    public static void loginAlert(@NonNull final Context context, String resId, String positiveButtonmsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Intent intent = new Intent((Activity) context, LoginScreen.class);
                context.startActivity(intent);
                ((Activity) context).finish();
//                ((Activity)context).finish();
            }
        });
        alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }


    public static void customshowAlertwithOneButtonString(@NonNull final Context context, String serverMsg, String positiveButtonmsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(serverMsg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((Activity) context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    public static void customshowAlertSign(@NonNull final Context context, String serverMsg, String positiveButtonmsg, final EditText edtText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(serverMsg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (edtText != null)
                    edtText.setText("");
//                ((Activity)context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }


    public static void doColorSpanForFirstString(Context c, String firstString,
                                                 String lastString, TextView txtSpan) {

        String changeString = (firstString != null ? firstString : "");

        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(c.getResources()
                .getColor(R.color.profile_arrow_orange)), 0, changeString.length(), 0);

        txtSpan.setText(spanText);
    }

    public static void doColorSpanForSecondString(Context c, String firstString,
                                                  String lastString, TextView txtSpan) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + " " + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(c.getResources()
                .getColor(R.color.profile_arrow_orange)), String.valueOf(firstString)
                .length(), totalString.length(), 0);
        txtSpan.setText(spanText);
    }

    public static void doColorForMoreThanOneText(Context c, String firstString, String secondString, String thirdString, String lastString, TextView txtSpan) {

        String changeString2 = (secondString != null ? secondString : "");
        String changeString3 = (thirdString != null ? thirdString : "");
        String changeStringlst = (lastString != null ? lastString : "");

        String totalString = firstString + " " + changeString2 + " " + changeString3 + " " + changeStringlst;

        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(c.getResources()
                .getColor(R.color.profile_arrow_orange)), String.valueOf(firstString)
                .length(), changeString2.length(), 0);
        txtSpan.setText(spanText);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // DATE-TIME
    public static String getCurrentDateTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00-00 00:00:00";
        }
    }

    public static int getDeviceWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getDeviceHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public void PhimpmeProgressBarHandler() {
     /*   loadingView = new LoadingView.Builder(mactivity)
                .setProgressColorResource(R.color.colorPrimaryDark)
                .setBackgroundColorRes(R.color.white)
                .setProgressStyle(LoadingView.ProgressStyle.HORIZONTAL)
                .setCustomMargins(0, 100, 100, 0)
                .attachTo(mactivity);*/

        ViewGroup layout = (ViewGroup) mactivity.findViewById(android.R.id.content)
                .getRootView();
        mProgressBar = new ProgressBar(mactivity, null, android.R.attr.progressBarStyle);
        mProgressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        rl = new RelativeLayout(mactivity);
        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);
        layout.addView(rl, params);
        hide();
    }

    public void show() {
        Log.e("Show...", "IsProgress");
        mProgressBar.setVisibility(View.VISIBLE);
        rl.setVisibility(View.VISIBLE);
    }

    public void hide() {
        Log.e("hide...", "IsProgress");
        mProgressBar.setVisibility(View.GONE);
        rl.setVisibility(View.GONE);
    }

    public void showProgress() {
        if (mactivity instanceof ProgressDisplay) {
            ((ProgressDisplay) mactivity).showProgress();
        }
    }

    public void hideProgress() {
        if (mactivity instanceof ProgressDisplay) {
            ((ProgressDisplay) mactivity).hideProgress();
        }
    }


    public String getDeviceToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public void setTitle(String title) {
        TextView toolbar_title = (TextView) mactivity.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(mactivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public UserList setUserList(JSONObject jUser) {
        UserList userList = new UserList();
        try {
            userList.setUser_id(jUser.getString(RestApi.PARAMETERS.USER_EQ_U_ID));
            userList.setUser_name(jUser.getString(RestApi.PARAMETERS.REG_USER_NAME));
            userList.setUser_email(jUser.getString(RestApi.PARAMETERS.REG_USER_EMAIL));
            userList.setUser_phoneno(jUser.getString(RestApi.PARAMETERS.REG_USER_PHONE));
            userList.setUser_pic(jUser.getString(RestApi.PARAMETERS.REG_USER_PROFILEPIC));

            if (jUser.has(RestApi.PARAMETERS.EXPERT_NAME_FAQ)) {
                userList.setExpert_name(jUser.getString(RestApi.PARAMETERS.EXPERT_NAME_FAQ));
            }
            userList.setQue_id(jUser.getString(RestApi.PARAMETERS.USER_EQ_ID));
            userList.setQue_title(jUser.getString(RestApi.PARAMETERS.USER_EQ_NAME));
            userList.setQue_description(jUser.getString(RestApi.PARAMETERS.USER_EQ_DESC));
            JSONArray jQuestionPics = jUser.getJSONArray(RestApi.PARAMETERS.USER_QUESTION_PICS);
            if (jQuestionPics.length() > 0) {
                for (int j = 0; j < jQuestionPics.length(); j++) {
                    JSONObject jQuestionPic = jQuestionPics.getJSONObject(j);
                    if (j == 0) {
                        userList.setQue_image_url_1(jQuestionPic.getString(RestApi.PARAMETERS.USER_QUESTION_PIC));
                    } else if (j == 1) {
                        userList.setQue_image_url_2(jQuestionPic.getString(RestApi.PARAMETERS.USER_QUESTION_PIC));
                    } else if (j == 2) {
                        userList.setQue_image_url_3(jQuestionPic.getString(RestApi.PARAMETERS.USER_QUESTION_PIC));
                    } else if (j == 3) {
                        userList.setQue_image_url_4(jQuestionPic.getString(RestApi.PARAMETERS.USER_QUESTION_PIC));
                    }
                }
            }
            userList.setQue_date(jUser.getString(RestApi.PARAMETERS.USER_EQ_DATE));
            userList.setQue_answer(jUser.getString(RestApi.PARAMETERS.USER_EQ_ANSWER));
            userList.setQue_status(2);

        } catch (Exception e) {
        }
        return userList;
    }
}
