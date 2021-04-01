package com.kf.regreen.regreenproject.Model;

import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_AMT;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_ID;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_IMGURL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_ISCONF;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_NURNAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_RECTOTAL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_REWARDS;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_USERFN;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.GBILL_USER_IS_NEW;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.MESSAGE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_DATE;

/**
 * Created by admin on 07/01/2018.
 */

public class RewardsList {

    int user_is_new = 0;
    private String recordsTotal, id, nurname, image_url, amount, is_confirm, user_fullname, reward_points, date,message;
    private static ArrayList<String> rearr_recordsTotal, rearr_id, rearr_nurname, rearr_image_url, rearr_amount, rearr_is_confirm, rearr_user_fullname, rearr_reward_points, rearr_date,rearr_message;


    public RewardsList(JSONObject jsonResult) {
        rearr_recordsTotal = new ArrayList<String>();
        rearr_id = new ArrayList<String>();
        rearr_nurname = new ArrayList<String>();
        rearr_amount = new ArrayList<String>();
        rearr_is_confirm = new ArrayList<String>();
        rearr_image_url = new ArrayList<String>();
        rearr_user_fullname = new ArrayList<String>();
        rearr_reward_points = new ArrayList<String>();
        rearr_date = new ArrayList<>();
        rearr_message=new ArrayList<>();


        setReward_points(jsonResult.optString(GBILL_REWARDS));
        setUser_is_new(jsonResult.optInt(GBILL_USER_IS_NEW));
        rearr_reward_points.add(getReward_points());

        JSONArray jsonArray = jsonResult.optJSONArray(RestApi.PARAMETERS.RESULT);
        setRecordsTotal("" + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(i);
                setId(data.optString(GBILL_ID));
                setNurname(data.optString(GBILL_NURNAME));
                setImage_url(data.optString(GBILL_IMGURL));
                setIs_confirm(data.optString(GBILL_ISCONF));
                setUser_fullname(data.optString(GBILL_USERFN));
                setAmount(data.optString(GBILL_AMT));
                setDate(data.optString(PLCAT_DATE));
                setMessage(data.optString(MESSAGE));


                rearr_id.add(getId());
                rearr_nurname.add(getNurname());
                rearr_image_url.add(getImage_url());
                rearr_amount.add(getAmount());
                rearr_is_confirm.add(getIs_confirm());
                rearr_user_fullname.add(getUser_fullname());
                rearr_date.add(getDate());
                rearr_message.add(getMessage());

            } catch (Exception e) {

            }
        }

        rearr_recordsTotal.add(getRecordsTotal());

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static void setRearr_message(ArrayList<String> rearr_message) {
        RewardsList.rearr_message = rearr_message;
    }

    public static ArrayList<String> getRearr_message() {
        return rearr_message;
    }

    public void setUser_is_new(int user_is_new) {
        this.user_is_new = user_is_new;
    }

    public int getUser_is_new() {
        return user_is_new;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public static ArrayList<String> getRearr_date() {
        return rearr_date;
    }

    public static void setRearr_date(ArrayList<String> rearr_date) {
        RewardsList.rearr_date = rearr_date;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    public static ArrayList<String> getRearr_reward_points() {
        return rearr_reward_points;
    }

    public static void setRearr_reward_points(ArrayList<String> rearr_reward_points) {
        RewardsList.rearr_reward_points = rearr_reward_points;
    }

    public String getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(String recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNurname() {
        return nurname;
    }

    public void setNurname(String nurname) {
        this.nurname = nurname;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(String is_confirm) {
        this.is_confirm = is_confirm;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public static ArrayList<String> getRearr_recordsTotal() {
        return rearr_recordsTotal;
    }

    public static void setRearr_recordsTotal(ArrayList<String> rearr_recordsTotal) {
        RewardsList.rearr_recordsTotal = rearr_recordsTotal;
    }

    public static ArrayList<String> getRearr_id() {
        return rearr_id;
    }

    public static void setRearr_id(ArrayList<String> rearr_id) {
        RewardsList.rearr_id = rearr_id;
    }

    public static ArrayList<String> getRearr_nurname() {
        return rearr_nurname;
    }

    public static void setRearr_nurname(ArrayList<String> rearr_nurname) {
        RewardsList.rearr_nurname = rearr_nurname;
    }

    public static ArrayList<String> getRearr_image_url() {
        return rearr_image_url;
    }

    public static void setRearr_image_url(ArrayList<String> rearr_image_url) {
        RewardsList.rearr_image_url = rearr_image_url;
    }

    public static ArrayList<String> getRearr_amount() {
        return rearr_amount;
    }

    public static void setRearr_amount(ArrayList<String> rearr_amount) {
        RewardsList.rearr_amount = rearr_amount;
    }

    public static ArrayList<String> getRearr_is_confirm() {
        return rearr_is_confirm;
    }

    public static void setRearr_is_confirm(ArrayList<String> rearr_is_confirm) {
        RewardsList.rearr_is_confirm = rearr_is_confirm;
    }

    public static ArrayList<String> getRearr_user_fullname() {
        return rearr_user_fullname;
    }

    public static void setRearr_user_fullname(ArrayList<String> rearr_user_fullname) {
        RewardsList.rearr_user_fullname = rearr_user_fullname;
    }
}
