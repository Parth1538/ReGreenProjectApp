package com.kf.regreen.regreenproject.Model;

import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_ADD;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_CONT_NUM;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_ID;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_IMG_URL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_IS_ACTIVE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_IS_MEM;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_OWNER_NAME;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_REC_TOTAL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_USER_LATITUDE;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.NUR_USER_LONGITUDE;

/**
 * Created by admin on 02/01/2018.
 */

public class NurseryList {
    private String recordsTotal, id, name, contact_no, address, image_url, is_active, owner_name, is_member;
    private double latitude, longitude;
    private static ArrayList<String> arr_recordsTotal, arr_id, arr_name, arr_contact_no, arr_address, arr_image_url, arr_is_active, arr_owner_name, arr_nursery_affilated, arr_bannerlist;
    private static ArrayList<Double> arr_latitude, arr_longitude;

    public NurseryList(JSONObject jsonResult) {
        arr_recordsTotal = new ArrayList<String>();
        arr_id = new ArrayList<String>();
        arr_name = new ArrayList<String>();
        arr_contact_no = new ArrayList<String>();
        arr_address = new ArrayList<String>();
        arr_image_url = new ArrayList<String>();
        arr_is_active = new ArrayList<String>();
        arr_latitude = new ArrayList<Double>();
        arr_longitude = new ArrayList<Double>();
        arr_owner_name = new ArrayList<String>();
        arr_nursery_affilated = new ArrayList<String>();
        arr_bannerlist = new ArrayList<>();


        if (jsonResult.has(RestApi.PARAMETERS.BANNER)) {
            JSONArray jsonArraybanner = jsonResult.optJSONArray(RestApi.PARAMETERS.BANNER);
            for (int i = 0; i < jsonArraybanner.length(); i++) {
                try {
                    JSONObject data = (JSONObject) jsonArraybanner.get(i);
                    arr_bannerlist.add(data.getString(RestApi.PARAMETERS.BILL_IMAGE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


        JSONArray jsonArray = jsonResult.optJSONArray(RestApi.PARAMETERS.RESULT);
        setRecordsTotal("" + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(i);
                setId(data.optString(NUR_ID));
                setName(data.optString(NUR_NAME));
                setImage_url(data.optString(NUR_IMG_URL));
                setContact_no(data.optString(NUR_CONT_NUM));
                setAddress(data.optString(NUR_ADD));
                setIs_member(data.optString(NUR_IS_MEM));
                setIs_active(data.optString(NUR_IS_ACTIVE));
                setLatitude(Double.parseDouble(data.optString(NUR_USER_LATITUDE)));
                setLongitude(Double.parseDouble(data.optString(NUR_USER_LONGITUDE)));
                setOwner_name((data.optString(NUR_OWNER_NAME)));

                arr_id.add(getId());
                arr_name.add(getName());
                arr_image_url.add(getImage_url());
                arr_contact_no.add(getContact_no());
                arr_is_active.add(getIs_active());
                arr_address.add(getAddress());
                arr_latitude.add(getLatitude());
                arr_longitude.add(getLongitude());
                arr_owner_name.add(getOwner_name());

//                if (getIs_member().equals("1")) {
                arr_nursery_affilated.add(getName());
//                }
            } catch (Exception e) {

            }
        }
        arr_nursery_affilated.add(0, "Select Nursery");
        arr_recordsTotal.add(getRecordsTotal());

    }

    public static void setArr_bannerlist(ArrayList<String> arr_bannerlist) {
        NurseryList.arr_bannerlist = arr_bannerlist;
    }

    public static ArrayList<String> getArr_bannerlist() {
        return arr_bannerlist;
    }

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public static ArrayList<String> getArr_nursery_affilated() {
        return arr_nursery_affilated;
    }

    public static void setArr_nursery_affilated(ArrayList<String> arr_nursery_affilated) {
        NurseryList.arr_nursery_affilated = arr_nursery_affilated;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public static ArrayList<String> getArr_owner_name() {
        return arr_owner_name;
    }

    public static void setArr_owner_name(ArrayList<String> arr_owner_name) {
        NurseryList.arr_owner_name = arr_owner_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static ArrayList<Double> getArr_latitude() {
        return arr_latitude;
    }

    public static void setArr_latitude(ArrayList<Double> arr_latitude) {
        NurseryList.arr_latitude = arr_latitude;
    }

    public static ArrayList<Double> getArr_longitude() {
        return arr_longitude;
    }

    public static void setArr_longitude(ArrayList<Double> arr_longitude) {
        NurseryList.arr_longitude = arr_longitude;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public static ArrayList<String> getArr_recordsTotal() {
        return arr_recordsTotal;
    }

    public static void setArr_recordsTotal(ArrayList<String> arr_recordsTotal) {
        NurseryList.arr_recordsTotal = arr_recordsTotal;
    }

    public static ArrayList<String> getArr_id() {
        return arr_id;
    }

    public static void setArr_id(ArrayList<String> arr_id) {
        NurseryList.arr_id = arr_id;
    }

    public static ArrayList<String> getArr_name() {
        return arr_name;
    }

    public static void setArr_name(ArrayList<String> arr_name) {
        NurseryList.arr_name = arr_name;
    }

    public static ArrayList<String> getArr_contact_no() {
        return arr_contact_no;
    }

    public static void setArr_contact_no(ArrayList<String> arr_contact_no) {
        NurseryList.arr_contact_no = arr_contact_no;
    }

    public static ArrayList<String> getArr_address() {
        return arr_address;
    }

    public static void setArr_address(ArrayList<String> arr_address) {
        NurseryList.arr_address = arr_address;
    }

    public static ArrayList<String> getArr_image_url() {
        return arr_image_url;
    }

    public static void setArr_image_url(ArrayList<String> arr_image_url) {
        NurseryList.arr_image_url = arr_image_url;
    }

    public static ArrayList<String> getArr_is_active() {
        return arr_is_active;
    }

    public static void setArr_is_active(ArrayList<String> arr_is_active) {
        NurseryList.arr_is_active = arr_is_active;
    }

}
