package com.kf.regreen.regreenproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;

/**
 * Created by admin on 11/02/2018.
 */


public class CustomHomePageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    int[] mResourcesC;
    RelativeLayout.LayoutParams rel_image;



    public CustomHomePageAdapter(Context context,int[] mResources) {
        mContext = context;
        mResourcesC = mResources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rel_image = new RelativeLayout.LayoutParams((540*Constant.getDeviceWidth((Activity) mContext))/540,(312* Constant.getDeviceHeight((Activity) mContext))/960);
    }

    @Override
    public int getCount() {
        return mResourcesC.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.home_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgHomePager);
        imageView.setLayoutParams(rel_image);
        imageView.setImageResource(mResourcesC[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
    }
}