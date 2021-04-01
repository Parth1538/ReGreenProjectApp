package com.kf.regreen.regreenproject.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kf.regreen.regreenproject.Fragments.ArticlesFragment;
import com.kf.regreen.regreenproject.Fragments.ExpertFragment;
import com.kf.regreen.regreenproject.Fragments.HomeFragment;
import com.kf.regreen.regreenproject.Fragments.NurseryPlantsFragment;
import com.kf.regreen.regreenproject.Fragments.Rewards;
import com.kf.regreen.regreenproject.Interface.ProgressDisplay;
import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Config;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.CustomBottomNavigationView;
import com.kf.regreen.regreenproject.Utils.GPSTracker;
import com.kf.regreen.regreenproject.Utils.NotificationUtils;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;

import java.lang.reflect.Field;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

public class HomeScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ProgressDisplay {

    private PreferencesUtils mPreferences;
    private GPSTracker gps;
    private double latitude, longitude;
    static Context myContext;
    private CustomBottomNavigationView mBottomNavigationView;
    Toolbar tb;
    GoogleApiClient mGoogleApiClient;
    public Intent intent;
    public int type = 0;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);

        myContext = HomeScreenActivity.this;
        mPreferences = new PreferencesUtils(HomeScreenActivity.this);
        tb = (Toolbar) findViewById(R.id.toolbarHome);

        intent = getIntent();
        type = intent.getIntExtra("type", 0);

        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        setupBottomNavigation();
        setTabPosition();
        callLocation(HomeScreenActivity.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        setNotificationReceiver();
        setExpertMenuBadgeValue(mPreferences.getNotificationExpert());
        setRewardMenuBadgeValue(mPreferences.getNotificationReward());
    }


    public void setTabPosition() {
        if (intent.getIntExtra(TABPOSITION, 2) == 0) {
            loadNurseryPlantsFragment();
            mBottomNavigationView.setSelectedItemId(R.id.action_nursery);
        } else if (intent.getIntExtra(TABPOSITION, 2) == 1) {
            loadExpertFragment();
            mBottomNavigationView.setSelectedItemId(R.id.action_experts);
        } else if (intent.getIntExtra(TABPOSITION, 2) == 3) {
            loadArticlesFragment();
            mBottomNavigationView.setSelectedItemId(R.id.action_articles);
        } else if (intent.getIntExtra(TABPOSITION, 2) == 4) {
            loadRewardsFragment();
            mBottomNavigationView.setSelectedItemId(R.id.action_rewards);
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.action_home);
            loadHomeFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intentPara) {
        super.onNewIntent(intentPara);
        intent = intentPara;
        type = intent.getIntExtra("type", 0);
        Log.e("onNewIntent..type", "" + type);
        setTabPosition();
    }

    private void setupBottomNavigation() {
        mBottomNavigationView = (CustomBottomNavigationView) findViewById(R.id.bottom_navigation);


 /*
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);

       View v1 = bottomNavigationMenuView.getChildAt(0);
        View v2 = bottomNavigationMenuView.getChildAt(1);
        View v3 = bottomNavigationMenuView.getChildAt(2);
        View v4 = bottomNavigationMenuView.getChildAt(3);
        View v5 = bottomNavigationMenuView.getChildAt(4); // number of menu from left

        new QBadgeView(this).bindTarget(v1).setBadgeNumber(5);
        new QBadgeView(this).bindTarget(v2).setBadgeNumber(15);
        new QBadgeView(this).bindTarget(v3).setBadgeNumber(25);
        new QBadgeView(this).bindTarget(v4).setBadgeNumber(35);
        new QBadgeView(this).bindTarget(v5).setBadgeNumber(45);*/

        if (mPreferences.getPrefUserType() == 1) {
            mBottomNavigationView.getMenu().findItem(R.id.action_experts).setIcon(R.mipmap.experts);
            mBottomNavigationView.getMenu().findItem(R.id.action_experts).setTitle(getResources().getString(R.string.tab_experts));
        } else {
//            mBottomNavigationView.getMenu().findItem(R.id.action_experts).setIcon(R.drawable.upload_photo);
            mBottomNavigationView.getMenu().findItem(R.id.action_experts).setTitle(getResources().getString(R.string.tab_user));

        }

//        disableShiftMode(mBottomNavigationView);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_nursery:
                        loadNurseryPlantsFragment();
                        return true;
                    case R.id.action_experts:
                        if (mPreferences.isLogin()) {
                            mPreferences.setNotificationExpert(0);
                            setExpertMenuBadgeValue(mPreferences.getNotificationExpert());
                            loadExpertFragment();
                        } else {
                            Constant.loginAlert(HomeScreenActivity.this, "You need to login first.", "Login");
                        }
                        return true;
                    case R.id.action_home:
                        loadHomeFragment();
                        return true;
                    case R.id.action_articles:
                        loadArticlesFragment();
                        return true;
                    case R.id.action_rewards:
                        if (mPreferences.isLogin()) {
                            mPreferences.setNotificationReward(0);
                            setRewardMenuBadgeValue(mPreferences.getNotificationReward());
                            loadRewardsFragment();
                        } else {
                            Constant.loginAlert(HomeScreenActivity.this, "You need to login first.", "Login");
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void setExpertMenuBadgeValue(int count) {
        mBottomNavigationView.setBadgePositionValue(1, count);
    }

    public void setRewardMenuBadgeValue(int count) {
        mBottomNavigationView.setBadgePositionValue(4, count);
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        return bundle;
    }

    private void loadNurseryPlantsFragment() {
        NurseryPlantsFragment fragment = NurseryPlantsFragment.newInstance();
        fragment.setArguments(getBundle());
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadExpertFragment() {
        ExpertFragment fragment = ExpertFragment.newInstance();
//        BidFragment fragment = BidFragment.newInstance();
        fragment.setArguments(getBundle());
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadRewardsFragment() {
        Rewards fragment = Rewards.newInstance();
        fragment.setArguments(getBundle());
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadHomeFragment() {
        HomeFragment fragment = HomeFragment.newInstance();
        fragment.setArguments(getBundle());
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadArticlesFragment() {
        ArticlesFragment fragment = ArticlesFragment.newInstance();
        fragment.setArguments(getBundle());
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

/*

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
*/


    //Location tracking method
    public void callLocation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheckFineLocation = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            int permissionCheckCoarseLocation = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (permissionCheckFineLocation != PackageManager.PERMISSION_GRANTED && permissionCheckCoarseLocation != PackageManager.PERMISSION_GRANTED) {
                if (mPreferences.isFineLocBlocked()) {
                    showAlertBlockedPerm(context, R.string.read_location_settings, Constant.RESPONSE_SETTINGS);
                } else {
                    String[] perms = {"android.permission.ACCESS_FINE_LOCATION"};
                    ((Activity) context).requestPermissions(perms, Constant.REQUEST_PERMISSION_ACCESS_FINE_LOCATION);
                }

            }
        }
    }

    public void showAlertBlockedPerm(@NonNull final Context context, @NonNull int message, @NonNull final int requestCode) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(R.string.dialog_btn_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                ((Activity) context).startActivityForResult(intent, requestCode);
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                        int permissionCheckFineLocation = ContextCompat.checkSelfPermission(HomeScreenActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION);

                        int permissionCheckCoarseLocation = ContextCompat.checkSelfPermission(HomeScreenActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION);

                        if (permissionCheckFineLocation == PackageManager.PERMISSION_GRANTED && permissionCheckCoarseLocation == PackageManager.PERMISSION_GRANTED) {
                            gps = new GPSTracker(HomeScreenActivity.this);
                            if (gps.canGetLocation()) {

                                latitude = gps.getLatitude();
                                longitude = gps.getLongitude();

                                UserDetails.setLatitude(latitude);
                                UserDetails.setLongitude(longitude);

                            }

                        }
                    }
                }
                return;
            }
        }
    }

    public static void updateStringLabels(String frag_name) {
       /* if (frag_name.equals("UploadBillFragment")) {
            toolbar_title.setText("Upload Bill");
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mPreferences.isLogin()) {
            tb.inflateMenu(R.menu.edit_profile);
            tb.setOnMenuItemClickListener(
                    new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            return onOptionsItemSelected(item);
                        }
                    });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_profile:
                intent = new Intent(HomeScreenActivity.this, SignUpForm.class);
                intent.putExtra("iseditprofile", true);
                startActivity(intent);
                break;
            case R.id.action_changepwd:
                intent = new Intent(HomeScreenActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                mPreferences.deleteSharePreference();
                LoginManager.getInstance().logOut();
                signOut();
                intent = new Intent(HomeScreenActivity.this, SplashScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.e("Logout Status", "" + status);
                    }
                });

        //Todo revokeAccess
       /* Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showProgress() {
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        findViewById(R.id.loading).setVisibility(View.GONE);
    }


    public void setNotificationReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    setExpertMenuBadgeValue(mPreferences.getNotificationExpert());
                    setRewardMenuBadgeValue(mPreferences.getNotificationReward());
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
