package com.kf.regreen.regreenproject.Model;

import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_BOTANICAL_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_CAT;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_CAT_ID;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_DESC;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_ENGLISH_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_EXTRA_COMMENT;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_FAMILY;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_HABIT;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_ID;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_IMG_URL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_IS_ACTIVE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_LOCAL_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_LOCATION;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_QR_CODE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_REC_TOTAL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_USE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.PLANT_WATERING;

/**
 * Created by admin on 02/01/2018.
 */

public class PlantList implements Serializable {

    int recordsTotal;
    private String id, name, image_url, is_active, extra_comment, english_name, botanical_name, habit, family, watering, location,plant_use;
    private static ArrayList<String> arr_id, arr_name, arr_image_url, arr_is_active, arr_extra_comment, arr_english_name, arr_botanical_name, arr_habit, arr_family, arr_watering, arr_location,arr_plantuse;
    private static ArrayList<Integer> arr_recordsTotal;

    public PlantList(JSONObject jsonResult) {
        arr_recordsTotal = new ArrayList<Integer>();
        arr_id = new ArrayList<String>();
        arr_image_url = new ArrayList<String>();
        arr_name = new ArrayList<String>();
        arr_is_active = new ArrayList<String>();
        arr_extra_comment = new ArrayList<>();
        arr_english_name = new ArrayList<>();
        arr_botanical_name = new ArrayList<>();
        arr_habit = new ArrayList<>();
        arr_family = new ArrayList<>();
        arr_watering = new ArrayList<>();
        arr_location = new ArrayList<>();
        arr_plantuse=new ArrayList<>();

        JSONArray jsonArray = jsonResult.optJSONArray(RestApi.PARAMETERS.RESULT);
        setRecordsTotal(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(i);
                setId(data.optString(PLANT_ID));
                setName(data.optString(PLANT_LOCAL_NAME));
                setImage_url(data.optString(PLANT_IMG_URL));
                setIs_active(data.optString(PLANT_IS_ACTIVE));
                setExtra_comment(data.optString(PLANT_EXTRA_COMMENT));
                setEnglish_name(data.optString(PLANT_ENGLISH_NAME));
                setBotanical_name(data.optString(PLANT_BOTANICAL_NAME));
                setHabit(data.optString(PLANT_HABIT));
                setFamily(data.optString(PLANT_FAMILY));
                setWatering(data.optString(PLANT_WATERING));
                setLocation(data.optString(PLANT_LOCATION));
                setPlant_use(data.optString(PLANT_USE));

                arr_id.add(getId());
                arr_name.add(getName());
                arr_image_url.add(getImage_url());
                arr_is_active.add(getIs_active());
                arr_extra_comment.add(getExtra_comment());
                arr_english_name.add(getEnglish_name());
                arr_botanical_name.add(getBotanical_name());
                arr_habit.add(getHabit());
                arr_family.add(getFamily());
                arr_watering.add(getWatering());
                arr_location.add(getLocation());
                arr_plantuse.add(getPlant_use());
            } catch (Exception e) {
            }
        }
        arr_recordsTotal.add(getRecordsTotal());
    }

    public void setPlant_use(String plant_use) {
        this.plant_use = plant_use;
    }

    public String getPlant_use() {
        return plant_use;
    }

    public static void setArr_plantuse(ArrayList<String> arr_plantuse) {
        PlantList.arr_plantuse = arr_plantuse;
    }

    public static ArrayList<String> getArr_plantuse() {
        return arr_plantuse;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public void setExtra_comment(String extra_comment) {
        this.extra_comment = extra_comment;
    }

    public String getExtra_comment() {
        return extra_comment;
    }

    public void setBotanical_name(String botanical_name) {
        this.botanical_name = botanical_name;
    }

    public String getBotanical_name() {
        return botanical_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamily() {
        return family;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getHabit() {
        return habit;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    public String getWatering() {
        return watering;
    }

    public static void setArr_botanical_name(ArrayList<String> arr_botanical_name) {
        PlantList.arr_botanical_name = arr_botanical_name;
    }

    public static void setArr_english_name(ArrayList<String> arr_english_name) {
        PlantList.arr_english_name = arr_english_name;
    }

    public static void setArr_extra_comment(ArrayList<String> arr_extra_comment) {
        PlantList.arr_extra_comment = arr_extra_comment;
    }

    public static void setArr_family(ArrayList<String> arr_family) {
        PlantList.arr_family = arr_family;
    }

    public static void setArr_habit(ArrayList<String> arr_habit) {
        PlantList.arr_habit = arr_habit;
    }

    public static void setArr_location(ArrayList<String> arr_location) {
        PlantList.arr_location = arr_location;
    }

    public static void setArr_watering(ArrayList<String> arr_watering) {
        PlantList.arr_watering = arr_watering;
    }

    public static ArrayList<String> getArr_botanical_name() {
        return arr_botanical_name;
    }

    public static ArrayList<String> getArr_english_name() {
        return arr_english_name;
    }

    public static ArrayList<String> getArr_extra_comment() {
        return arr_extra_comment;
    }

    public static ArrayList<String> getArr_family() {
        return arr_family;
    }

    public static ArrayList<String> getArr_habit() {
        return arr_habit;
    }

    public static ArrayList<String> getArr_location() {
        return arr_location;
    }

    public static ArrayList<String> getArr_watering() {
        return arr_watering;
    }

    public static ArrayList<Integer> getArr_recordsTotal() {
        return arr_recordsTotal;
    }

    public static void setArr_recordsTotal(ArrayList<Integer> arr_recordsTotal) {
        PlantList.arr_recordsTotal = arr_recordsTotal;
    }

    public static ArrayList<String> getArr_id() {
        return arr_id;
    }

    public static void setArr_id(ArrayList<String> arr_id) {
        PlantList.arr_id = arr_id;
    }

    public static ArrayList<String> getArr_name() {
        return arr_name;
    }

    public static void setArr_name(ArrayList<String> arr_name) {
        PlantList.arr_name = arr_name;
    }


    public static ArrayList<String> getArr_image_url() {
        return arr_image_url;
    }

    public static void setArr_image_url(ArrayList<String> arr_image_url) {
        PlantList.arr_image_url = arr_image_url;
    }

    public static ArrayList<String> getArr_is_active() {
        return arr_is_active;
    }

    public static void setArr_is_active(ArrayList<String> arr_is_active) {
        PlantList.arr_is_active = arr_is_active;
    }

}
