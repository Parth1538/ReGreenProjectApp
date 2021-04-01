package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kf.regreen.regreenproject.Adapters.ImageAdapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.PlanCategories;
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

public class MapMainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    double lat = 23.167875;
    double longs = 72.63723;
    String name = "", nur_id = "", owner_name, address, contact_no, image_url = "";
    public static View topView;
    public static ViewGroup topViewGroup;
    Typeface tf_nub, tf_nul, tf_nl;
    TextView txtplantsavailable, txtabout, txtnurseryname, txtaddress, txtcontactname, txtcontactno, txtNurseryNameMap, txtNurseryAddMap, txtnodatafound;
    ScrollView svabout;
    RelativeLayout mContainer;
    LinearLayout llplantavailable;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.img_plants_available, R.drawable.img_tools_available2,
            R.mipmap.tut_bg
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main_two);

        tf_nul = Typeface.createFromAsset(getAssets(),
                "fonts/nunito_extra_light.ttf");
        tf_nub = Typeface.createFromAsset(getAssets(),
                "fonts/nunito_medium.ttf");

        tf_nl = Typeface.createFromAsset(getAssets(),
                "fonts/nunito_light.ttf");

        Intent i = getIntent();
        name = i.getStringExtra("name");
        lat = i.getDoubleExtra("lat", 0);
        longs = i.getDoubleExtra("long", 0);
        nur_id = i.getStringExtra("nur_id");
        owner_name = i.getStringExtra("owner_name");
        address = i.getStringExtra("address");
        contact_no = i.getStringExtra("contact_no");
        image_url = i.getStringExtra("image_url");

        Constant.getinstance(this).PhimpmeProgressBarHandler();
        initialization();


    }

    public void initialization() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView2);

        mapFragment.getMapAsync(this);

        llplantavailable = (LinearLayout) findViewById(R.id.llplantavailable);
        txtnodatafound = (TextView) findViewById(R.id.txtnodatafound);
        txtplantsavailable = (TextView) findViewById(R.id.txtplantsavailable);
        txtabout = (TextView) findViewById(R.id.txtabout);
        txtnurseryname = (TextView) findViewById(R.id.txtnurseryname);
        txtaddress = (TextView) findViewById(R.id.txtaddress);
        txtcontactname = (TextView) findViewById(R.id.txtcontactname);
        txtcontactno = (TextView) findViewById(R.id.txtcontactno);
        txtNurseryNameMap = (TextView) findViewById(R.id.txtNurseryNameMap);
        txtNurseryAddMap = (TextView) findViewById(R.id.txtNurseryAddMap);

        svabout = (ScrollView) findViewById(R.id.svabout);
        mContainer = (RelativeLayout) findViewById(R.id.mContainer);

        txtNurseryNameMap.setText(name);
        txtNurseryAddMap.setText(address);
        txtNurseryAddMap.setVisibility(View.GONE);

        txtplantsavailable.setOnClickListener(this);
        txtabout.setOnClickListener(this);
        visiblePlantAvailableView();
        getNurseryPlantsCategoryList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng data = new LatLng(lat, longs);
        googleMap.addMarker(new MarkerOptions().position(data)
                .title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(data));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtplantsavailable:
                visiblePlantAvailableView();
                break;
            case R.id.txtabout:
                visibleAboutView();
                break;
        }
    }

    private void visiblePlantAvailableView() {
        txtabout.setTextColor(Color.BLACK);
        txtplantsavailable.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        llplantavailable.setVisibility(View.VISIBLE);
        svabout.setVisibility(View.GONE);


    }


    private void visibleAboutView() {
        txtabout.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        txtplantsavailable.setTextColor(Color.BLACK);
        llplantavailable.setVisibility(View.GONE);
        svabout.setVisibility(View.VISIBLE);
        txtnurseryname.setText(name);
        txtaddress.setText(address);
        txtcontactname.setText(owner_name);
        txtcontactno.setText(contact_no);
    }

    public void getNurseryPlantsCategoryList() {
        Constant.getinstance(MapMainActivity.this).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.plantsListByNursery(nur_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(MapMainActivity.this).hide();
                    String result = response.body().string();
                    JSONObject jsonresult = null;
                    jsonresult = new JSONObject(result);
                    PlanCategories plant_cat_list = new PlanCategories(jsonresult);
                    if (plant_cat_list.getRecordsTotal() != 0) {
                        initializationPlants();
                        txtnodatafound.setVisibility(View.GONE);
                        mContainer.setVisibility(View.VISIBLE);
                    } else {
                        txtnodatafound.setVisibility(View.VISIBLE);
                        mContainer.setVisibility(View.GONE);
                        txtnodatafound.setText(R.string.no_plant_cat);
//                        Constant.customshowAlertwithOneButton(MapMainActivity.this, R.string.no_plant_cat, "Ok");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(MapMainActivity.this).hide();
                // Log error here since request failed
            }
        });
    }

    private void initializationPlants() {
        ImageAdapter mAdapter = new ImageAdapter(this, 1);
        final View view = getLayoutInflater().inflate(R.layout.image_grid, mContainer, true);
        ParallaxGridView mGridView = (ParallaxGridView) view.findViewById(R.id.parallaxGridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MapMainActivity.this, PlantListActivity.class);
                intent.putExtra("CategoryID", PlanCategories.getArr_id().get(position));
                intent.putExtra("CategoryName", PlanCategories.getArr_name().get(position));
                intent.putExtra("nur_id",nur_id);
                startActivity(intent);

               /* cat_id = PlanCategories.getArr_id().get(position);
                String cat_name = PlanCategories.getArr_name().get(position);
                Log.d(TAG,cat_id+" "+cat_name+" "+position);

                Bundle bundle = new Bundle();
                bundle.putString("cat_id", cat_id);

                Fragment fragment = new PlantListActivity();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();*/
            }
        });
//        mGridView.setParallaxView(getLayoutInflater().inflate(R.layout.plants_top_layout, mGridView, false));
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(3);
    }
}
