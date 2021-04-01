package com.kf.regreen.regreenproject.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Activities.LoginScreen;
import com.kf.regreen.regreenproject.Activities.SignUpForm;
import com.kf.regreen.regreenproject.Activities.UploadBillActivity;
import com.kf.regreen.regreenproject.Adapters.CustomHomePageAdapter;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Interface.ProgressDisplay;
import com.kf.regreen.regreenproject.QRCode.ScanActivity;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FrameLayout frameViewPagerC;
    ViewPager viewPagerHomeC;
    TextView txtQuoteC, txtQuoteWriterC, txtUserNameC, txtPointsC;
    ImageView imgQuoteC, imgFacebookC, imgInstac, imgWebsiteC;

    View includedViewC;
    CircleImageView profileImageC;

    CustomHomePageAdapter customHomePageAdapterC;

    CirclePageIndicator circlePageIndicator;

    private PreferencesUtils mPreferences;

    private Uri initialURI;

    private static final int ACTION_REQUEST_GALLERY = 0, ACTION_REQUEST_CAMERA = 1;


    public static String FACEBOOK_URL = "https://www.facebook.com/ReGreenamd/";
    public static String FACEBOOK_PAGE_ID = "ReGreenamd";
    public static String INSTAGRAM_URL = "https://www.instagram.com/regreenamd/";
    public static String WEBSITE_URL = "http://www.regreen.co.in/";
    int currentPage = 0;
    int NUM_PAGES = 3;
    Timer timer;
    final long DELAY_MS = 3000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 10000; // time in milliseconds between successive task executions.
    View v;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);
        Constant.getinstance(getActivity()).setTitle(getResources().getString(R.string.tab_home));
        mPreferences = new PreferencesUtils(getActivity());
//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();

        initialization(v);
        setViewPagerAds();
        getQuoteMessage();

        return v;
    }


    public void initialization(View v) {
        includedViewC = v.findViewById(R.id.homeProfile);

        profileImageC = (CircleImageView) includedViewC.findViewById(R.id.profile_image);
        txtUserNameC = (TextView) includedViewC.findViewById(R.id.txtUserName);
        txtPointsC = (TextView) includedViewC.findViewById(R.id.txtPoints);
        ImageView imgmenuC = (ImageView) includedViewC.findViewById(R.id.imgmenu);

        viewPagerHomeC = (ViewPager) v.findViewById(R.id.viewPagerHome);
        circlePageIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);
        txtQuoteC = (TextView) v.findViewById(R.id.txtQuote);
        txtQuoteWriterC = (TextView) v.findViewById(R.id.txtQuoteWriter);
        imgQuoteC = (ImageView) v.findViewById(R.id.imgQuote);
        imgFacebookC = (ImageView) v.findViewById(R.id.imgFacebook);
        imgInstac = (ImageView) v.findViewById(R.id.imgInsta);
        imgWebsiteC = (ImageView) v.findViewById(R.id.imgWebsite);

        Typeface tf_nub = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/nunito_medium.ttf");
        if (mPreferences.isLogin()) {
            txtUserNameC.setText(mPreferences.getPrefFirstName());
//            txtPointsC.setText("Total points " + mPreferences.getBillPoints());
            txtUserNameC.setTypeface(tf_nub);
//            txtUserNameC.setTextSize(TypedValue.COMPLEX_UNIT_PX, (32 * Constant.getDeviceWidth(getActivity())) / 540);
//            txtPointsC.setTypeface(tf_nub);
//            txtPointsC.setTextSize(TypedValue.COMPLEX_UNIT_PX, (15 * Constant.getDeviceWidth(getActivity())) / 540);
            setProfilePhoto();

        } else {
//            top_profC.setVisibility(View.GONE);
        }
        setOnClickListner();

    }

    public void setProfilePhoto() {
        Picasso.with(getActivity())
                .load(mPreferences.getPrefUserProfilePic()).error(R.drawable.upload_photo)
                .into(profileImageC);
    }

    public void setOnClickListner() {
        imgFacebookC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callFbPage(getActivity());
                redirectWebPage(getFacebookPageURL(getActivity()));
            }
        });

        imgInstac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callInstaPage(getActivity());
                redirectWebPage(INSTAGRAM_URL);
            }
        });

        imgWebsiteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectWebPage(WEBSITE_URL);
            }
        });

    }

    public void setViewPagerAds() {

        int[] mResources = {
                R.drawable.banner3,
                R.drawable.banner1,
                R.drawable.banner2,
        };
        customHomePageAdapterC = new CustomHomePageAdapter(getActivity(), mResources);
        viewPagerHomeC.setAdapter(customHomePageAdapterC);
        circlePageIndicator.setViewPager(viewPagerHomeC);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPagerHomeC.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public void getQuoteMessage() {
//        Constant.getinstance(getActivity()).show();
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.getQuoteMsg();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
//                Constant.getinstance(getActivity()).hide();
                Constant.getinstance(getActivity()).hideProgress();
                try {

                    String result = response.body().string();
                    JSONObject jsonresult = new JSONObject(result);
//                    String success = jsonresult.getString(RestApi.PARAMETERS.STATUS);
//                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    JSONArray jQuoteList = jsonresult.getJSONArray(RestApi.PARAMETERS.RESULT);
                    if (jQuoteList.length() > 0) {
                        JSONObject jQuote = jQuoteList.getJSONObject(0);
                        txtQuoteC.setText(jQuote.getString("quote_message"));
                        txtQuoteWriterC.setText(jQuote.getString("quote_author"));

                    } else {
//                    txtQuoteC.setText("");
//                    txtQuoteWriterC.setText("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
//                Constant.getinstance(getActivity()).hide();
                Constant.getinstance(getActivity()).hideProgress();
            }
        });
    }

    public boolean isAppInstalledOrNot(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("isAppInstalledOrNot...", "" + e.toString());
        }
        return false;
    }

    public boolean isAppEnableOrNot(String packageName) {
        boolean appStatus = false;
        try {
            ApplicationInfo ai = getActivity().getPackageManager().getApplicationInfo(packageName, 0);
            appStatus = ai.enabled;
        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("isAppEnableOrNot...", "" + e.toString());
        }
        return appStatus;
    }

    public void redirectGooglePlayStore() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.android.chrome")));
    }

    public void redirectWebPage(String url) {
        if (isAppInstalledOrNot("com.android.chrome")) {
            //This intent will help you to launch if the package is already installed
            if (isAppEnableOrNot("com.android.chrome")) {
                Log.e("redirectWebPage...if", "" + url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } else {
                redirectGooglePlayStore();
            }
        } else {
//            Log.e("redirectWebPage...else", "" + url);
            redirectGooglePlayStore();
        }
    }
}
