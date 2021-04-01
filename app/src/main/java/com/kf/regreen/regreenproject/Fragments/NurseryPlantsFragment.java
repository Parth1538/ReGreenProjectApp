package com.kf.regreen.regreenproject.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Activities.ExpertsAnswerActivity;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Activities.MapMainActivity;
import com.kf.regreen.regreenproject.Activities.PlantListActivity;
import com.kf.regreen.regreenproject.Adapters.ImageAdapter;
import com.kf.regreen.regreenproject.Adapters.SlidingImage_Adapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.NurseryList;
import com.kf.regreen.regreenproject.Model.NurseryPlanCategories;
import com.kf.regreen.regreenproject.Model.PlanCategories;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.poliveira.apps.parallaxlistview.ParallaxGridView;
import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link NurseryPlantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NurseryPlantsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    View v;
    String TAG = "NurseryAndPlants", result = "";
    RecyclerView recyclerViewNurseryC;
    TextView txtNursery, txtPlants;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ProgressDialog progress;
    RelativeLayout mContainer;
    LinearLayout llplantheader;

    ViewPager mPager;
    CirclePageIndicator indicator;
    RelativeLayout rlimageslider;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    public NurseryPlantsFragment() {
        // Required empty public constructor
    }

    public static NurseryPlantsFragment newInstance() {
        return new NurseryPlantsFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NurseryPlantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static NurseryPlantsFragment newInstance(String param1, String param2) {
        NurseryPlantsFragment fragment = new NurseryPlantsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_nursery_plants, container, false);

        Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.plants_and_nursery));

//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();
        txtNursery = (TextView) v.findViewById(R.id.txtNursery);
        txtPlants = (TextView) v.findViewById(R.id.txtPlants);
        mContainer = (RelativeLayout) v.findViewById(R.id.mContainer);

        llplantheader = (LinearLayout) v.findViewById(R.id.llplantheader);

        recyclerViewNurseryC = (RecyclerView) v.findViewById(R.id.recyclerViewNursery);
        Constant.getinstance(getActivity()).setRecyclerView(recyclerViewNurseryC);

        setTextViewColor(0);
        txtNursery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewNurseryC.setVisibility(View.VISIBLE);
                mContainer.setVisibility(View.GONE);
                setTextViewColor(0);
            }
        });
        txtPlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewNurseryC.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
                setTextViewColor(1);

            }
        });

        nurseryListAPI();
        return v;
    }

    public void setTextViewColor(int index) {
        if (index == 0) {
            txtNursery.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtPlants.setTextColor(Color.BLACK);
        } else {
            txtNursery.setTextColor(Color.BLACK);
            txtPlants.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void initializationNursery() {
        ParallaxRecyclerAdapter<String> stringAdapter = new ParallaxRecyclerAdapter<>(NurseryList.getArr_name());
        stringAdapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((SimpleViewHolder) viewHolder).txtNurseryNameC.setText(NurseryList.getArr_name().get(i));
                ((SimpleViewHolder) viewHolder).txtBestNurseryC.setText(NurseryList.getArr_address().get(i));
                ((SimpleViewHolder) viewHolder).txtContactNoC.setText(NurseryList.getArr_contact_no().get(i));

                ((SimpleViewHolder) viewHolder).txtContactNoC.setTag(i);
                ((SimpleViewHolder) viewHolder).txtContactNoC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        customshowAlert(getActivity(), R.string.call_msg, "Yes", "No", NurseryList.getArr_contact_no().get(pos));
                    }
                });

                ((SimpleViewHolder) viewHolder).imgPhoneC.setTag(i);
                ((SimpleViewHolder) viewHolder).imgPhoneC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        customshowAlert(getActivity(), R.string.call_msg, "Yes", "No", NurseryList.getArr_contact_no().get(pos));
                    }
                });
                Picasso.with(getActivity())
                        .load(NurseryList.getArr_image_url().get(i))
                        .into(((SimpleViewHolder) viewHolder).imgNurseryC);

                ((SimpleViewHolder) viewHolder).rel_nur_detailc.setTag(i);
                ((SimpleViewHolder) viewHolder).rel_nur_detailc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Intent intent = new Intent(getActivity(), MapMainActivity.class);
                        intent.putExtra("nur_id", NurseryList.getArr_id().get(pos));
                        intent.putExtra("lat", NurseryList.getArr_latitude().get(pos));
                        intent.putExtra("long", NurseryList.getArr_longitude().get(pos));
                        intent.putExtra("name", NurseryList.getArr_name().get(pos));
                        intent.putExtra("owner_name", NurseryList.getArr_owner_name().get(pos));
                        intent.putExtra("address", NurseryList.getArr_address().get(pos));
                        intent.putExtra("contact_no", NurseryList.getArr_contact_no().get(pos));
                        intent.putExtra("image_url", NurseryList.getArr_image_url().get(pos));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new SimpleViewHolder(getActivity().getLayoutInflater().inflate(R.layout.nursery_detail_screen, viewGroup, false), getActivity());
            }

            @Override
            public int getItemCount() {
                return NurseryList.getArr_name().size();
            }
        });

        View viewheader = getActivity().getLayoutInflater().inflate(R.layout.nursery_top_layout, recyclerViewNurseryC, false);
        mPager = (ViewPager) viewheader.findViewById(R.id.pager);
        rlimageslider = (RelativeLayout) viewheader.findViewById(R.id.rlimageslider);
        indicator = (CirclePageIndicator)
                viewheader.findViewById(R.id.indicator);
        stringAdapter.setParallaxHeader(viewheader, recyclerViewNurseryC);
        stringAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                //TODO: implement toolbar alpha. See README for details
            }
        });
        recyclerViewNurseryC.setAdapter(stringAdapter);
    }

    public void setBannerList(ArrayList<String> image_Slider_List) {
        if (image_Slider_List.size() > 0) {
            mPager.setAdapter(new SlidingImage_Adapter(getActivity(), image_Slider_List));
            indicator.setViewPager(mPager);
            final float density = getResources().getDisplayMetrics().density;
            //Set circle indicator radius
            indicator.setRadius(5 * density);
            NUM_PAGES = image_Slider_List.size();
            // Auto start of viewpager
            final Handler handler = new Handler();
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
            }, 3000, 3000);

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
    }

    public void nurseryListAPI() {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.nurseryList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    NurseryList nursery_list = new NurseryList(jsonresult);
                    if (!nursery_list.getRecordsTotal().equals(0)) {
                        recyclerViewNurseryC.setVisibility(View.VISIBLE);
                        llplantheader.setVisibility(View.VISIBLE);
                        initializationNursery();
                        setBannerList(nursery_list.getArr_bannerlist());
                        plantCatListAPI();
                    } else {
                        Constant.customshowAlertwithOneButton(getActivity(), R.string.nursery_not_avail, "Ok");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(getActivity()).hideProgress();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void plantCatListAPI() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.plantList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    result = response.body().string();
                    JSONObject jsonresult = null;
                    jsonresult = new JSONObject(result);
                    NurseryPlanCategories plant_cat_list = new NurseryPlanCategories(jsonresult);
                    if (plant_cat_list.getRecordsTotal() != 0) {
                        initializationPlants();
                    } else {
                        Constant.customshowAlertwithOneButton(getActivity(), R.string.no_plant_cat, "Ok");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(getActivity()).hideProgress();
                // Log error here since request failed
            }
        });
    }

    public void initializationPlants() {
        ImageAdapter mAdapter = new ImageAdapter(getActivity(), 0);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.image_grid, mContainer, true);
        ParallaxGridView mGridView = (ParallaxGridView) view.findViewById(R.id.parallaxGridview);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PlantListActivity.class);
                intent.putExtra("CategoryID", NurseryPlanCategories.getArr_id().get(position));
                intent.putExtra("nur_id", "0");
                intent.putExtra("CategoryName", NurseryPlanCategories.getArr_name().get(position));
                startActivity(intent);

            }
        });
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(3);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        //CardView cv;
        TextView txtNurseryNameC, txtBestNurseryC, txtContactNoC;
        TextView txtDistanceC;
        ImageView imgNurseryC, imgPhoneC, img_ic_locC;
        RelativeLayout rel_nur_detailc;

        public SimpleViewHolder(View itemView, Activity activity) {
            super(itemView);

            Typeface tf_nub = Typeface.createFromAsset(activity.getAssets(),
                    "fonts/nunito_medium.ttf");
            Typeface rb_thin = Typeface.createFromAsset(activity.getAssets(),
                    "fonts/roboto_thin.ttf");

            CardView cd_view_nur_addC = (CardView) itemView.findViewById(R.id.cd_view_nur_add);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (80 * Constant.getDeviceHeight(activity)) / 960, 0, 0);
            cd_view_nur_addC.setLayoutParams(params);

            txtNurseryNameC = (TextView) itemView.findViewById(R.id.txtNurseryName);
            txtContactNoC = (TextView) itemView.findViewById(R.id.txtContactNo);
            txtBestNurseryC = (TextView) itemView.findViewById(R.id.txtBestNursery);
            txtDistanceC = (TextView) itemView.findViewById(R.id.txtDistance);
            imgNurseryC = (ImageView) itemView.findViewById(R.id.imgNursery);
            imgPhoneC = (ImageView) itemView.findViewById(R.id.imgPhone);
            rel_nur_detailc = (RelativeLayout) itemView.findViewById(R.id.rel_nur_detail);
            img_ic_locC = (ImageView) itemView.findViewById(R.id.img_ic_loc);

            RelativeLayout.LayoutParams params_img_ic_loc = new RelativeLayout.LayoutParams((28 * Constant.getDeviceWidth(activity)) / 640, (28 * Constant.getDeviceHeight(activity)) / 960);
//            params_img_ic_loc.addRule(RelativeLayout.BELOW,R.id.txtNurseryName);
            params_img_ic_loc.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params_img_ic_loc.setMargins((10 * Constant.getDeviceWidth(activity)) / 540, (30 * Constant.getDeviceHeight(activity)) / 960, (20 * Constant.getDeviceWidth(activity)) / 540, (10 * Constant.getDeviceHeight(activity)) / 960);
            img_ic_locC.setLayoutParams(params_img_ic_loc);

            RelativeLayout.LayoutParams params_img_phone = new RelativeLayout.LayoutParams((48 * Constant.getDeviceWidth(activity)) / 640, (48 * Constant.getDeviceHeight(activity)) / 960);
            params_img_phone.addRule(RelativeLayout.BELOW, R.id.txtBestNursery);
            imgPhoneC.setLayoutParams(params_img_phone);

            txtNurseryNameC.setTypeface(tf_nub);
            txtBestNurseryC.setTypeface(rb_thin);
            txtContactNoC.setTypeface(rb_thin);

            txtBestNurseryC.setTextSize(TypedValue.COMPLEX_UNIT_PX, (18 * Constant.getDeviceWidth(activity)) / 540);
            txtContactNoC.setTextSize(TypedValue.COMPLEX_UNIT_PX, (18 * Constant.getDeviceWidth(activity)) / 540);
//            txtContactNoC.setTextColor(c.getResources().getColor(android.R.color.holo_blue_dark));

        }
    }

    public void customshowAlert(@NonNull final Context context, @StringRes int resId, String positiveButtonmsg, String negativeButtonMsg, final String call_num) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + call_num));
                getActivity().startActivity(intent);
                dialogInterface.dismiss();
//                ((Activity)context).finish();
            }
        });
        alertDialog.setNegativeButton(negativeButtonMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }
}
