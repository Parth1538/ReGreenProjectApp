package com.kf.regreen.regreenproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Model.NurseryPlanCategories;
import com.kf.regreen.regreenproject.Model.PlanCategories;
import com.kf.regreen.regreenproject.Model.PlantList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String callFragName = "";
    Transformation transformation;
    Typeface tf_nub;
    int isPlantsType = 0;

    // Constructor
    public ImageAdapter(Context c, int isPlantsTypePara) {
        mContext = c;
        final int radius = 23;
        final int margin = 23;
        isPlantsType = isPlantsTypePara;
        transformation = new RoundedCornersTransformation(radius, margin);
        tf_nub = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/nunito_medium.ttf");

    }

    @Override
    public int getCount() {
        if (isPlantsType == 0) {
            return NurseryPlanCategories.getArr_id().size();
        } else if (isPlantsType == 1) {
            return PlanCategories.getArr_id().size();
        } else {
            return PlantList.getArr_id().size();
        }

    }

    @Override
    public Object getItem(int position) {
        if (isPlantsType == 0) {
            return NurseryPlanCategories.getArr_id().get(position);
        } else if (isPlantsType == 1) {
            return PlanCategories.getArr_id().get(position);
        } else {
            return PlantList.getArr_id().get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.custom_image_for_gridview, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.imgGridPlant);
            TextView txtPlantCat = (TextView) grid.findViewById(R.id.txtPlCat);
            txtPlantCat.setTypeface(tf_nub);
            txtPlantCat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (21 * Constant.getDeviceWidth((Activity) mContext)) / 540);

            if (isPlantsType == 0) {
                Picasso.with(mContext)
                        .load(NurseryPlanCategories.getArr_image_url().get(position))
                        .resize((212 * Constant.getDeviceWidth((Activity) mContext)) / 640, (200 * Constant.getDeviceHeight((Activity) mContext)) / 960)
                        .onlyScaleDown()
                        .transform(transformation)
                        .into(imageView);
//                txtPlantCat.setText(PlanCategories.getArr_name().get(position));
                txtPlantCat.setText(NurseryPlanCategories.getArr_name().get(position));
            } else if (isPlantsType == 1) {
                Picasso.with(mContext)
                        .load(PlanCategories.getArr_image_url().get(position))
                        .resize((212 * Constant.getDeviceWidth((Activity) mContext)) / 640, (200 * Constant.getDeviceHeight((Activity) mContext)) / 960)
                        .onlyScaleDown()
                        .transform(transformation)
                        .into(imageView);
//                txtPlantCat.setText(PlanCategories.getArr_name().get(position));
                txtPlantCat.setText(PlanCategories.getArr_name().get(position));
            } else {
                Picasso.with(mContext)
                        .load(PlantList.getArr_image_url().get(position))
                        .resize((212 * Constant.getDeviceWidth((Activity) mContext)) / 640, (200 * Constant.getDeviceHeight((Activity) mContext)) / 960)
                        .onlyScaleDown()
                        .transform(transformation)
                        .into(imageView);
//                txtPlantCat.setText(PlanCategories.getArr_name().get(position));
                txtPlantCat.setText(PlantList.getArr_name().get(position));
            }

            int color = Color.parseColor("#70000000"); //The color u want to change
            imageView.setColorFilter(color);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}