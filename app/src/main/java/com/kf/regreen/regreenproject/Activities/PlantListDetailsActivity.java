package com.kf.regreen.regreenproject.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Model.PlantList;
import com.kf.regreen.regreenproject.R;
import com.squareup.picasso.Picasso;

public class PlantListDetailsActivity extends Activity {
    ImageView ivplantspic;
    TextView toolbar_title, plantname, plantbotaname, plantengname, planthabit, plantfamily, plantwatering, plantlocation, plantuse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_details);
        initialization();
    }

    public void initialization() {
        ivplantspic = (ImageView) findViewById(R.id.ivplantspic);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        plantname = (TextView) findViewById(R.id.plantname);
        plantbotaname = (TextView) findViewById(R.id.plantbotaname);
        plantengname = (TextView) findViewById(R.id.plantengname);
        planthabit = (TextView) findViewById(R.id.planthabit);
        plantfamily = (TextView) findViewById(R.id.plantfamily);
        plantwatering = (TextView) findViewById(R.id.plantwatering);
        plantlocation = (TextView) findViewById(R.id.plantlocation);
        plantuse = (TextView) findViewById(R.id.plantuse);

        toolbar_title.setText(getIntent().getStringExtra("CategoryName"));

    /*    intent.putExtra("Watering", PlantList.getArr_watering().get(position));
        intent.putExtra("Location", PlantList.getArr_location().get(position));
        intent.putExtra("Plant_use", PlantList.getArr_plantuse().get(position));*/

        plantname.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_name), getIntent().getStringExtra("Name")));
        plantbotaname.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_botname), getIntent().getStringExtra("BotanicalName")));
        plantengname.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_engname), getIntent().getStringExtra("EnglishName")));
        planthabit.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_habit), getIntent().getStringExtra("Habit")));
        plantfamily.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_family), getIntent().getStringExtra("Family")));
        plantwatering.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_watering), getIntent().getStringExtra("Watering")));
        plantlocation.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_location), getIntent().getStringExtra("Location")));
        plantuse.setText(setHtmlFormatValue(getResources().getString(R.string.plant_detail_use), getIntent().getStringExtra("Plant_use")));

        Picasso.with(this)
                .load(getIntent().getStringExtra("Url")).error(R.drawable.upload_photo)
                .into(ivplantspic);

    }

    public Spanned setHtmlFormatValue(String key, String value) {
        return Html.fromHtml("<font color='#1B5E20'> <b>" + key + " </b></font>" + value);
    }
}
