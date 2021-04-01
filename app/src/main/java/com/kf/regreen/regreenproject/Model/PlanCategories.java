package com.kf.regreen.regreenproject.Model;

import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_DATE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_ID;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_IMAGE_URL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_IS_ACTIVE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLCAT_REC_TOTAL;

/**
 * Created by admin on 02/01/2018.
 */

public class PlanCategories {

    int recordsTotal = 0;
    private String id, name, is_active, image_url, date;
    private static ArrayList<String> arr_id, arr_name, arr_is_active, arr_image_url, arr_date;
    private static ArrayList<Integer> arr_recordsTotal;

    public PlanCategories(JSONObject jsonResult) {

        arr_recordsTotal = new ArrayList<Integer>();
        arr_id = new ArrayList<String>();
        arr_name = new ArrayList<String>();
        arr_is_active = new ArrayList<String>();
        arr_image_url = new ArrayList<String>();
        arr_date = new ArrayList<String>();


        JSONArray jsonArray = jsonResult.optJSONArray(RestApi.PARAMETERS.RESULT);
        setRecordsTotal(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(i);
                setId(data.optString(PLCAT_ID));
                setName(data.optString(PLCAT_NAME));
                setIs_active(data.optString(PLCAT_IS_ACTIVE));
                setImage_url(data.optString(PLCAT_IMAGE_URL));
                if (data.has(PLCAT_DATE))
                    setDate(data.optString(PLCAT_DATE));

                arr_id.add(getId());
                arr_name.add(getName());
                arr_is_active.add(getIs_active());
                if (data.has(PLCAT_DATE))
                    arr_date.add(getDate());
                arr_image_url.add(getImage_url());

            } catch (Exception e) {
            }
        }
        arr_recordsTotal.add(getRecordsTotal());

    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDate() {
        return date;
    }

    public static void setArr_date(ArrayList<String> arr_date) {
        PlanCategories.arr_date = arr_date;
    }

    public static void setArr_image_url(ArrayList<String> arr_image_url) {
        PlanCategories.arr_image_url = arr_image_url;
    }

    public static ArrayList<String> getArr_image_url() {
        return arr_image_url;
    }

    public static ArrayList<String> getArr_date() {
        return arr_date;
    }

    public static ArrayList<Integer> getArr_recordsTotal() {
        return arr_recordsTotal;
    }

    public static void setArr_recordsTotal(ArrayList<Integer> arr_recordsTotal) {
        PlanCategories.arr_recordsTotal = arr_recordsTotal;
    }

    public static ArrayList<String> getArr_id() {
        return arr_id;
    }

    public static void setArr_id(ArrayList<String> arr_id) {
        PlanCategories.arr_id = arr_id;
    }

    public static ArrayList<String> getArr_name() {
        return arr_name;
    }

    public static void setArr_name(ArrayList<String> arr_name) {
        PlanCategories.arr_name = arr_name;
    }

    public static ArrayList<String> getArr_is_active() {
        return arr_is_active;
    }

    public static void setArr_is_active(ArrayList<String> arr_is_active) {
        PlanCategories.arr_is_active = arr_is_active;
    }
}
