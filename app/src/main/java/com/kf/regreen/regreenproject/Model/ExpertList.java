package com.kf.regreen.regreenproject.Model;

import java.io.Serializable;

public class ExpertList implements Serializable {

    public String user_id = "";
    public String first_name = "";
    public String last_name = "";
    public String user_name = "";
    public String email = "";
    public String e_designation = "";
    public String e_area_experts = "";
    public String phone_no = "";
    public String city = "";
    public String birth_date = "";
    public String profile_pic = "";

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setE_designation(String e_designation) {
        this.e_designation = e_designation;
    }

    public String getE_designation() {
        return e_designation;
    }

    public void setE_area_experts(String e_area_experts) {
        this.e_area_experts = e_area_experts;
    }

    public String getE_area_experts() {
        return e_area_experts;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getProfile_pic() {
        return profile_pic;
    }
}
