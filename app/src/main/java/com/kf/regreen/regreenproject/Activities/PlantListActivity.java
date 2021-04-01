package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Adapters.ImageAdapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.PlanCategories;
import com.kf.regreen.regreenproject.Model.PlantList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.poliveira.apps.parallaxlistview.ParallaxGridView;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlantListActivity extends AppCompatActivity {

    RelativeLayout mContainer;
    TextView toolbar_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        mContainer = (RelativeLayout) findViewById(R.id.mContainer);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(getIntent().getStringExtra("CategoryName"));

        Constant.getinstance(PlantListActivity.this).PhimpmeProgressBarHandler();

        plantListAPI(getIntent().getStringExtra("CategoryID"), getIntent().getStringExtra("nur_id"));
    }

    public void plantListAPI(String ind, String nursery_id) {
        Constant.getinstance(PlantListActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);

        Call<ResponseBody> call = restInterface.plantList(ind, nursery_id);
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(PlantListActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    final PlantList plant_list = new PlantList(jsonresult);
                    if (plant_list.getRecordsTotal() != 0) {
                        ImageAdapter mAdapter = new ImageAdapter(PlantListActivity.this, 2);
                        final View view = getLayoutInflater().inflate(R.layout.image_grid, mContainer, true);
                        ParallaxGridView mGridView = (ParallaxGridView) view.findViewById(R.id.parallaxGridview);
                        mGridView.setVerticalSpacing(10);
                        mGridView.setHorizontalSpacing(10);
                        mGridView.setAdapter(mAdapter);
                        mGridView.setNumColumns(3);
                        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(PlantListActivity.this, PlantListDetailsActivity.class);
                                intent.putExtra("Name", PlantList.getArr_name().get(position));
                                intent.putExtra("CategoryName", getIntent().getStringExtra("CategoryName"));
                                intent.putExtra("BotanicalName", PlantList.getArr_botanical_name().get(position));
                                intent.putExtra("EnglishName", PlantList.getArr_english_name().get(position));
                                intent.putExtra("Family", PlantList.getArr_family().get(position));
                                intent.putExtra("Habit", PlantList.getArr_habit().get(position));
                                intent.putExtra("Watering", PlantList.getArr_watering().get(position));
                                intent.putExtra("Location", PlantList.getArr_location().get(position));
                                intent.putExtra("Plant_use", PlantList.getArr_plantuse().get(position));
                                intent.putExtra("Url", PlantList.getArr_image_url().get(position));
                                startActivity(intent);
                            }
                        });
                    } else {
                        Constant.customshowAlertwithOneButton(PlantListActivity.this, R.string.no_plant, "Ok");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(PlantListActivity.this).hide();
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
