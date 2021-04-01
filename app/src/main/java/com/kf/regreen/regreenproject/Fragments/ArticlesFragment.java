package com.kf.regreen.regreenproject.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Activities.DiscountScreen;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Activities.WebViewActivity;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.Article_List;
import com.kf.regreen.regreenproject.Model.NurseryList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.MySpannable;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ArticlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticlesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View v;
    static Context mContext;
    RecyclerView recyclerViewHomeScreenC;
    public static View topView;
    private static String billamt = "";
    public static ViewGroup topViewGroup;

    //    CustomAdapter customAdapter;
    private static String user_full_name;
    private PreferencesUtils mPreferences;
    static boolean IsLogin;
    private String result = "";
    int count = 0;


    public ArticlesFragment() {
        // Required empty public constructor
    }

    public static ArticlesFragment newInstance() {
        return new ArticlesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_article, container, false);
//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();
        mContext = getActivity();
        mPreferences = new PreferencesUtils(getActivity());
        user_full_name = mPreferences.getPrefFirstName() + " " + mPreferences.getPrefLastName();
        billamt = mPreferences.getBillPoints();
        IsLogin = mPreferences.isLogin();

        recyclerViewHomeScreenC = (RecyclerView) v.findViewById(R.id.recyclerViewHomeScreen);
        Constant.getinstance(getActivity()).setRecyclerView(recyclerViewHomeScreenC);

        getArticalList();

        Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.tab_articles));
        return v;
    }

    public void getArticalList() {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getArticleList(mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    Article_List nursery_list = new Article_List(jsonresult);
                    if (nursery_list.getRecordTotal() != 0) {
                        callHomeView();
                    } else {
                        Constant.customshowAlertwithOneButton(getActivity(), R.string.article_not_avail, "Ok");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(getActivity()).hideProgress();
            }
        });
    }

    public void callHomeView() {
        ParallaxRecyclerAdapter<String> stringAdapter = new ParallaxRecyclerAdapter<>(Article_List.getArr_description());
        stringAdapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {

                ((SimpleViewHolder) viewHolder).txtRecyclerNameC.setText("ReGreen");
                String s = Article_List.getArr_created_at().get(i);
                String date = s.substring(0, s.length() - 8);
                ((SimpleViewHolder) viewHolder).txtRecyclerTimeC.setText(date);
                String text = Article_List.getArr_description().get(i);
                if (text.length() > 160) {
                    text = text.substring(0, 160) + "...";
                    ((SimpleViewHolder) viewHolder).txtRecyclerTextC.setText(Html.fromHtml(text + "<font color='blue'> <u>View More</u></font>"));
                } else {
                    ((SimpleViewHolder) viewHolder).txtRecyclerTextC.setText(text);
                }

                ((SimpleViewHolder) viewHolder).txtTitle.setText(Article_List.getArr_title().get(i));

                ((SimpleViewHolder) viewHolder).txtSourceByValue.setText(Article_List.getArr_source().get(i));
                ((SimpleViewHolder) viewHolder).txtSubmittedByValue.setText(Article_List.getArr_submitted_by().get(i));

                ((SimpleViewHolder) viewHolder).txtUpvoteCountC.setText(Article_List.getArr_upvotes().get(i) + " Likes");
                if (Article_List.getArr_islike().get(i) == 0) {
                    ((SimpleViewHolder) viewHolder).imgLikeC.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                } else {
                    ((SimpleViewHolder) viewHolder).imgLikeC.setImageDrawable(getResources().getDrawable(R.drawable.heart_sel_d));
                }
               /* if (Article_List.getArr_upvotes().get(i).contains(mPreferences.getPrefUserId())) {
                    ((SimpleViewHolder) viewHolder).imgLikeC.setImageDrawable(getResources().getDrawable(R.drawable.heart_sel_d));
                } else {
                    ((SimpleViewHolder) viewHolder).imgLikeC.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                }*/
                ((SimpleViewHolder) viewHolder).recyclersecondContainer.setTag(i);
                ((SimpleViewHolder) viewHolder).recyclersecondContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", Article_List.getArr_url().get(pos));
                        startActivity(intent);
                    }
                });

                ((SimpleViewHolder) viewHolder).recyclerthirdContainer.setTag(i);
                ((SimpleViewHolder) viewHolder).recyclerthirdContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", Article_List.getArr_url().get(pos));
                        startActivity(intent);
                    }
                });
                ((SimpleViewHolder) viewHolder).shareContainerC.setTag(i);
                ((SimpleViewHolder) viewHolder).shareContainerC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/html");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Shared via ReGreen " + Article_List.getArr_url().get(pos));
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }

                });

                Picasso.with(getActivity())
                        .load(Article_List.getArr_image_url().get(i)).error(R.drawable.upload_photo)
                        .into(((SimpleViewHolder) viewHolder).imgRecylerContentC);


                count = Article_List.getArr_upvotes().size();
                ((SimpleViewHolder) viewHolder).imgLikeC.setTag(i);


                ((SimpleViewHolder) viewHolder).imgLikeC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IsLogin) {
                            int pos = (int) v.getTag();
                            if (Article_List.getArr_islike().get(pos) == 0) {
                                Article_List.getArr_islike().set(pos, 1);
                                int count = Integer.parseInt(Article_List.getArr_upvotes().get(i));
                                Article_List.getArr_upvotes().set(pos, "" + (count + 1));
                            } else {
                                Article_List.getArr_islike().set(pos, 0);
                                int count = Integer.parseInt(Article_List.getArr_upvotes().get(i));
                                Article_List.getArr_upvotes().set(pos, "" + (count - 1));
                            }
                            upVote(Article_List.getArr_id().get(pos));
                        } else {
                            Constant.loginAlert(getActivity(), "You need to login first.", "Login");
                        }
                    }
                });

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                topViewGroup = viewGroup;
                return new SimpleViewHolder(getActivity().getLayoutInflater().inflate(R.layout.home_recycler_list_view, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return Article_List.getArr_id().size();
            }
        });

        topView = getActivity().getLayoutInflater().inflate(R.layout.article_top_layout, topViewGroup, false);
        stringAdapter.setParallaxHeader(topView, recyclerViewHomeScreenC);
        stringAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                //TODO: implement toolbar alpha. See README for details
            }
        });
        recyclerViewHomeScreenC.setAdapter(stringAdapter);

    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        //CardView cv;
        TextView txtRecyclerNameC;
        TextView txtRecyclerTimeC;
        TextView txtRecyclerTextC;
        ImageView imgRecylerContentC, imgLikeC;
        TextView txtTitle;
        TextView txtUserName, txtSourceBy, txtSourceByValue, txtSubmittedBy, txtSubmittedByValue;
        TextView txtPoints;
        TextView txtUpvoteCountC;
        RelativeLayout recyclersecondContainer, recyclerthirdContainer, shareContainerC, top_prof, homescreenLeftImage, homescreenRightImage;

        ImageView imgUserProfilePicTop;

        public SimpleViewHolder(View itemView) {
            super(itemView);
////            cv = (CardView)itemView.findViewById(R.id.card);
            txtRecyclerNameC = (TextView) itemView.findViewById(R.id.txtRecyclerName);
            txtRecyclerTimeC = (TextView) itemView.findViewById(R.id.txtRecyclerTime);
            txtRecyclerTextC = (TextView) itemView.findViewById(R.id.txtRecyclerText);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgRecylerContentC = (ImageView) itemView.findViewById(R.id.imgRecylerContent);

            txtSourceBy = (TextView) itemView.findViewById(R.id.txtSourceBy);
            txtSourceByValue = (TextView) itemView.findViewById(R.id.txtSourceByValue);
            txtSubmittedBy = (TextView) itemView.findViewById(R.id.txtSubmittedBy);
            txtSubmittedByValue = (TextView) itemView.findViewById(R.id.txtSubmittedByValue);

            recyclersecondContainer = (RelativeLayout) itemView.findViewById(R.id.recyclersecondContainer);
            recyclerthirdContainer = (RelativeLayout) itemView.findViewById(R.id.recyclerthirdContainer);
            shareContainerC = (RelativeLayout) itemView.findViewById(R.id.shareContainer);


            imgLikeC = (ImageView) itemView.findViewById(R.id.imgLike);

            RelativeLayout.LayoutParams params_imgLike = new RelativeLayout.LayoutParams((48 * Constant.getDeviceWidth((Activity) mContext)) / 540, (48 * Constant.getDeviceHeight((Activity) mContext)) / 960);
            imgLikeC.setLayoutParams(params_imgLike);


            txtUpvoteCountC = (TextView) itemView.findViewById(R.id.txtUpvoteCount);


            Typeface tf_nul = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/nunito_extra_light.ttf");
            Typeface tf_nub = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/nunito_medium.ttf");
            Typeface rb_light = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/roboto_light.ttf");


            txtRecyclerNameC.setTypeface(tf_nub);
            txtRecyclerTimeC.setTypeface(tf_nul);
            txtRecyclerTextC.setTypeface(rb_light);
        }
    }

//    public String getListString(int position) {
//        return position + " - android";
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mPreferences.getPrefDiscSkiped().equals("false")) {
            inflater.inflate(R.menu.menu_main_2, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }

//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void upVote(String article_id) {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);

        Call<ResponseBody> call = restInterface.upvote(article_id, mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String success = jsonresult.getString(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
//                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                    recyclerViewHomeScreenC.getAdapter().notifyDataSetChanged();
                    /*if (success.equals("true")) {
                    } else {
                        Constant.customshowAlertSign(getActivity(), msg, "Ok", null);
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Constant.getinstance(getActivity()).hideProgress();
            }
        });
    }
}
