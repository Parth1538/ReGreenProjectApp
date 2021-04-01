package com.kf.regreen.regreenproject.Model;

import com.kf.regreen.regreenproject.Utils.RestApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.ART_REC_TOTAL;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.ART_REWARD_POINTS;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.ART_SOURCE_BY;
import static com.kf.regreen.regreenproject.Utils.RestApi.PARAMETERS.ART_SUBMITTED_BY;

/**
 * Created by admin on 31/12/2017.
 */

public class Article_List {

    private String id, description, image_url, url, title, is_active, user_id, created_at, user_fullname, user_profile_pic, reward_points, submitted_by, source, upvotes;
    private static ArrayList<String> arr_id, arr_description, arr_image_url, arr_url, arr_title, arr_is_active, arr_user_id, arr_created_at, arr_user_fullname, arr_user_profile_pic, arr_reward_points, arr_source, arr_submitted_by, arr_upvotes;
    int islike = 0, recordTotal;

    private static ArrayList<Integer> arr_record_total, arr_islike;

    public Article_List(JSONObject jsonObject) {

        arr_record_total = new ArrayList<>();
        arr_id = new ArrayList<String>();
        arr_description = new ArrayList<String>();
        arr_image_url = new ArrayList<String>();
        arr_url = new ArrayList<String>();
        arr_title = new ArrayList<String>();
        arr_is_active = new ArrayList<String>();
        arr_user_id = new ArrayList<String>();
        arr_created_at = new ArrayList<String>();
        arr_user_fullname = new ArrayList<String>();
        arr_user_profile_pic = new ArrayList<String>();
        arr_reward_points = new ArrayList<String>();
        arr_source = new ArrayList<String>();
        arr_submitted_by = new ArrayList<String>();
        arr_upvotes = new ArrayList<String>();
        arr_islike = new ArrayList<>();

//        setReward_points(jsonObject.optString(ART_REWARD_POINTS));

        JSONArray jsonArray = jsonObject.optJSONArray(RestApi.PARAMETERS.RESULT);
        setRecordTotal(jsonArray.length());
//        ArrayList<MenuSubcategory> subcategory = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(i);
                setId(data.optString("id"));
                setDescription(data.optString("description"));
                setImage_url(data.optString("image_url"));
                setUrl(data.optString("url"));
                setTitle(data.optString("title"));
                setIs_active(data.optString("is_active"));
                setUser_id(data.optString("user_id"));
                setCreated_at(data.optString("created_at"));
//                setUser_fullname(data.optString("user_fullname"));
//                setUser_profile_pic(data.optString("user_profile_pic"));
                setUpVotes(data.optString("total_likes"));
                setIslike(data.optInt("is_like"));
                setSubmitted_by(data.optString(ART_SUBMITTED_BY));
                setSource(data.optString(ART_SOURCE_BY));


                arr_id.add(getId());
                arr_description.add(getDescription());
                arr_image_url.add(getImage_url());
                arr_url.add(getUrl());
                arr_title.add(getTitle());
                arr_is_active.add(getIs_active());
                arr_user_id.add(getUser_id());
                arr_created_at.add(getCreated_at());
                arr_user_fullname.add(getUser_fullname());
                arr_user_profile_pic.add(getUser_profile_pic());
                arr_submitted_by.add(getSubmitted_by());
                arr_upvotes.add(getUpVotes());
                arr_source.add(getSource());
                arr_islike.add(getIslike());
            } catch (Exception e) {

            }
        }

        arr_record_total.add(getRecordTotal());
        arr_reward_points.add(getReward_points());

    }

    public Article_List() {

    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getIslike() {
        return islike;
    }

    public static void setArr_islike(ArrayList<Integer> arr_islike) {
        Article_List.arr_islike = arr_islike;
    }

    public static ArrayList<Integer> getArr_islike() {
        return arr_islike;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public void setSubmitted_by(String submitted_by) {
        this.submitted_by = submitted_by;
    }

    public String getUpVotes() {
        return upvotes;
    }

    public void setUpVotes(String upVotes) {
        this.upvotes = upVotes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static ArrayList<String> getArr_source() {
        return arr_source;
    }

    public static void setArr_source(ArrayList<String> arr_source) {
        Article_List.arr_source = arr_source;
    }

    public static ArrayList<String> getArr_submitted_by() {
        return arr_submitted_by;
    }

    public static ArrayList<String> getArr_upvotes() {
        return arr_upvotes;
    }

    public static void setArr_upvotes(ArrayList<String> arr_upvotes) {
        Article_List.arr_upvotes = arr_upvotes;
    }

    public static void setArr_submitted_by(ArrayList<String> arr_submitted_by) {
        Article_List.arr_submitted_by = arr_submitted_by;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    public static ArrayList<String> getArr_reward_points() {
        return arr_reward_points;
    }

    public static void setArr_reward_points(ArrayList<String> arr_reward_points) {
        Article_List.arr_reward_points = arr_reward_points;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_profile_pic() {
        return user_profile_pic;
    }

    public void setUser_profile_pic(String user_profile_pic) {
        this.user_profile_pic = user_profile_pic;
    }

    public static ArrayList<Integer> getArr_record_total() {
        return arr_record_total;
    }

    public static void setArr_record_total(ArrayList<Integer> arr_record_total) {
        Article_List.arr_record_total = arr_record_total;
    }

    public static ArrayList<String> getArr_id() {
        return arr_id;
    }

    public static void setArr_id(ArrayList<String> arr_id) {
        Article_List.arr_id = arr_id;
    }

    public static ArrayList<String> getArr_description() {
        return arr_description;
    }

    public static void setArr_description(ArrayList<String> arr_description) {
        Article_List.arr_description = arr_description;
    }

    public static ArrayList<String> getArr_image_url() {
        return arr_image_url;
    }

    public static void setArr_image_url(ArrayList<String> arr_image_url) {
        Article_List.arr_image_url = arr_image_url;
    }

    public static ArrayList<String> getArr_url() {
        return arr_url;
    }

    public static void setArr_url(ArrayList<String> arr_url) {
        Article_List.arr_url = arr_url;
    }

    public static ArrayList<String> getArr_title() {
        return arr_title;
    }

    public static void setArr_title(ArrayList<String> arr_title) {
        Article_List.arr_title = arr_title;
    }

    public static ArrayList<String> getArr_is_active() {
        return arr_is_active;
    }

    public static void setArr_is_active(ArrayList<String> arr_is_active) {
        Article_List.arr_is_active = arr_is_active;
    }

    public static ArrayList<String> getArr_user_id() {
        return arr_user_id;
    }

    public static void setArr_user_id(ArrayList<String> arr_user_id) {
        Article_List.arr_user_id = arr_user_id;
    }

    public static ArrayList<String> getArr_created_at() {
        return arr_created_at;
    }

    public static void setArr_created_at(ArrayList<String> arr_created_at) {
        Article_List.arr_created_at = arr_created_at;
    }

    public static ArrayList<String> getArr_user_fullname() {
        return arr_user_fullname;
    }

    public static void setArr_user_fullname(ArrayList<String> arr_user_fullname) {
        Article_List.arr_user_fullname = arr_user_fullname;
    }

    public static ArrayList<String> getArr_user_profile_pic() {
        return arr_user_profile_pic;
    }

    public static void setArr_user_profile_pic(ArrayList<String> arr_user_profile_pic) {
        Article_List.arr_user_profile_pic = arr_user_profile_pic;
    }
}
