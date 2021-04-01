package com.kf.regreen.regreenproject.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kf.regreen.ListenerUtils.FAQListener;
import com.kf.regreen.regreenproject.Activities.ExpertsFaqActivity;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Adapters.CustomAdapterexpert;
import com.kf.regreen.regreenproject.Adapters.UserExpertAdapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.ExpertList;
import com.kf.regreen.regreenproject.Model.UserList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
 * Use the {@link ExpertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpertFragment extends Fragment implements View.OnClickListener {

    View v;
    LinearLayout expert_f_and_q_C, lluserlist, llexpertlist;
    TextView txt_expert_f_and_q_C, txtpending, txtcomplete, txtnotfound, txtexpert, txtyourquestion;
    ImageView imgExpertArrowC;
    RelativeLayout rlfaq;

    CustomAdapterexpert customAdapter;
    RecyclerView recyclerViewExpertsC;
    ArrayList<ExpertList> arrExpertList = new ArrayList<>();

    UserExpertAdapter userExpertAdapter;
    ArrayList<UserList> arrUserList = new ArrayList<>();
    private PreferencesUtils mPreferences;
    int type = 0;

    public ExpertFragment() {
    }

    public static ExpertFragment newInstance() {
        return new ExpertFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_experts, container, false);

        mPreferences = new PreferencesUtils(getActivity());
//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();


        initialization();

        type = getArguments().getInt("type");
        Log.e("onCreateView..type", "" + type);
        if (mPreferences.getPrefUserType() == 1) {
            // TODO type=0 then after login redirection this page with type=0 and
            // TODO type=1 or 2 or 3 then user come from notification click
            if (type == 0) {
                getExpertsList();
            } else {
                getUserList(2);
            }

            lluserlist.setVisibility(View.GONE);
            Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.tab_experts));
        } else {
            type = 0;
            llexpertlist.setVisibility(View.GONE);
            Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.tab_user));
            getUserList(0);
        }

        return v;
    }

    public void initialization() {
        // Set title bar
        expert_f_and_q_C = (LinearLayout) v.findViewById(R.id.expert_f_and_q);
        rlfaq = (RelativeLayout) v.findViewById(R.id.rlfaq);

        txt_expert_f_and_q_C = (TextView) v.findViewById(R.id.txt_expert_f_and_q);
        imgExpertArrowC = (ImageView) v.findViewById(R.id.imgExpertArrow);
        recyclerViewExpertsC = (RecyclerView) v.findViewById(R.id.recyclerViewExperts);
        txtnotfound = (TextView) v.findViewById(R.id.txtnotfound);
        txtexpert = (TextView) v.findViewById(R.id.txtexpert);
        txtyourquestion = (TextView) v.findViewById(R.id.txtyourquestion);

        lluserlist = (LinearLayout) v.findViewById(R.id.lluserlist);
        llexpertlist = (LinearLayout) v.findViewById(R.id.llexpertlist);
        txtpending = (TextView) v.findViewById(R.id.txtpending);
        txtcomplete = (TextView) v.findViewById(R.id.txtcomplete);

        setOnClickListenerView();
        Constant.getinstance(getActivity()).setRecyclerView(recyclerViewExpertsC);
    }

    public void setOnClickListenerView() {
        txtpending.setOnClickListener(this);
        txtcomplete.setOnClickListener(this);
        txtexpert.setOnClickListener(this);
        txtyourquestion.setOnClickListener(this);
        rlfaq.setOnClickListener(this);
    }

    public void getExpertsList() {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getExpertsList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);

                    JSONArray jExpertList = jsonresult.getJSONArray(RestApi.PARAMETERS.RESULT);
                    arrExpertList = new ArrayList<>();
                    for (int i = 0; i < jExpertList.length(); i++) {
                        JSONObject jexpert = jExpertList.getJSONObject(i);
                        ExpertList expertList = new ExpertList();
                        expertList.setFirst_name(jexpert.getString(RestApi.PARAMETERS.REG_USER_FIRST_NAME));
                        expertList.setEmail(jexpert.getString(RestApi.PARAMETERS.REG_USER_EMAIL));
                        expertList.setUser_id(jexpert.getString(RestApi.PARAMETERS.REG_USER_ID));
                        expertList.setPhone_no(jexpert.getString(RestApi.PARAMETERS.REG_USER_PHONE));
                        expertList.setE_designation(jexpert.getString(RestApi.PARAMETERS.REG_USER_DESIGNATION));
                        expertList.setProfile_pic(jexpert.getString(RestApi.PARAMETERS.REG_USER_PROFILEPIC));
                        expertList.setE_area_experts(jexpert.getString(RestApi.PARAMETERS.REG_USER_AREAEXPERTS));
                        expertList.setCity(jexpert.getString(RestApi.PARAMETERS.REG_CITY));
                        arrExpertList.add(expertList);
                    }
                    fillExpertData();

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

    public void fillExpertData() {
        if (arrExpertList.size() > 0) {
            customAdapter = new CustomAdapterexpert(getActivity(), arrExpertList);
            recyclerViewExpertsC.setAdapter(customAdapter);
            recyclerViewExpertsC.setVisibility(View.VISIBLE);
            txtnotfound.setVisibility(View.GONE);
        } else {
            recyclerViewExpertsC.setVisibility(View.GONE);
            txtnotfound.setVisibility(View.VISIBLE);
            txtnotfound.setText(getResources().getString(R.string.nodatafound));
        }
        llexpertlist.setVisibility(View.VISIBLE);
        expert_f_and_q_C.setVisibility(View.VISIBLE);
        setQuestionStatusVisible(0);
    }


    public void getUserList(final int status) {
        if (status == 2) {
            type = 2;
        }
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getUserList(mPreferences.getPrefUserId(), status, type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    JSONArray jUserList = jsonresult.getJSONArray(RestApi.PARAMETERS.RESULT);
                    arrUserList = new ArrayList<>();
                    for (int i = 0; i < jUserList.length(); i++) {
                        JSONObject jUser = jUserList.getJSONObject(i);
                        UserList userList = Constant.getinstance(getActivity()).setUserList(jUser);
                        arrUserList.add(userList);
                    }
                    fillUserData(status);

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

    public void fillUserData(int status) {

        if (arrUserList.size() > 0) {
            userExpertAdapter = new UserExpertAdapter(getActivity(), arrUserList, status, faqListener);
            recyclerViewExpertsC.setAdapter(userExpertAdapter);
            recyclerViewExpertsC.setVisibility(View.VISIBLE);
            txtnotfound.setVisibility(View.GONE);
        } else {
            recyclerViewExpertsC.setVisibility(View.GONE);
            txtnotfound.setVisibility(View.VISIBLE);
            txtnotfound.setText(getResources().getString(R.string.nodatafound));
        }
        if (type == 2) {
            llexpertlist.setVisibility(View.VISIBLE);
            lluserlist.setVisibility(View.GONE);
            setQuestionStatusVisible(2);
        } else {
            lluserlist.setVisibility(View.VISIBLE);
            llexpertlist.setVisibility(View.GONE);
            setAnswerStatusVisible(status);
        }

        expert_f_and_q_C.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtpending:
                getUserList(0);
//                setAnswerStatusVisible(0);
                break;
            case R.id.txtcomplete:
                getUserList(1);
//                setAnswerStatusVisible(1);
                break;
            case R.id.txtexpert:
                getExpertsList();
                break;
            case R.id.txtyourquestion:
                getUserList(2);
                break;
            case R.id.rlfaq:
                Intent intent = new Intent(getActivity(), ExpertsFaqActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RestApi.PARAMETERS.ANSWER_REQUESTCODE) {
            int status = data.getIntExtra(RestApi.PARAMETERS.STATUS, 0);
            getUserList(status);
            setAnswerStatusVisible(status);
        }
    }

    public void setAnswerStatusVisible(int status) {
        if (status == 0) {
            txtpending.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtcomplete.setTextColor(Color.BLACK);
        } else {
            txtpending.setTextColor(Color.BLACK);
            txtcomplete.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void setQuestionStatusVisible(int status) {
        if (status == 0) {
            txtexpert.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtyourquestion.setTextColor(Color.BLACK);
        } else {
            txtexpert.setTextColor(Color.BLACK);
            txtyourquestion.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }


    FAQListener faqListener = new FAQListener() {
        @Override
        public void onSuccess(int index, int type) {
            if (type == 0) {
                addToFaq(index);
            } else {
                addToSpam(index);
            }

        }
    };

    public void addToFaq(int index) {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.addFaq(arrUserList.get(index).getQue_id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String msg = jsonresult.getString(RestApi.PARAMETERS.MESSAGE);
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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

    public void addToSpam(int index) {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.addSpam(arrUserList.get(index).getQue_id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
                    String msg = jsonresult.getString(RestApi.PARAMETERS.MESSAGE);
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
