package com.kf.regreen.regreenproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by admin on 29/12/2017.
 */

public class PreferencesUtils {

    private SharedPreferences pref;

    //user_type
    private static final String PREF_USER_TYPE = "prefProfileUserType";
    private static final String PREF_USER_GENDER = "prefProfileGender";
    private static final String PREF_USER_PROFILEPIC = "prefProfilePic";
    private static final String PREF_USER_ID = "prefUserId";
    private static final String PREF_USER_EMAIL = "prefUserEmail";
    private static final String PREF_LOGIN = "prefLogin";
    private static final String PREF_USER_PHONE_NO = "prefUserPhoneNo";
    private static final String PREF_USER_NAME = "prefUserName";
    private static final String PREF_FIRST_NAME = "prefFirstName";
    private static final String PREF_LAST_NAME = "prefLastName";
    private static final String PREF_ACCESS_FINE_LOC = "access_fine_loc";
    private static boolean isFineLocBlocked = false;
    private static final String PREF_DISC_SKIPED = "disc_skiped";
    private static boolean isUserVerified = false;
    private static final String PREF_IS_USER_VERIFIED = "user_verified";
    private static final String PREF_BILL_TOTAL_POINTS = "bill_points";

    private static final String NOTIFICATION_EXPERT = "notification_expert";
    private static final String NOTIFICATION_REWARD = "notification_reward";

    private static final String PREF_OTP = "pref_otp";

    public PreferencesUtils(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isUserVerified() {
        return pref.getBoolean(PREF_IS_USER_VERIFIED, false);
    }

    public void setisUserVerified(boolean isUserVerified) {
        PreferencesUtils.isUserVerified = isUserVerified;
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putBoolean(PREF_IS_USER_VERIFIED, PreferencesUtils.isUserVerified);
        Editor.apply();
    }

    public boolean isFineLocBlocked() {
        return pref.getBoolean(PREF_ACCESS_FINE_LOC, false);
    }

    public void setIsFineLocBlocked(boolean isFineLocBlocked) {
        PreferencesUtils.isFineLocBlocked = isFineLocBlocked;
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putBoolean(PREF_ACCESS_FINE_LOC, PreferencesUtils.isFineLocBlocked);
        Editor.apply();
    }

    public String getPrefUserName() {
        return pref.getString(PREF_USER_NAME, "");
    }

    public void setPrefUserName(String prefUserName) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_NAME, prefUserName);
        Editor.apply();
    }

    public String getBillPoints() {
        return pref.getString(PREF_BILL_TOTAL_POINTS, "0");
    }

    public void setPrefBillPoints(String prefBillAmt) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_BILL_TOTAL_POINTS, prefBillAmt);
        Editor.apply();
    }

    // Todo 1=User Login , 2=Expert Login
    public int getPrefUserType() {
        return pref.getInt(PREF_USER_TYPE, 1);
    }

    public void setPrefUserType(int prefUserType) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putInt(PREF_USER_TYPE, prefUserType);
        Editor.apply();
    }

    public String getPrefUserProfilePic() {
        return pref.getString(PREF_USER_PROFILEPIC, "");
    }

    public void setPrefUserProfilePic(String prefUserProfilePic) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_PROFILEPIC, prefUserProfilePic);
        Editor.apply();
    }

    public String getPrefUserProfileGender() {
        return pref.getString(PREF_USER_GENDER, "");
    }

    public void setPrefUserProfileGender(String prefUserProfileGender) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_GENDER, prefUserProfileGender);
        Editor.apply();
    }

    public String getPrefUserEmail() {
        return pref.getString(PREF_USER_EMAIL, "");
    }

    public void setPrefUserEmail(String prefUserEmail) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_EMAIL, prefUserEmail);
        Editor.apply();
    }

    public void setPrefUserPhoneNo(String prefUserPhoneNo) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_PHONE_NO, prefUserPhoneNo);
        //    ////Log.e("stored ph-", pref.getString(PREF_USER_PHONE_NO, ""));
        Editor.apply();
    }

    public String getPrefUserPhoneNo() {
        return pref.getString(PREF_USER_PHONE_NO, "");
    }

    public void setPrefDiscSkiped(String prefDiscSkiped) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_DISC_SKIPED, prefDiscSkiped);
        //    ////Log.e("stored ph-", pref.getString(PREF_USER_PHONE_NO, ""));
        Editor.apply();
    }

    public String getPrefDiscSkiped() {
        return pref.getString(PREF_DISC_SKIPED, "true");
    }

    public void setPrefUserId(String prefUserId) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_USER_ID, prefUserId);

        Editor.apply();
    }

    public String getPrefUserId() {
        return pref.getString(PREF_USER_ID, "-1");

    }

    public boolean isLogin() {
        return pref.getBoolean(PREF_LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putBoolean(PREF_LOGIN, isLogin);
        Editor.apply();
    }

    public String getPrefFirstName() {
        return pref.getString(PREF_FIRST_NAME, "");
    }

    public void setPrefFirstName(String prefFirstName) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_FIRST_NAME, prefFirstName);
        Editor.apply();
    }

    public String getPrefLastName() {
        return pref.getString(PREF_LAST_NAME, "");
    }

    public void setPrefLastName(String prefLastName) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_LAST_NAME, prefLastName);
        Editor.apply();
    }

    public String getPrefOtp() {
        return pref.getString(PREF_OTP, "");
    }

    public void setPrefOtp(String prefOtp) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putString(PREF_OTP, prefOtp);
        Editor.apply();
    }

    public void deleteSharePreference() {
        pref.edit().clear().apply();
    }

    public int getNotificationExpert() {
        return pref.getInt(NOTIFICATION_EXPERT, 0);
    }

    public void setNotificationExpert(int count) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putInt(NOTIFICATION_EXPERT, count);
        Editor.apply();
    }
    public int getNotificationReward() {
        return pref.getInt(NOTIFICATION_REWARD, 0);
    }

    public void setNotificationReward(int count) {
        SharedPreferences.Editor Editor = pref.edit();
        Editor.putInt(NOTIFICATION_REWARD, count);
        Editor.apply();
    }

}
