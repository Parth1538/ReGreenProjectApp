package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.ListenerUtils.FAQListener;
import com.kf.regreen.regreenproject.Adapters.UserExpertAdapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.Model.UserList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpertsFaqActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewExpertsC;
    TextView txtnotfound;

    private PreferencesUtils mPreferences;
    UserExpertAdapter userExpertAdapter;
    ArrayList<UserList> arrUserList = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_faq);

        Constant.getinstance(ExpertsFaqActivity.this).PhimpmeProgressBarHandler();
        mPreferences = new PreferencesUtils(ExpertsFaqActivity.this);
        intent = getIntent();
        initialization();
    }

    public void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtnotfound = (TextView) findViewById(R.id.txtnotfound);
        recyclerViewExpertsC = (RecyclerView) findViewById(R.id.recyclerViewExperts);


        toolbar.setTitleTextColor(Color.WHITE);
        setTitle();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewExpertsC.setLayoutManager(manager);

        getFaqList();
    }

    public void setTitle() {
//        if (intent.getIntExtra("type", 0) == 0) {
            toolbar.setTitle("FAQ");
       /* } else {
            toolbar.setTitle("Your Questions");
        }*/
    }

   /* @Override
    protected void onNewIntent(Intent intentPara) {
        super.onNewIntent(intentPara);
        intent = intentPara;
        if (intent != null) {
            setTitle();
        }
    }*/

    @Override
    public void onBackPressed() {
//        if (intent.getIntExtra("type", 0) == 0) {
            super.onBackPressed();
       /* } else {
            Intent i = new Intent(ExpertsFaqActivity.this, HomeScreenActivity.class);
            i.putExtra("TabPosition", 2);
            startActivity(i);
            finish();
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getFaqList() {
        Constant.getinstance(ExpertsFaqActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getFaq(mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(ExpertsFaqActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    JSONArray jUserList = jsonresult.getJSONArray(RestApi.PARAMETERS.RESULT);
                    arrUserList = new ArrayList<>();
                    for (int i = 0; i < jUserList.length(); i++) {
                        JSONObject jUser = jUserList.getJSONObject(i);
                        UserList userList = Constant.getinstance(ExpertsFaqActivity.this).setUserList(jUser);
                        userList.setQue_status(2);
                        arrUserList.add(userList);
                    }
                    fillFaqData(2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(ExpertsFaqActivity.this).hide();
            }
        });
    }

    public void fillFaqData(int status) {
        if (arrUserList.size() > 0) {
            userExpertAdapter = new UserExpertAdapter(ExpertsFaqActivity.this, arrUserList, status, faqListener);
            recyclerViewExpertsC.setAdapter(userExpertAdapter);
            recyclerViewExpertsC.setVisibility(View.VISIBLE);
            txtnotfound.setVisibility(View.GONE);
        } else {
            recyclerViewExpertsC.setVisibility(View.GONE);
            txtnotfound.setVisibility(View.VISIBLE);
            txtnotfound.setText(getResources().getString(R.string.nodatafound));
        }
    }

    FAQListener faqListener = new FAQListener() {
        @Override
        public void onSuccess(int index, int type) {

        }
    };


}
