package com.kf.regreen.regreenproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Adapters.CustomAdapter;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;

import java.util.ArrayList;

public class NursaryActivity extends AppCompatActivity {

    Toolbar toolbarNurseryC;
    RecyclerView recyclerViewNurseryC;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    CustomAdapter customAdapter;

    ImageView imgNurseryC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursary);

        toolbarNurseryC = (Toolbar)findViewById(R.id.toolbarNursery);
        setSupportActionBar(toolbarNurseryC);

        imgNurseryC = (ImageView)findViewById(R.id.imgPlantAndNursery);
        imgNurseryC.setImageResource(R.mipmap.nursserrysel);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerViewNurseryC = (RecyclerView) findViewById(R.id.recyclerViewNursery);
        Constant.getinstance(NursaryActivity.this).setRecyclerView(recyclerViewNurseryC);

        final ArrayList<String> nurseryNameList = new ArrayList<>();
        nurseryNameList.add("Raj Plants Store");
        nurseryNameList.add("Raj Plants Store");
        nurseryNameList.add("Raj Plants Store");
        nurseryNameList.add("Raj Plants Store");
        nurseryNameList.add("Raj Plants Store");


        final ArrayList<String> distanceList = new ArrayList<>();
        distanceList.add("1.2 k.m.");
        distanceList.add("5 k.m.");
        distanceList.add("12 k.m.");
        distanceList.add("11 k.m.");
        distanceList.add("21 k.m.");

        final ArrayList<Integer> recyclerNurseryImageList = new ArrayList<>();
        recyclerNurseryImageList.add(R.mipmap.tut_bg);
        recyclerNurseryImageList.add(R.mipmap.tut_bg);
        recyclerNurseryImageList.add(R.mipmap.tut_bg);
        recyclerNurseryImageList.add(R.mipmap.tut_bg);
        recyclerNurseryImageList.add(R.mipmap.tut_bg);

        ParallaxRecyclerAdapter<String> stringAdapter = new ParallaxRecyclerAdapter<>(nurseryNameList);
        stringAdapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((SimpleViewHolder)viewHolder).txtNurseryNameC.setText(nurseryNameList.get(i));
                ((SimpleViewHolder)viewHolder).txtDistanceC.setText(distanceList.get(i));
                ((SimpleViewHolder)viewHolder).imgNurseryC.setImageDrawable(getResources().getDrawable(R.mipmap.tut_bg));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new SimpleViewHolder(getLayoutInflater().inflate(R.layout.nursery_detail_screen, viewGroup, false));
            }
            @Override
            public int getItemCount() {
                return nurseryNameList.size();
            }
        });

        stringAdapter.setParallaxHeader(getLayoutInflater().inflate(R.layout.nursery_top_layout, recyclerViewNurseryC, false), recyclerViewNurseryC);
        stringAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                //TODO: implement toolbar alpha. See README for details
            }
        });
        recyclerViewNurseryC.setAdapter(stringAdapter);
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        //CardView cv;
        TextView txtNurseryNameC;
        TextView txtDistanceC;
        ImageView imgNurseryC;

        public SimpleViewHolder(View itemView) {
            super(itemView);
////            cv = (CardView)itemView.findViewById(R.id.card);
            txtNurseryNameC = (TextView)itemView.findViewById(R.id.txtNurseryName);
            txtDistanceC = (TextView)itemView.findViewById(R.id.txtDistance);
            imgNurseryC = (ImageView)itemView.findViewById(R.id.imgNursery);
        }
    }

    public String getListString(int position) {
        return position + " - android";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
