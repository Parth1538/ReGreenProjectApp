package com.kf.regreen.regreenproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.kf.regreen.regreenproject.bsimagepicker.BSImagePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

import static com.kf.regreen.regreenproject.Utils.Constant.device_type;

public class ExpertsQuestionActivity extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener, View.OnClickListener {

    Toolbar toolbar;
    ImageView ivParallax;
    ExpertList expertList;
    EditText edtquestion, edtdescription;
    ImageView ivattachment1, ivattachment2, ivattachment3, ivattachment4;
    TextView txtsubmit;
    String filePath = "";
    Intent intent;
    private PreferencesUtils mPreferences;
    int attachment = 0;
    String[] fileImagePath = new String[]{"NoImage", "NoImage", "NoImage", "NoImage"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_question);
        expertList = (ExpertList) getIntent().getSerializableExtra("ExpertDetail");
        Constant.getinstance(ExpertsQuestionActivity.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(ExpertsQuestionActivity.this);
        initialization();
    }

    public void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        edtquestion = (EditText) findViewById(R.id.edtquestion);
        edtdescription = (EditText) findViewById(R.id.edtdescription);
        txtsubmit = (TextView) findViewById(R.id.txtsubmit);
        ivattachment1 = (ImageView) findViewById(R.id.ivattachment1);
        ivattachment2 = (ImageView) findViewById(R.id.ivattachment2);
        ivattachment3 = (ImageView) findViewById(R.id.ivattachment3);
        ivattachment4 = (ImageView) findViewById(R.id.ivattachment4);

        toolbar.setTitle("Add Question");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*
        ivParallax = (ImageView) findViewById(R.id.ivParallax);
          AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        Picasso.with(this)
                .load(expertList.getProfile_pic()).error(R.drawable.upload_photo)
                .into(ivParallax);


       barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
//                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
//                    hideOption(R.id.action_info);
                }
            }
        });
*/
        setOnClickListner();
    }

    public void setOnClickListner() {
        txtsubmit.setOnClickListener(this);
        ivattachment1.setOnClickListener(this);
        ivattachment2.setOnClickListener(this);
        ivattachment3.setOnClickListener(this);
        ivattachment4.setOnClickListener(this);
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
//        filePath = uri.getPath();
        fileImagePath[attachment - 1] = uri.getPath();
        if (attachment == 1) {
            Picasso.with(this).load(uri).into(ivattachment1);
        } else if (attachment == 2) {
            Picasso.with(this).load(uri).into(ivattachment2);
        } else if (attachment == 3) {
            Picasso.with(this).load(uri).into(ivattachment3);
        } else if (attachment == 4) {
            Picasso.with(this).load(uri).into(ivattachment4);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivattachment1:
                attachment = 1;
                showImagePicker();
                break;
            case R.id.ivattachment2:
                attachment = 2;
                showImagePicker();
                break;
            case R.id.ivattachment3:
                attachment = 3;
                showImagePicker();
                break;
            case R.id.ivattachment4:
                attachment = 4;
                showImagePicker();
                break;

            case R.id.txtsubmit:
                if (edtquestion.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "Please enter question", Toast.LENGTH_LONG).show();
                } else if (edtdescription.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "Please enter description", Toast.LENGTH_LONG).show();
                } else {
                    expertQuestionUpload();
                }

                break;
        }
    }

    public void showImagePicker() {
        BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                .build();
        pickerDialog.show(getSupportFragmentManager(), "picker");
    }

    /*  public void expertQuestionUpload() {
          Constant.getinstance(this).show();
          Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
          ApiList restInterface = retrofit.create(ApiList.class);
          Call<ResponseBody> call;
          RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), mPreferences.getPrefUserId());
          RequestBody expert_id = RequestBody.create(MediaType.parse("text/plain"), expertList.getUser_id());
          RequestBody title = RequestBody.create(MediaType.parse("text/plain"), edtquestion.getText().toString());
          RequestBody description = RequestBody.create(MediaType.parse("text/plain"), edtdescription.getText().toString());

          //image
          MultipartBody.Part body = null;
          if (filePath.length() > 0) {
              File file = new File(filePath);
              RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
              body = MultipartBody.Part.createFormData(RestApi.PARAMETERS.BILL_IMAGE, "expertquestion.png", reqFile);
          }
          call = restInterface.expertQuestionUpload(user_id, expert_id,
                  title,
                  description, body);
          call.enqueue(new Callback<ResponseBody>() {
              @Override
              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  try {
                      Constant.getinstance(ExpertsQuestionActivity.this).hide();
                      String result = response.body().string();
                      JSONObject objJson = new JSONObject(result);
                      String msg = objJson.optString(RestApi.PARAMETERS.MESSAGE);
                      int status = objJson.optInt(RestApi.PARAMETERS.STATUS);
                      if (status == RestApi.PARAMETERS.STATUS_PASS) {
                          finish();
                      }
                      Toast.makeText(ExpertsQuestionActivity.this, msg, Toast.LENGTH_LONG).show();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }

              @Override
              public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Constant.getinstance(ExpertsQuestionActivity.this).hide();
              }
          });
      }*/
    public void expertQuestionUpload() {
        Constant.getinstance(this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart(RestApi.PARAMETERS.EXPERT_USER_ID, mPreferences.getPrefUserId());
        builder.addFormDataPart(RestApi.PARAMETERS.EXPERT_ID, expertList.getUser_id());
        builder.addFormDataPart(RestApi.PARAMETERS.EXPERT_NAME, edtquestion.getText().toString());
        builder.addFormDataPart(RestApi.PARAMETERS.EXPERT_DESC, edtdescription.getText().toString());


        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
        for (int i = 0; i < fileImagePath.length; i++) {
            if (!fileImagePath[i].equalsIgnoreCase("NoImage")) {
                File file = new File(fileImagePath[i]);
//            builder.addFormDataPart("file[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                builder.addFormDataPart("image[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        }

        MultipartBody requestBody = builder.build();
        Call<ResponseBody> call = restInterface.expertQuestionUpload(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(ExpertsQuestionActivity.this).hide();
                    String result = response.body().string();
                    JSONObject objJson = new JSONObject(result);
                    String msg = objJson.optString(RestApi.PARAMETERS.MESSAGE);
                    int status = objJson.optInt(RestApi.PARAMETERS.STATUS);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        finish();
                    }
                    Toast.makeText(ExpertsQuestionActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(ExpertsQuestionActivity.this).hide();
            }
        });

    }
}
