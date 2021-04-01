package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Adapters.SlidingImage_Adapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.Model.UserList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.kf.regreen.regreenproject.bsimagepicker.BSImagePicker;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpertsAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    UserList userList;
    EditText edtanswer;
    //    ImageView ivattachment1, ivattachment2, ivattachment3, ivattachment4;
    TextView txtsubmit, txtquestion;
    Intent intent;
    private PreferencesUtils mPreferences;
    int status = 0;

    private static ViewPager mPager;
    CirclePageIndicator indicator;
    RelativeLayout rlimageslider;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<String> image_Slider_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_answer);

        userList = (UserList) getIntent().getSerializableExtra("UserDetails");
        Constant.getinstance(ExpertsAnswerActivity.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(ExpertsAnswerActivity.this);
        status = getIntent().getIntExtra("status", 0);

        initialization();
    }

    public void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtanswer = (EditText) findViewById(R.id.edtanswer);
        txtsubmit = (TextView) findViewById(R.id.txtsubmit);
        txtquestion = (TextView) findViewById(R.id.txtquestion);
        rlimageslider = (RelativeLayout) findViewById(R.id.rlimageslider);
        mPager = (ViewPager) findViewById(R.id.pager);

        indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
//        ivattachment1 = (ImageView) findViewById(R.id.ivattachment1);
//        ivattachment2 = (ImageView) findViewById(R.id.ivattachment2);
//        ivattachment3 = (ImageView) findViewById(R.id.ivattachment3);
//        ivattachment4 = (ImageView) findViewById(R.id.ivattachment4);

        txtquestion.setText(setHtmlFormatValue("Question: ",userList.getQue_description()));
        toolbar.setTitle("Answer");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAttachmentPics();
        setOnClickListner();

        if (status == 1 || status == 2) {
            edtanswer.setEnabled(false);
            edtanswer.setFocusable(false);

//            txtsubmit.setEnabled(false);
//            if (status == 2) {
            txtsubmit.setVisibility(View.GONE);
//            }
            edtanswer.setText(userList.getQue_answer());
        }
    }
    public Spanned setHtmlFormatValue(String key, String value) {
        return Html.fromHtml("<font color='#1B5E20'>" + key + " </font>" + value);
    }
    public void setAttachmentPics() {
        if (!userList.getQue_image_url_1().isEmpty()) {
            image_Slider_List.add(userList.getQue_image_url_1());
        }
        if (!userList.getQue_image_url_2().isEmpty()) {
            image_Slider_List.add(userList.getQue_image_url_2());
        }
        if (!userList.getQue_image_url_3().isEmpty()) {
            image_Slider_List.add(userList.getQue_image_url_3());
        }
        if (!userList.getQue_image_url_4().isEmpty()) {
            image_Slider_List.add(userList.getQue_image_url_4());
        }

       /* for (int i = 0; i < image_Slider_List.size(); i++) {
            Log.e("SliderImage...", "" + image_Slider_List.get(i));
        }*/
        if (image_Slider_List.size() > 0) {
            rlimageslider.setVisibility(View.VISIBLE);
            init();
        } else {
            rlimageslider.setVisibility(View.GONE);
        }
    }

    private void init() {


        mPager.setAdapter(new SlidingImage_Adapter(ExpertsAnswerActivity.this, image_Slider_List));

        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = image_Slider_List.size();

        // Auto start of viewpager
      /*  final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    public void setOnClickListner() {
        txtsubmit.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtsubmit:
                if (edtanswer.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "Please enter answer", Toast.LENGTH_LONG).show();
                } else {
                    saveAnswer();
                }
                break;
        }
    }

    public void saveAnswer() {
        Constant.getinstance(ExpertsAnswerActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.saveAnswer(userList.getQue_id(), edtanswer.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(ExpertsAnswerActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String msg = jsonresult.getString(RestApi.PARAMETERS.MESSAGE);
                    if (jsonresult.getInt(RestApi.PARAMETERS.STATUS) == RestApi.PARAMETERS.STATUS_PASS) {
                        Intent intent = new Intent();
                        intent.putExtra(RestApi.PARAMETERS.STATUS, userList.getQue_status());
                        setResult(RestApi.PARAMETERS.ANSWER_REQUESTCODE, intent);
                        finish();
                    }
                    Toast.makeText(ExpertsAnswerActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(ExpertsAnswerActivity.this).hide();
            }
        });
    }
}
