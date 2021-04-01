package com.kf.regreen.regreenproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Adapters.CustomAdapter;
import com.kf.regreen.regreenproject.Adapters.CustomAdapterexpert;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExpertsDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rvItems;
    FloatingActionButton fab;
    ImageView ivParallax;
    ExpertList expertList;


    Intent intent;
    public static View v;
    ArrayList<String> expertKey = new ArrayList<>();
    ArrayList<String> expertValue = new ArrayList<>();
    int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_detail);
        expertList = (ExpertList) getIntent().getSerializableExtra("ExpertDetail");

        fillExpertDetailData();
        getDeviceWidthHeight();
        initialization();

    }

    public void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        ivParallax = (ImageView) findViewById(R.id.ivParallax);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        toolbar.setTitle(expertList.first_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerAdapter adapter = new RecyclerAdapter(this);
        rvItems.setAdapter(adapter);
        Constant.getinstance(ExpertsDetailActivity.this).setRecyclerView(rvItems);

        int imageHeight = (int) (height / 2.5);
        Picasso.with(this)
                .load(expertList.getProfile_pic()).error(R.drawable.upload_photo).resize(width, imageHeight)
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
        setOnClickListner();
    }

    public void setOnClickListner() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpertsDetailActivity.this, ExpertsQuestionActivity.class);
                intent.putExtra("ExpertDetail", expertList);
                startActivity(intent);

            }
        });
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
        Context context;
        LayoutInflater inflater;
        int[] expertIcon = new int[]{R.drawable.ic_expert_person,
                R.drawable.ic_expert_designation,
                R.drawable.ic_expert_area,
                R.drawable.ic_expert_date,
                R.drawable.ic_expert_email,
                R.drawable.ic_expert_phone,
                R.drawable.ic_expert_place};

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView titlekey, titlevalue;
            ImageView ivexperticon;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                titlekey = (TextView) itemView.findViewById(R.id.titlekey);
                titlevalue = (TextView) itemView.findViewById(R.id.titlevalue);
                ivexperticon = (ImageView) itemView.findViewById(R.id.ivexperticon);
            }
        }

        public RecyclerAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.activity_experts_detail_item, parent, false);

            RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.titlevalue.setText(expertValue.get(position));
            holder.titlekey.setText(expertKey.get(position));
            holder.ivexperticon.setImageResource(expertIcon[position]);
        }

        @Override
        public int getItemCount() {
            return expertValue.size();
        }
    }

    public void fillExpertDetailData() {

        expertKey.add("Name");
        expertValue.add(isEmptyValueCheck(expertList.first_name));
        expertKey.add("Designation");
        expertValue.add(isEmptyValueCheck(expertList.e_designation));
        expertKey.add("Area Of Expertise");
        expertValue.add(isEmptyValueCheck(expertList.e_area_experts));
        expertKey.add("Date Of Birth");
        expertValue.add(isEmptyValueCheck(expertList.birth_date));
        expertKey.add("For Further Enquiries Contact");
        expertValue.add("regreenamd@gmail.com");
        /*expertKey.add("Mobile Number");
        expertValue.add(isEmptyValueCheck(expertList.phone_no));*/
        expertKey.add("City");
        expertValue.add(isEmptyValueCheck(expertList.city));
    }

    public String isEmptyValueCheck(String value) {
        if (value.isEmpty()) {
            value = "-  -  -  -";
        }
        return value;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getDeviceWidthHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Log.e("getDeviceWidthHeight.", "" + width + "..." + height);
    }
}
