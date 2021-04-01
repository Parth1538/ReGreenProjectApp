package com.kf.regreen.regreenproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CongratulationsActivity extends AppCompatActivity {

    TextView txtDiscountPercent, txttitle, txtfinish;
    LinearLayout lldiscount;
    String type = "";
    private PreferencesUtils mPreferences;
    private String result = "", discount_val = "";
    String barcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulation_screen);

        Intent i = getIntent();
        type = i.getStringExtra("type");
        Constant.getinstance(this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(CongratulationsActivity.this);

        initialization();
        setBarcodeValue();

    }

    public void initialization() {

        txtDiscountPercent = (TextView) findViewById(R.id.txtDiscountPercent);
        txttitle = (TextView) findViewById(R.id.txttitle);
        txtfinish = (TextView) findViewById(R.id.txtfinish);
        lldiscount = (LinearLayout) findViewById(R.id.lldiscount);

        txtfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDiscountPercent.getText().toString().equalsIgnoreCase("10%")) {
                    checkWelcomeDiscount();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    public void setBarcodeValue() {

        barcode = getIntent().getStringExtra("code");
//        Log.e("setBarcodeValue...",""+barcode);
        try {
            if (TextUtils.isEmpty(barcode)) {
                Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (barcode.equalsIgnoreCase("250")
                        || barcode.equalsIgnoreCase("500")
                        || barcode.equalsIgnoreCase("1000")
                        || barcode.equalsIgnoreCase("2000")
                        || barcode.equalsIgnoreCase("5000")
                        || barcode.equalsIgnoreCase("10000")) {
                    showAlertRedemption("Are you sure you want to redeem " + barcode + " Points?");
                } else if (barcode.equalsIgnoreCase("10% discount")) {
                    showAlertRedemption("Are you sure you want to use " + barcode + " ?");
                } else {
                    finish();
                    Toast.makeText(getApplicationContext(), "Invalid QR code", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
        }
    }


    private void searchBarcode() {
        Constant.getinstance(CongratulationsActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.checkDiscount(mPreferences.getPrefUserId(), barcode);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Constant.getinstance(CongratulationsActivity.this).hide();
                    result = response.body().string();
                    JSONObject jsonresult = null;
                    jsonresult = new JSONObject(result);
                    int status = jsonresult.getInt(RestApi.PARAMETERS.STATUS);
                    String message = jsonresult.getString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        lldiscount.setVisibility(View.VISIBLE);
                        if (message.equalsIgnoreCase("Congratulation you have got 10% welcome discount")) {
//                        showWelcomeDiscount(message);
                            txtDiscountPercent.setText("10%");
                        } else {
                            if (jsonresult.getString(RestApi.PARAMETERS.DISCOUNT).equalsIgnoreCase("one plant free")) {
                                txtDiscountPercent.setText(jsonresult.getString(RestApi.PARAMETERS.DISCOUNT));
                            } else {
                                txtDiscountPercent.setText(jsonresult.getString(RestApi.PARAMETERS.DISCOUNT) + "%");
                            }
                        }
                    } else {
                        showWelcomeDiscount(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(CongratulationsActivity.this).hide();
            }
        });
    }

    public void showAlertRedemption(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                searchBarcode();
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void showWelcomeDiscount(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void checkWelcomeDiscount() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.checkWelcomeDiscount(mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
