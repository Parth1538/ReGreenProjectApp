package com.kf.regreen.regreenproject.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kf.regreen.regreenproject.Activities.DiscountScreen;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Adapters.CustomAdapterBonus;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.RewardsList;
import com.kf.regreen.regreenproject.QRCode.ScanActivity;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rewards extends Fragment {

    RecyclerView recyclerRewardsC;
    View v;
    String TAG = "REWARDS";
    PreferencesUtils mPreferences;
    TextView txtRewardsGbill, txtWelComeBonusTenPerC, txtRedeemC, txtMyRewardsC;
    RelativeLayout rel_redeem_now, rel_upload_bill;
    ImageView imgRedeemC;

    public static Rewards newInstance() {

        return new Rewards();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rewards, container, false);
        Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.tab_rewards));
//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();
        Typeface tf_nub = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/nunito_medium.ttf");
        Typeface rb_light = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/roboto_light.ttf");

        mPreferences = new PreferencesUtils(getActivity());

        txtRewardsGbill = (TextView) v.findViewById(R.id.txtRewardsGbill);
        txtMyRewardsC = (TextView) v.findViewById(R.id.txtMyRewards);
        rel_redeem_now = (RelativeLayout) v.findViewById(R.id.rel_redeem_now);
        rel_upload_bill = (RelativeLayout) v.findViewById(R.id.rel_upload_bill);
        txtWelComeBonusTenPerC = (TextView) v.findViewById(R.id.txtWelComeBonusTenPer);
        txtRedeemC = (TextView) v.findViewById(R.id.txtRedeem);
        imgRedeemC = (ImageView) v.findViewById(R.id.imgRedeem);
        recyclerRewardsC = (RecyclerView) v.findViewById(R.id.recyclerRewards);

        Constant.getinstance(getActivity()).setRecyclerView(recyclerRewardsC);
        rel_redeem_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
            }
        });

        rel_upload_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UploadBillFragment();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });

        txtWelComeBonusTenPerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), DiscountScreen.class);
                startActivity(intent);*/
            }
        });
        getBills();
        return v;
    }

    public void getBills() {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getBill(mPreferences.getPrefUserId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                Constant.getinstance(getActivity()).hideProgress();
                try {
                    String result = response.body().string();
                    JSONObject jsonresult = null;
                    try {
                        jsonresult = new JSONObject(result);
                        int status = jsonresult.optInt(RestApi.PARAMETERS.STATUS);
                        String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                        RewardsList rewards_list = new RewardsList(jsonresult);
                        if (status == RestApi.PARAMETERS.STATUS_PASS) {
                            CustomAdapterBonus cstbonus = new CustomAdapterBonus(getActivity(), rewards_list.getRearr_nurname(),
                                    rewards_list.getRearr_amount(),
                                    rewards_list.getRearr_image_url(),
                                    rewards_list.getRearr_date(),
                                    rewards_list.getRearr_message());

                            recyclerRewardsC.setAdapter(cstbonus);
                        } else {
                            Constant.customshowAlert(getActivity(), R.string.gbill_not_avail, "Ok");
                        }

                        String points = rewards_list.getRearr_reward_points().get(0).toString().trim();
                        txtRewardsGbill.setText(points);
                        mPreferences.setPrefBillPoints(points);
                        if (rewards_list.getUser_is_new() == 0) {
                            txtWelComeBonusTenPerC.setVisibility(View.GONE);
                        } else {
                            txtWelComeBonusTenPerC.setVisibility(View.VISIBLE);
                        }

                        //
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Constant.getinstance(getActivity()).hideProgress();
            }
        });
    }
}
